package com.sunasterisk.music_72.screen.fragment.playtrack

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import com.sunasterisk.music_72.R

class PlayTrackFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_play_track, container, false)
    }

    companion object {
        private const val ARGUMENT_PLAY_TRACK_KEY = "PLAY_TRACK_KEY"

        fun newInstance(trackId: Int) =
            PlayTrackFragment().apply {
                arguments = bundleOf(ARGUMENT_PLAY_TRACK_KEY to trackId)
            }
    }
}
