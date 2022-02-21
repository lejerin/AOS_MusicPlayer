package lej.happy.musicapp.ui.player

import android.util.Log
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.happy.commons.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import lej.happy.musicapp.R
import lej.happy.musicapp.databinding.FragmentPlayerListBinding
import lej.happy.musicapp.ui.adapter.PlayerListAdapter
import lej.happy.musicapp.ui.viewmodel.MusicPlayViewModel

@AndroidEntryPoint
class PlayerListFragment: BaseFragment<FragmentPlayerListBinding>() {

    override val layoutResourceId = R.layout.fragment_player_list

    private val mMusicPlayViewModel: MusicPlayViewModel by activityViewModels()

    private val playerListAdapter = PlayerListAdapter {

    }

    override fun initUi() {
        initRecyclerView()
    }

    override fun setupObservers() {
        initObserver()
    }

    private fun initRecyclerView() {
        binding.rvList.apply {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            adapter = playerListAdapter
        }
    }

    private fun initObserver() {
        Log.i("eunjin", "initObserver")
        mMusicPlayViewModel.music.observe(viewLifecycleOwner, {
            // 한 곡 재생
            mMusicPlayViewModel.mMusicPlayService?.getPlayList().let {
                playerListAdapter.submitList(it)
            }
        })
        mMusicPlayViewModel.musicList.observe(viewLifecycleOwner, {
            // 여러 곡 재생
            mMusicPlayViewModel.mMusicPlayService?.getPlayList().let {
                playerListAdapter.submitList(it)
            }
        })
    }
}