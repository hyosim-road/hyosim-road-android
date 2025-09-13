package com.hyosim.hamkkae.presentation.main.home

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintSet
import androidx.lifecycle.lifecycleScope
import coil.load
import com.hyosim.hamkkae.R
import com.hyosim.hamkkae.data.response_dto.home.ProgressTripResponseDto
import com.hyosim.hamkkae.databinding.ActivityMainBinding
import com.hyosim.hamkkae.domain.model.TodaySchedule
import com.hyosim.hamkkae.extension.home.ProgressTripState
import com.hyosim.hamkkae.presentation.main.family_conversation.FamilyConversationActivity
import com.hyosim.hamkkae.presentation.main.home.adapter.TodayScheduleAdapter
import com.hyosim.hamkkae.presentation.main.plan.PlanActivity
import com.hyosim.hamkkae.presentation.main.trip_detail.TripDetailActivity
import com.hyosim.hamkkae.presentation.main.setting.SettingActivity
import com.hyosim.hamkkae.util.StateConstants.TYPE_BEFORE_STARTING
import com.hyosim.hamkkae.util.StateConstants.TYPE_COMPLETE
import com.hyosim.hamkkae.util.StateConstants.TYPE_IN_PROCESS
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import timber.log.Timber
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import kotlin.jvm.java

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val mainViewModel: MainViewModel by viewModels()
    private lateinit var planActivityLauncher: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initBinds()
        setting()
    }

    private fun initBinds() {
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    private fun setting() {
        planActivityLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == RESULT_OK) {
                    // PlanActivity → Trip 완료 후 돌아온 경우
                    Timber.d("main으로 돌아옴!")
                    mainViewModel.progressStateLoading()
                    mainViewModel.progressTrip()
                    //setVisibilityPlan(true, state.course)
                }
            }

        binding.btnPlan.text = getString(R.string.main_plan_first_btn)
        binding.btnPlan.isSelected = true

        //getRecents()
        //getTripRecords()

        checkProgressTrip()
        //setVisibilityPlan(false, state.course)
        showQuestion(false)
        clickPlan()
        clickSetting()
    }

    private fun checkProgressTrip() {
        lifecycleScope.launch {
            mainViewModel.progressTripState.collect { state ->
                when (state) {
                    is ProgressTripState.Success -> {
                        setVisibilityPlan(true, state.course)
                        clickTripDetail(state.course)
                    }

                    is ProgressTripState.Error -> {
                        setVisibilityPlan(false, null)
                    }

                    is ProgressTripState.Loading -> {
                        setVisibilityPlan(false, null)
                    }
                }
            }
        }

        mainViewModel.progressTrip()
    }

    private fun setVisibilityPlan(plan: Boolean, course: ProgressTripResponseDto?) {
        Timber.d("setVisibilityPlan 호출: plan = $plan")
        val constraintSet = ConstraintSet()
        constraintSet.clone(binding.clMain)
        val marginTop = resources.getDimensionPixelSize(R.dimen.margin_20dp)

        clickPhotoAlbum()

        if (plan) {
            //binding.cvQuestion.visibility = View.VISIBLE
            with(binding) {
                cvTrip.visibility = View.VISIBLE
                cvSchedule.visibility = View.VISIBLE
                cvPlan.visibility = View.GONE
                btnTripDetail.isSelected = true

                setTrip(course!!)
                clickUpload()
                clickAnswer()

                // cvAlbum의 top을 cvSchedule의 bottom에 연결
                cvSchedule.post {
                    val constraintSet = ConstraintSet()
                    constraintSet.clone(binding.clMain)
                    constraintSet.clear(R.id.cv_album, ConstraintSet.TOP)
                    constraintSet.connect(
                        R.id.cv_album,
                        ConstraintSet.TOP,
                        R.id.cv_schedule,
                        ConstraintSet.BOTTOM,
                        marginTop
                    )
                    constraintSet.applyTo(binding.clMain)
                }

                ivTripImage.load(R.drawable.ic_default)
            }

           /* lifecycleScope.launch {
                delay(3000) // 3초 대기
                showQuestion(true)
            }*/

        } else {
            //binding.cvQuestion.visibility = View.GONE
            binding.cvTrip.visibility = View.GONE
            binding.cvSchedule.visibility = View.GONE
            binding.cvPlan.visibility = View.VISIBLE

            // cvAlbum의 top을 cvPlan의 bottom에 연결
            binding.cvPlan.post {
                val set = ConstraintSet()
                set.clone(binding.clMain)
                set.clear(R.id.cv_album, ConstraintSet.TOP)
                set.connect(
                    R.id.cv_album,
                    ConstraintSet.TOP,
                    R.id.cv_plan,
                    ConstraintSet.BOTTOM,
                    marginTop
                )
                set.applyTo(binding.clMain)
            }
        }
    }

    private fun setTrip(course: ProgressTripResponseDto) {
        val now = System.currentTimeMillis()
        val dateFormatter = SimpleDateFormat("yyyy-MM-dd", Locale.KOREA)
        val dateTimeFormatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.KOREA)

        val departureDayStr = course.itinerary.first().day
        val arrivalDayStr = course.itinerary.last().day
        val departureDay = dateFormatter.parse(departureDayStr)?.time

        var currentLocation: String? = null
        var currentDayIndex: Int? = null
        var nextAttraction: ProgressTripResponseDto.Itinerary.Attraction? = null
        var nextDayIndex: Int? = null

        // 🚩 출발 전이라면 "여행 준비 중" 처리
        if (departureDay != null && now < departureDay) {
            with(binding) {
                tvTripCurrentLocationTitle.text =
                    getString(R.string.main_trip_preparing) // "여행 준비 중"
                tvTripDay.text = "" //getString(R.string.main_trip_expected_day1)

                // 날짜는 그대로 보여줌
                tvTripDate.text = getString(
                    R.string.main_trip_record_during,
                    departureDayStr,
                    arrivalDayStr
                )
            }
            getTodaySchedule(0, course.itinerary[0].attractions)
            return
        }

        Timber.d("course: $course")

        // 🚩 출발 후 일정 처리
        course.itinerary.forEachIndexed { index, itinerary ->
            itinerary.attractions.forEach { attraction ->
                val start = dateTimeFormatter.parse(attraction.startTime)?.time
                val end = dateTimeFormatter.parse(attraction.endTime)?.time

                if (start != null && end != null) {
                    when {
                        now in start..end -> {
                            currentLocation = attraction.name
                            currentDayIndex = index + 1
                            return@forEachIndexed
                        }

                        start > now -> {
                            if (nextAttraction == null || start < dateTimeFormatter.parse(
                                    nextAttraction!!.startTime
                                )?.time!!
                            ) {
                                nextAttraction = attraction
                                nextDayIndex = index + 1
                            }
                        }
                    }
                }
            }
        }


        with(binding) {
            when {
                currentLocation != null -> {
                    tvTripCurrentLocationTitle.text =
                        getString(R.string.main_trip_current_location, currentLocation)
                    tvTripDay.text =
                        getString(R.string.main_trip_current_day, currentDayIndex ?: 0)
                    tvTodaySchedule.text = getString(R.string.main_today_schedule)

                    // 오늘 일정 전달
                    currentDayIndex?.let { dayIdx ->
                        getTodaySchedule(dayIdx, course.itinerary[currentDayIndex - 1].attractions)
                    }
                }

                nextAttraction != null -> {
                    val todayStr = dateFormatter.format(Date(now))
                    val nextDayStr = course.itinerary[(nextDayIndex ?: 1) - 1].day

                    if (todayStr == nextDayStr) {
                        // 오늘 일정인데 아직 시작 전
                        tvTripCurrentLocationTitle.text = getString(R.string.main_trip_next_location, nextAttraction!!.name)
                        tvTripDay.text = getString(R.string.main_trip_current_day, nextDayIndex ?: 0)
                        tvTodaySchedule.text = getString(R.string.main_today_schedule)

                        val todayIndex = nextDayIndex ?: 1
                        getTodaySchedule(todayIndex, course.itinerary[todayIndex - 1].attractions)
                    } else {
                        // 오늘 일정은 다 끝났고, 다음 날 일정
                        tvTripCurrentLocationTitle.text = getString(R.string.main_trip_all_done) // "오늘 일정: 모두 완료 ✅"
                        tvTripDay.text = getString(R.string.main_trip_current_day, nextDayIndex ?: 0)
                        tvTodaySchedule.text = getString(R.string.main_tomorrow_schedule)

                        val tomorrowIndex = nextDayIndex ?: 1
                        getTodaySchedule(tomorrowIndex, course.itinerary[tomorrowIndex - 1].attractions)
                    }
                }

                else -> {
                    tvTripCurrentLocationTitle.text =
                        getString(R.string.main_trip_current_location, "일정 없음")
                    tvTripDay.text =
                        getString(R.string.main_trip_current_day, 0)
                }
            }

            tvTripDate.text = getString(
                R.string.main_trip_record_during,
                departureDayStr,
                arrivalDayStr
            )
        }

        setProgress(course)
    }


    private fun setProgress(course: ProgressTripResponseDto) {
        val now = System.currentTimeMillis()
        val dateTimeFormatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.KOREA)

        // 전체 attraction 수
        val allAttractions = course.itinerary.flatMap { it.attractions }
        val totalCount = allAttractions.size

        // 끝난 일정 수
        val completedCount = allAttractions.count { attraction ->
            val end = dateTimeFormatter.parse(attraction.endTime)?.time
            end != null && now > end
        }

        // 진행률 계산
        val progressPercent =
            if (totalCount > 0) (completedCount * 100 / totalCount) else 0

        with(binding) {
            tvTripProgress.text = "$progressPercent%"
            pbTrip.progress = progressPercent
        }
    }

    private fun getTodaySchedule(
        day: Int,
        planList: List<ProgressTripResponseDto.Itinerary.Attraction>
    ) {
        // 진행 중이면 '오늘의 일정', 시작 전이면 '예정된 Day 1 일정'을 보여줌
        with(binding) {
            if (day > 0) {
                //tvTodaySchedule.text = getString(R.string.main_today_schedule)
                tvScheduleDay.visibility = View.VISIBLE
                tvScheduleDay.text = "Day $day"
            } else {
                tvTodaySchedule.text = getString(R.string.main_trip_expected_day1)
                tvScheduleDay.visibility = View.GONE
            }
        }

        Timber.d("planList size = ${planList.size}")

        val now = System.currentTimeMillis()
        val dateTimeFormatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.KOREA)

        // Attraction -> TodaySchedule 변환
        val todaySchedules = planList.mapIndexed { index, attraction ->
            val start = dateTimeFormatter.parse(attraction.startTime)?.time ?: 0L
            val end = dateTimeFormatter.parse(attraction.endTime)?.time ?: 0L

            val status = when {
                now in start..end -> TYPE_IN_PROCESS
                now > end -> TYPE_COMPLETE
                else -> TYPE_BEFORE_STARTING
            }

            TodaySchedule(
                id = index + 1,                  // 일정 순서
                place = attraction.name,         // 장소명
                keyword = attraction.address,    // 키워드 대신 address 임시 사용
                startTime = attraction.startTime.substring(11, 16), // HH:mm
                endTime = attraction.endTime.substring(11, 16),
                status = status
            )
        }

        val todayScheduleAdapter = TodayScheduleAdapter(false)
        binding.rvMainSchedule.adapter = todayScheduleAdapter
        todayScheduleAdapter.submitList(todaySchedules)
    }

    /* private fun getRecents() {
         val recentAdapter = RecentAdapter()
         binding.rvMainRecent.adapter = recentAdapter
         val list = mainViewModel.recentActivityList
         Timber.d("recentActivityList: $list")
         recentAdapter.submitList(list)

         with(binding) {
             rvMainRecent.visibility = View.GONE
             tvRecentNoContent.visibility = View.VISIBLE
         }
     }*/

    /* private fun getTripRecords() {
         val tripRecordAdapter = TripRecordAdapter()
         binding.rvMainTripRecord.adapter = tripRecordAdapter
         tripRecordAdapter.submitList(mainViewModel.tripRecordList)
         with(binding) {
             rvMainTripRecord.visibility = View.GONE
             tvTripRecordNoContent.visibility = View.VISIBLE
         }
     }*/

    private fun showQuestion(isShow: Boolean) {
        val constraintSet = ConstraintSet()
        constraintSet.clone(binding.clMain)
        val marginTop20 = resources.getDimensionPixelSize(R.dimen.margin_20dp)
        val marginTop30 = resources.getDimensionPixelSize(R.dimen.margin_30dp)

        if (isShow) {
            binding.cvQuestion.visibility = View.VISIBLE
            binding.cvSchedule.post {
                val constraintSet = ConstraintSet()
                constraintSet.clone(binding.clMain)
                constraintSet.clear(R.id.cv_trip, ConstraintSet.TOP)
                constraintSet.connect(
                    R.id.cv_trip,
                    ConstraintSet.TOP,
                    R.id.cv_question,
                    ConstraintSet.BOTTOM,
                    marginTop20
                )
                constraintSet.applyTo(binding.clMain)
            }

            startBlinkingAnimation(binding.tvQuestionTitle)
        } else {
            binding.cvQuestion.visibility = View.GONE
            binding.cvSchedule.post {
                val constraintSet = ConstraintSet()
                constraintSet.clone(binding.clMain)
                constraintSet.clear(R.id.cv_trip, ConstraintSet.TOP)
                constraintSet.connect(
                    R.id.cv_trip,
                    ConstraintSet.TOP,
                    R.id.cl_header,
                    ConstraintSet.BOTTOM,
                    marginTop30
                )
                constraintSet.applyTo(binding.clMain)
            }
        }
    }

    private fun clickPlan() {
        binding.btnPlan.setOnClickListener {
            planActivityLauncher.launch(Intent(this, PlanActivity::class.java))
        }
    }

    private fun clickAnswer() {
        binding.btnAnswer.setOnClickListener {
            navigateTo(FamilyConversationActivity::class.java)
        }
    }

    private fun clickTripDetail(course: ProgressTripResponseDto) {
        binding.btnTripDetail.setOnClickListener {
            val intent  = Intent(this, TripDetailActivity::class.java)
            intent.putExtra("course", course)
            startActivity(intent)
        }
    }

    private fun clickUpload() {
        /* binding.btnUpload.setOnClickListener {
             // 새로 만든 메서드 호출
             navigateTo(UploadPhotoActivity::class.java)
         }*/
    }

    private fun clickPhotoAlbum() {
        /* binding.clAlbum.setOnClickListener {
             navigateTo(PhotoAlbumActivity::class.java)
         }*/
    }

    private fun clickSetting() {
        binding.btnSetting.setOnClickListener {
            Timber.d("setting click!")

            val intent = Intent(it.context, SettingActivity::class.java)
            startActivity(intent)
        }
    }

    private fun clickTripRecords(course: ProgressTripResponseDto) {
        /* binding.btnTripRecordAll.setOnClickListener {
             Timber.d("click trip detail!")
             navigateTo(TripRecordsActivity::class.java)
         }*/
    }

    private fun navigateTo(destination: Class<*>) {
        val intent = Intent(this, destination)
        startActivity(intent)
    }


    private fun startBlinkingAnimation(view: View) {
        val blinkAnimation = AlphaAnimation(1.0f, 0.3f).apply {
            duration = 1000
            repeatMode = Animation.REVERSE
            repeatCount = Animation.INFINITE
        }
        view.startAnimation(blinkAnimation)
    }
}