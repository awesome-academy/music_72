package com.sunasterisk.music_72.screen.viewmodel

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.sunasterisk.music_72.BR
import com.sunasterisk.music_72.data.model.Track
import com.sunasterisk.music_72.utils.OnRecyclerViewItemListener

class ItemTrackViewModel(
    private val data: Track,
    private val listener: OnRecyclerViewItemListener<Track>?
) : BaseObservable() {

    @Bindable
    var track: Track? = null

    init {
        track = data
        notifyPropertyChanged(BR.track)
    }

    fun onClickListener() { track?.let { listener?.onItemClick(it) } }
}
