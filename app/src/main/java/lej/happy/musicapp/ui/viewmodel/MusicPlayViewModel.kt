package lej.happy.musicapp.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import lej.happy.musicapp.data.ResponseData
import lej.happy.musicapp.ui.music.MediaPlayerManager
import javax.inject.Inject

@HiltViewModel
class MusicPlayViewModel @Inject constructor() : ViewModel() {

    @Inject
    lateinit var mediaPlayerManager: MediaPlayerManager

    val music: MutableLiveData<ResponseData.MusicInfo> = MutableLiveData()
    val musicList: MutableLiveData<ArrayList<ResponseData.MusicInfo>> = MutableLiveData()

    // XML 위한 LiveData

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

    fun setPlayList(playList: MutableList<ResponseData.MusicInfo>) {
        mediaPlayerManager.start(playList = playList)
    }

    fun setPlayTime(progress: Int) {
        mediaPlayerManager.setPlayTime(progress = progress)
    }

    fun setCurrentPlayTimeString(progress: Int) {
        mediaPlayerManager.setCurrentPlayTime(progress = progress)
    }

    fun addPlayList(musicInfo: ResponseData.MusicInfo) {
        mediaPlayerManager.add(musicInfo)
    }

    fun pauseMusic() {
        mediaPlayerManager.pause()
    }

    fun resumeMusic() {
        mediaPlayerManager.resume()
    }
}