package lej.happy.musicapp.ui.music

import android.media.AudioManager
import android.media.MediaPlayer
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.happy.commons.data.SingleLiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import lej.happy.musicapp.data.ResponseData
import lej.happy.musicapp.util.TimeUtils

class MediaPlayerManager : MusicListManager(), IMediaPlayerManager {

    companion object {
        val musicEvent: SingleLiveData<MusicEvent> = SingleLiveData()

        /** 음악 변경될 때마다 이벤트 전달 */
        private val _duration: MutableLiveData<Int?> = MutableLiveData()
        val durationTimeString: LiveData<String> = Transformations.map(_duration) {
            TimeUtils.getDurationString(it)
        }

        /** 값 변경이 있을 때만 이벤트 전달 */
        private val _currentPosition: MutableLiveData<Int?> = MutableLiveData()
        val currentTimeString: LiveData<String> = Transformations.map(_currentPosition) {
            TimeUtils.getDurationString(it)
        }

        private val _currentProgress: MutableLiveData<Int> = MutableLiveData(0)
        val currentProgress: LiveData<Int> = Transformations.distinctUntilChanged(_currentProgress)
    }

    enum class MusicEvent {
        PLAY,
        RESUME,
        PAUSE,
        STOP
    }

    /** 값 변경이 있을 때만 이벤트 전달 */
    var changingSeekBarProgress = false

    private val mediaPlayer by lazy {
        MediaPlayer().apply {
            setAudioStreamType(AudioManager.STREAM_MUSIC)
            setOnPreparedListener(MediaPlayer.OnPreparedListener { mp ->
                mp.start()
                _duration.postValue(mp.duration)
                _currentPosition.postValue(0)
                _currentProgress.postValue(0)
                musicEvent.postValue(MusicEvent.PLAY)
            })
        }
    }

    private val job = CoroutineScope(Dispatchers.IO).launch {
        while (true) {
            if (mediaPlayer.isPlaying && !changingSeekBarProgress) {
                val progress = (mediaPlayer.currentPosition.toFloat() / mediaPlayer.duration * 100).toInt()
                _currentProgress.postValue(progress)
                _currentPosition.postValue(mediaPlayer.currentPosition)
            }
            Thread.sleep(1000)
        }
    }

    init {
        job.start()
    }

    private fun initLiveData() {
        _currentProgress.value = 0
        _currentPosition.value = null
        _duration.value = null
    }

    override fun start(playList: MutableList<ResponseData.MusicInfo>) {
        setPlayList(playList)
    }

    override fun play(mck: Int) {
        mediaPlayer.reset()
        initLiveData()
        mediaPlayer.setDataSource("https://happyweatherapp.herokuapp.com/music/play?mck=$mck")
        mediaPlayer.prepareAsync()
    }

    override fun setPlayTime(progress: Int) {
        mediaPlayer.seekTo(((mediaPlayer.duration / 100.0f) * progress).toInt())
    }

    override fun setCurrentPlayTime(progress: Int) {
        _currentPosition.postValue(((mediaPlayer.duration / 100.0f) * progress).toInt())
    }

    override fun pause() {
        mediaPlayer.pause()
        musicEvent.postValue(MusicEvent.PAUSE)
    }

    override fun resume() {
        mediaPlayer.start()
        musicEvent.postValue(MusicEvent.RESUME)
    }

    override fun stop() {
        mediaPlayer.stop()
        musicEvent.postValue(MusicEvent.STOP)
    }
}