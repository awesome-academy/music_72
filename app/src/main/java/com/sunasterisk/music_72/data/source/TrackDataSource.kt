package com.sunasterisk.music_72.data.source

import com.sunasterisk.music_72.data.model.Track
import io.reactivex.Observable

interface TrackDataSource {

    interface Local {
        fun getAudiosDataStorage(): Observable<MutableList<Track>>
    }

    interface Remote {
        fun getAllTrack(limit: Int): Observable<MutableList<Track>>
        fun getTracksByGenre(genre: String, limit: Int): Observable<MutableList<Track>>
        fun getTrackById(id: Int): Observable<Track>
    }
}
