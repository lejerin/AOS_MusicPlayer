package lej.happy.musicapp.service

import android.content.Intent
import android.os.Binder
import android.os.IBinder
import androidx.lifecycle.LifecycleService
import lej.happy.musicapp.data.ResponseData
import lej.happy.musicapp.ui.music.MediaPlayerManager
import lej.happy.musicapp.ui.music.MusicListManager

class MusicPlayService : LifecycleService() {

    private val binder = LocalBinder()
    inner class LocalBinder : Binder() {
        fun getService(): MusicPlayService = this@MusicPlayService
    }

    override fun onBind(intent: Intent): IBinder {
        super.onBind(intent)
        return binder
    }

    var mediaPlayerManager = MediaPlayerManager()

    override fun onCreate() {
        super.onCreate()
        initObserver()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    private fun initObserver() {
        MusicListManager.currentMusicInfo.observe(this@MusicPlayService, {
            mediaPlayerManager.play(mck = it.mck)
        })
    }

    fun setPlayList(playList: MutableList<ResponseData.MusicInfo>) {
        mediaPlayerManager.start(playList = playList)
    }

    fun movePlay(requestPlay: ResponseData.MusicInfo) {
        mediaPlayerManager.movePlay(requestMusic = requestPlay)
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