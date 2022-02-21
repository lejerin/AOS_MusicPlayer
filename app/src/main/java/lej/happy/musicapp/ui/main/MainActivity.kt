package lej.happy.musicapp.ui.main

import android.os.Bundle
import android.util.Log
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.happy.commons.ui.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint
import lej.happy.musicapp.R
import lej.happy.musicapp.databinding.ActivityMainBinding
import lej.happy.musicapp.ui.music.MediaPlayerManager
import lej.happy.musicapp.util.navigationHeight
import lej.happy.musicapp.util.setStatusBarTransparent
import lej.happy.musicapp.util.statusBarHeight
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>() {
    override val layoutResourceId: Int = R.layout.activity_main

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
            Log.i("eunjin", "height ${layoutParams.height}")
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

    override fun onPause() {
        super.onPause()
        mMediaPlayerManager.stop()
    }
}