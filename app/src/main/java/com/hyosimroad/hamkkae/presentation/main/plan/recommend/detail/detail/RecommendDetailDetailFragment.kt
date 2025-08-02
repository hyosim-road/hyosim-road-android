package com.hyosimroad.hamkkae.presentation.main.plan.recommend.detail.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.hyosimroad.hamkkae.databinding.FragmentRecommendDetailDetailBinding
import com.hyosimroad.hamkkae.presentation.main.plan.recommend.detail.detail.adapter.RecommendDetailDetailAdapter
import timber.log.Timber

class RecommendDetailDetailFragment: Fragment() {
    private var _binding: FragmentRecommendDetailDetailBinding? = null
    private val binding: FragmentRecommendDetailDetailBinding
        get() = requireNotNull(_binding) { "detail detail fragment is null" }

    private val recommendDetailDetailViewModel: RecommendDetailDetailViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRecommendDetailDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Timber.d("detail detail fragment started!")
        setting()
    }

    private fun setting() {
        getDetail()
    }

    private fun getDetail(){
        val detailAdapter = RecommendDetailDetailAdapter()
        binding.rvDetail.apply {
            layoutManager = LinearLayoutManager(requireContext())  // 반드시 필요
            adapter = detailAdapter
            setHasFixedSize(true) // 선택: 아이템 높이 고정이면 성능 ↑
            isNestedScrollingEnabled = false // 바깥에 ScrollView가 있을 때만 고려
            overScrollMode = View.OVER_SCROLL_NEVER
        }
        detailAdapter.submitList(recommendDetailDetailViewModel.courseDetailList)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}