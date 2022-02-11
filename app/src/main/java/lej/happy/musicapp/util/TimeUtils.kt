package lej.happy.musicapp.util

import java.util.*
import java.util.concurrent.TimeUnit

object TimeUtils {


    fun getDurationString(durationMs: Int?): String {
        return if (durationMs == null) {
            ""
        } else {
            val minutes = TimeUnit.MILLISECONDS.toMinutes(durationMs.toLong())
            val seconds = TimeUnit.MILLISECONDS.toSeconds(durationMs.toLong())

            java.lang.String.format(
                Locale.getDefault(), "%s%02d:%02d",
                "",
                minutes,
                seconds - TimeUnit.MINUTES.toSeconds(minutes)
            )
        }
    }
}