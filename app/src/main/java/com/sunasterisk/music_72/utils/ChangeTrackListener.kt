package com.sunasterisk.music_72.utils

interface ChangeTrackListener {
    fun onChangeState(state: Int)
    fun onChangeTrackComplete()
}
