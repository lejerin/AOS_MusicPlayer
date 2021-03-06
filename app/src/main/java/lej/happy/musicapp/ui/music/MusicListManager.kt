package lej.happy.musicapp.ui.music

import androidx.lifecycle.MutableLiveData
import com.happy.commons.data.ListLiveData
import lej.happy.musicapp.data.ResponseData

open class MusicListManager : IMusicListManger {

    companion object {
        val currentMusicInfo: MutableLiveData<ResponseData.MusicInfo> = MutableLiveData()
        val musicInfoList =  ListLiveData<ResponseData.MusicInfo>()
    }

    private var currentPlayIndex : Int? = null
        set(value) {
            value?.let {
                currentMusicInfo.value = musicInfoList.value?.get(it)
            }
            field = value
        }



    override fun setPlayList(newList: MutableList<ResponseData.MusicInfo>) {
        musicInfoList.clearAndAddAll(newList)
        currentPlayIndex = if (musicInfoList.value?.isNotEmpty() == true) {
            0
        } else {
            null
        }
    }

    override fun play(requestMusic: ResponseData.MusicInfo) {
        musicInfoList.value?.indexOf(requestMusic)?.let { findIndex ->
            currentPlayIndex = findIndex
        }
    }

    override fun playPrev() {
       if (currentPlayIndex ?: 0 > 0) {
           currentPlayIndex = currentPlayIndex?.minus(1)
       }
    }

    override fun playNext() {
        if (currentPlayIndex ?: 0 < musicInfoList.value?.size?.minus(1) ?: 0) {
            currentPlayIndex = currentPlayIndex?.plus(1)
        }
    }

    override fun getPlayList(): List<ResponseData.MusicInfo>? {
        return if (musicInfoList.value?.isNotEmpty() == true) musicInfoList.value?.toList() else null
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
