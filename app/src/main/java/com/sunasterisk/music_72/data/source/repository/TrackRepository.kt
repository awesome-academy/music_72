package com.sunasterisk.music_72.data.source.repository

import com.sunasterisk.music_72.data.model.Track
import com.sunasterisk.music_72.data.source.local.TrackLocalDataSource
import com.sunasterisk.music_72.data.source.remote.TrackRemoteDataSource
import io.reactivex.Observable

interface TrackRepository{
    fun getAllTrack(limit: Int): Observable<MutableList<Track>>
    fun getTracksByGenre(genre: String, limit: Int): Observable<MutableList<Track>>
    fun getTrackById(id: Int): Observable<Track>
    fun getAudiosDataStorage(): Observable<MutableList<Track>>
}

class TrackRepositoryImplementor(
    private val local: TrackLocalDataSource,
    private val remote: TrackRemoteDataSource
) : TrackRepository {

    override fun getAllTrack(limit: Int) = remote.getAllTrack(limit)

    override fun getTracksByGenre(genre: String, limit: Int) =
        remote.getTracksByGenre(genre, limit)

    override fun getTrackById(id: Int) = remote.getTrackById(id)

    override fun getAudiosDataStorage() = local.getAudiosDataStorage()
}
