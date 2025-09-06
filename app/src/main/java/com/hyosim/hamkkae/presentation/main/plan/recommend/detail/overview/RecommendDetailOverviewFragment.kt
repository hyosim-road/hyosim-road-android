package com.hyosim.hamkkae.presentation.main.plan.recommend.detail.overview

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.hyosim.hamkkae.databinding.FragmentRecommendDetailOverviewBinding
import com.hyosim.hamkkae.presentation.main.plan.recommend.detail.RecommendDetailViewModel
import com.hyosim.hamkkae.presentation.main.plan.recommend.detail.overview.adapter.ItineraryAdapter
import com.hyosim.hamkkae.presentation.main.plan.recommend.detail.overview.adapter.RecommendDetailOverviewHighlightAdapter
import com.hyosim.hamkkae.presentation.main.plan.recommend.detail.overview.adapter.RecommendDetailOverviewPreviewAdapter
import timber.log.Timber
import kotlin.getValue

class RecommendDetailOverviewFragment: Fragment() {
    private var _binding: FragmentRecommendDetailOverviewBinding? = null
    private val binding: FragmentRecommendDetailOverviewBinding
        get() = requireNotNull(_binding) { "detail overview fragment is null" }

    private val detailOverviewViewModel: RecommendDetailOverviewViewModel by viewModels()
    private val detailViewModel: RecommendDetailViewModel by viewModels({requireParentFragment()})


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRecommendDetailOverviewBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Timber.d("Overview onViewCreated. rootSize=${view.width}x${view.height}")
        setting()
    }

    private fun setting() {
        //getHighlights()
        getPreviews()
    }

    /*private fun getHighlights(){
        val highlightsAdapter = RecommendDetailOverviewHighlightAdapter()
        binding.rvHighlights.adapter = highlightsAdapter
        highlightsAdapter.submitList(detailOverviewViewModel.highlightsList)
    }*/

    private fun getPreviews(){
        val previewAdapter = ItineraryAdapter()
        binding.rvPreview.adapter = previewAdapter
        previewAdapter.submitList(detailViewModel.course!!.itinerary)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}