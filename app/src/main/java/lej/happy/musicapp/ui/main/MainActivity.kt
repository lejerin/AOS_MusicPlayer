package lej.happy.musicapp.ui.main

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import dagger.hilt.android.AndroidEntryPoint
import lej.happy.musicapp.R
import lej.happy.musicapp.databinding.ActivityMainBinding
import lej.happy.musicapp.ui.base.BaseActivity
import lej.happy.musicapp.ui.music.MediaPlayerManager
import lej.happy.musicapp.ui.player.PlayerFragment
import lej.happy.musicapp.util.navigationHeight
import lej.happy.musicapp.util.setStatusBarTransparent
import lej.happy.musicapp.util.statusBarHeight
import javax.inject.Inject
import kotlin.math.abs

@AndroidEntryPoint
class MainActivity : BaseActivity() {

    private val binding: ActivityMainBinding by binding(R.layout.activity_main)

    @Inject
    lateinit var mMediaPlayerManager: MediaPlayerManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.lifecycleOwner = this@MainActivity

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
    }

    private fun initNavigation() {
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fcv_main) as NavHostFragment
        val navController = navHostFragment.navController
        binding.bnvMain.setupWithNavController(navController)
    }


    fun setMotionProgress(progress: Float) {
        binding.mlMain.progress = progress
    }

    override fun onPause() {
        super.onPause()
        mMediaPlayerManager.stop()
    }
}