package lej.happy.musicapp.ui.player

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import dagger.hilt.android.AndroidEntryPoint
import lej.happy.musicapp.R
import lej.happy.musicapp.data.ResponseData
import lej.happy.musicapp.databinding.ActivityPlayerBinding
import lej.happy.musicapp.ui.base.BaseActivity
import lej.happy.musicapp.ui.music.MediaPlayerManager
import lej.happy.musicapp.ui.viewmodel.MusicPlayViewModel

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
            binding.vm = mMusicPlayViewModel
        }
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