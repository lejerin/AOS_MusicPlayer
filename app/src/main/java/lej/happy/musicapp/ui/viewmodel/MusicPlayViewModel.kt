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

    val playList
    get() = mediaPlayerManager.getPlayList()

    fun setPlayList(playList: ArrayList<ResponseData.MusicInfo>) {
        val list = mutableListOf<ResponseData.MusicInfo>()
        list.addAll(playList)
        mediaPlayerManager.start(playList = list)
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