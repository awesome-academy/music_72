package com.sunasterisk.music_72.screen.fragment.audio

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sunasterisk.music_72.data.model.Track
import com.sunasterisk.music_72.data.source.repository.TrackRepositoryImplementor
import com.sunasterisk.music_72.utils.addTo
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class AudioViewModel(private val repository: TrackRepositoryImplementor) : ViewModel() {
    private val _audios = MutableLiveData<List<Track>>()
    val audios: LiveData<List<Track>>
        get() = _audios
    private val _error = MutableLiveData<Throwable>()
    val error: LiveData<Throwable>
        get() = _error
    private val compositeDisposable = CompositeDisposable()

    fun getAudios() {
        repository.getAudiosDataStorage()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                _audios.value = it
            }, {
                _error.value = it
            }).addTo(compositeDisposable)
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}
