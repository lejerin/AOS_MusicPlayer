package lej.happy.musicapp.ui.player

import android.util.Log
import android.view.View
import android.widget.SeekBar
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import lej.happy.musicapp.R
import lej.happy.musicapp.databinding.FragmentPlayerBinding
import lej.happy.musicapp.ui.base.BaseFragment
import lej.happy.musicapp.ui.main.MainActivity
import lej.happy.musicapp.ui.music.MediaPlayerManager
import lej.happy.musicapp.ui.viewmodel.MusicPlayViewModel
import kotlin.math.abs

@AndroidEntryPoint
class PlayerFragment : BaseFragment<FragmentPlayerBinding>() {
    override val layoutResourceId = R.layout.fragment_player

    private val mMusicPlayViewModel: MusicPlayViewModel by activityViewModels()

    override fun initBinding() {
        // binding.mlPlayer.getTransition(R.id.transition_sliding).isEnabled = false
        initView()
        initMotionLayout()
        initObserver()
    }

    private fun initView() {
        binding.vm = mMusicPlayViewModel
        binding.fragment = this@PlayerFragment
        setTimerTextAnimation()
        // window.navigationBarColor = getColor(R.color.purple_500)
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

    private fun initMotionLayout() {
        binding.mlPlayer.setTransitionListener(object :
            MotionLayout.TransitionListener {
            override fun onTransitionStarted(p0: MotionLayout?, p1: Int, p2: Int) {
            }

            override fun onTransitionChange( // 재정의를 통해 메인 엑티비티(모션 레이아웃)과 연동한다.
                motionLayout: MotionLayout?,
                startId: Int,
                endId: Int,
                progress: Float
            ) {
                (activity as? MainActivity)?.also { mainActivity ->
                    mainActivity.setMotionProgress(progress)
                }
            }

            override fun onTransitionCompleted(p0: MotionLayout?, p1: Int) {
                (activity as? MainActivity)?.also { mainActivity ->
                    if (p0?.progress ?: 0f > 0.5f) {
                        mainActivity.setMotionProgress(100f)
                    } else {
                        mainActivity.setMotionProgress(0f)
                    }
                }
            }

            override fun onTransitionTrigger(p0: MotionLayout?, p1: Int, p2: Boolean, p3: Float) {
            }

        })
    }

    private fun setTimerTextAnimation() {
        GlobalScope.launch(Dispatchers.IO) {
            delay(2000)
            withContext(Dispatchers.Main){
                binding.tvSmallSongTitle.isSelected = true
                binding.tvTitleSongPlayer.isSelected = true
            }
        }
    }

    fun onClickPlayButton(view: View) {
        changeSelectedView(binding.btnPlay)
        changeSelectedView(binding.ivSmallPlay)
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
        Log.i("eunjin", "initObserver")
        mMusicPlayViewModel.musicEvent.observe(viewLifecycleOwner, {
            when (it) {
                MediaPlayerManager.MusicEvent.START -> {
                    // UI 작업
                    Log.i("eunjin", "play")
                }
            }
        })
        mMusicPlayViewModel.music.observe(viewLifecycleOwner, {
            // 한 곡 재생
            it?.let { mMusicPlayViewModel.setPlayList(mutableListOf(it)) }
        })
        mMusicPlayViewModel.musicList.observe(viewLifecycleOwner, {
            // 여러 곡 재생
            it?.let { mMusicPlayViewModel.setPlayList(it) }
        })
    }

    override fun onPause() {
        super.onPause()
        mMusicPlayViewModel.mediaPlayerManager.pause()
    }
}