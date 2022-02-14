package lej.happy.musicapp.ui.music

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import lej.happy.musicapp.data.ResponseData

open class MusicListManager : IMusicListManger {

    private val musicInfoList = mutableListOf<ResponseData.MusicInfo>()

    private var currentPlayIndex : Int? = null
        set(value) {
            value?.let {
                _currentMusicInfo.value = musicInfoList[it]
            }
            field = value
        }

    private val _currentMusicInfo: MutableLiveData<ResponseData.MusicInfo> = MutableLiveData()
    val currentMusicInfo: LiveData<ResponseData.MusicInfo> = _currentMusicInfo



    override fun setPlayList(newList: MutableList<ResponseData.MusicInfo>) {
        musicInfoList.clear()
        musicInfoList.addAll(newList)
        currentPlayIndex = if (musicInfoList.isNotEmpty()) {
            0
        } else {
            null
        }
    }

    override fun getPlayList(): List<ResponseData.MusicInfo>? {
        return if (musicInfoList.isNotEmpty()) musicInfoList.toList() else null
    }

    override fun add(musicInfo: ResponseData.MusicInfo) {
        musicInfoList.add(musicInfo)
    }

    override fun add(addList: MutableList<ResponseData.MusicInfo>) {

    }

    override fun remove(musicInfo: ResponseData.MusicInfo) {

    }

    override fun remove(addList: MutableList<ResponseData.MusicInfo>) {

    }

    override fun removeAll() {

    }
}
