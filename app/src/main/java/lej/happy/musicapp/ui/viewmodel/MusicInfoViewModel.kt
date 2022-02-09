package lej.happy.musicapp.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import lej.happy.musicapp.data.ResponseData
import lej.happy.musicapp.data.remote.NetworkResult
import lej.happy.musicapp.data.remote.repository.MusicInfoRepository
import javax.inject.Inject

@HiltViewModel
class MusicInfoViewModel @Inject constructor(private val repository: MusicInfoRepository) : ViewModel() {

    private val _newReleasesResponse: MutableLiveData<NetworkResult<ResponseData>> = MutableLiveData()
    val newReleasesResponse: LiveData<NetworkResult<ResponseData>> = _newReleasesResponse

    private val _topRankResponse: MutableLiveData<NetworkResult<ResponseData>> = MutableLiveData()
    val topRankResponse: LiveData<NetworkResult<ResponseData>> = _newReleasesResponse

    fun fetchDataResponse() = viewModelScope.launch {
        repository.requestNewReleasesMusicList().collect { values ->
            _newReleasesResponse.value = values
        }
        repository.requestTopMusicList().collect { values ->
            _topRankResponse.value = values
        }
    }

}