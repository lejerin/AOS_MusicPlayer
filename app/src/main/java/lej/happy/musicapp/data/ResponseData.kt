package lej.happy.musicapp.data

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

open class ResponseData(
    @SerializedName("result") val result: Boolean,
    @SerializedName("message") val message: String,
    @SerializedName("data") val data: List<MusicInfo>
) {
    data class MusicInfo(
        @SerializedName("mck") val mck: Int,
        @SerializedName("title") val title: String,
        @SerializedName("album") val album: String,
        @SerializedName("albumCover") val albumCover: String,
        @SerializedName("composer") val composer: String,
        @SerializedName("genre") val genre: String,
        @SerializedName("subGenre") val subGenre: String?,
        @SerializedName("lyricist") val lyricist: String,
        @SerializedName("profile") val profile: String,
        @SerializedName("rank") val rank: Int?,
        @SerializedName("releaseDate") val releaseDate: String,
        @SerializedName("singer") val singer: String
    ) : Serializable
}