package lej.happy.musicapp.ui.main.home

import android.content.Intent
import android.util.Log
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import lej.happy.musicapp.R
import lej.happy.musicapp.data.ResponseData
import lej.happy.musicapp.data.remote.NetworkResult
import lej.happy.musicapp.databinding.FragmentHomeBinding
import lej.happy.musicapp.ui.adapter.BottomSheetMenuAdapter
import lej.happy.musicapp.ui.adapter.NewReleasesAdapter
import lej.happy.musicapp.ui.adapter.TopRankAdapter
import lej.happy.musicapp.ui.base.BaseFragment
import lej.happy.musicapp.ui.player.PlayMoreDialog
import lej.happy.musicapp.ui.player.PlayerFragment
import lej.happy.musicapp.ui.viewmodel.MusicInfoViewModel
import lej.happy.musicapp.ui.viewmodel.MusicPlayViewModel

@AndroidEntryPoint
class HomeFragment: BaseFragment<FragmentHomeBinding>() {
    override val layoutResourceId = R.layout.fragment_home

    private val mMusicPlayViewModel: MusicPlayViewModel by activityViewModels()
    private val mMusicInfoViewModel: MusicInfoViewModel by activityViewModels()

    private val newReleasesAdapter = NewReleasesAdapter {
        startMusic(it)
    }
    private val topRankAdapter = TopRankAdapter { event, item ->
        when (event) {
            TopRankAdapter.ClickMusicListEvent.PLAY -> {
                startMusic(item)
            }
            TopRankAdapter.ClickMusicListEvent.MORE -> {
                val bottomSheetDialog = PlayMoreDialog(item) {
                    when (it) {
                        BottomSheetMenuAdapter.Item.START -> {
                            startMusic(item)
                        }
                        BottomSheetMenuAdapter.Item.ADD_LIST -> {
                            mMusicPlayViewModel.addPlayList(item)
                        }
                        BottomSheetMenuAdapter.Item.SAVE_MY_LIST -> {

                        }
                    }
                }
                bottomSheetDialog.show(parentFragmentManager, "myBottomSheetDialog")
            }
        }
    }

    override fun initBinding() {
        initRecyclerView()
        initObserver()
        mMusicInfoViewModel.fetchDataResponse()
    }

    private fun startMusic(item: ResponseData.MusicInfo) {
        Log.i("eunjin", "start $item")
        mMusicPlayViewModel.music.value = item
    }

    private fun initObserver() {
        mMusicInfoViewModel.newReleasesResponse.observe(viewLifecycleOwner, { response ->
            when (response) {
                is NetworkResult.Success -> {
                    response.data?.let {
                        if (it.result) {
                            CoroutineScope(Dispatchers.Main).launch {
                                newReleasesAdapter.items.addAll(it.data)
                                newReleasesAdapter.notifyDataSetChanged()

                                mMusicPlayViewModel.music.value = it.data[0]
                            }
                        }
                    }
                }
                is NetworkResult.Error -> {
                    // show error message
                }
                is NetworkResult.Loading -> {
                    // show a progress bar
                }
            }
        })
        mMusicInfoViewModel.topRankResponse.observe(viewLifecycleOwner, { response ->
            when (response) {
                is NetworkResult.Success -> {
                    response.data?.let {
                        if (it.result) {
                            CoroutineScope(Dispatchers.Main).launch {
                                topRankAdapter.items.addAll(it.data)
                                topRankAdapter.notifyDataSetChanged()
                            }
                        }
                    }
                }
                is NetworkResult.Error -> {
                    // show error message
                }
                is NetworkResult.Loading -> {
                    // show a progress bar
                }
            }
        })
    }

    private fun initRecyclerView() {
        binding.rvNewReleasesMain.apply {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = newReleasesAdapter
        }
        binding.rvTopMain.apply {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            adapter = topRankAdapter
        }
    }
}
