package com.sunasterisk.music_72.screen.fragment.playtrack

import android.net.Uri
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.sunasterisk.music_72.R
import com.sunasterisk.music_72.data.model.Track
import com.sunasterisk.music_72.data.source.local.TrackLocalDataSource
import com.sunasterisk.music_72.data.source.remote.TrackRemoteDataSource
import com.sunasterisk.music_72.data.source.remote.connection.RetrofitClient
import com.sunasterisk.music_72.data.source.repository.TrackRepositoryImplementor
import com.sunasterisk.music_72.databinding.FragmentPlayTrackBinding
import com.sunasterisk.music_72.screen.factory.ViewModelFactory
import com.sunasterisk.music_72.utils.BindingUtils
import com.sunasterisk.music_72.utils.MusicUtils
import com.sunasterisk.music_72.utils.media.MediaManager
import com.sunasterisk.music_72.utils.setupToolbar
import kotlinx.android.synthetic.main.fragment_play_track.*

class PlayTrackFragment : Fragment(), View.OnClickListener {
    private lateinit var binding: FragmentPlayTrackBinding
    private lateinit var mediaManager: MediaManager
    private var track: Track? = null
    private var tracks = mutableListOf<Track>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater,
            R.layout.fragment_play_track, container, false)
        createViewModel()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initData()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        context?.let {
            mediaManager = MediaManager.getInstance(it)
        }
        initMediaData()
    }

    private fun initMediaData() {
        track?.let {
            mediaManager.setCurrentTrack(it)
            mediaManager.change(it)
        }
        tracks?.let { mediaManager.setTracks(it) }
    }

    private fun initView() {
        (activity as AppCompatActivity).run {
            setupToolbar(toolBarPlayTrack)
        }
        setHasOptionsMenu(true)
        buttonBack.setOnClickListener(this)
    }

    private fun initData() {
        arguments?.apply {
            track = getParcelable(ARGUMENT_PLAY_TRACK_KEY)
            getParcelableArrayList<Track>(ARGUMENT_PLAY_TRACK_LIST_KEY)?.let { tracks.addAll(it) }
        }
        track?.let { binding.viewModel?.getTrackbyId(it.id) }
        bindDataToView()
    }

    private fun bindDataToView() {
        binding.viewModel?.track?.observe(this, Observer {
            BindingUtils.image(imageLogoUser, it.user.avatarUrl)
            textTrackRepeatCount.text = it.playbackCount.toString()
            textTrackName.text = it.title
            textUserName.text = it.user.username
        })
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_download, menu)
    }

    private fun createViewModel() {
        binding.viewModel =
            ViewModelProviders.of(
                this,
                ViewModelFactory{
                    PlayTrackViewModel(
                        TrackRepositoryImplementor(
                            TrackLocalDataSource(),
                            TrackRemoteDataSource(RetrofitClient())
                        )
                    )
                }
            ).get(PlayTrackViewModel::class.java)
    }

    companion object {
        private const val ARGUMENT_PLAY_TRACK_KEY = "ARGUMENT_PLAY_TRACK_KEY"
        private const val ARGUMENT_PLAY_TRACK_LIST_KEY = "ARGUMENT_PLAY_TRACK_LIST_KEY"

        fun newInstance(track: Track, tracks: MutableList<Track>) =
            PlayTrackFragment().apply {
                arguments = bundleOf(
                    ARGUMENT_PLAY_TRACK_KEY to track,
                    ARGUMENT_PLAY_TRACK_LIST_KEY to tracks
                )
            }
    }

    override fun onClick(v: View?) {
        (activity as AppCompatActivity).supportFragmentManager.apply {
            popBackStack()
        }
    }
}
