package com.sunasterisk.music_72.screen.fragment.tracks

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.sunasterisk.music_72.R
import com.sunasterisk.music_72.data.anotation.GenreName
import com.sunasterisk.music_72.data.model.Track
import com.sunasterisk.music_72.data.source.local.TrackLocalDataSource
import com.sunasterisk.music_72.data.source.remote.TrackRemoteDataSource
import com.sunasterisk.music_72.data.source.remote.connection.RetrofitClient
import com.sunasterisk.music_72.data.source.repository.TrackRepositoryImplementor
import com.sunasterisk.music_72.databinding.FragmentTracksBinding
import com.sunasterisk.music_72.screen.adapter.TrackAdapter
import com.sunasterisk.music_72.screen.factory.ViewModelFactory
import com.sunasterisk.music_72.screen.fragment.playtrack.PlayTrackFragment
import com.sunasterisk.music_72.utils.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_tracks.*

class TracksFragment : Fragment(), View.OnClickListener,
    OnRecyclerViewItemListener<Track>, OnDownloadItemListener {
    private lateinit var binding: FragmentTracksBinding
    private val adapter: TrackAdapter by lazy { TrackAdapter() }
    private var genreName: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_tracks,
                container, false)
        createViewModel()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initData()
    }

    private fun initView() {
        (activity as? AppCompatActivity)?.run {
            setupToolbar(toolBarTracks)
        }
        activity?.navigationBottomHome?.visibility = View.GONE
        buttonBack.setOnClickListener(this)
        adapter.setOnItemClickListener(this, this)
        recyclerViewTracks.adapter = adapter
    }

    private fun initData() {
        genreName = arguments?.getString(ARGUMENT_GENRE_KEY, GenreName.ALL_TRACK)
        genreName?.let { it ->
            MusicUtils.getGenreImage(it)?.let {
                imageGenre.setImageResource(it)
            }
            binding.viewModel?.getTracks(it)
        }
        bindDataToView()
    }

    private fun bindDataToView() {
        binding.viewModel?.tracks?.observe(this, Observer {
            textTotalTrack.text = it.size.toString()
            textDescriptionTotalTrack.text =
                binding.viewModel?.getDescriptionTotalTrack(it.size)
            adapter.submitList(it)
        })
        binding.viewModel?.error?.observe(this, Observer {
            Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
        })
    }

    private fun createViewModel() {
        binding.viewModel =
            ViewModelProviders.of(
                this,
                ViewModelFactory{
                    TracksViewModel(
                        TrackRepositoryImplementor(
                            TrackLocalDataSource(),
                            TrackRemoteDataSource(RetrofitClient())
                        )
                    )
                }
            ).get(TracksViewModel::class.java)
    }

    override fun onClick(v: View?) {
        (activity as? AppCompatActivity)?.apply {
            supportFragmentManager.apply {
                popBackStack()
            }
            navigationBottomHome.visibility = View.VISIBLE
        }
    }

    override fun onItemClick(data: Track) {
        (activity as AppCompatActivity).apply {
            replaceFragmentToActivity(
                supportFragmentManager,
                PlayTrackFragment.newInstance(data.id),
                R.id.container
            )
        }
    }

    override fun onItemDownloadClick(data: Track) {}

    companion object {
        private const val ARGUMENT_GENRE_KEY = "ARGUMENT_GENRE_KEY"

        fun newInstance(genre: String) =
            TracksFragment().apply {
                arguments = bundleOf(ARGUMENT_GENRE_KEY to genre)
            }
    }
}
