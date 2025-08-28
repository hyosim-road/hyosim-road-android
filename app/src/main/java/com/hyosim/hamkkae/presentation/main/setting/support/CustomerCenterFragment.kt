package com.hyosim.hamkkae.presentation.main.setting.support

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import com.hyosim.hamkkae.R
import com.hyosim.hamkkae.databinding.FragmentCustomerCenterBinding
import timber.log.Timber

class CustomerCenterFragment : Fragment() {

    private var _binding: FragmentCustomerCenterBinding? = null
    private val binding: FragmentCustomerCenterBinding
        get() = requireNotNull(_binding) { "customer center fragment is null" }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCustomerCenterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Timber.d("CustomerCenterFragment started!")

        setupSpinner()
    }

    private fun setupSpinner() {
        val askTypes = resources.getStringArray(R.array.setting_ask_types)

        val adapter = ArrayAdapter(
            requireContext(),
            R.layout.item_spinner,
            askTypes
        )

        adapter.setDropDownViewResource(R.layout.item_spinner)

        binding.spinnerAskType.adapter = adapter
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}