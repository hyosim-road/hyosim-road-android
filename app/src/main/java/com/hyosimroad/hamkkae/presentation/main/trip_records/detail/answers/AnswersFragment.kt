package com.hyosimroad.hamkkae.presentation.main.trip_records.detail.answers

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.hyosimroad.hamkkae.databinding.FragmentTripRecordsDetailAnswersBinding
import timber.log.Timber

class AnswersFragment: Fragment() {
    private var _binding: FragmentTripRecordsDetailAnswersBinding? = null
    private val binding: FragmentTripRecordsDetailAnswersBinding
        get() = requireNotNull(_binding) { "detail overview fragment is null" }
    private val answersViewModel: AnswersViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTripRecordsDetailAnswersBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Timber.d("Overview onViewCreated. rootSize=${view.width}x${view.height}")
        setting()
    }

    private fun setting() {
        val answersAdapter = AnswersAdapter()
        binding.rvAnswers.adapter = answersAdapter
        answersAdapter.submitList(answersViewModel.answerList)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}