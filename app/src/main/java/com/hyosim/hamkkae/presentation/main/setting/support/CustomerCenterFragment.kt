package com.hyosim.hamkkae.presentation.main.setting.support

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.hyosim.hamkkae.R
import com.hyosim.hamkkae.databinding.FragmentCustomerCenterBinding
import com.hyosim.hamkkae.domain.model.AskType
import com.hyosim.hamkkae.extension.setting.InquiryState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import timber.log.Timber

@AndroidEntryPoint
class CustomerCenterFragment : Fragment() {

    private var _binding: FragmentCustomerCenterBinding? = null
    private val binding: FragmentCustomerCenterBinding
        get() = requireNotNull(_binding) { "customer center fragment is null" }
    private val customerCenterViewModel: CustomerCenterViewModel by viewModels()

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

        listOf(binding.btnBack, binding.btnCancellation).forEach { button ->
            button.setOnClickListener { clickBack() }
        }
    }

    private fun setupSpinner()=  with(binding) {
        val labels = resources.getStringArray(R.array.setting_ask_types)
        val values = resources.getStringArray(R.array.setting_ask_types_values)

        val adapter = ArrayAdapter(
            requireContext(),
            R.layout.item_spinner,
            labels
        ).apply { setDropDownViewResource(R.layout.item_spinner) }

        spinnerAskType.adapter = adapter
        spinnerAskType.setSelection(0, false)

        spinnerAskType.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View?,
                position: Int,
                id: Long
            ) {
                val token = values.getOrNull(position) ?: return
                if (token == "HINT") return

                // enum 이름과 동일하게 두었으니 안전하게 매핑
                val type = runCatching { AskType.valueOf(token) }.getOrNull() ?: return

                binding.btnAsk.setOnClickListener {
                    Timber.d("btn ask clicked!")
                    clickSubmit(type)
                }
            }
            override fun onNothingSelected(parent: AdapterView<*>) = Unit
        }
        /*val askTypes = resources.getStringArray(R.array.setting_ask_types)

        val adapter = ArrayAdapter(
            requireContext(),
            R.layout.item_spinner,
            askTypes
        )

        adapter.setDropDownViewResource(R.layout.item_spinner)

        binding.spinnerAskType.adapter = adapter*/
    }

    private fun clickSubmit(type: AskType) {
        with(binding){
            val title = etAskTitle.text.toString()
            val context = etAskContext.text.toString()
            val email = etAskEmail.text.toString()

            lifecycleScope.launch {
                customerCenterViewModel.inquiryState.collect { state->
                    when(state){
                        is InquiryState.Loading -> {}
                        is InquiryState.Success -> {
                            Toast.makeText(requireContext(), getText(R.string.setting_ask_complete), Toast.LENGTH_SHORT).show()
                            findNavController().popBackStack()
                        }
                        is InquiryState.Error -> {}
                    }
                }
            }

            customerCenterViewModel.inquiry(type, title, context, email)
        }

    }

    private fun clickBack() {
        findNavController().popBackStack()
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}