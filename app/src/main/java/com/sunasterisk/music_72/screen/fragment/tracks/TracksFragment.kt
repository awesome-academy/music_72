package com.sunasterisk.music_72.screen.fragment.tracks

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import com.sunasterisk.music_72.R
import com.sunasterisk.music_72.data.anotation.GenreName
import kotlinx.android.synthetic.main.activity_main.*

class TracksFragment : Fragment() {
    private var genreName: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_tracks, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initData()
    }

    private fun initView() {
        activity?.navigationBottomHome?.visibility = View.GONE
    }

    private fun initData() {
        genreName = arguments?.getString(ARGUMENT_GENRE_KEY, GenreName.ALL_TRACK)
    }

    companion object {
        private const val ARGUMENT_GENRE_KEY = "GENRE_KEY"

        fun getInstance(genre: String) =
            TracksFragment().apply {
                arguments = bundleOf(ARGUMENT_GENRE_KEY to genre)
            }
    }
}
