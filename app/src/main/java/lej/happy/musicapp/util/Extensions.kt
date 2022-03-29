package lej.happy.musicapp.util

import androidx.annotation.Keep
import java.net.URLEncoder
import java.nio.charset.Charset

@Keep
fun String.urlEncode(charset: Charset): String {
    return URLEncoder.encode(this, charset.name())
}