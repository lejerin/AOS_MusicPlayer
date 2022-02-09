package lej.happy.musicapp.ui.player

import android.os.Bundle
import android.util.Log
import android.view.ViewGroup
import dagger.hilt.android.AndroidEntryPoint
import lej.happy.musicapp.R
import lej.happy.musicapp.data.ResponseData
import lej.happy.musicapp.databinding.ActivityPlayerBinding
import lej.happy.musicapp.ui.base.BaseActivity

@AndroidEntryPoint
class PlayerActivity : BaseActivity() {

    private val binding: ActivityPlayerBinding by binding(R.layout.activity_player)

    private val musicInfo: ResponseData.MusicInfo? by lazy { intent.getSerializableExtra("music") as? ResponseData.MusicInfo }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.lifecycleOwner = this@PlayerActivity

        binding.musicInfo = musicInfo
        initView()
    }

    private fun initView() {
//        val imgWidth = binding.imgAlbumCover.width
//        val param = binding.clDefault.layoutParams as ViewGroup.MarginLayoutParams
//        param.setMargins(0, imgWidth + 30,0,0)
//        param.width = imgWidth
//        binding.clDefault.layoutParams = param

    }

}