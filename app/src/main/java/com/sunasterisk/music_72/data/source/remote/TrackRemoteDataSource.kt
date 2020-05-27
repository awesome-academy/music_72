package com.sunasterisk.music_72.data.source.remote

import com.sunasterisk.music_72.data.source.TrackDataSource
import com.sunasterisk.music_72.data.source.remote.connection.RetrofitClient

class TrackRemoteDataSource(apiService: RetrofitClient) : TrackDataSource.Remote {

    private val trackService = apiService.getTrackService()

    override fun getAllTrack(limit: Int) = trackService.getAllTrack(limit)

    override fun getTracksByGenre(genre: String, limit: Int) =
        trackService.getTrackByGenre(genre, limit)

    override fun getTrackById(id: Int) = trackService.getTrackById(id)
}
