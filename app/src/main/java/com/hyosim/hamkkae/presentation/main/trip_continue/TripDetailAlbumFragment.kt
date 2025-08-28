package com.hyosim.hamkkae.presentation.main.trip_continue

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.hyosim.hamkkae.databinding.FragmentTripDetailAlbumBinding
import timber.log.Timber

class TripDetailAlbumFragment: Fragment() {
    private var _binding: FragmentTripDetailAlbumBinding? = null
    private val binding: FragmentTripDetailAlbumBinding
        get() = requireNotNull(_binding) { "home fragment is null" }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTripDetailAlbumBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Timber.d("recommendCourseFragment started!")
        setting()
    }

    private fun setting() {

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}