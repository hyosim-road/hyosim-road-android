package com.hyosim.hamkkae.presentation.main.plan.select

import android.animation.ObjectAnimator
import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.hyosim.hamkkae.R
import com.hyosim.hamkkae.databinding.FragmentSelectTripInfoBinding
import com.hyosim.hamkkae.presentation.main.plan.PlanViewModel
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

@AndroidEntryPoint
class SelectTripInfoFragment : Fragment() {
    private var _binding: FragmentSelectTripInfoBinding? = null
    private val binding: FragmentSelectTripInfoBinding
        get() = requireNotNull(_binding) { "plan-trip-info fragment is null" }
    private val planViewModel: PlanViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSelectTripInfoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Timber.d("selectTripInfoFragment started!")

        setting()
        setDate()
        setPerson()
        setBudget()
        clickNextButton()
        observeViewModel()
    }

    private fun setting() {
        binding.pbTrip.progress = 25

        val animator = ObjectAnimator.ofInt(binding.pbTrip, "progress", 0, 25)
        animator.duration = 1000
        animator.interpolator = AccelerateDecelerateInterpolator()
        animator.start()

        binding.clSelectDate.strokeColor =
            ContextCompat.getColor(requireContext(), android.R.color.transparent)
        binding.clSelectPerson.strokeColor =
            ContextCompat.getColor(requireContext(), android.R.color.transparent)
        binding.clSelectBudget.strokeColor =
            ContextCompat.getColor(requireContext(), android.R.color.transparent)

        binding.btnNext.isEnabled = false
        binding.btnNext.isSelected = false
        binding.tvDone.visibility = View.GONE
    }

    private fun setDate() {
        binding.tvStartDate.setOnClickListener {
            val today = Calendar.getInstance()
            showDatePicker(minDate = today.timeInMillis) { uiDate, apiDate ->
                binding.tvStartDate.text = uiDate        // 화면 표시용
                planViewModel.setDepartureDate(apiDate)  // API 전송용
                planViewModel.setArrivalDate("")         // 초기화
                Timber.d("api: $apiDate")
            }
        }

        binding.tvEndDate.setOnClickListener {
            val minDateMillis = binding.tvStartDate.text.toString().toMillis()
            showDatePicker(minDate = minDateMillis) { uiDate, apiDate ->
                binding.tvEndDate.text = uiDate         // 화면 표시용
                planViewModel.setArrivalDate(apiDate)   // API 전송용
            }
        }
    }

    private fun showDatePicker(
        minDate: Long? = null,
        onDateSelected: (uiDate: String, apiDate: String) -> Unit
    ) {
        val uiFormat = SimpleDateFormat("yyyy.MM.dd (E)", Locale.KOREA)
        val apiFormat = SimpleDateFormat("yyyy-MM-dd", Locale.KOREA)
        val today = Calendar.getInstance()

        val dialog = DatePickerDialog(
            requireContext(),
            R.style.MyDatePickerDialogTheme,
            { _, year, month, dayOfMonth ->
                val date = Calendar.getInstance().apply {
                    set(year, month, dayOfMonth)
                }
                onDateSelected(uiFormat.format(date.time), apiFormat.format(date.time))
            },
            today.get(Calendar.YEAR),
            today.get(Calendar.MONTH),
            today.get(Calendar.DAY_OF_MONTH)
        )

        minDate?.let { dialog.datePicker.minDate = it }
        dialog.show()
    }

    private fun String.toMillis(): Long {
        val sdf = SimpleDateFormat("yyyy.MM.dd (E)", Locale.KOREA)
        return sdf.parse(this)?.time ?: System.currentTimeMillis()
    }

    private fun updatePersonBorder(count: Int) {
        val color = if (count > 0) R.color.hover_orange else android.R.color.transparent
        binding.clSelectPerson.strokeColor =
            ContextCompat.getColor(requireContext(), color)
    }

    private fun setPerson() {
        binding.btnDecrease.setOnClickListener { planViewModel.decreasePeople() }
        binding.btnIncrease.setOnClickListener { planViewModel.increasePeople() }
    }

    private fun setBudget() {
        val budgetViews = listOf(binding.tvBudgetLow, binding.tvBudgetMid, binding.tvBudgetHigh)
        budgetViews.forEach { view ->
            view.setOnClickListener {
                when (view.id) {
                    binding.tvBudgetLow.id -> planViewModel.setBudgetRange("ECONOMY")
                    binding.tvBudgetMid.id -> planViewModel.setBudgetRange("STANDARD")
                    binding.tvBudgetHigh.id -> planViewModel.setBudgetRange("LUXURY")
                }
            }
        }
    }

    private fun checkAllInfoEntered(
        startDate: String?,
        endDate: String?,
        people: Int?,
        budget: String?
    ) {
        val isDateSelected = !startDate.isNullOrEmpty() && !endDate.isNullOrEmpty()
        val isPersonSelected = (people ?: 0) > 0
        val isBudgetSelected = !budget.isNullOrEmpty()

        val allInfoEntered = isDateSelected && isPersonSelected && isBudgetSelected

        binding.btnNext.isEnabled = allInfoEntered
        binding.btnNext.isSelected = allInfoEntered
        binding.tvDone.visibility = if (allInfoEntered) View.VISIBLE else View.GONE
    }

    private fun clickNextButton() {
        binding.btnNext.setOnClickListener {
            if (binding.btnNext.isEnabled) {
                findNavController().navigate(
                    R.id.action_selectTripInfoFragment_to_selectTripStyleFragment
                )
            }
        }
    }

    private fun observeViewModel() {
        planViewModel.departureDate.observe(viewLifecycleOwner) { date ->
            binding.tvStartDate.text = if (date.isNullOrEmpty()) "시작 날짜" else binding.tvStartDate.text
            binding.tvEndDate.isEnabled = !date.isNullOrEmpty()
            checkAllInfoEntered(date, planViewModel.arrivalDate.value, planViewModel.numberOfPeople.value, planViewModel.budgetRange.value)
        }

        planViewModel.arrivalDate.observe(viewLifecycleOwner) { date ->
            binding.tvEndDate.text = if (date.isNullOrEmpty()) "종료 날짜" else binding.tvEndDate.text
            val colorRes = if (!date.isNullOrEmpty()) R.color.hover_orange else android.R.color.transparent
            binding.clSelectDate.strokeColor = ContextCompat.getColor(requireContext(), colorRes)
            checkAllInfoEntered(planViewModel.departureDate.value, date, planViewModel.numberOfPeople.value, planViewModel.budgetRange.value)
        }

        planViewModel.numberOfPeople.observe(viewLifecycleOwner) { count ->
            binding.tvPersonCount.text = "$count 명"
            updatePersonBorder(count)
            checkAllInfoEntered(planViewModel.departureDate.value, planViewModel.arrivalDate.value, count, planViewModel.budgetRange.value)
        }

        planViewModel.budgetRange.observe(viewLifecycleOwner) { budget ->
            val budgetViews = listOf(binding.tvBudgetLow, binding.tvBudgetMid, binding.tvBudgetHigh)
            budgetViews.forEach { view ->
                val isSelected = when (view.id) {
                    binding.tvBudgetLow.id -> budget == "ECONOMY"
                    binding.tvBudgetMid.id -> budget == "STANDARD"
                    binding.tvBudgetHigh.id -> budget == "HIGH"
                    else -> false
                }
                view.isSelected = isSelected
                val backgroundRes =
                    if (isSelected) R.drawable.bg_selected_info_box else R.drawable.bg_select_info_box
                view.setBackgroundResource(backgroundRes)
            }
            val colorRes =
                if (!budget.isNullOrEmpty()) R.color.hover_orange else android.R.color.transparent
            binding.clSelectBudget.strokeColor = ContextCompat.getColor(requireContext(), colorRes)
            checkAllInfoEntered(planViewModel.departureDate.value, planViewModel.arrivalDate.value, planViewModel.numberOfPeople.value, budget)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}