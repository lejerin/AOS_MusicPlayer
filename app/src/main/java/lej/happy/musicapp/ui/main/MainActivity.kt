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
import lej.happy.musicapp.databinding.ActivityMainBinding
import lej.happy.musicapp.service.MusicPlayService
import lej.happy.musicapp.ui.music.MediaPlayerManager
import lej.happy.musicapp.ui.viewmodel.MusicPlayViewModel
import lej.happy.musicapp.util.navigationHeight
import lej.happy.musicapp.util.setStatusBarTransparent
import lej.happy.musicapp.util.statusBarHeight
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>() {
    override val layoutResourceId: Int = R.layout.activity_main

    private val mMusicPlayViewModel: MusicPlayViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.lifecycleOwner = this@MainActivity

        initNavigation()
        initTransparentSystemBar()
        bindMusicPlayService()
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
        if (mMusicPlayViewModel.mMusicPlayServiceConnection == null) {
            mMusicPlayViewModel.mMusicPlayServiceConnection = object : ServiceConnection {
                override fun onServiceConnected(className: ComponentName, service: IBinder) {
                    val binder = service as MusicPlayService.LocalBinder
                    mMusicPlayViewModel.mMusicPlayService = binder.getService()
                }
                override fun onServiceDisconnected(arg0: ComponentName) {

                }
            }
        }
        Intent(this, MusicPlayService::class.java).also { intent ->
            bindService(intent, mMusicPlayViewModel.mMusicPlayServiceConnection!!, Context.BIND_AUTO_CREATE)
        }
    }

    override fun onPause() {
        super.onPause()
    }
}