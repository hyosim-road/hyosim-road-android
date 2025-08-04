package com.hyosimroad.hamkkae.presentation.main.plan.select

import android.animation.ObjectAnimator
import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.hyosimroad.hamkkae.R
import com.hyosimroad.hamkkae.databinding.FragmentSelectTripInfoBinding
import timber.log.Timber
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class SelectTripInfoFragment : Fragment() {
    private var _binding: FragmentSelectTripInfoBinding? = null
    private val binding: FragmentSelectTripInfoBinding
        get() = requireNotNull(_binding) { "home fragment is null" }

    private var selectedStartDate: String? = null
    private var selectedEndDate: String? = null

    private var personCount = 0

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
    }

    private fun setting() {
        binding.pbTrip.progress = 25

        val animator = ObjectAnimator.ofInt(binding.pbTrip, "progress", 0, 25)
        animator.duration = 1000 // 1초
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
        binding.tvEndDate.isEnabled = false

        binding.tvStartDate.setOnClickListener {
            showDatePicker { selectedDate ->
                selectedStartDate = selectedDate
                binding.tvStartDate.text = selectedDate
                binding.tvEndDate.isEnabled = true
            }
        }

        binding.tvEndDate.setOnClickListener {
            if (selectedStartDate != null) {
                showDatePicker(minDate = selectedStartDate!!.toMillis()) { selectedDate ->
                    selectedEndDate = selectedDate
                    binding.tvEndDate.text = selectedDate
                    binding.clSelectDate.strokeColor =
                        ContextCompat.getColor(requireContext(), R.color.hover_orange)
                    checkAllInfoEntered()
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
        binding.tvPersonCount.text = personCount.toString() + "명"

        binding.btnDecrease.setOnClickListener {
            if (personCount > 0) {
                personCount--
                binding.tvPersonCount.text = personCount.toString() + "명"
                updatePersonBorder()
                checkAllInfoEntered()
            }
        }

        binding.btnIncrease.setOnClickListener {
            personCount++
            binding.tvPersonCount.text = personCount.toString() + "명"
            updatePersonBorder()
            checkAllInfoEntered()
        }
    }

    private fun updatePersonBorder() {
        val color = if (personCount > 0) {
            R.color.hover_orange
        } else {
            android.R.color.transparent
        }

        binding.clSelectPerson.strokeColor = ContextCompat.getColor(requireContext(), color)
    }

    private fun setBudget() {
        val budgetViews = listOf(
            binding.tvBudgetLow, binding.tvBudgetMid, binding.tvBudgetHigh
        )

        budgetViews.forEach { view ->
            view.setOnClickListener {
                budgetViews.forEach {
                    it.isSelected = false
                    it.setBackgroundResource(R.drawable.bg_select_info_box)
                }

                view.isSelected = true
                view.setBackgroundResource(R.drawable.bg_selected_info_box)

                binding.clSelectBudget.strokeColor =
                    ContextCompat.getColor(requireContext(), R.color.hover_orange)

                checkAllInfoEntered()
            }
        }
    }

    private fun checkAllInfoEntered() {
        val isDateSelected = !selectedStartDate.isNullOrEmpty() && !selectedEndDate.isNullOrEmpty()
        val isPersonSelected = personCount > 0
        val isBudgetSelected = listOf(
            binding.tvBudgetLow,
            binding.tvBudgetMid,
            binding.tvBudgetHigh
        ).any { it.isSelected }

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