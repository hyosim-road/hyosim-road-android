package com.hyosim.hamkkae.presentation.main.trip_detail.schedule

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.hyosim.hamkkae.databinding.FragmentTripDetailScheduleBinding
import com.hyosim.hamkkae.extension.conversation.GetAnswersState
import com.hyosim.hamkkae.presentation.main.family_conversation.adapter.ConversationAdapter
import com.hyosim.hamkkae.presentation.main.trip_detail.TripDetailViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import timber.log.Timber

@AndroidEntryPoint
class TripDetailScheduleFragment: Fragment() {
    private var _binding: FragmentTripDetailScheduleBinding? = null
    private val binding: FragmentTripDetailScheduleBinding
        get() = requireNotNull(_binding) { "home fragment is null" }
    private val tripDetailScheduleViewModel: TripDetailScheduleViewModel by viewModels()
    private val tripDetailViewModel: TripDetailViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTripDetailScheduleBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Timber.d("recommendCourseFragment started!")
        setting()
    }

    private fun setting() {
        getSchedules()
        getAnswers(tripDetailViewModel.course.value!!.id)
    }

    private fun getSchedules(){
        val tripDetailScheduleAdapter = TripDetailScheduleAdapter()
        binding.rvSchedule.adapter = tripDetailScheduleAdapter
        tripDetailScheduleAdapter.submitList(tripDetailViewModel.course.value!!.itinerary)
    }

    private fun getAnswers(tripId:Int) {
        lifecycleScope.launch {
            tripDetailScheduleViewModel.getAnswersState.collect { state ->
                when (state) {
                    is GetAnswersState.Success -> {
                        val ConversationAdapter = ConversationAdapter()
                        binding.rvAnswers.adapter = ConversationAdapter

                        val answerList =
                            state.question.filter { !it.answerContent.isNullOrEmpty() }
                        ConversationAdapter.submitList(answerList)
                    }

                    is GetAnswersState.Loading -> {}
                    is GetAnswersState.Error -> {

                    }
                }
            }
        }

        tripDetailScheduleViewModel.getAnswers(tripId)

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}