package com.sunasterisk.music_72.screen.fragment.audio

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.sunasterisk.music_72.R
import com.sunasterisk.music_72.data.model.Track
import com.sunasterisk.music_72.data.source.local.TrackLocalDataSource
import com.sunasterisk.music_72.data.source.remote.TrackRemoteDataSource
import com.sunasterisk.music_72.data.source.remote.connection.RetrofitClient
import com.sunasterisk.music_72.data.source.repository.TrackRepositoryImplementor
import com.sunasterisk.music_72.databinding.FragmentAudioBinding
import com.sunasterisk.music_72.screen.adapter.TrackAdapter
import com.sunasterisk.music_72.screen.factory.ViewModelFactory
import com.sunasterisk.music_72.screen.fragment.playtrack.PlayTrackFragment
import com.sunasterisk.music_72.utils.OnRecyclerViewItemListener
import com.sunasterisk.music_72.utils.replaceFragmentToActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_audio.*

class AudioFragment : Fragment(), OnRecyclerViewItemListener<Track> {
    private lateinit var binding: FragmentAudioBinding
    private val adapter: TrackAdapter by lazy { TrackAdapter() }
    private val audios = mutableListOf<Track>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_audio, container, false)
        createViewModel()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initData()
    }

    private fun initView() {
        adapter.setOnItemClickListener(this, null)
        recyclerViewAudios.adapter = adapter
    }

    private fun initData() {
        binding.viewModel?.getAudios()
        binding.viewModel?.audios?.observe(this, Observer {
            adapter.submitList(it)
            audios.addAll(it)
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
                    AudioViewModel(
                        TrackRepositoryImplementor(
                            TrackLocalDataSource(), TrackRemoteDataSource(RetrofitClient())
                        )
                    )
                }
            ).get(AudioViewModel::class.java)
    }

    override fun onItemClick(data: Track) {

        (activity as AppCompatActivity).apply {
            navigationBottomHome.visibility = View.GONE
            replaceFragmentToActivity(
                supportFragmentManager,
                PlayTrackFragment.newInstance(data, audios, AudioFragment::class.java.simpleName),
                R.id.container
            )
        }
    }

    companion object {
        fun newInstance() = AudioFragment()
    }
}
