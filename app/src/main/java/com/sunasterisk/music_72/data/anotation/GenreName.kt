package com.sunasterisk.music_72.data.anotation

import androidx.annotation.StringDef
import com.sunasterisk.music_72.data.anotation.GenreName.Companion.ALL_TRACK
import com.sunasterisk.music_72.data.anotation.GenreName.Companion.AMBIENT
import com.sunasterisk.music_72.data.anotation.GenreName.Companion.CLASSICAL
import com.sunasterisk.music_72.data.anotation.GenreName.Companion.COUNTRY
import com.sunasterisk.music_72.data.anotation.GenreName.Companion.ROCK

@Retention(AnnotationRetention.SOURCE)
@StringDef(
    ALL_TRACK,
    COUNTRY,
    ROCK,
    AMBIENT,
    CLASSICAL
)

annotation class GenreName {
    companion object {
        const val ALL_TRACK = "ALL TRACK"
        const val COUNTRY = "COUNTRY"
        const val ROCK = "ROCK"
        const val AMBIENT = "AMBIENT"
        const val CLASSICAL = "CLASSICAL"
    }
}
