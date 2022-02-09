package lej.happy.musicapp.ui.music

import android.media.AudioManager
import android.media.MediaPlayer
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import lej.happy.musicapp.data.ResponseData
import lej.happy.musicapp.data.remote.NetworkResult
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MediaPlayerManager @Inject constructor() : MusicListManager(), IMediaPlayerManager {

    enum class MusicEvent {
        START
    }

    private val _musicEvent: MutableLiveData<MusicEvent> = MutableLiveData()
    val musicEvent: LiveData<MusicEvent> = _musicEvent

    private val mediaPlayer by lazy {
        MediaPlayer().apply {
            setAudioStreamType(AudioManager.STREAM_MUSIC)
            setOnPreparedListener(MediaPlayer.OnPreparedListener { mp ->
                mp.start()
                _musicEvent.postValue(MusicEvent.START)
            })
        }
    }

    override fun start(playList: MutableList<ResponseData.MusicInfo>) {
        mediaPlayer.reset()
        setPlayList(playList)
        currentMusicInfo?.mck?.let {
            mediaPlayer.setDataSource("https://happyweatherapp.herokuapp.com/music/play?mck=${it}")
            mediaPlayer.prepareAsync()
        }
    }

    override fun pause() {
        mediaPlayer.pause()
    }

    override fun stop() {
        mediaPlayer.stop()
    }

}