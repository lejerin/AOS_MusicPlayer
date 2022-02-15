package lej.happy.musicapp.ui.player

import android.util.Log
import android.view.View
import android.widget.SeekBar
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import lej.happy.musicapp.R
import lej.happy.musicapp.databinding.FragmentPlayerBinding
import lej.happy.musicapp.ui.base.BaseFragment
import lej.happy.musicapp.ui.music.MediaPlayerManager
import lej.happy.musicapp.ui.viewmodel.MusicPlayViewModel

@AndroidEntryPoint
class PlayerFragment : BaseFragment<FragmentPlayerBinding>() {
    override val layoutResourceId = R.layout.fragment_player

    private val mMusicPlayViewModel: MusicPlayViewModel by activityViewModels()

    override fun initBinding() {
        initView()
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
        mMusicPlayViewModel.music.observe(this@PlayerFragment, {
            // 한 곡 재생
            Log.i("eunjin", "data ${it}")
            it?.let { mMusicPlayViewModel.setPlayList(mutableListOf(it)) }
        })
        mMusicPlayViewModel.musicList.observe(viewLifecycleOwner, {
            // 여러 곡 재생
            it?.let { mMusicPlayViewModel.setPlayList(it) }
        })
    }

    override fun onPause() {
        super.onPause()
        Log.i("eunjin", "pause")
        mMusicPlayViewModel.mediaPlayerManager.pause()
    }
}