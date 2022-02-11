package lej.happy.musicapp.ui.player

import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import lej.happy.musicapp.R
import lej.happy.musicapp.databinding.FragmentPlayerListBinding
import lej.happy.musicapp.ui.adapter.PlayerListAdapter
import lej.happy.musicapp.ui.base.BaseFragment
import lej.happy.musicapp.ui.viewmodel.MusicPlayViewModel

@AndroidEntryPoint
class PlayerListFragment: BaseFragment<FragmentPlayerListBinding>() {

    override val layoutResourceId = R.layout.fragment_player_list

    private val mMusicPlayViewModel: MusicPlayViewModel by viewModels()

    private val playerListAdapter = PlayerListAdapter {

    }

    override fun initBinding() {
        initRecyclerView()
    }

    private fun initRecyclerView() {
        binding.rvList.apply {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            adapter = playerListAdapter.apply {
                mMusicPlayViewModel.playList?.let {
                    items.clear()
                    items.addAll(it)
                }
            }
        }
    }
}