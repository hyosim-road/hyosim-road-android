package com.hyosimroad.hamkkae.presentation.main.trip_records.detail.overview

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent
import com.hyosimroad.hamkkae.R
import com.hyosimroad.hamkkae.databinding.FragmentTripRecordsDetailOverviewBinding
import com.hyosimroad.hamkkae.presentation.main.trip_records.adapter.TextAdapter
import com.hyosimroad.hamkkae.presentation.main.trip_records.detail.answers.AnswersAdapter
import timber.log.Timber

class OverviewFragment: Fragment() {
    private var _binding: FragmentTripRecordsDetailOverviewBinding? = null
    private val binding: FragmentTripRecordsDetailOverviewBinding
        get() = requireNotNull(_binding) { "detail overview fragment is null" }

    private val overviewViewModel: OverviewViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTripRecordsDetailOverviewBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Timber.d("Overview onViewCreated. rootSize=${view.width}x${view.height}")
        setting()
    }

    private fun setting() {
        setKeywords()
    }

    private fun setKeywords(){
        val textAdapter = OverviewTextAdapter()

        val flexboxLayoutManager = FlexboxLayoutManager(requireContext()).apply {
            flexDirection = FlexDirection.ROW
            flexWrap = FlexWrap.WRAP
            justifyContent = JustifyContent.FLEX_START
        }

        binding.rvKeywords.layoutManager = flexboxLayoutManager
        binding.rvKeywords.adapter = textAdapter

        val modifyList = overviewViewModel.keywordsList.map{getString(R.string.photo_album_tag, it)}
        textAdapter.submitList(modifyList)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}