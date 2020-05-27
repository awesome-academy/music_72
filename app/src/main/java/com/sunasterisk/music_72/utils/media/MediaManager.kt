package com.sunasterisk.music_72.utils.media

import android.media.AudioManager
import android.net.Uri
import com.sunasterisk.music_72.data.anotation.State
import com.sunasterisk.music_72.data.model.Track
import com.sunasterisk.music_72.screen.service.PlayTrackService
import com.sunasterisk.music_72.utils.ChangeTrackListener
import com.sunasterisk.music_72.utils.Constants

class MediaManager(private val service: PlayTrackService) : MediaSetting() {
    private var currentTrack: Track? = null
    private var tracks = mutableListOf<Track>()
    private var changeTrackListener: ChangeTrackListener? = null

    override fun create() {
        mediaPlayer.reset()
        try {
            mediaPlayer.setDataSource(
                service,
                Uri.parse(
                    currentTrack?.streamUrl
                            + QUESTION_MASK + Constants.CLIENT_ID
                            + EQUAL + Constants.API_KEY
                )
            )
        } catch (e: Exception) {
            e.printStackTrace()
        }
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC)
        mediaPlayer.setOnCompletionListener(service)
        mediaPlayer.setOnPreparedListener(service)
        mediaPlayer.prepare()
    }

    override fun start() {
        trackState = State.PLAY
        changeTrackListener?.onChangeState(trackState)
        mediaPlayer.start()
        changeTrackListener?.onChangeTrackComplete()
    }

    override fun change(track: Track) {
        currentTrack = track
        create()
    }

    override fun pause() {
        trackState = State.PAUSE
        changeTrackListener?.onChangeState(trackState)
        mediaPlayer.pause()
    }

    override fun previousTrack() {
        change(getPreviousTrack())
    }

    private fun getPreviousTrack(): Track {
        val position = tracks.indexOf(currentTrack)
        return if (position == 0) {
            tracks[tracks.size - 1]
        } else {
            tracks[position - 1]
        }
    }

    override fun nextTrack() {
        change(getNextTrack())
    }

    private fun getNextTrack(): Track {
        val position = tracks.indexOf(currentTrack)
        return if (position == tracks.size - 1) {
            tracks[0]
        } else {
            tracks[position + 1]
        }
    }

    fun setChangeTrackListener(changeTrackListener: ChangeTrackListener) {
        this.changeTrackListener = changeTrackListener
    }

    fun setTracks(tracks: MutableList<Track>) {
        this.tracks = tracks
    }

    fun getCurrentTrack() = this.currentTrack

    fun setCurrentTrack(track: Track) {
        currentTrack = track
    }

    override fun stop() {
        mediaPlayer.stop()
    }

    companion object {
        const val EQUAL = "="
        const val QUESTION_MASK = "?"

        @Volatile
        @JvmStatic
        private var INSTANCE: MediaManager? = null

        @JvmStatic
        fun getInstance(service: PlayTrackService): MediaManager =
            (INSTANCE ?: synchronized(this) {
                INSTANCE ?: MediaManager(service).also { INSTANCE = it }
            })
    }
}
