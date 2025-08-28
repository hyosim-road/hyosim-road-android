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
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.hyosim.hamkkae.R
import com.hyosim.hamkkae.databinding.FragmentSelectTripInfoBinding
import timber.log.Timber
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class SelectTripInfoFragment : Fragment() {
    private var _binding: FragmentSelectTripInfoBinding? = null
    private val binding: FragmentSelectTripInfoBinding
        get() = requireNotNull(_binding) { "plan-trip-info fragment is null" }

    private val viewModel: SelectTripInfoViewModel by viewModels()

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

    private fun observeViewModel() {
        viewModel.startDate.observe(viewLifecycleOwner) { date ->
            binding.tvStartDate.text = date ?: "시작 날짜"
            binding.tvEndDate.isEnabled = (date != null)
            checkAllInfoEntered()
        }

        viewModel.endDate.observe(viewLifecycleOwner) { date ->
            binding.tvEndDate.text = date ?: "종료 날짜"
            val colorRes = if (date != null) R.color.hover_orange else android.R.color.transparent
            binding.clSelectDate.strokeColor = ContextCompat.getColor(requireContext(), colorRes)
            checkAllInfoEntered()
        }

        viewModel.personCount.observe(viewLifecycleOwner) { count ->
            binding.tvPersonCount.text = "${count}명"
            updatePersonBorder()
            checkAllInfoEntered()
        }

        viewModel.selectedBudget.observe(viewLifecycleOwner) { selectedBudgetId ->
            val budgetViews = listOf(binding.tvBudgetLow, binding.tvBudgetMid, binding.tvBudgetHigh)
            budgetViews.forEach { view ->
                val isSelected = view.id == selectedBudgetId
                view.isSelected = isSelected
                val backgroundRes = if (isSelected) R.drawable.bg_selected_info_box else R.drawable.bg_select_info_box
                view.setBackgroundResource(backgroundRes)
            }
            val colorRes = if (selectedBudgetId != null) R.color.hover_orange else android.R.color.transparent
            binding.clSelectBudget.strokeColor = ContextCompat.getColor(requireContext(), colorRes)
            checkAllInfoEntered()
        }
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
            showDatePicker(minDate = today.timeInMillis) { selectedDate ->
                viewModel.setStartDate(selectedDate)
                viewModel.setEndDate(null)
            }
        }

        binding.tvEndDate.setOnClickListener {
            val minDateMillis = viewModel.startDate.value?.toMillis()
            if (minDateMillis != null) {
                showDatePicker(minDate = minDateMillis) { selectedDate ->
                    viewModel.setEndDate(selectedDate)
                }
            }
        }
    }

    private fun showDatePicker(
        minDate: Long? = null,
        onDateSelected: (String) -> Unit
    ) {
        val sdf = SimpleDateFormat("yyyy.MM.dd (E)", Locale.KOREA)
        val today = Calendar.getInstance()

        val dialog = DatePickerDialog(
            requireContext(),
            R.style.MyDatePickerDialogTheme,
            { _, year, month, dayOfMonth ->
                val date = Calendar.getInstance().apply {
                    set(year, month, dayOfMonth)
                }
                onDateSelected(sdf.format(date.time))
            },
            today.get(Calendar.YEAR),
            today.get(Calendar.MONTH),
            today.get(Calendar.DAY_OF_MONTH)
        )

        minDate?.let {
            dialog.datePicker.minDate = it
        }

        dialog.show()
    }

    private fun String.toMillis(): Long {
        val sdf = SimpleDateFormat("yyyy.MM.dd (E)", Locale.KOREA)
        return sdf.parse(this)?.time ?: System.currentTimeMillis()
    }

    private fun setPerson() {
        binding.btnDecrease.setOnClickListener {
            val currentCount = viewModel.personCount.value ?: 0
            if (currentCount > 0) {
                viewModel.setPersonCount(currentCount - 1)
            }
        }

        binding.btnIncrease.setOnClickListener {
            val currentCount = viewModel.personCount.value ?: 0
            viewModel.setPersonCount(currentCount + 1)
        }
    }

    private fun updatePersonBorder() {
        val count = viewModel.personCount.value ?: 0
        val color = if (count > 0) R.color.hover_orange else android.R.color.transparent
        binding.clSelectPerson.strokeColor = ContextCompat.getColor(requireContext(), color)
    }

    private fun setBudget() {
        val budgetViews = listOf(binding.tvBudgetLow, binding.tvBudgetMid, binding.tvBudgetHigh)
        budgetViews.forEach { view ->
            view.setOnClickListener {
                viewModel.setSelectedBudget(view.id)
            }
        }
    }

    private fun checkAllInfoEntered() {
        val isDateSelected = !viewModel.startDate.value.isNullOrEmpty() && !viewModel.endDate.value.isNullOrEmpty()
        val isPersonSelected = (viewModel.personCount.value ?: 0) > 0
        val isBudgetSelected = viewModel.selectedBudget.value != null

        val allInfoEntered = isDateSelected && isPersonSelected && isBudgetSelected

        binding.btnNext.isEnabled = allInfoEntered
        binding.btnNext.isSelected = allInfoEntered
        binding.tvDone.visibility = if (allInfoEntered) View.VISIBLE else View.GONE
    }

    private fun clickNextButton() {
        binding.btnNext.setOnClickListener {
            if (binding.btnNext.isEnabled) {
                findNavController().navigate(R.id.action_selectTripInfoFragment_to_selectTripStyleFragment)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}