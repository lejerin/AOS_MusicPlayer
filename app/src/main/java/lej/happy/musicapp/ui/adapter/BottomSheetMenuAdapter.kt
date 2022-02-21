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
import lej.happy.musicapp.ui.player.PlayMoreDialog

class BottomSheetMenuAdapter(private val itemClickAction: (PlayMoreDialog.Item) -> Unit) : RecyclerView.Adapter<BottomSheetMenuAdapter.MainViewHolder>() {

    val items = mutableListOf(
        PlayMoreDialog.Item.START,
        PlayMoreDialog.Item.ADD_LIST,
        PlayMoreDialog.Item.SAVE_MY_LIST
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

        fun bind(item: PlayMoreDialog.Item) {
            binding.apply {
                ivIcon.setImageResource(when (item) {
                    PlayMoreDialog.Item.START -> R.drawable.ic_baseline_play_arrow_24
                    PlayMoreDialog.Item.ADD_LIST -> R.drawable.ic_baseline_queue_music_24
                    PlayMoreDialog.Item.SAVE_MY_LIST -> R.drawable.ic_baseline_playlist_add_24
                    else -> R.drawable.ic_baseline_playlist_add_24
                })
                text = when (item) {
                    PlayMoreDialog.Item.START -> binding.root.context.getString(R.string.bottom_sheet_menu_start)
                    PlayMoreDialog.Item.ADD_LIST -> binding.root.context.getString(R.string.bottom_sheet_menu_add)
                    PlayMoreDialog.Item.SAVE_MY_LIST -> binding.root.context.getString(R.string.bottom_sheet_menu_save)
                    else -> ""
                }
                executePendingBindings()
            }
        }
    }
}