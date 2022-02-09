package lej.happy.musicapp.data.remote

import lej.happy.musicapp.data.ResponseData
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {

    @GET("info/new")
    suspend fun getNewReleasesMusicList(): Response<ResponseData>

    @GET("info/top")
    suspend fun getTopMusicList(): Response<ResponseData>
}