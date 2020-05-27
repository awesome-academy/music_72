package com.sunasterisk.music_72.screen.service

import android.app.Service
import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.os.Binder
import android.os.IBinder
import com.sunasterisk.music_72.data.anotation.State
import com.sunasterisk.music_72.data.model.Track
import com.sunasterisk.music_72.utils.ChangeTrackListener
import com.sunasterisk.music_72.utils.media.MediaManager

class PlayTrackService: Service(),
    MediaPlayer.OnPreparedListener,
    MediaPlayer.OnErrorListener,
    MediaPlayer.OnCompletionListener
{
    private var context: Context? = null
    private var binder : PlayBinder? = null
    private var mediaManager: MediaManager? = null
    override fun onCreate() {
        super.onCreate()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return START_STICKY
    }

    override fun onBind(p0: Intent?): IBinder? {
        binder = PlayBinder()
        mediaManager = MediaManager.getInstance(this)
        return binder
    }

    inner class PlayBinder : Binder() {
        fun getService(): PlayTrackService = this@PlayTrackService
    }

    fun playOrPause() {
        if (mediaManager!!.trackState == State.PAUSE) {
            startTrack()
        } else {
            pauseTrack()
        }
    }

    private fun pauseTrack() {
        mediaManager?.pause()
    }

    private fun startTrack() {
        mediaManager?.start()
    }

    fun changeTrack(track: Track) {
        mediaManager?.change(track)
    }

    fun nextTrack() {
        mediaManager?.nextTrack()
    }

    fun preTrack() {
        mediaManager?.previousTrack()
    }

    fun setCurTrack(track: Track) {
        mediaManager?.setCurrentTrack(track)
    }

    fun getCurTrack() = mediaManager?.getCurrentTrack()

    fun setTracks(tracks: MutableList<Track>) {
        mediaManager?.setTracks(tracks)
    }

    fun setContext(context: Context) {
        this.context = context
    }

    fun getState(): Int? = mediaManager?.trackState

    fun getDuration(): Int? = mediaManager?.getDuration()

    fun getCurrentDuration(): Int? = mediaManager?.getCurrentDuration()

    fun seekTo(progress: Int) {
        mediaManager?.seek(progress)
    }

    fun setChangeTrackListener(changeTrackListener: ChangeTrackListener) {
        mediaManager?.setChangeTrackListener(changeTrackListener)
    }


    override fun onPrepared(mediaPlayer: MediaPlayer?) {
        startTrack()
    }

    override fun onError(p0: MediaPlayer?, p1: Int, p2: Int): Boolean {
        return true;
    }

    override fun onCompletion(p0: MediaPlayer?) {
        nextTrack()
    }

    companion object {
        fun getIntent(context: Context) = Intent(context, PlayTrackService::class.java)
    }
}
