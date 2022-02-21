package lej.happy.musicapp.ui.player

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import lej.happy.musicapp.R
import lej.happy.musicapp.data.ResponseData
import lej.happy.musicapp.databinding.DialogPlayMoreBinding
import lej.happy.musicapp.ui.adapter.BottomSheetMenuAdapter

class PlayMoreDialog(private val musicInfo: ResponseData.MusicInfo,
                     private val itemClickAction: (Item) -> Unit) : BottomSheetDialogFragment() {

    lateinit var binding: DialogPlayMoreBinding

    enum class Item {
        START,
        ADD_LIST,
        SAVE_MY_LIST,
        LIKE,
        UNLIKE
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.dialog_play_more, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.musicInfo = musicInfo
        binding.rvMore.adapter = BottomSheetMenuAdapter {
            itemClickAction.invoke(it)
            dismiss()
        }
        binding.ivMoreLike.setOnClickListener {
            it.isSelected = !it.isSelected
            itemClickAction.invoke(Item.LIKE)
        }
        binding.ivMoreUnlike.setOnClickListener {
            it.isSelected = !it.isSelected
            itemClickAction.invoke(Item.UNLIKE)
        }
    }
}