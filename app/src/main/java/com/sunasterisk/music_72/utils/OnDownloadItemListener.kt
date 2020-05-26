package com.sunasterisk.music_72.utils

import com.sunasterisk.music_72.data.model.Track

interface OnDownloadItemListener {
    fun onItemDownloadClick(data: Track)
}
