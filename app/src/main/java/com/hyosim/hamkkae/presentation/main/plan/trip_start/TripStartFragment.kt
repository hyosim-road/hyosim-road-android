package com.hyosim.hamkkae.presentation.main.plan.trip_start

import android.animation.ObjectAnimator
import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.hyosim.hamkkae.R
import com.hyosim.hamkkae.databinding.FragmentTripStartBinding
import com.hyosim.hamkkae.domain.model.TodaySchedule
import com.hyosim.hamkkae.presentation.main.home.adapter.today_schedule.TodayScheduleAdapter
import com.hyosim.hamkkae.presentation.main.plan.PlanViewModel
import timber.log.Timber
import java.text.SimpleDateFormat
import java.util.Locale
import kotlin.getValue

class TripStartFragment : Fragment() {
    private var _binding: FragmentTripStartBinding? = null
    private val binding: FragmentTripStartBinding
        get() = requireNotNull(_binding) { "home fragment is null" }

    //private val tripStartViewModel: TripStartViewModel by viewModels()
    private val args: TripStartFragmentArgs by navArgs()
    private val planViewModel: PlanViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTripStartBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Timber.d("recommendCourseFragment started!")
        setting()
    }

    private fun setting() {
        binding.pbTrip.progress = 75

        // 애니메이션 적용
        val animator = ObjectAnimator.ofInt(binding.pbTrip, "progress", 75, 100)
        animator.duration = 1000 // 1초
        animator.interpolator = AccelerateDecelerateInterpolator()
        animator.start()

        binding.btnStart.isSelected = true

        showTrip()
        showPreview()
        clickStart()
    }

    private fun showTrip() {
        val planData = planViewModel.toRequestDto()
        with(binding) {
            tvTripName.text = "여행 이름"
            tvDuring.text = getTripDuration(planData.departureDate, planData.arrivalDate)
            tvPersonnel.text = planData.numberOfPeople.toString()
            tvDate.text = getString(
                R.string.main_trip_record_during,
                planData.departureDate,
                planData.arrivalDate
            )
        }
    }

    private fun getTripDuration(departure: String, arrival: String): String {
        val format = SimpleDateFormat("yyyy-MM-dd", Locale.KOREA) // 서버에서 내려주는 형식 맞추기
        val depDate = format.parse(departure)
        val arrDate = format.parse(arrival)

        if (depDate != null && arrDate != null) {
            val diffInMillis = arrDate.time - depDate.time
            val days = (diffInMillis / (1000 * 60 * 60 * 24)).toInt() + 1
            val nights = if (days > 0) days - 1 else 0

            return "${nights}박 ${days}일"
        }
        return ""
    }

    private fun showPreview() {
        val course = args.course
        val attractionsList = course?.itinerary?.firstOrNull()?.attractions ?: emptyList()

        val firstDay = attractionsList.mapIndexed { index, attraction ->
            TodaySchedule(
                id = index + 1,
                place = attraction.name,
                keyword = attraction.address, // or phone, price 등 적절한 필드 매핑
                startTime = attraction.startTime.substring(
                    11,
                    16
                ), // "2025-09-10T10:00:00" -> "10:00"
                endTime = attraction.endTime.substring(11, 16),
                status = "BEFORE_STARTING"
            )
        }
        val adapter = TripStartAdapter(true)
        binding.rvPreview.adapter = adapter
        adapter.submitList(firstDay)
    }

    private fun clickStart() {
        binding.btnStart.setOnClickListener {
            goMain()
        }

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            goMain()
        }
    }

    private fun goMain() {
        requireActivity().setResult(Activity.RESULT_OK) // ✅ 결과값 설정
        requireActivity().finish()                     // ✅ PlanActivity 종료
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}