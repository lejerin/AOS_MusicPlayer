package lej.happy.musicapp.ui.music

import android.media.AudioManager
import android.media.MediaPlayer
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
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

    private val _currentProgress: MutableLiveData<Int> = MutableLiveData()
    val currentProgress: LiveData<Int> = _currentProgress

    private val mediaPlayer by lazy {
        MediaPlayer().apply {
            setAudioStreamType(AudioManager.STREAM_MUSIC)
            setOnPreparedListener(MediaPlayer.OnPreparedListener { mp ->
                mp.start()
                setProgressChangedListener()
                _musicEvent.postValue(MusicEvent.START)
            })
        }
    }

    private val job = CoroutineScope(Dispatchers.IO).launch {
        while (mediaPlayer.isPlaying) {
            val progress = (mediaPlayer.currentPosition / mediaPlayer.duration.toFloat()).toInt()
            _currentProgress.postValue(progress)
        }
    }

    private fun setProgressChangedListener() {
        if (!job.isActive || job.isCompleted) {
            job.start()
        }
    }

    override fun start(playList: MutableList<ResponseData.MusicInfo>) {
        mediaPlayer.reset()
        setPlayList(playList)
        currentMusicInfo.value?.mck?.let {
            mediaPlayer.setDataSource("https://happyweatherapp.herokuapp.com/music/play?mck=${it}")
            mediaPlayer.prepareAsync()
        }
    }

    override fun pause() {
        mediaPlayer.pause()
        job.cancel()
    }

    override fun resume() {
        mediaPlayer.start()
        setProgressChangedListener()
        _musicEvent.postValue(MusicEvent.START)
    }

    override fun stop() {
        mediaPlayer.stop()
        job.cancel()
    }

}