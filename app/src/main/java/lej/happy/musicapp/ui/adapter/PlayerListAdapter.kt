package lej.happy.musicapp.ui.adapter

import android.annotation.SuppressLint
import com.happy.commons.ui.base.BaseListAdapter
import lej.happy.musicapp.R
import lej.happy.musicapp.data.ResponseData
import lej.happy.musicapp.databinding.ItemRvPlayListBinding

class PlayerListAdapter(private val itemClickAction: (ResponseData.MusicInfo) -> Unit) :
    BaseListAdapter<ResponseData.MusicInfo,
            PlayerListAdapter.MainViewHolder,
            ItemRvPlayListBinding>(
        itemsTheSame = { old: ResponseData.MusicInfo, new : ResponseData.MusicInfo ->
            old.mck == new.mck
        },
        contentsTheSame = { old: ResponseData.MusicInfo, new : ResponseData.MusicInfo ->
            old.mck == new.mck
        }
    ) {
    override val layoutResourceId: Int = R.layout.item_rv_play_list
    override val action: (ItemRvPlayListBinding) -> PlayerListAdapter.MainViewHolder = { MainViewHolder(it) }

    @SuppressLint("ClickableViewAccessibility")
    inner class MainViewHolder(private val binding: ItemRvPlayListBinding) :
        BaseViewHolder(binding) {

        override fun onBind(item: Any?) {
            (item as? ResponseData.MusicInfo)?.let {
                binding.apply {
                    musicInfo = item
                    root.setOnClickListener {
                        itemClickAction.invoke(currentList[adapterPosition])
                    }
                    executePendingBindings()
                }
            }
        }
    }
}