package lej.happy.musicapp.ui.music

import lej.happy.musicapp.data.ResponseData

open class MusicListManager : IMusicListManger {

    private val musicInfoList = mutableListOf<ResponseData.MusicInfo>()

    private var currentPlayIndex : Int? = null

    val currentMusicInfo: ResponseData.MusicInfo?
    get() = if (currentPlayIndex != null && musicInfoList.isNotEmpty()) {
        musicInfoList[currentPlayIndex!!]}
    else {
        null
    }
        override fun setPlayList(newList: MutableList<ResponseData.MusicInfo>) {
            musicInfoList.clear()
            musicInfoList.addAll(newList)
            currentPlayIndex = if (musicInfoList.isNotEmpty()) {
                0
            } else {
                null
            }
        }

        override fun add(musicInfo: ResponseData.MusicInfo) {

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
