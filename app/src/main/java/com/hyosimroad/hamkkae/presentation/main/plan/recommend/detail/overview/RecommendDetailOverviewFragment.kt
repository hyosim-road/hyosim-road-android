package com.hyosimroad.hamkkae.presentation.main.plan.recommend.detail.overview

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.hyosimroad.hamkkae.databinding.FragmentRecommendDetailOverviewBinding
import com.hyosimroad.hamkkae.presentation.main.plan.recommend.detail.overview.adapter.RecommendDetailOverviewHighlightAdapter
import com.hyosimroad.hamkkae.presentation.main.plan.recommend.detail.overview.adapter.RecommendDetailOverviewPreviewAdapter
import timber.log.Timber

class RecommendDetailOverviewFragment: Fragment() {
    private var _binding: FragmentRecommendDetailOverviewBinding? = null
    private val binding: FragmentRecommendDetailOverviewBinding
        get() = requireNotNull(_binding) { "detail overview fragment is null" }

    private val detailOverviewViewModel: RecommendDetailOverviewViewModel by viewModels()


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
        getHighlights()
        getPreviews()
    }

    private fun getHighlights(){
        val highlightsAdapter = RecommendDetailOverviewHighlightAdapter()
        binding.rvHighlights.adapter = highlightsAdapter
        highlightsAdapter.submitList(detailOverviewViewModel.highlightsList)
    }

    private fun getPreviews(){
        val previewAdapter = RecommendDetailOverviewPreviewAdapter()
        binding.rvPreview.adapter = previewAdapter
        previewAdapter.submitList(detailOverviewViewModel.previewList)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}