package com.hyosim.hamkkae.presentation.main.family_conversation

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.hyosim.hamkkae.presentation.main.family_conversation.adapter.ConversationAdapter
import com.hyosim.hamkkae.databinding.ActivityFamilyConversationBinding
import com.hyosim.hamkkae.presentation.main.family_conversation.adapter.KeywordAdapter
import com.hyosim.hamkkae.presentation.main.home.MainActivity

class FamilyConversationActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFamilyConversationBinding

    private val familyConversationViewModel: FamilyConversationViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initBinds()
        setting()
    }

    private fun initBinds() {
        binding = ActivityFamilyConversationBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    private fun setting() {
        val ConversationAdapter = ConversationAdapter()
        binding.rvAnswers.adapter = ConversationAdapter
        ConversationAdapter.submitList(familyConversationViewModel.answerList)

        binding.btnSave.isEnabled = false
        binding.btnSave.isSelected = false

        binding.cvMyAnswer.visibility = View.GONE
        binding.clComplete.visibility = View.GONE
        binding.clKeyword.visibility = View.GONE
        binding.btnHome.visibility = View.GONE

        binding.etAnswerContext.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val isInputValid = s?.isNotBlank() == true && s.length >= 20

                binding.btnSave.isEnabled = isInputValid
                binding.btnSave.isSelected = isInputValid
            }

            override fun afterTextChanged(s: Editable?) {
            }
        })

        setupKeywordRecyclerView()
        clickNext()
        clickEdit()
        clickHome()
    }

    private fun setupKeywordRecyclerView() {
        val keywordAdapter = KeywordAdapter()
        binding.rvKeyword.adapter = keywordAdapter

        // ViewModel에서 keywordList를 가져와 어댑터에 전달
        keywordAdapter.submitList(familyConversationViewModel.keywordList)
    }

    private fun clickNext() {
        binding.btnSave.setOnClickListener {
            binding.tilAnswerContext.visibility = View.GONE
            binding.tvAnswerSize.visibility = View.GONE
            binding.btnSave.visibility = View.GONE

            binding.cvMyAnswer.visibility = View.VISIBLE
            binding.clComplete.visibility = View.VISIBLE
            binding.clKeyword.visibility = View.VISIBLE
            binding.btnHome.visibility = View.VISIBLE

            binding.btnHome.isEnabled = true
            binding.btnHome.isSelected = true

            val completeFadeIn = ObjectAnimator.ofFloat(binding.clComplete, "alpha", 0f, 1f).apply {
                duration = 700
            }

            val keywordFadeIn = ObjectAnimator.ofFloat(binding.clKeyword, "alpha", 0f, 1f).apply {
                duration = 700
            }

            val animatorSet = AnimatorSet()
            animatorSet.playTogether(completeFadeIn, keywordFadeIn)
            animatorSet.start()

            binding.tvMyAnswerContent.text = binding.etAnswerContext.text.toString()
        }
    }

    private fun clickEdit() {
        binding.tvMyAnswerContent.setOnClickListener {
            binding.tilAnswerContext.visibility = View.VISIBLE
            binding.tvAnswerSize.visibility = View.VISIBLE
            binding.btnSave.visibility = View.VISIBLE

            binding.cvMyAnswer.visibility = View.GONE
            binding.clComplete.visibility = View.GONE
            binding.clKeyword.visibility = View.GONE
            binding.btnHome.visibility = View.GONE

            binding.etAnswerContext.setText(binding.tvMyAnswerContent.text.toString())
        }
    }

    private fun clickHome() {
        binding.btnHome.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
            finish()
        }
    }
}
