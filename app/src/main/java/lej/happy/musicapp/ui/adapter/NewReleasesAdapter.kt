package lej.happy.musicapp.ui.adapter

import android.annotation.SuppressLint
import com.happy.commons.ui.base.BaseListAdapter
import lej.happy.musicapp.R
import lej.happy.musicapp.data.ResponseData
import lej.happy.musicapp.databinding.ItemRvNewReleasesBinding

class NewReleasesAdapter(private val itemClickAction: (ResponseData.MusicInfo) -> Unit) :
    BaseListAdapter<ResponseData.MusicInfo,
            NewReleasesAdapter.MainViewHolder,
            ItemRvNewReleasesBinding>(
        contentsTheSame = { old: ResponseData.MusicInfo, new : ResponseData.MusicInfo ->
            old.mck == new.mck
        }
    ) {
    override val layoutResourceId: Int = R.layout.item_rv_new_releases
    override val action: (ItemRvNewReleasesBinding) -> NewReleasesAdapter.MainViewHolder = { MainViewHolder(it) }

    @SuppressLint("ClickableViewAccessibility")
    inner class MainViewHolder(private val binding: ItemRvNewReleasesBinding) :
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