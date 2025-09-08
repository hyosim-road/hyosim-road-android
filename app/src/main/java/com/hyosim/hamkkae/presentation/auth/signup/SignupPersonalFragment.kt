package com.hyosim.hamkkae.presentation.auth.signup

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.hyosim.hamkkae.R
import com.hyosim.hamkkae.databinding.FragmentSignupPersonalBinding
import androidx.core.text.HtmlCompat
import timber.log.Timber

class SignupPersonalFragment : Fragment() {
    private var _binding: FragmentSignupPersonalBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSignupPersonalBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Timber.d("SignupPersonalFragment started for TextView content.")

        val termsContent = getString(R.string.privacy_policy)

        val formattedText = HtmlCompat.fromHtml(termsContent, HtmlCompat.FROM_HTML_MODE_LEGACY)
        binding.tvTermsContent.text = formattedText
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}