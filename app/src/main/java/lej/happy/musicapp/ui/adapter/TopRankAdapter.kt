package lej.happy.musicapp.ui.adapter

import com.happy.commons.ui.base.BaseListAdapter
import lej.happy.musicapp.R
import lej.happy.musicapp.data.ResponseData
import lej.happy.musicapp.databinding.ItemRvMusicBinding

class TopRankAdapter(private val itemClickAction: (ClickMusicListEvent ,ResponseData.MusicInfo) -> Unit) : BaseListAdapter<ResponseData.MusicInfo,
        TopRankAdapter.MainViewHolder,
        ItemRvMusicBinding>(
    contentsTheSame = { old: ResponseData.MusicInfo, new : ResponseData.MusicInfo ->
        old.mck == new.mck
    }
) {
    override val layoutResourceId: Int = R.layout.item_rv_music
    override val action: (ItemRvMusicBinding) -> TopRankAdapter.MainViewHolder = { MainViewHolder(it) }


    enum class ClickMusicListEvent {
        PLAY,
        MORE
    }

    inner class MainViewHolder(private val binding: ItemRvMusicBinding) :
        BaseViewHolder(binding) {

        override fun onBind(item: Any?) {
            (item as? ResponseData.MusicInfo)?.let {
                binding.apply {
                    musicInfo = item
                    root.setOnClickListener {
                        itemClickAction.invoke(ClickMusicListEvent.PLAY, currentList[adapterPosition])
                    }
                    ivMoreInfo.setOnClickListener {
                        itemClickAction.invoke(ClickMusicListEvent.MORE, currentList[adapterPosition])
                    }
                    executePendingBindings()
                }
            }
        }
    }
}