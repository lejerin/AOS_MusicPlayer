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
}