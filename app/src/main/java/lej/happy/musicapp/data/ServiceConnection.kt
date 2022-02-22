package lej.happy.musicapp.data

import android.annotation.SuppressLint
import android.content.ServiceConnection
import lej.happy.musicapp.service.MusicPlayService

/** Service */
var mMusicPlayService: MusicPlayService? = null
var mMusicPlayServiceConnection: ServiceConnection? = null