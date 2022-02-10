package lej.happy.musicapp.ui.viewmodel

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

    val playProgression
    get() = mediaPlayerManager.currentProgress

    val currentPlayInfo
    get() = mediaPlayerManager.currentMusicInfo

    fun setPlayList(setList: MutableList<ResponseData.MusicInfo>) {
        mediaPlayerManager.start(setList)
    }
}