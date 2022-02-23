package lej.happy.musicapp.ui.player

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
import lej.happy.musicapp.data.mMusicPlayService

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
                    mMusicPlayService?.mediaPlayerManager?.setCurrentPlayTime(progress)
                }
            }
            override fun onStartTrackingTouch(p0: SeekBar?) {
                mMusicPlayService?.mediaPlayerManager?.changingSeekBarProgress = true
            }
            override fun onStopTrackingTouch(p0: SeekBar?) {
                p0?.progress?.let { mMusicPlayService?.mediaPlayerManager?.setPlayTime(it) }
                mMusicPlayService?.mediaPlayerManager?.changingSeekBarProgress = false
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

    fun onClickPrevButton(view: View) {
        mMusicPlayService?.mediaPlayerManager?.playPrev()
    }

    fun onClickNextButton(view: View) {
        mMusicPlayService?.mediaPlayerManager?.playNext()
    }

    fun onClickPlayButton(view: View) {
        changePlayButtonSelectState(!view.isSelected)
        if (view.isSelected) {
            mMusicPlayService?.mediaPlayerManager?.pause()
        } else {
            mMusicPlayService?.mediaPlayerManager?.resume()
        }
    }

    fun onClickUnlikeButton(view: View) {
        toggleSelectedView(view)
    }

    fun onClickLikeButton(view: View) {
        toggleSelectedView(view)
    }

    fun onClickLoopButton(view: View) {
        toggleSelectedView(view)
    }

    fun onClickRandomButton(view: View) {
        toggleSelectedView(view)
    }

    private fun toggleSelectedView(view: View) {
        view.isSelected = !view.isSelected
    }

    private fun changePlayButtonSelectState(isSelected: Boolean) {
        if (binding.btnPlay.isSelected != isSelected) {
            binding.btnPlay.isSelected = isSelected
            binding.btnPlay.isSelected = isSelected
        }
    }

    private fun initObserver() {
        mMusicPlayViewModel.musicEvent.observe(viewLifecycleOwner, {
            when (it) {
                MediaPlayerManager.MusicEvent.PLAY,
                MediaPlayerManager.MusicEvent.RESUME
                -> {
                    changePlayButtonSelectState(false)
                }
                MediaPlayerManager.MusicEvent.PAUSE,
                MediaPlayerManager.MusicEvent.STOP -> {
                    changePlayButtonSelectState(true)
                }
                else -> {

                }
            }
        })
    }

    override fun onPause() {
        super.onPause()
        mMusicPlayService?.mediaPlayerManager?.pause()
    }
}