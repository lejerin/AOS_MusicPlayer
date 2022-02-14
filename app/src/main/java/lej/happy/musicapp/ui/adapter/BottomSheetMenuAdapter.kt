package lej.happy.musicapp.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import lej.happy.musicapp.R
import lej.happy.musicapp.data.ResponseData
import lej.happy.musicapp.databinding.ItemDefaultListBinding
import lej.happy.musicapp.databinding.ItemRvPlayListBinding

class BottomSheetMenuAdapter(private val itemClickAction: (Item) -> Unit) : RecyclerView.Adapter<BottomSheetMenuAdapter.MainViewHolder>() {

    enum class Item {
        START,
        ADD_LIST,
        SAVE_MY_LIST
    }

    val items = mutableListOf<Item>(
        Item.START,
        Item.ADD_LIST,
        Item.SAVE_MY_LIST
    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        DataBindingUtil.inflate<ItemDefaultListBinding>(
            LayoutInflater.from(parent.context),
            R.layout.item_default_list,
            parent,
            false
        ).let { MainViewHolder(it) }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) =
        holder.bind(items[position])

    @SuppressLint("ClickableViewAccessibility")
    inner class MainViewHolder(private val binding: ItemDefaultListBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                itemClickAction.invoke(items[adapterPosition])
            }
        }

        fun bind(item: Item) {
            binding.apply {
                ivIcon.setImageResource(when (item) {
                    Item.START -> R.drawable.ic_baseline_play_arrow_24
                    Item.ADD_LIST -> R.drawable.ic_baseline_queue_music_24
                    Item.SAVE_MY_LIST -> R.drawable.ic_baseline_playlist_add_24
                })
                text = when (item) {
                    Item.START -> binding.root.context.getString(R.string.bottom_sheet_menu_start)
                    Item.ADD_LIST -> binding.root.context.getString(R.string.bottom_sheet_menu_add)
                    Item.SAVE_MY_LIST -> binding.root.context.getString(R.string.bottom_sheet_menu_save)
                }
                executePendingBindings()
            }
        }
    }
}