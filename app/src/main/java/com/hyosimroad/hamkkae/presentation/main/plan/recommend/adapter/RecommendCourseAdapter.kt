package com.hyosimroad.hamkkae.presentation.main.plan.recommend.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintSet
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.hyosimroad.hamkkae.databinding.ItemRecommendCourseBinding
import com.hyosimroad.hamkkae.domain.model.Course

class RecommendCourseAdapter(
    private val clickItem: (Course) -> Unit
) :
    ListAdapter<Course, RecommendCourseAdapter.RecommendCourseViewHolder>(
        RecommendCourseDiffCallback
    ) {
    private val viewPool = RecyclerView.RecycledViewPool()
    private var selectedPosition: Int? = null // 선택된 아이템 position 저장

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecommendCourseViewHolder {
        val binding =
            ItemRecommendCourseBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RecommendCourseViewHolder(binding, viewPool)
    }

    override fun onBindViewHolder(
        holder: RecommendCourseViewHolder,
        position: Int
    ) {
        val course = getItem(position)
        holder.bind(course, position == selectedPosition)

        holder.setClickListener { clickedCourse ->
            val adapterPosition = holder.adapterPosition
            if (adapterPosition != RecyclerView.NO_POSITION) {
                val previousPosition = selectedPosition

                if (adapterPosition == selectedPosition) {
                    selectedPosition = null
                    notifyItemChanged(adapterPosition)
                } else {
                    selectedPosition = adapterPosition
                    if (previousPosition != null) notifyItemChanged(previousPosition)
                    notifyItemChanged(adapterPosition)
                }

                // 여기에 호출!
                clickItem(clickedCourse)
            }
        }
    }

    inner class RecommendCourseViewHolder(
        val binding: ItemRecommendCourseBinding,
        private val viewPool: RecyclerView.RecycledViewPool
    ) : RecyclerView.ViewHolder(binding.root) {
        private val recommendCourseKeywordAdapter = RecommendCourseKeywordAdapter()
        private val recommendCoursePlaceAdapter = RecommendCoursePlaceAdapter()

        init {
            binding.rvKeyword.apply {
                layoutManager = LinearLayoutManager(
                    binding.root.context,
                    LinearLayoutManager.HORIZONTAL, false
                )
                adapter = recommendCourseKeywordAdapter
                setRecycledViewPool(viewPool)
            }

            // 내부 RecyclerView 초기 설정
            binding.rvPlaces.apply { // ItemRecommendCourseBinding에 rvInnerPlaces ID가 있다고 가정
                layoutManager =
                    LinearLayoutManager(
                        binding.root.context,
                        LinearLayoutManager.VERTICAL, false
                    )
                adapter = recommendCoursePlaceAdapter
                setRecycledViewPool(viewPool) // 성능 최적화를 위해 RecycledViewPool 설정
            }
        }

        fun bind(course: Course, isSelected: Boolean) {
            with(binding) {
                tvName.text = course.name
                tvNumberOfNights.text = course.numberOfNights

                recommendCourseKeywordAdapter.submitList(course.keyword)
                recommendCoursePlaceAdapter.submitList(course.places)

                clCourse.isSelected = isSelected
                clBtns.visibility = if (isSelected) View.VISIBLE else View.GONE

                val constraintSet = ConstraintSet()
                constraintSet.clone(clCourse)

                if (isSelected) {
                    // rv_places의 bottom을 cl_btns의 top에 붙이기
                    constraintSet.connect(
                        rvPlaces.id,
                        ConstraintSet.BOTTOM,
                        clBtns.id,
                        ConstraintSet.TOP
                    )
                } else {
                    // rv_places의 bottom을 parent에 붙이기
                    constraintSet.connect(
                        rvPlaces.id,
                        ConstraintSet.BOTTOM,
                        ConstraintSet.PARENT_ID,
                        ConstraintSet.BOTTOM
                    )
                }

                constraintSet.applyTo(clCourse)
            }
        }

        fun setClickListener(onClick: (Course) -> Unit) {
            binding.clCourse.setOnClickListener {
                onClick(adapterPosition.takeIf { it != RecyclerView.NO_POSITION }?.let { getItem(it) } ?: return@setOnClickListener)
            }
        }

    }
}

object RecommendCourseDiffCallback : DiffUtil.ItemCallback<Course>() {
    override fun areItemsTheSame(oldItem: Course, newItem: Course): Boolean {
        return oldItem.id == newItem.id // id로 비교 (식별자)
    }

    override fun areContentsTheSame(oldItem: Course, newItem: Course): Boolean {
        return oldItem == newItem // 데이터 클래스라면 자동 equals 비교 가능
    }
}