package com.hyosimroad.hamkkae.presentation.main.trip_continue.album

import android.graphics.Rect
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.hyosimroad.hamkkae.R
import com.hyosimroad.hamkkae.databinding.FragmentTripDetailAlbumBinding

class TripDetailAlbumFragment: Fragment() {
    private var _binding: FragmentTripDetailAlbumBinding? = null
    private val binding: FragmentTripDetailAlbumBinding
        get() = requireNotNull(_binding) { "home fragment is null" }
    private val tripDetailAlbumViewModel:TripDetailAlbumViewModel by viewModels()

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
        setting()
    }

    private fun setting(){
        getTips()
        binding.tvPlace.text = getString(R.string.preview_place_mock)
        binding.tvPhotoCount.text = "1장"
        binding.ivImage.load(R.drawable.image_trip)
        binding.tvDescription.text = "가족사진"
    }

    private fun getTips(){
        val tipsAdpater = TextAdapter()
        binding.rvTips.adapter = tipsAdpater
        tipsAdpater.submitList(tripDetailAlbumViewModel.tipList)

        val gap6 = resources.getDimensionPixelSize(R.dimen.dp6)
        binding.rvTips.addItemDecoration(SimpleGapDecoration(gap6))
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding=null
    }

    inner class SimpleGapDecoration(private val gap: Int) : RecyclerView.ItemDecoration() {
        override fun getItemOffsets(outRect: Rect, v: View, p: RecyclerView, s: RecyclerView.State) {
            outRect.set(0, 0, 0, gap)
        }
    }
}