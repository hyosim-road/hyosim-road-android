package com.hyosimroad.hamkkae.presentation.main.plan.recommend.detail.tip

import android.graphics.Rect
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelStore
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hyosimroad.hamkkae.R
import com.hyosimroad.hamkkae.databinding.FragmentRecommendDetailTipBinding
import timber.log.Timber

class RecommendDetailTipFragment: Fragment() {
    private var _binding: FragmentRecommendDetailTipBinding? = null
    private val binding: FragmentRecommendDetailTipBinding
        get() = requireNotNull(_binding) { "detail tip fragment is null" }
    private val tipViewModel: RecommendDetailTipViewModel by viewModels()
    private val cautionAdapter by lazy { TextAdapter(TextMode.NORMAL) }
    private val bestVisitAdapter by lazy { TextAdapter(TextMode.PREFIX_BOLD) }
    private val checklistAdapter by lazy { TextAdapter(TextMode.FULL_BOLD) }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRecommendDetailTipBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Timber.d("detail tip fragment started!")
        setting()
    }

    private fun setting() {
        getList()
    }

    private fun getList(){
        fun RecyclerView.tuneForNestedScrollParent() {
            isNestedScrollingEnabled = false
            overScrollMode = View.OVER_SCROLL_NEVER
            setHasFixedSize(true)
        }

        binding.rvCaution.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = cautionAdapter
            tuneForNestedScrollParent()
        }
        binding.rvBestVisit.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = bestVisitAdapter
            tuneForNestedScrollParent()
        }
        binding.rvChecklist.apply {
            // XML에서 GridLayoutManager + spanCount=2 지정했으면 생략 가능
            layoutManager = GridLayoutManager(requireContext(), 2)
            adapter = checklistAdapter
            tuneForNestedScrollParent()
        }

        // 데이터 주입
        cautionAdapter.submitList(tipViewModel.cautionList)
        bestVisitAdapter.submitList(tipViewModel.bestVisitList)
        checklistAdapter.submitList(tipViewModel.checklist)

        // (선택) 아이템 간격
        val gap6 = resources.getDimensionPixelSize(R.dimen.dp6)
        val gap10 = resources.getDimensionPixelSize(R.dimen.dp10)
        binding.rvCaution.addItemDecoration(SimpleGapDecoration(gap6))
        binding.rvBestVisit.addItemDecoration(SimpleGapDecoration(gap6))
        binding.rvChecklist.addItemDecoration(SimpleGapDecoration(gap10))
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    inner class SimpleGapDecoration(private val gap: Int) : RecyclerView.ItemDecoration() {
        override fun getItemOffsets(outRect: Rect, v: View, p: RecyclerView, s: RecyclerView.State) {
            outRect.set(0, 0, 0, gap)
        }
    }
}
