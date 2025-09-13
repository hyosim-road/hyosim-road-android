package com.hyosim.hamkkae.presentation.main.plan.select

import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.hyosim.hamkkae.R
import com.hyosim.hamkkae.databinding.FragmentSelectTripStyleBinding
import com.hyosim.hamkkae.presentation.main.plan.PlanViewModel
import com.hyosim.hamkkae.presentation.main.plan.select.adapter.SelectTripStyleAdapter
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class SelectTripStyleFragment : Fragment() {
    private var _binding: FragmentSelectTripStyleBinding? = null
    private val binding: FragmentSelectTripStyleBinding
        get() = requireNotNull(_binding) { "home fragment is null" }

    private val selectTripStyleViewModel: SelectTripStyleViewModel by viewModels()
    private val planViewModel: PlanViewModel by activityViewModels()
    private lateinit var tripStyleAdapter: SelectTripStyleAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentSelectTripStyleBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Timber.d("selectTripStyleFragment started!")

        setupRecyclerView()
        observeViewModel()

        setting()
    }

    private fun setupRecyclerView() {
        tripStyleAdapter = SelectTripStyleAdapter { style ->
            // 클릭할 때마다 ViewModel에 단일 선택 반영
            selectTripStyleViewModel.selectStyle(style)
        }

        binding.rvTripStyle.apply {
            layoutManager = GridLayoutManager(requireContext(), 2)
            adapter = tripStyleAdapter
            setHasFixedSize(true)
        }
        tripStyleAdapter.submitList(selectTripStyleViewModel.tripStyleList)
    }

    private fun observeViewModel() {
        selectTripStyleViewModel.selectedStyle.observe(viewLifecycleOwner) { selected ->
            tripStyleAdapter.selectItem(selected)

            val isSelectionNotEmpty = selected != null
            binding.btnNext.isEnabled = isSelectionNotEmpty
            binding.btnNext.isSelected = isSelectionNotEmpty
            binding.tvDone.visibility = if (isSelectionNotEmpty) View.VISIBLE else View.GONE
        }
    }

    /*private fun setupRecyclerView() {
        tripStyleAdapter = SelectTripStyleAdapter { style ->
            selectTripStyleViewModel.toggleTripStyleSelection(style)
        }

        binding.rvTripStyle.apply {
            layoutManager = GridLayoutManager(requireContext(), 2)
            adapter = tripStyleAdapter
            setHasFixedSize(true)
        }
        tripStyleAdapter.submitList(selectTripStyleViewModel.tripStyleList)
    }*/

   /* private fun observeViewModel() {
        selectTripStyleViewModel.selectedStyles.observe(viewLifecycleOwner) { selectedList ->
            tripStyleAdapter.submitSelection(selectedList)

            val isSelectionNotEmpty = selectedList.isNotEmpty()
            binding.btnNext.isEnabled = isSelectionNotEmpty
            binding.btnNext.isSelected = isSelectionNotEmpty
            binding.tvDone.visibility = if (isSelectionNotEmpty) View.VISIBLE else View.GONE
        }
    }
*/
  /* private fun observeViewModel() {
       selectTripStyleViewModel.selectedStyle.observe(viewLifecycleOwner) { selected ->
           tripStyleAdapter.selectItem(selected)

           val isSelectionNotEmpty = selected != null
           binding.btnNext.isEnabled = isSelectionNotEmpty
           binding.btnNext.isSelected = isSelectionNotEmpty
           binding.tvDone.visibility = if (isSelectionNotEmpty) View.VISIBLE else View.GONE
       }
   }*/

    private fun setting() {
        binding.pbTrip.progress = 50
        val animator = ObjectAnimator.ofInt(binding.pbTrip, "progress", 25, 50)
        animator.duration = 1000
        animator.interpolator = AccelerateDecelerateInterpolator()
        animator.start()
        clickNextButton()
    }

   /* private fun clickNextButton() {
        binding.btnNext.setOnClickListener {
            val selected = selectTripStyleViewModel.selectedStyles.value.orEmpty()
            // 여러 개 중 첫 번째만 API에 넣는다고 가정
            val styleCode = selected.firstOrNull()?.code ?: ""
            planViewModel.setTravelStyle(styleCode)

            findNavController().navigate(R.id.action_selectTripStyleFragment_to_loadingFragment)
        }
    }*/

    private fun clickNextButton() {
        binding.btnNext.setOnClickListener {
            val selected = selectTripStyleViewModel.selectedStyle.value
            val styleCode = selected?.code ?: ""

            planViewModel.setTravelStyle(styleCode)
            findNavController().navigate(R.id.action_selectTripStyleFragment_to_loadingFragment)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}