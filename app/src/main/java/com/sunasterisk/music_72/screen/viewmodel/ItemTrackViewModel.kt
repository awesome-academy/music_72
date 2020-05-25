package com.sunasterisk.music_72.screen.viewmodel

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.sunasterisk.music_72.BR
import com.sunasterisk.music_72.data.model.Track
import com.sunasterisk.music_72.utils.MusicUtils
import com.sunasterisk.music_72.utils.OnDownloadItemListener
import com.sunasterisk.music_72.utils.OnRecyclerViewItemListener

class ItemTrackViewModel(
    private val data: Track,
    private val listener: OnRecyclerViewItemListener<Track>?,
    private val downloadListener: OnDownloadItemListener?
) : BaseObservable() {

    @Bindable
    var track: Track? = null

    init {
        track = data
        notifyPropertyChanged(BR.track)
    }

    fun setVisibilityActionDownload() = MusicUtils.setVisibilityDownload(data.downloadable)

    fun onDownloadListener() { track?.let { downloadListener?.onItemDownloadClick(it) } }

    fun onClickListener() { track?.let { listener?.onItemClick(it) } }
}
