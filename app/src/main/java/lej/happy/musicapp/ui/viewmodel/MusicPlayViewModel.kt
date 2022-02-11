package lej.happy.musicapp.ui.viewmodel

import android.util.Log
import android.widget.SeekBar
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import lej.happy.musicapp.data.ResponseData
import lej.happy.musicapp.ui.music.MediaPlayerManager
import javax.inject.Inject

@HiltViewModel
class MusicPlayViewModel @Inject constructor() : ViewModel() {

    @Inject
    lateinit var mediaPlayerManager: MediaPlayerManager

    val musicEvent
    get() = mediaPlayerManager.musicEvent

    val playCurrentTimeString
        get() = mediaPlayerManager.currentTimeString

    val playDurationTimeString
        get() = mediaPlayerManager.durationTimeString

    val playProgress
    get() = mediaPlayerManager.currentProgress

    val currentPlayInfo
    get() = mediaPlayerManager.currentMusicInfo

    fun setPlayList(playList: MutableList<ResponseData.MusicInfo>) {
        mediaPlayerManager.start(playList = playList)
    }

    fun setPlayTime(progress: Int) {
        mediaPlayerManager.setPlayTime(progress = progress)
    }

    fun setCurrentPlayTimeString(progress: Int) {
        mediaPlayerManager.setCurrentPlayTime(progress = progress)
    }

    fun pauseMusic() {
        mediaPlayerManager.pause()
    }

    fun resumeMusic() {
        mediaPlayerManager.resume()
    }
}