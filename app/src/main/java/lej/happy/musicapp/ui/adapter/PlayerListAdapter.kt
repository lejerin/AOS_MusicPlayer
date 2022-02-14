package lej.happy.musicapp.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import lej.happy.musicapp.R
import lej.happy.musicapp.data.ResponseData
import lej.happy.musicapp.databinding.ItemRvMusicBinding
import lej.happy.musicapp.databinding.ItemRvPlayListBinding

class PlayerListAdapter(private val itemClickAction: (ResponseData.MusicInfo) -> Unit) : RecyclerView.Adapter<PlayerListAdapter.MainViewHolder>() {

    val items = mutableListOf<ResponseData.MusicInfo>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        DataBindingUtil.inflate<ItemRvPlayListBinding>(
            LayoutInflater.from(parent.context),
            R.layout.item_rv_play_list,
            parent,
            false
        ).let { MainViewHolder(it) }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) =
        holder.bind(items[position])

    @SuppressLint("ClickableViewAccessibility")
    inner class MainViewHolder(private val binding: ItemRvPlayListBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                itemClickAction.invoke(items[adapterPosition])
            }
        }

        fun bind(item: ResponseData.MusicInfo) {
            binding.apply {
                musicInfo = item
                executePendingBindings()
            }
        }
    }
}