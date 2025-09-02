package com.hyosim.hamkkae.presentation.main.setting.resignation

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.hyosim.hamkkae.R
import com.hyosim.hamkkae.databinding.DialogResignationBinding
import com.hyosim.hamkkae.databinding.FragmentResignationBinding
import timber.log.Timber

class ResignationFragment : Fragment() {
    private var _binding: FragmentResignationBinding? = null
    private val binding: FragmentResignationBinding
        get() = requireNotNull(_binding) { "setting fragment is null" }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentResignationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Timber.d("SettingFragment started!")
        setting()
    }

    private fun setting() {
        with(binding){
            rgReasons.setOnCheckedChangeListener { _, checkedId ->
                if(checkedId == R.id.rb_other) {
                    etOther.visibility = View.VISIBLE
                    tvCounter.visibility = View.VISIBLE

                    // 포커스 주고 키보드 열기
                    etOther.requestFocus()
                    val imm = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    imm.showSoftInput(etOther, InputMethodManager.SHOW_IMPLICIT)
                }
                else {
                    etOther.visibility = View.GONE
                    tvCounter.visibility=View.GONE

                    btnResignation.apply {
                        isEnabled = true
                        isSelected = true
                    }
                }
            }

            etOther.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(s: Editable?) {
                    val length = s?.length ?: 0
                    tvCounter.text = "$length / 500"

                    if (length > 0) {
                        btnResignation.apply {
                            isEnabled = true
                            isSelected = true
                        }
                    }else{
                        btnResignation.apply {
                            isEnabled = false
                            isSelected = false
                        }
                    }
                }
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            })
        }

        binding.btnResignation.setOnClickListener {
            // 다이얼로그용 뷰 바인딩
            val dialogBinding = DialogResignationBinding.inflate(layoutInflater)

            val dialog = AlertDialog.Builder(requireContext())
                .setView(dialogBinding.root)
                .create()

            // 기본 흰색 배경 제거
            dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

            // 탈퇴하기 버튼 클릭
            dialogBinding.btnConfirm.setOnClickListener {
                // 실제 탈퇴 처리 로직 넣기
                dialog.dismiss()

                val intent = Intent(requireContext(), ResignationCompleteActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
            }

            // 취소 버튼 클릭
            dialogBinding.btnCancel.setOnClickListener {
                dialog.dismiss()
            }

            dialog.show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}