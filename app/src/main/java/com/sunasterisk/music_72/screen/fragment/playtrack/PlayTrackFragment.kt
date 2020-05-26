package com.sunasterisk.music_72.screen.fragment.playtrack

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.sunasterisk.music_72.R
import com.sunasterisk.music_72.data.model.Track
import com.sunasterisk.music_72.data.source.local.TrackLocalDataSource
import com.sunasterisk.music_72.data.source.remote.TrackRemoteDataSource
import com.sunasterisk.music_72.data.source.remote.connection.RetrofitClient
import com.sunasterisk.music_72.data.source.repository.TrackRepositoryImplementor
import com.sunasterisk.music_72.databinding.FragmentPlayTrackBinding
import com.sunasterisk.music_72.screen.factory.ViewModelFactory
import com.sunasterisk.music_72.utils.setupToolbar
import kotlinx.android.synthetic.main.fragment_play_track.*

class PlayTrackFragment : Fragment(){
    private lateinit var binding: FragmentPlayTrackBinding
    private var trackId = 0
    private var track: Track? = null

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

    private fun initView() {
        (activity as AppCompatActivity).run {
            setupToolbar(toolBarPlayTrack)
        }
        setHasOptionsMenu(true)
    }

    private fun initData() {
        arguments?.apply {
            trackId = getInt(ARGUMENT_PLAY_TRACK_KEY)
        }
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

        fun newInstance(trackId: Int) =
            PlayTrackFragment().apply {
                arguments = bundleOf(ARGUMENT_PLAY_TRACK_KEY to trackId)
            }
    }
}
