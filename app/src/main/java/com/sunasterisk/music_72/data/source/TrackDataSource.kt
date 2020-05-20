package com.sunasterisk.music_72.data.source

import com.sunasterisk.music_72.data.model.Track
import io.reactivex.Observable

interface TrackDataSource {

    interface Local

    interface Remote {
        fun getAllTrack(): Observable<MutableList<Track>>?
        fun getTracksByGenre(genre: String, limit: Int) : Observable<MutableList<Track>>?
    }
}
