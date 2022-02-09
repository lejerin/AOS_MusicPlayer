package lej.happy.musicapp.ui.player

import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import lej.happy.musicapp.R
import lej.happy.musicapp.data.ResponseData
import lej.happy.musicapp.databinding.ActivityPlayerBinding
import lej.happy.musicapp.ui.base.BaseActivity
import lej.happy.musicapp.ui.music.MediaPlayerManager
import lej.happy.musicapp.ui.viewmodel.MusicInfoViewModel
import lej.happy.musicapp.ui.viewmodel.MusicPlayViewModel
import javax.inject.Inject

@AndroidEntryPoint
class PlayerActivity : BaseActivity() {

    private val binding: ActivityPlayerBinding by binding(R.layout.activity_player)

    private val musicInfo: ResponseData.MusicInfo? by lazy { intent.getSerializableExtra("music") as? ResponseData.MusicInfo }

    private val mMusicPlayViewModel: MusicPlayViewModel by viewModels()

    @Inject
    lateinit var mMediaPlayerManager: MediaPlayerManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.lifecycleOwner = this@PlayerActivity
        initView()
        initObserver()
    }

    private fun initView() {
        musicInfo?.let {
            binding.musicInfo = musicInfo
            mMusicPlayViewModel.setPlayList(mutableListOf(it))
        }
    }

    private fun initObserver() {
        mMediaPlayerManager.musicEvent.observe(this@PlayerActivity, {
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
        mMediaPlayerManager.pause()
    }

}