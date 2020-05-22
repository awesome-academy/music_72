package com.sunasterisk.music_72.screen.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sunasterisk.music_72.R
import com.sunasterisk.music_72.data.model.Genre
import com.sunasterisk.music_72.databinding.LayoutItemHomeBinding
import com.sunasterisk.music_72.screen.viewmodel.ItemGenreViewModel
import com.sunasterisk.music_72.utils.OnRecyclerViewItemListener
import kotlinx.android.synthetic.main.layout_item_home.view.*

class GenreAdapter : ListAdapter<Genre, GenreAdapter.ViewHolder>(GenreDiffCallback()) {
    private var listener: OnRecyclerViewItemListener<Genre>? = null

    fun setOnItemClickListener(listener: OnRecyclerViewItemListener<Genre>?) {
        this.listener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            DataBindingUtil.inflate<ViewDataBinding>(LayoutInflater.from(parent.context),
                R.layout.layout_item_home, parent, false)
        return ViewHolder(binding as LayoutItemHomeBinding, listener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val animationId = if ((position % 2) == 0) {
            R.anim.anim_fade_in
        } else {
            R.anim.anim_fade_out
        }
        holder.loadAnimation(animationId)
        holder.bind(getItem(position))
    }

    class ViewHolder(
        private val binding: LayoutItemHomeBinding,
        private val listener: OnRecyclerViewItemListener<Genre>?
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(genre: Genre) {
            binding.viewModel = ItemGenreViewModel(genre, listener)
            binding.executePendingBindings()
        }

        fun loadAnimation(animationId: Int) {
            binding.root.apply {
                containerItem.animation = AnimationUtils.loadAnimation(context, animationId)
            }
        }
    }

    class GenreDiffCallback : DiffUtil.ItemCallback<Genre>() {
        override fun areItemsTheSame(oldItem: Genre, newItem: Genre): Boolean {
            return oldItem.title == newItem.title
        }

        override fun areContentsTheSame(oldItem: Genre, newItem: Genre): Boolean {
            return oldItem == newItem
        }
    }
}
