package com.hyosim.hamkkae.presentation.main.plan.recommend.detail.restaurant

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.hyosim.hamkkae.data.response_dto.plan.toInfo
import com.hyosim.hamkkae.databinding.FragmentRecommendDetailInfoBinding
import com.hyosim.hamkkae.presentation.main.map.MapActivity
import com.hyosim.hamkkae.presentation.main.plan.recommend.detail.RecommendDetailViewModel
import timber.log.Timber
import kotlin.jvm.java

class RecommendDetailInfoFragment : Fragment() {
    companion object {
        private const val ARG_CATEGORY = "category"

        fun newInstance(category: String): RecommendDetailInfoFragment {
            val fragment = RecommendDetailInfoFragment()
            val args = Bundle()
            args.putString(ARG_CATEGORY, category)
            fragment.arguments = args
            return fragment
        }
    }

    private var _binding: FragmentRecommendDetailInfoBinding? = null
    private val binding: FragmentRecommendDetailInfoBinding
        get() = requireNotNull(_binding) { "detail tip fragment is null" }
    private val restaurantViewModel: RecommendDetailInfoViewModel by viewModels()
    private val detailViewModel: RecommendDetailViewModel by viewModels({requireParentFragment()})


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRecommendDetailInfoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Timber.d("detail tip fragment started!")
        setting()
    }

    private fun setting() {
        val category = arguments?.getString(ARG_CATEGORY)
        getList(category!!)
    }

    private fun getList(category:String) {
        Timber.d("category: $category")
        val infoAdapter = RecommendDetailInfoAdapter(
            category,
            clickMap = {
                val intent = Intent(requireActivity(), MapActivity::class.java)
                startActivity(intent)
            })
        binding.rvInfos.adapter = infoAdapter
        val items = if (category == "restaurant") {
            detailViewModel.course!!.restaurants.map { it.toInfo() }
        } else {
            detailViewModel.course!!.lodgings.map { it.toInfo() }
        }

        infoAdapter.submitList(items)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
