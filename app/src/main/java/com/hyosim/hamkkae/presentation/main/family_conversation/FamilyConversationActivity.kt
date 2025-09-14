package com.hyosim.hamkkae.presentation.main.family_conversation

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.hyosim.hamkkae.R
import com.hyosim.hamkkae.presentation.main.family_conversation.adapter.ConversationAdapter
import com.hyosim.hamkkae.databinding.ActivityFamilyConversationBinding
import com.hyosim.hamkkae.extension.conversation.GetAnswersState
import com.hyosim.hamkkae.extension.conversation.PostAnswerState
import com.hyosim.hamkkae.presentation.main.family_conversation.adapter.KeywordAdapter
import com.hyosim.hamkkae.presentation.main.home.MainActivity
import com.hyosim.hamkkae.presentation.main.home.noWordBreak
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
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
        val tripId = intent.getIntExtra("tripId", -1)
        val currentLocation = intent.getStringExtra("currentLocation")
        val questionId = intent.getIntExtra("questionId", -1)
        val content = intent.getStringExtra("content")

        binding.tvQuestionContent.apply{
            text = content!!.noWordBreak()
        }

        binding.tvPlace.text = currentLocation!!.noWordBreak()

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
                s ?: return
                val fixed = s.toString().replace(" ", "\u00A0")
                if (fixed != s.toString()) {
                    binding.etAnswerContext.setText(fixed)
                    binding.etAnswerContext.setSelection(fixed.length) // 커서 끝으로 이동
                }
            }
        })

        getAnswers(tripId)
        clickNext(questionId)
        clickEdit()
        clickHome()
    }

    private fun clickNext(questionId: Int) {
        binding.btnSave.setOnClickListener {
            val imm = this.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(binding.etAnswerContext.windowToken, 0)
            binding.etAnswerContext.clearFocus()

            postAnswers()

            familyConversationViewModel.postAnswer(
                questionId,
                binding.etAnswerContext.text.toString()
            )
            binding.btnSave.apply {
                text = getString(R.string.conversation_loading)
                isEnabled = false
                isSelected = false
            }
        }
    }

    private fun postAnswers() {
        lifecycleScope.launch {
            familyConversationViewModel.postAnswerState.collect { state ->
                when (state) {
                    is PostAnswerState.Success -> {

                        binding.btnSave.apply {
                            text = getString(R.string.family_conversation_save)
                            isEnabled = true
                            isSelected = true
                        }

                        binding.tilAnswerContext.visibility = View.GONE
                        binding.tvAnswerSize.visibility = View.GONE
                        binding.btnSave.visibility = View.GONE

                        binding.cvMyAnswer.visibility = View.VISIBLE
                        binding.clComplete.visibility = View.VISIBLE
                        binding.clKeyword.visibility = View.VISIBLE
                        binding.btnHome.visibility = View.VISIBLE

                        binding.btnHome.isEnabled = true
                        binding.btnHome.isSelected = true

                        val completeFadeIn =
                            ObjectAnimator.ofFloat(binding.clComplete, "alpha", 0f, 1f).apply {
                                duration = 700
                            }

                        val keywordFadeIn =
                            ObjectAnimator.ofFloat(binding.clKeyword, "alpha", 0f, 1f).apply {
                                duration = 700
                            }

                        val animatorSet = AnimatorSet()
                        animatorSet.playTogether(completeFadeIn, keywordFadeIn)
                        animatorSet.start()

                        binding.tvMyAnswerContent.text = binding.etAnswerContext.text.toString()

                        setupKeywordRecyclerView(state.question.keywords)

                        familyConversationViewModel.postAnswerReset()
                    }

                    is PostAnswerState.Loading -> {}
                    is PostAnswerState.Error -> {
                        if(state.status=="COMMON500"){
                            Toast.makeText(this@FamilyConversationActivity, "${state.message} 잠시 후 다시 시도해주세요.", Toast.LENGTH_SHORT).show()
                        }

                        binding.btnSave.apply {
                            text = getString(R.string.family_conversation_save)
                            isEnabled = true
                            isSelected = true
                        }

                        familyConversationViewModel.postAnswerReset()
                    }
                }
            }
        }
    }

    private fun setupKeywordRecyclerView(keywords: List<String>) {
        val keywordAdapter = KeywordAdapter()
        binding.rvKeyword.adapter = keywordAdapter

        // ViewModel에서 keywordList를 가져와 어댑터에 전달
        keywordAdapter.submitList(keywords)
    }

    private fun getAnswers(tripId:Int) {
        lifecycleScope.launch {
            familyConversationViewModel.getAnswersState.collect { state ->
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

        familyConversationViewModel.getAnswers(tripId)

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
