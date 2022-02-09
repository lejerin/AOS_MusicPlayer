package lej.happy.musicapp.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import lej.happy.musicapp.R
import lej.happy.musicapp.data.ResponseData
import lej.happy.musicapp.databinding.ItemRvNewReleasesBinding

class NewReleasesAdapter(private val itemClickAction: (ResponseData.MusicInfo) -> Unit) : RecyclerView.Adapter<NewReleasesAdapter.MainViewHolder>() {

    val items = mutableListOf<ResponseData.MusicInfo>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        DataBindingUtil.inflate<ItemRvNewReleasesBinding>(
            LayoutInflater.from(parent.context),
            R.layout.item_rv_new_releases,
            parent,
            false
        ).let { MainViewHolder(it) }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) =
        holder.bind(items[position])

    @SuppressLint("ClickableViewAccessibility")
    inner class MainViewHolder(private val binding: ItemRvNewReleasesBinding) :
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