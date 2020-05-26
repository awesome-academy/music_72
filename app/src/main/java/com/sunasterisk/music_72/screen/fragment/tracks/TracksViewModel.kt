package com.sunasterisk.music_72.screen.fragment.tracks

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sunasterisk.music_72.data.anotation.GenreName
import com.sunasterisk.music_72.data.model.Track
import com.sunasterisk.music_72.data.source.repository.TrackRepositoryImplementor
import com.sunasterisk.music_72.utils.addTo
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.util.*

class TracksViewModel(private val repository: TrackRepositoryImplementor) : ViewModel() {
    private val _tracks = MutableLiveData<List<Track>>()
    val tracks: LiveData<List<Track>>
        get() = _tracks
    private val _error = MutableLiveData<Throwable>()
    val error: LiveData<Throwable>
        get() = _error
    private val compositeDisposable = CompositeDisposable()

    fun getTracks(genre: String) {
        if (genre == GenreName.ALL_TRACK) getAllTrack()
        else getTracksByGenre(genre)
    }

    private fun getTracksByGenre(genre: String) {
        repository.getTracksByGenre(genre.toLowerCase(Locale.getDefault()), LIMIT_ITEM)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                _tracks.value = it
            }, {
                _error.value = it
            }).addTo(compositeDisposable)
    }

    private fun getAllTrack() {
        val disposable = repository.getAllTrack()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                _tracks.value = it
            }, {
                _error.value = it
            }).addTo(compositeDisposable)
    }

    fun getDescriptionTotalTrack(total: Int) = String.format(DESCRIPTION_TOTAL_TRACK, total)

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }

    companion object {
        private const val LIMIT_ITEM = 10
        const val DESCRIPTION_TOTAL_TRACK = "Choose %s tracks for you"
    }
}
