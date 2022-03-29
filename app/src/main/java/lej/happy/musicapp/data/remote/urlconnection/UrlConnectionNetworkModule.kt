package lej.happy.musicapp.data.remote.urlconnection

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import lej.happy.musicapp.data.remote.NetworkModule
import lej.happy.musicapp.data.remote.NetworkService
import lej.happy.musicapp.util.urlEncode
import java.io.InputStream
import java.net.HttpURLConnection

class UrlConnectionNetworkModule : NetworkModule {
    override suspend fun get(
        url: String,
        params: Map<String, String>?,
        headers: Map<String, String>?,
        callback: (result: ByteArray?, error: Any?) -> Unit
    ) {
        request(NetworkService.HttpMethod.GET, url, params, null, headers)
    }

    override suspend fun post(
        url: String,
        body: Map<String, Any?>?,
        headers: Map<String, String>?,
        callback: (result: ByteArray?, error: Any?) -> Unit
    ) {
        // TODO("Not yet implemented")
    }

    override suspend fun put(
        url: String,
        body: Map<String, Any?>?,
        headers: Map<String, String>?,
        callback: (result: ByteArray?, error: Any?) -> Unit
    ) {
        // TODO("Not yet implemented")
    }

    override suspend fun delete(
        url: String,
        params: Map<String, String>?,
        headers: Map<String, String>?,
        callback: (result: ByteArray?, error: Any?) -> Unit
    ) {
        // TODO("Not yet implemented")
    }

    private fun request(
        method: NetworkService.HttpMethod,
        url: String,
        params: Map<String, String>?,
        nothing: Nothing?,
        headers: Map<String, String>?
    ) {
        val sbUrl = StringBuffer()
        var connection: HttpURLConnection? = null
        var inputStream: InputStream? = null

        when (method) {
            NetworkService.HttpMethod.GET,
                NetworkService.HttpMethod.DELETE -> {

                }
        }

        try {

        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
        }
    }

}