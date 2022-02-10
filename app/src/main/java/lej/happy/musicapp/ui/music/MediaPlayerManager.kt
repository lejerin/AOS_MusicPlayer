package lej.happy.musicapp.ui.music

import android.media.AudioManager
import android.media.MediaPlayer
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import lej.happy.musicapp.data.ResponseData
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MediaPlayerManager @Inject constructor() : MusicListManager(), IMediaPlayerManager {

    enum class MusicEvent {
        START,
        PAUSE,
        STOP
    }

    private val _musicEvent: MutableLiveData<MusicEvent> = MutableLiveData()
    val musicEvent: LiveData<MusicEvent> = _musicEvent

    var changingSeekBarProgress = false

    /** 값 변경이 있을 때만 이벤트 전달 */
    private val _currentProgress: MutableLiveData<Int> = MutableLiveData()
    val currentProgress: LiveData<Int> = Transformations.distinctUntilChanged(_currentProgress)

    private val mediaPlayer by lazy {
        MediaPlayer().apply {
            setAudioStreamType(AudioManager.STREAM_MUSIC)
            setOnPreparedListener(MediaPlayer.OnPreparedListener { mp ->
                mp.start()
                _musicEvent.postValue(MusicEvent.START)
            })
        }
    }

    private val job = CoroutineScope(Dispatchers.IO).launch {
        while (true) {
            if (mediaPlayer.isPlaying && !changingSeekBarProgress) {
                val progress = (mediaPlayer.currentPosition.toFloat() / mediaPlayer.duration * 100).toInt()
                _currentProgress.postValue(progress)
            }
            Thread.sleep(1000)
        }
    }

    init {
        job.start()
    }

    override fun start(playList: MutableList<ResponseData.MusicInfo>) {
        mediaPlayer.reset()
        setPlayList(playList)
        currentMusicInfo.value?.mck?.let {
            mediaPlayer.setDataSource("https://happyweatherapp.herokuapp.com/music/play?mck=${it}")
            mediaPlayer.prepareAsync()
        }
    }

    override fun setPlayTime(progress: Int) {
        mediaPlayer.seekTo(((mediaPlayer.duration / 100.0f) * progress).toInt())
    }

    override fun pause() {
        mediaPlayer.pause()
        _musicEvent.postValue(MusicEvent.PAUSE)
    }

    override fun resume() {
        mediaPlayer.start()
        _musicEvent.postValue(MusicEvent.START)
    }

    override fun stop() {
        mediaPlayer.stop()
        _musicEvent.postValue(MusicEvent.STOP)
    }

}