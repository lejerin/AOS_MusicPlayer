package lej.happy.musicapp.ui.player

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.SeekBar
import androidx.activity.viewModels
import dagger.hilt.android.AndroidEntryPoint
import lej.happy.musicapp.R
import lej.happy.musicapp.data.ResponseData
import lej.happy.musicapp.databinding.ActivityPlayerBinding
import lej.happy.musicapp.ui.base.BaseActivity
import lej.happy.musicapp.ui.music.MediaPlayerManager
import lej.happy.musicapp.ui.viewmodel.MusicPlayViewModel
import lej.happy.musicapp.util.TimeUtils

@AndroidEntryPoint
class PlayerActivity : BaseActivity() {

    private val binding: ActivityPlayerBinding by binding(R.layout.activity_player)

    private val musicInfo: ResponseData.MusicInfo? by lazy { intent.getSerializableExtra("music") as? ResponseData.MusicInfo }

    private val mMusicPlayViewModel: MusicPlayViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.lifecycleOwner = this@PlayerActivity
        initView()
        initObserver()
    }

    private fun initView() {
        musicInfo?.let {
            mMusicPlayViewModel.setPlayList(mutableListOf(it))
        }
        binding.vm = mMusicPlayViewModel
        binding.activity = this@PlayerActivity
        binding.appCompatSeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(p0: SeekBar?, progress: Int, fromUser: Boolean) {
                if (fromUser) {
                    mMusicPlayViewModel.setCurrentPlayTimeString(progress)
                }
            }
            override fun onStartTrackingTouch(p0: SeekBar?) {
                mMusicPlayViewModel.mediaPlayerManager.changingSeekBarProgress = true
            }
            override fun onStopTrackingTouch(p0: SeekBar?) {
                p0?.progress?.let { mMusicPlayViewModel.setPlayTime(it) }
                mMusicPlayViewModel.mediaPlayerManager.changingSeekBarProgress = false
            }
        })
    }

    fun onClickPlayButton(view: View) {
        changeSelectedView(view)
        if (view.isSelected) {
            mMusicPlayViewModel.pauseMusic()
        } else {
            mMusicPlayViewModel.resumeMusic()
        }
    }

    fun onClickUnlikeButton(view: View) {
        changeSelectedView(view)
    }

    fun onClickLikeButton(view: View) {
        changeSelectedView(view)
    }

    fun onClickLoopButton(view: View) {
        changeSelectedView(view)
    }

    fun onClickRandomButton(view: View) {
        changeSelectedView(view)
    }

    private fun changeSelectedView(view: View) {
        view.isSelected = !view.isSelected
    }

    private fun initObserver() {
        mMusicPlayViewModel.musicEvent.observe(this@PlayerActivity, {
            when (it) {
                MediaPlayerManager.MusicEvent.START -> {
                    // UI 작업
                    Log.i("eunjin", "play")
                }
            }
        })
    }

    override fun onPause() {
        super.onPause()
        mMusicPlayViewModel.mediaPlayerManager.pause()
    }

}