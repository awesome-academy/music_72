package com.sunasterisk.music_72.screen.fragment.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.sunasterisk.music_72.R
import com.sunasterisk.music_72.data.anotation.GenreName
import com.sunasterisk.music_72.data.model.Genre
import com.sunasterisk.music_72.data.source.local.TrackLocalDataSource
import com.sunasterisk.music_72.data.source.remote.TrackRemoteDataSource
import com.sunasterisk.music_72.data.source.remote.connection.RetrofitClient
import com.sunasterisk.music_72.data.source.repository.TrackRepositoryImplementor
import com.sunasterisk.music_72.databinding.FragmentHomeBinding
import com.sunasterisk.music_72.screen.adapter.GenreAdapter
import com.sunasterisk.music_72.screen.factory.ViewModelFactory
import com.sunasterisk.music_72.screen.fragment.tracks.TracksFragment
import com.sunasterisk.music_72.utils.OnRecyclerViewItemListener
import com.sunasterisk.music_72.utils.addFragmentToActivity
import com.sunasterisk.music_72.utils.setupToolbar
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment(), OnRecyclerViewItemListener<Genre> {
    private lateinit var binding: FragmentHomeBinding
    private val genres = mutableListOf<Genre>(
        Genre(GenreName.ALL_TRACK, R.drawable.bg_all_music),
        Genre(GenreName.ROCK, R.drawable.bg_rock),
        Genre(GenreName.COUNTRY, R.drawable.bg_country),
        Genre(GenreName.CLASSICAL, R.drawable.bg_classical),
        Genre(GenreName.AMBIENT, R.drawable.bg_ambient)
    )
    private val adapter: GenreAdapter by lazy { GenreAdapter().apply { submitList(genres) } }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
         binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        createViewModel()
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initView()
    }

    private fun initView() {
        (activity as AppCompatActivity).run {
            setupToolbar(toolBarHome)
        }
        adapter.setOnItemClickListener(this)
        recyclerView.adapter = adapter
    }

    override fun onItemClick(data: Genre) {
        (activity as AppCompatActivity).apply {
            addFragmentToActivity(
                supportFragmentManager,
                TracksFragment.getInstance(data.title),
                R.id.container
            )
        }
    }

    private fun createViewModel() {
        binding.viewModel =
            ViewModelProviders.of(
                this,
                ViewModelFactory{
                    HomeViewModel(
                        TrackRepositoryImplementor(
                            TrackLocalDataSource(), TrackRemoteDataSource(RetrofitClient())
                        )
                    )
                }
            ).get(HomeViewModel::class.java)
    }

    companion object {
        fun newInstance() = HomeFragment()
    }
}
