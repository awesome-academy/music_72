package com.sunasterisk.music_72.utils.media

import android.media.MediaPlayer
import com.sunasterisk.music_72.data.model.Track
import com.sunasterisk.music_72.data.anotation.State

abstract class MediaSetting {
    var mediaPlayer = MediaPlayer()
    @State
    private var trackState =  State.PAUSE

    abstract fun create()
    abstract fun start()
    abstract fun change(track: Track)
    abstract fun pause()
    abstract fun previousTrack()
    abstract fun nextTrack()
    abstract fun stop()

    open fun release() {
        mediaPlayer.release()
    }

    open fun reset() {
        mediaPlayer.reset()
    }

    open fun seek(milliseconds: Int) {
        mediaPlayer.seekTo(milliseconds)
    }

    open fun getDuration() = mediaPlayer.duration

    open fun getCurrentDuration() = mediaPlayer.currentPosition

    open fun setState(@State state: Int) {
        this.trackState = state
    }

    @State
    open fun getState() = trackState
}
