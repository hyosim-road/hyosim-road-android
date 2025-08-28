package com.hyosim.hamkkae.presentation.main.family_conversation

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.hyosim.hamkkae.presentation.main.family_conversation.adapter.FamilyConversationAdapter
import com.hyosim.hamkkae.databinding.ActivityFamilyConversationBinding

class FamilyConversationActivity: AppCompatActivity() {
    private lateinit var binding: ActivityFamilyConversationBinding

    private val familyConversationViewModel: FamilyConversationViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initBinds()
        setting()
    }

    private fun initBinds(){
        binding = ActivityFamilyConversationBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    private fun setting() {
        val familyConversationAdapter = FamilyConversationAdapter()
        binding.rvAnswers.adapter = familyConversationAdapter
        familyConversationAdapter.submitList(familyConversationViewModel.answerList)

        binding.btnSave.isEnabled = false
        binding.btnSave.isSelected = false

        binding.cvMyAnswer.visibility = View.GONE
        binding.btnHome.visibility = View.GONE

        binding.etAnswerContext.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val isInputValid = s?.isNotBlank() == true && s.length >= 5

                binding.btnSave.isEnabled = isInputValid
                binding.btnSave.isSelected = isInputValid
            }

            override fun afterTextChanged(s: Editable?) {
            }
        })

        clickNext()
    }

    private fun clickNext() {
        binding.btnSave.setOnClickListener {
            binding.tilAnswerContext.visibility = View.GONE
            binding.btnSave.visibility = View.GONE

            binding.cvMyAnswer.visibility = View.VISIBLE
            binding.btnHome.visibility = View.VISIBLE

            binding.btnHome.isEnabled = true
            binding.btnHome.isSelected = true

            binding.tvMyAnswerContent.text = binding.etAnswerContext.text.toString()
        }
    }
}