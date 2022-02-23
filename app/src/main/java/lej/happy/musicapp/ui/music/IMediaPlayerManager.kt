package lej.happy.musicapp.ui.music

import lej.happy.musicapp.data.ResponseData

interface IMediaPlayerManager {
    fun start(playList: MutableList<ResponseData.MusicInfo>)
    fun play(mck: Int)
    fun stop()
    fun pause()
    fun resume()
    fun setPlayTime(progress: Int)
    fun setCurrentPlayTime(progress: Int)
}