package com.sunasterisk.music_72.utils.media

import android.content.Context
import android.media.MediaPlayer
import android.net.Uri
import com.sunasterisk.music_72.data.model.Track
import com.sunasterisk.music_72.utils.Constants

class MediaManager(private val context: Context) : MediaSetting() {
    private var currentTrack: Track? = null
    private var tracks = mutableListOf<Track>()

    override fun create() {
        mediaPlayer.reset()
        try {
            mediaPlayer.setDataSource(
                context,
                Uri.parse(
                    currentTrack?.streamUrl
                            + QUESTION_MASK + Constants.CLIENT_ID
                            + EQUAL + Constants.API_KEY
                )
            )
        } catch (e: Exception) {
            e.printStackTrace()
        }
        mediaPlayer.prepare()
    }

    override fun start() {
        mediaPlayer.start()
    }

    override fun change(track: Track) {
        currentTrack = track
        create()
    }

    override fun pause() {
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

    fun setTracks(tracks: MutableList<Track>) {
        this.tracks = tracks
    }

    fun setCurrentTrack(track: Track) {
        currentTrack = track
    }

    fun getCurrentPosition() = mediaPlayer.currentPosition

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
        fun getInstance(context: Context): MediaManager =
            (INSTANCE ?: synchronized(this) {
                INSTANCE ?: MediaManager(context).also { INSTANCE = it }
            })
    }
}
