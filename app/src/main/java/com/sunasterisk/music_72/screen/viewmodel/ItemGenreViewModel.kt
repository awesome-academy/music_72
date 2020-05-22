package com.sunasterisk.music_72.screen.viewmodel

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.sunasterisk.music_72.BR
import com.sunasterisk.music_72.data.model.Genre
import com.sunasterisk.music_72.utils.OnRecyclerViewItemListener

class ItemGenreViewModel(
    private val data: Genre,
    private val listener: OnRecyclerViewItemListener<Genre>?
) : BaseObservable() {

    @Bindable
    var genre: Genre? = null

    init {
        genre = data
        notifyPropertyChanged(BR.genre)
    }

    fun onClickListener() { genre?.let { listener?.onItemClick(it) } }
}
