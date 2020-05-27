package com.sunasterisk.music_72.screen.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.AsyncDifferConfig
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sunasterisk.music_72.R
import com.sunasterisk.music_72.data.model.Track
import com.sunasterisk.music_72.databinding.LayoutItemTrackBinding
import com.sunasterisk.music_72.screen.viewmodel.ItemTrackViewModel
import com.sunasterisk.music_72.utils.OnDownloadItemListener
import com.sunasterisk.music_72.utils.OnRecyclerViewItemListener
import kotlinx.android.synthetic.main.layout_item_track.view.*
import java.util.concurrent.Executors

class TrackAdapter() : ListAdapter<Track, TrackAdapter.ViewHolder>(
    AsyncDifferConfig.Builder<Track>(GenreDiffCallback())
        .setBackgroundThreadExecutor(Executors.newSingleThreadExecutor())
        .build()
) {
    private var listener: OnRecyclerViewItemListener<Track>? = null
    private var downloadListener: OnDownloadItemListener? = null

    fun setOnItemClickListener(
        listener: OnRecyclerViewItemListener<Track>?,
        downloadListener: OnDownloadItemListener?
    ) {
        this.listener = listener
        this.downloadListener = downloadListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            DataBindingUtil.inflate<LayoutItemTrackBinding>(LayoutInflater.from(parent.context),
                R.layout.layout_item_track, parent, false)
        return ViewHolder(binding as LayoutItemTrackBinding, listener, downloadListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val animationId = if ((position % 2) == 0) {
            R.anim.anim_fade_in
        } else {
            R.anim.anim_fade_out
        }
        holder.loadAnimation(animationId)
        holder.bindData(getItem(position))
    }

    class ViewHolder(
        private val binding: LayoutItemTrackBinding,
        private val listener: OnRecyclerViewItemListener<Track>?,
        private val downloadListener: OnDownloadItemListener?
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bindData(data: Track) {
            if (data.downloadable) {
                binding.viewModel = ItemTrackViewModel(data, listener, downloadListener)
            } else {
                binding.viewModel = ItemTrackViewModel(data, listener, null)
            }
            binding.executePendingBindings()
        }

        fun loadAnimation(animationId: Int) {
            binding.root.apply {
                containerItemTrack.animation = AnimationUtils.loadAnimation(context, animationId)
            }
        }
    }

    class GenreDiffCallback : DiffUtil.ItemCallback<Track>() {
        override fun areItemsTheSame(oldItem: Track, newItem: Track): Boolean {
            return oldItem.title == newItem.title
        }

        override fun areContentsTheSame(oldItem: Track, newItem: Track): Boolean {
            return oldItem == newItem
        }
    }
}
