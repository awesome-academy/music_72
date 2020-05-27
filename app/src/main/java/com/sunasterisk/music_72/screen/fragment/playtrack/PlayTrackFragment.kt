package com.sunasterisk.music_72.screen.fragment.playtrack

import android.os.Bundle
import android.os.Handler
import android.view.*
import android.widget.SeekBar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.sunasterisk.music_72.R
import com.sunasterisk.music_72.data.anotation.State
import com.sunasterisk.music_72.data.model.Track
import com.sunasterisk.music_72.data.source.local.TrackLocalDataSource
import com.sunasterisk.music_72.data.source.remote.TrackRemoteDataSource
import com.sunasterisk.music_72.data.source.remote.connection.RetrofitClient
import com.sunasterisk.music_72.data.source.repository.TrackRepositoryImplementor
import com.sunasterisk.music_72.databinding.FragmentPlayTrackBinding
import com.sunasterisk.music_72.screen.MainActivity
import com.sunasterisk.music_72.screen.factory.ViewModelFactory
import com.sunasterisk.music_72.screen.service.PlayTrackService
import com.sunasterisk.music_72.utils.BindingUtils
import com.sunasterisk.music_72.utils.ChangeTrackListener
import com.sunasterisk.music_72.utils.CommonUtils
import com.sunasterisk.music_72.utils.setupToolbar
import kotlinx.android.synthetic.main.fragment_play_track.*

class PlayTrackFragment : Fragment(), ChangeTrackListener, View.OnClickListener{
    private lateinit var binding: FragmentPlayTrackBinding
    private var playTrackService: PlayTrackService? = null
    private var handlerSyncTime: Handler? = null
    private var track: Track? = null
    private var tracks = mutableListOf<Track>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_play_track, container, false
        )
        createViewModel()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initData()
        handelEventClick()
        handlerSyncTime()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        playTrackService = (activity as MainActivity).getService()
        initMediaData()
        seekBarTime.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, isUser: Boolean) {
                if (isUser) playTrackService?.seekTo(progress)
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {
            }

            override fun onStopTrackingTouch(p0: SeekBar?) {
            }

        })
    }

    private fun handelEventClick() {
        cbActionPlayTrack.setOnClickListener { playAndPause() }
        buttonNext.setOnClickListener { nextTrack() }
        buttonPrevious.setOnClickListener { preTrack() }
    }

    private fun preTrack() {
        playTrackService?.preTrack()
    }

    private fun nextTrack() {
        playTrackService?.nextTrack()
    }

    private fun playAndPause() {
        playTrackService?.playOrPause()
    }

    private fun initMediaData() {
        track?.let {
            playTrackService?.setCurTrack(it)
            playTrackService?.changeTrack(it)
        }
        tracks?.let { playTrackService?.setTracks(it) }
        playTrackService!!.setChangeTrackListener(this)
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

    private fun handlerSyncTime() {
        handlerSyncTime = Handler()
        handlerSyncTime!!.postDelayed(object : Runnable {
            override fun run() {
                if (playTrackService!!.getState() == State.PLAY) {
                    val totalTime = playTrackService?.getDuration()!!
                    val currentTime = playTrackService?.getCurrentDuration()!!
                    seekBarTime.progress =
                        ((currentTime / 1000) % (totalTime / 1000)) % (totalTime / 1000)
                    textCurrentDuration.text =
                        CommonUtils.convertTimeInMilisToString(currentTime.toLong())
                }
                handlerSyncTime!!.postDelayed(
                    this,
                    TIME_DELAY
                )
            }
        }, TIME_DELAY)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_download, menu)
    }

    private fun createViewModel() {
        binding.viewModel =
            ViewModelProviders.of(
                this,
                ViewModelFactory {
                    PlayTrackViewModel(
                        TrackRepositoryImplementor(
                            TrackLocalDataSource(),
                            TrackRemoteDataSource(RetrofitClient())
                        )
                    )
                }
            ).get(PlayTrackViewModel::class.java)
    }

    override fun onClick(v: View?) {
        (activity as AppCompatActivity).supportFragmentManager.apply {
            popBackStack()
        }
    }

    override fun onChangeState(state: Int) {
        if (state == State.PAUSE) {
            cbActionPlayTrack.setImageResource(R.drawable.ic_play_orange_100dp)
        } else {
            cbActionPlayTrack.setImageResource(R.drawable.ic_pause_orange_100dp)
        }
    }

    override fun onChangeTrackComplete() {
        seekBarTime.max = playTrackService?.getDuration()!! / 1000
        textDuration.text = CommonUtils
            .convertTimeInMilisToString(playTrackService?.getDuration()!!.toLong())
        track = playTrackService?.getCurTrack()
        track?.let { binding.viewModel?.getTrackbyId(it.id) }
    }

    companion object {
        private const val TIME_DELAY = 1000L
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
}
