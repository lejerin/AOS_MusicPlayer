package lej.happy.musicapp.ui.player

import android.os.Build
import android.util.Log
import android.view.View
import android.widget.SeekBar
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.fragment.app.activityViewModels
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import lej.happy.musicapp.R
import lej.happy.musicapp.databinding.FragmentPlayerBinding
import lej.happy.musicapp.ui.main.MainActivity
import lej.happy.musicapp.ui.music.MediaPlayerManager
import lej.happy.musicapp.ui.viewmodel.MusicPlayViewModel
import lej.happy.musicapp.util.navigationHeight
import androidx.constraintlayout.widget.ConstraintSet
import com.happy.commons.ui.base.BaseFragment

@AndroidEntryPoint
class PlayerFragment : BaseFragment<FragmentPlayerBinding>() {
    override val layoutResourceId = R.layout.fragment_player

    private val mMusicPlayViewModel: MusicPlayViewModel by activityViewModels()

    override fun initUi() {
        initView()
        initMotionLayout()
        binding.btnDummy.setOnClickListener { }
    }

    override fun setupObservers() {
        initObserver()
    }

    override fun initData() {

    }

    private fun initView() {
        binding.vm = mMusicPlayViewModel
        binding.fragment = this@PlayerFragment
        setTimerTextAnimation()
        binding.seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(p0: SeekBar?, progress: Int, fromUser: Boolean) {
                if (fromUser) {
                    mMusicPlayViewModel.mMusicPlayService?.setCurrentPlayTimeString(progress)
                }
            }
            override fun onStartTrackingTouch(p0: SeekBar?) {
                mMusicPlayViewModel.mMusicPlayService?.mediaPlayerManager?.changingSeekBarProgress = true
            }
            override fun onStopTrackingTouch(p0: SeekBar?) {
                p0?.progress?.let { mMusicPlayViewModel.mMusicPlayService?.setPlayTime(it) }
                mMusicPlayViewModel.mMusicPlayService?.mediaPlayerManager?.changingSeekBarProgress = false
            }
        })
    }


    private fun initMotionLayout() {
        binding.mlPlayer.getConstraintSet(R.id.floating)?.setMargin(R.id.cl_small_view, ConstraintSet.BOTTOM, requireContext().navigationHeight() + (activity as MainActivity).getNavigationHeight())
        binding.mlPlayer.setTransitionListener(object : MotionLayout.TransitionListener {
            override fun onTransitionStarted(
                motionLayout: MotionLayout?,
                startId: Int,
                endId: Int
            ) {

            }

            override fun onTransitionChange(
                motionLayout: MotionLayout?,
                startId: Int,
                endId: Int,
                progress: Float
            ) {
                when {
                    (startId == R.id.floating && endId == R.id.unfloating) or
                            (endId == R.id.floating && startId == R.id.unfloating) -> {
                        (activity as? MainActivity)?.also { mainActivity ->
                            mainActivity.setMotionProgress(progress)
                        }
                    }
                    else -> {

                    }
                }
            }

            override fun onTransitionCompleted(motionLayout: MotionLayout?, currentId: Int) {
                when(currentId) {
                    R.id.floating -> {
                        (activity as? MainActivity)?.also { mainActivity ->
                            mainActivity.setMotionProgress(0f)
                        }
                    }
                    R.id.unfloating -> {
                        (activity as? MainActivity)?.also { mainActivity ->
                            mainActivity.setMotionProgress(1f)
                        }
                    }
                }
            }

            override fun onTransitionTrigger(
                motionLayout: MotionLayout?,
                triggerId: Int,
                positive: Boolean,
                progress: Float
            ) {

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

    fun onClickNextButton(view: View) {
        Log.i("eunjin", "setOnClickListener onClickNextButton")
    }

    fun onClickPlayButton(view: View) {
        Log.i("eunjin", "setOnClickListener onClickPlayButton")
        changeSelectedView(binding.btnPlay)
        changeSelectedView(binding.ivSmallPlay)
        if (view.isSelected) {
            mMusicPlayViewModel.mMusicPlayService?.pauseMusic()
        } else {
            mMusicPlayViewModel.mMusicPlayService?.resumeMusic()
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
            it?.let { mMusicPlayViewModel.mMusicPlayService?.setPlayList(mutableListOf(it)) }
        })
        mMusicPlayViewModel.musicList.observe(viewLifecycleOwner, {
            // 여러 곡 재생
            it?.let { mMusicPlayViewModel.mMusicPlayService?.setPlayList(it) }
        })
    }

    override fun onPause() {
        super.onPause()
        mMusicPlayViewModel.mMusicPlayService?.mediaPlayerManager?.pause()
    }
}