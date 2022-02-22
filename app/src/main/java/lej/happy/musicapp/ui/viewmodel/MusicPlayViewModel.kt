package lej.happy.musicapp.ui.viewmodel

import android.annotation.SuppressLint
import android.content.ServiceConnection
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import lej.happy.musicapp.data.ResponseData
import lej.happy.musicapp.service.MusicPlayService
import lej.happy.musicapp.ui.music.MediaPlayerManager
import lej.happy.musicapp.ui.music.MusicListManager
import javax.inject.Inject

@HiltViewModel
class MusicPlayViewModel @Inject constructor() : ViewModel() {

    // XML 위한 LiveData
    val musicEvent
        get() = MediaPlayerManager.musicEvent

    val playCurrentTimeString
        get() = MediaPlayerManager.currentTimeString

    val playDurationTimeString
        get() = MediaPlayerManager.durationTimeString

    val playProgress
        get() = MediaPlayerManager.currentProgress

    val currentPlayInfo
        get() = MusicListManager.currentMusicInfo
}