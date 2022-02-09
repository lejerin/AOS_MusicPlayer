package lej.happy.musicapp.data.remote.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import lej.happy.musicapp.data.ResponseData
import lej.happy.musicapp.data.remote.ApiService
import lej.happy.musicapp.data.remote.BaseApiResponse
import lej.happy.musicapp.data.remote.NetworkResult

class MusicInfoRepository(private val service: ApiService) : BaseApiResponse() {

    /** 전체 음악 리스트 */
    suspend fun requestNewReleasesMusicList(): Flow<NetworkResult<ResponseData>> {
        return flow {
            emit(safeApiCall { service.getNewReleasesMusicList() })
        }.flowOn(Dispatchers.IO)
    }

    /** 랭킹 음악 리스트 */
    suspend fun requestTopMusicList(): Flow<NetworkResult<ResponseData>> {
        return flow {
            emit(safeApiCall { service.getTopMusicList() })
        }.flowOn(Dispatchers.IO)
    }
}