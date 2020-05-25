package com.sunasterisk.music_72.screen.fragment.tracks

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sunasterisk.music_72.data.model.Track
import com.sunasterisk.music_72.data.source.repository.TrackRepositoryImplementor

class TracksViewModel(private val repository: TrackRepositoryImplementor) : ViewModel() {
    private val _tracks = MutableLiveData<List<Track>>()
    val tracks: LiveData<List<Track>>
        get() = _tracks

    fun getDescriptionTotalTrack(total: Int) = String.format(DESCRIPTION_TOTAL_TRACK, total)

    companion object {
        const val DESCRIPTION_TOTAL_TRACK = "Choose %s tracks for you"
    }
}
