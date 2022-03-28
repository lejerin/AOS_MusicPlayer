package lej.happy.musicapp.ui.main

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import androidx.activity.viewModels
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.happy.commons.ui.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint
import lej.happy.musicapp.R
import lej.happy.musicapp.data.mMusicPlayService
import lej.happy.musicapp.data.mMusicPlayServiceConnection
import lej.happy.musicapp.databinding.ActivityMainBinding
import lej.happy.musicapp.service.MusicPlayService
import lej.happy.musicapp.ui.music.MediaPlayerManager
import lej.happy.musicapp.ui.player.PlayerFragment
import lej.happy.musicapp.ui.viewmodel.MusicPlayViewModel
import lej.happy.musicapp.util.navigationHeight
import lej.happy.musicapp.util.setStatusBarTransparent
import lej.happy.musicapp.util.statusBarHeight
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>() {
    override val layoutResourceId: Int = R.layout.activity_main

    override fun initData() {
        bindMusicPlayService()
    }

    override fun initUi() {
        initNavigation()
        initTransparentSystemBar()
    }

    private fun initTransparentSystemBar() {
        this.setStatusBarTransparent()
        binding.viewStatusBar.apply {
            layoutParams = layoutParams.apply {
                height = this@MainActivity.statusBarHeight()
            }
        }
        binding.viewMargin.apply {
            layoutParams = layoutParams.apply {
                height = this@MainActivity.navigationHeight()
            }
        }
    }

    private fun initNavigation() {
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fcv_main) as NavHostFragment
        val navController = navHostFragment.navController
        binding.bnvMain.setupWithNavController(navController)
    }


    fun setMotionProgress(progress: Float) {
        binding.mlMain.progress = progress
    }

    fun getNavigationHeight() : Int {
        return binding.bnvMain.layoutParams.height
    }

    private fun bindMusicPlayService() {
        if (mMusicPlayServiceConnection == null) {
            mMusicPlayServiceConnection = object : ServiceConnection {
                override fun onServiceConnected(className: ComponentName, service: IBinder) {
                    val binder = service as MusicPlayService.LocalBinder
                    mMusicPlayService = binder.getService()
                }
                override fun onServiceDisconnected(arg0: ComponentName) {
                    mMusicPlayService = null
                    mMusicPlayServiceConnection = null
                }
            }
        }
        Intent(this, MusicPlayService::class.java).also { intent ->
            bindService(intent, mMusicPlayServiceConnection!!, Context.BIND_AUTO_CREATE)
        }
    }

    override fun onBackPressed() {
        // PlayerFragment 펼쳐져 있을 경우
        if (binding.mlMain.progress >= 1f) {
            binding.fcvPlayer.getFragment<PlayerFragment>().let { pf ->
                if (pf.isListOpened) {
                    pf.binding.mlPlayer.transitionToState(R.id.unfloating)
                } else {
                    pf.binding.mlPlayer.transitionToState(R.id.floating)
                }
            }
        } else {
            super.onBackPressed()
        }
    }
}