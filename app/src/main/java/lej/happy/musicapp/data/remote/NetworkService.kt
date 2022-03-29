package lej.happy.musicapp.data.remote

import androidx.annotation.Keep

class NetworkService {
    companion object {
        @JvmStatic
        val instance by lazy { NetworkService() }

        const val domain = "https://happyweatherapp.herokuapp.com"
    }

    @Keep
    enum class Api(name: String) {
        LIST_TOP_API("TOP") {
            override val path: String
                get() = "$domain/music/info/top"
            override val method: HttpMethod
                get() = HttpMethod.GET
        },
        LIST_RECENT_API("RECENT") {
            override val path: String
                get() = "$domain/music/info/new"
            override val method: HttpMethod
                get() = HttpMethod.GET
        };
        internal abstract val path: String
        internal abstract val method: HttpMethod
    }

    enum class HttpMethod {
        GET,
        POST,
        PUT,
        DELETE
    }
}