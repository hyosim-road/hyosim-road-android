package com.hyosim.hamkkae.presentation.main.plan.recommend.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import coil.load
import com.google.android.material.tabs.TabLayoutMediator
import com.hyosim.hamkkae.R
import com.hyosim.hamkkae.data.response_dto.plan.AiCourseRecommendResponseDto
import com.hyosim.hamkkae.databinding.FragmentRecommendDetailBinding
import com.hyosim.hamkkae.databinding.ItemRecommendCourseKeywordBinding
import com.hyosim.hamkkae.extension.plan.RegisterState
import com.hyosim.hamkkae.presentation.main.plan.PlanViewModel
import com.hyosim.hamkkae.presentation.main.plan.recommend.RecommendCourseFragmentDirections
import com.hyosim.hamkkae.presentation.main.plan.recommend.RecommendCourseViewModel
import com.hyosim.hamkkae.presentation.main.plan.recommend.adapter.RecommendCourseKeywordAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import timber.log.Timber
import java.text.SimpleDateFormat
import java.util.Locale

@AndroidEntryPoint
class RecommendDetailFragment : Fragment() {
    private var _binding: FragmentRecommendDetailBinding? = null
    private val binding: FragmentRecommendDetailBinding
        get() = requireNotNull(_binding) { "home fragment is null" }

    private var pageChangeCallback: ViewPager2.OnPageChangeCallback? = null
    private var dataObserver: RecyclerView.AdapterDataObserver? = null
    private val detailViewModel: RecommendDetailViewModel by viewModels()
    private val recommendCourseViewModel: RecommendCourseViewModel by viewModels()
    private val planViewModel: PlanViewModel by activityViewModels()
    private val args : RecommendDetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRecommendDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Timber.d("recommendCourseFragment started!")
        setting()
    }

    private fun setting() {
        showInfo()
        showTab()
        showKeywords()

        binding.btnStart.isSelected=true
        binding.btnStart.setOnClickListener {
            register()
            recommendCourseViewModel.register(planViewModel.toRequestDto(), args.course)
        }
    }

    private fun showInfo(){
        with(binding){
            val course = args.course
            // tvName.text = course.caption

            val durationText = getTripDuration(course)
            tvDuring.text = durationText
        }
    }

    private fun getTripDuration(course: AiCourseRecommendResponseDto): String {
        if (course.itinerary.isEmpty()) return ""

        val dateFormatter = SimpleDateFormat("yyyy-MM-dd", Locale.KOREA)

        val firstDay = course.itinerary.first().day
        val lastDay = course.itinerary.last().day

        // 날짜 파싱해서 유효성 체크
        val startDate = dateFormatter.parse(firstDay)
        val endDate = dateFormatter.parse(lastDay)

        if (startDate == null || endDate == null) return ""

        // 그냥 문자열로 연결해서 반환
        return "$firstDay ~ $lastDay"
    }

    private fun showTab() {
        val pagerAdapter = RecommendDetailAdapter(this)
        binding.vpDetail.adapter = pagerAdapter
        binding.vpDetail.offscreenPageLimit = 3
        binding.vpDetail.setCurrentItem(0, false)

        val titles = listOf(
            getString(R.string.bar_overview),
            getString(R.string.bar_detail),
            getString(R.string.bar_restaurant),
            getString(R.string.bar_accommodation)
        )

        TabLayoutMediator(binding.tlDetail, binding.vpDetail) { tab, pos ->
            tab.text = titles[pos]
        }.attach()

        Timber.d("vpDetail adapter set? ${binding.vpDetail.adapter != null}, itemCount=${binding.vpDetail.adapter?.itemCount}")

        // 2) 첫 그리기 후 높이 맞추기
        view?.post { updateVpHeightForCurrentPage() }

        // 3) 페이지 바뀔 때마다 높이 갱신
        pageChangeCallback = object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                view?.post { updateVpHeightForCurrentPage() }
            }
        }.also { binding.vpDetail.registerOnPageChangeCallback(it) }

        // 4) 어댑터 데이터 변경 시에도 높이 갱신
        binding.vpDetail.adapter?.let { adapter ->
            dataObserver = object : RecyclerView.AdapterDataObserver() {
                override fun onChanged()                   { view?.post { updateVpHeightForCurrentPage() } }
                override fun onItemRangeInserted(s:Int,c:Int){ onChanged() }
                override fun onItemRangeRemoved(s:Int,c:Int) { onChanged() }
            }.also { adapter.registerAdapterDataObserver(it) }
        }
    }

    private fun showKeywords(){
        /*val keywordAdapter = RecommendCourseKeywordAdapter()
        binding.rvKeyword.adapter= keywordAdapter
        keywordAdapter.submitList(listOf("역사","문화","유적"))*/

        val keywordBinding =
            ItemRecommendCourseKeywordBinding.bind(binding.llKeyword.getChildAt(0))
        /*keywordBinding.ivIcon.load(R.drawable.ic_style_nature)
        keywordBinding.tvKeyword.text = "자연"*/

        when(detailViewModel.travelStyle){
            "NATURE"->{
                keywordBinding.ivIcon.load(R.drawable.ic_style_nature)
                keywordBinding.tvKeyword.text = "자연"
            }
            "HISTORY"->{
                keywordBinding.ivIcon.load(R.drawable.ic_style_history)
                keywordBinding.tvKeyword.text = "역사"
            }
            "TEMPLE"->{
                keywordBinding.ivIcon.load(R.drawable.ic_style_temple)
                keywordBinding.tvKeyword.text = "사찰"
            }
            "FOOD"->{
                keywordBinding.ivIcon.load(R.drawable.ic_style_food)
                keywordBinding.tvKeyword.text = "음식"
            }
            else->{}
        }
    }

    private fun updateVpHeightForCurrentPage() {
        val vp = binding.vpDetail
        val rv = vp.getChildAt(0) as? RecyclerView ?: return
        val pos = vp.currentItem
        val vh = rv.findViewHolderForAdapterPosition(pos) ?: return
        val page = vh.itemView

        // 현재 페이지 측정
        val wSpec = View.MeasureSpec.makeMeasureSpec(rv.width, View.MeasureSpec.EXACTLY)
        val hSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
        page.measure(wSpec, hSpec)

        // ViewPager2 높이를 페이지 높이에 맞춤
        val lp = vp.layoutParams
        if (lp.height != page.measuredHeight && page.measuredHeight > 0) {
            lp.height = page.measuredHeight
            vp.layoutParams = lp
            vp.invalidate()
        }
    }

    private fun register(){
        lifecycleScope.launch {
            recommendCourseViewModel.registerState.collect { state ->
                when (state) {
                    is RegisterState.Success -> {
                        val action = RecommendDetailFragmentDirections
                            .actionRecommendDetailFragmentToTripStartFragment(state.course)

                        findNavController().navigate(action)

                    }

                    is RegisterState.Error -> {
                    }

                    is RegisterState.Loading -> {
                    }
                }
            }
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}