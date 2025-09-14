package com.hyosim.hamkkae.presentation.main.plan.recommend.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintSet
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.hyosim.hamkkae.R
import com.hyosim.hamkkae.data.response_dto.plan.AiCourseRecommendResponseDto
import com.hyosim.hamkkae.data.response_dto.plan.CourseRecommendResponseData
import com.hyosim.hamkkae.databinding.ItemRecommendCourseBinding
import com.hyosim.hamkkae.databinding.ItemRecommendCourseKeywordBinding
import com.hyosim.hamkkae.domain.model.Course
import timber.log.Timber
import java.security.PrivateKey
import java.text.SimpleDateFormat
import java.util.Locale

class RecommendCourseAdapter(
    private val clickItem: (AiCourseRecommendResponseDto) -> Unit,
    private val clickDetail: (AiCourseRecommendResponseDto) -> Unit,
    private val clickMap: (AiCourseRecommendResponseDto)->Unit,
) :
    ListAdapter<AiCourseRecommendResponseDto, RecommendCourseAdapter.RecommendCourseViewHolder>(
        RecommendCourseDiffCallback
    ) {
    private val viewPool = RecyclerView.RecycledViewPool()
    private var selectedPosition: Int? = null // 선택된 아이템 position 저장
    private var travelStyle: String? = null

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
        // 현재 position이 선택된 카드인지 여부
        val isSelected = position == selectedPosition
        holder.bind(course, isSelected)

        holder.binding.cvCourse.setOnClickListener {
            val adapterPos = holder.adapterPosition
            if (adapterPos == RecyclerView.NO_POSITION) return@setOnClickListener

            Timber.d("cvCourse clicked! position=$adapterPos")

            val prevPos = selectedPosition
            if (selectedPosition == adapterPos) {
                selectedPosition = null
                notifyItemChanged(adapterPos)
            } else {
                selectedPosition = adapterPos
                notifyItemChanged(adapterPos)
                prevPos?.let { notifyItemChanged(it) }
            }

            clickItem(getItem(adapterPos))
        }

        /*holder.bind(course, position == selectedPosition)

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
               // clickItem(clickedCourse)
            }
        }*/
    }

    fun saveStyle(style: String) {
        travelStyle = style
    }

    inner class RecommendCourseViewHolder(
        val binding: ItemRecommendCourseBinding,
        private val viewPool: RecyclerView.RecycledViewPool
    ) : RecyclerView.ViewHolder(binding.root) {
        private val recommendCourseKeywordAdapter = RecommendCourseKeywordAdapter()
        private val recommendCoursePlaceAdapter = RecommendCoursePlaceAdapter()

        init {
            /*binding.rvKeyword.apply {
                layoutManager = LinearLayoutManager(
                    binding.root.context,
                    LinearLayoutManager.HORIZONTAL, false
                )
                adapter = recommendCourseKeywordAdapter
                setRecycledViewPool(viewPool)
            }*/

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

        fun bind(course: AiCourseRecommendResponseDto, isSelected: Boolean) {
            with(binding) {
                // 수정해야할 부분
                //tvName.text = course.caption
                Timber.d("recommend course adapter")
                tvNumberOfNights.text = getTripDuration(
                    course.itinerary[0].day,
                    course.itinerary[course.itinerary.size - 1].day
                )

                val image: String? = course.itinerary
                    .flatMap { it.attractions }
                    .firstOrNull { !it.image.isNullOrEmpty() } // image가 null이 아니고 비어있지 않은 경우
                    ?.image                                     // 찾으면 image 값, 없으면 null

                ivImage.load(image) {
                    listener(
                        onError = { _, throwable ->
                            Timber.e("이미지 로드 실패: $image")
                        },
                        onSuccess = { _, _ ->
                            Timber.d("이미지 로드 성공: $image")
                        }
                    )
                }


                val keywordBinding =
                    ItemRecommendCourseKeywordBinding.bind(binding.llKeyword.getChildAt(0))
                /*keywordBinding.ivIcon.load(R.drawable.ic_style_nature)
                keywordBinding.tvKeyword.text = "자연"*/

                when(travelStyle){
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

                //recommendCourseKeywordAdapter.submitList("HISTORY" as List<String?>?)
                recommendCoursePlaceAdapter.submitList(course.itinerary[0].attractions)

                // 선택 상태에 따라 버튼 표시 + constraint 갱신
                clBtns.visibility = if (isSelected) View.VISIBLE else View.GONE
                val constraintSet = ConstraintSet().apply {
                    clone(clCourse)
                    if (isSelected) {
                        connect(rvPlaces.id, ConstraintSet.BOTTOM, clBtns.id, ConstraintSet.TOP)
                    } else {
                        connect(rvPlaces.id, ConstraintSet.BOTTOM, ConstraintSet.PARENT_ID, ConstraintSet.BOTTOM)
                    }
                }
                constraintSet.applyTo(clCourse)

                btnDetail.setOnClickListener { clickDetail(course) }
                btnMap.setOnClickListener { clickMap(course) }
            }
        }

        private fun getTripDuration(departure: String, arrival: String): String {
            val format = SimpleDateFormat("yyyy-MM-dd", Locale.KOREA) // 서버에서 내려주는 형식 맞추기
            val depDate = format.parse(departure)
            val arrDate = format.parse(arrival)

            if (depDate != null && arrDate != null) {
                val diffInMillis = arrDate.time - depDate.time
                val days = (diffInMillis / (1000 * 60 * 60 * 24)).toInt() + 1
                val nights = if (days > 0) days - 1 else 0

                return "${nights}박 ${days}일"
            }
            return ""
        }

        fun setClickListener(onClick: (Course) -> Unit) {
            binding.clCourse.setOnClickListener {
                //onClick(adapterPosition.takeIf { it != RecyclerView.NO_POSITION }?.let { getItem(it) } ?: return@setOnClickListener)
            }
        }

    }
}

object RecommendCourseDiffCallback : DiffUtil.ItemCallback<AiCourseRecommendResponseDto>() {
    /*override fun areItemsTheSame(oldItem: AiCourseRecommendResponseDto, newItem: AiCourseRecommendResponseDto): Boolean {
        return oldItem.title == newItem.title // id로 비교 (식별자)
    }

    override fun areContentsTheSame(oldItem: AiCourseRecommendResponseDto, newItem: AiCourseRecommendResponseDto): Boolean {
        return oldItem == newItem // 데이터 클래스라면 자동 equals 비교 가능
    }*/

    override fun areItemsTheSame(
        oldItem: AiCourseRecommendResponseDto,
        newItem: AiCourseRecommendResponseDto
    ): Boolean {
        // 그냥 전체를 equals 비교 (리스트 변경시마다 새로 갱신)
        return oldItem == newItem
    }

    override fun areContentsTheSame(
        oldItem: AiCourseRecommendResponseDto,
        newItem: AiCourseRecommendResponseDto
    ): Boolean {
        return oldItem == newItem
    }
}