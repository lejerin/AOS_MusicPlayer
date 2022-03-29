package lej.happy.musicapp.data.remote

interface NetworkModule {
    suspend fun get(
        url: String,
        params: Map<String, String>? = null,
        headers: Map<String, String>? = null,
        callback: (result: ByteArray?, error: Any?) -> Unit,
    )

    suspend fun post(
        url: String,
        body: Map<String, Any?>? = null,
        headers: Map<String, String>? = null,
        callback: (result: ByteArray?, error: Any?) -> Unit,
    )

    suspend fun put(
        url: String,
        body: Map<String, Any?>? = null,
        headers: Map<String, String>? = null,
        callback: (result: ByteArray?, error: Any?) -> Unit,
    )

    suspend fun delete(
        url: String,
        params: Map<String, String>? = null,
        headers: Map<String, String>? = null,
        callback: (result: ByteArray?, error: Any?) -> Unit,
    )
}