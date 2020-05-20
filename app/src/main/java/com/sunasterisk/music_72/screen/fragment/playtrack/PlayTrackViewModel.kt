package com.sunasterisk.music_72.screen.fragment.playtrack

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sunasterisk.music_72.data.model.Track
import com.sunasterisk.music_72.data.source.repository.TrackRepositoryImplementor
import com.sunasterisk.music_72.utils.addTo
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class PlayTrackViewModel(private val repository: TrackRepositoryImplementor) : ViewModel() {
    private val _track = MutableLiveData<Track>()
    val track: LiveData<Track>
        get() = _track
    private val _error = MutableLiveData<Throwable>()
    val error: LiveData<Throwable>
        get() = _error
    val compositeDisposable = CompositeDisposable()

    fun getTrackbyId(id: Int) {
        val disposable = repository.getTrackById(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe ({
                _track.value = it
            },{
                _error.value = it
            }).addTo(compositeDisposable)
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}
