package lej.happy.musicapp.service

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import lej.happy.musicapp.data.ResponseData
import lej.happy.musicapp.ui.music.MediaPlayerManager

class MusicPlayService : Service() {

    private val binder = LocalBinder()
    inner class LocalBinder : Binder() {
        fun getService(): MusicPlayService = this@MusicPlayService
    }

    override fun onBind(intent: Intent?): IBinder = binder

    var mediaPlayerManager = MediaPlayerManager()

    override fun onCreate() {
        super.onCreate()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onDestroy() {
        super.onDestroy()
    }

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

    fun getPlayList() = mediaPlayerManager.getPlayList()

    fun pauseMusic() {
        mediaPlayerManager.pause()
    }

    fun resumeMusic() {
        mediaPlayerManager.resume()
    }
}