package com.sunasterisk.music_72.utils

import com.sunasterisk.music_72.R
import com.sunasterisk.music_72.data.anotation.GenreName

object MusicUtils {

    fun getGenreImage(@GenreName genre: String) = getGenreImages()[genre]

    private fun getGenreImages() = hashMapOf(
        GenreName.ALL_TRACK to R.drawable.bg_all_music,
        GenreName.COUNTRY to R.drawable.bg_country,
        GenreName.AMBIENT to R.drawable.bg_ambient,
        GenreName.ROCK to R.drawable.bg_rock,
        GenreName.CLASSICAL to R.drawable.bg_classical
    )

    fun setVisibilityDownload(isDownload: Boolean) =
        if (isDownload) R.drawable.ic_download_white_24dp else R.drawable.ic_non_download_24dp
}
