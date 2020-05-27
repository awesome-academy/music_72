package com.sunasterisk.music_72.utils

import java.util.concurrent.TimeUnit

object CommonUtils {

    fun convertTimeInMilisToString(duration: Long) =
        String.format(
            TIME_FORMAT,
            TimeUnit.MILLISECONDS.toMinutes(duration) -
                    TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(duration)),
            TimeUnit.MILLISECONDS.toSeconds(duration) -
                    TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(duration))
        )

    private const val TIME_FORMAT = "%02d:%02d"
}
