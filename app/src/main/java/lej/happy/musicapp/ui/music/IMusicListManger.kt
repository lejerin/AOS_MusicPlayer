package lej.happy.musicapp.ui.music

import lej.happy.musicapp.data.ResponseData

interface IMusicListManger {

    fun setPlayList(newList: MutableList<ResponseData.MusicInfo>)
    fun play(requestMusic: ResponseData.MusicInfo)
    fun playNext()
    fun playPrev()
    fun getPlayList() : List<ResponseData.MusicInfo>?
    fun add(musicInfo: ResponseData.MusicInfo)
    fun add(addList: MutableList<ResponseData.MusicInfo>)
    fun remove(musicInfo: ResponseData.MusicInfo)
    fun remove(addList: MutableList<ResponseData.MusicInfo>)
    fun removeAll()
}