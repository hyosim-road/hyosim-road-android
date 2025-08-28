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
import com.hyosim.hamkkae.R
import com.hyosim.hamkkae.databinding.ActivityMainBinding
import com.hyosim.hamkkae.presentation.main.family_conversation.FamilyConversationActivity
import com.hyosim.hamkkae.presentation.main.home.adapter.today_schedule.TodayScheduleAdapter
import com.hyosim.hamkkae.presentation.main.home.adapter.trip_record.TripRecordAdapter
import com.hyosim.hamkkae.presentation.main.photo_album.PhotoAlbumActivity
import com.hyosim.hamkkae.presentation.main.plan.PlanActivity
import com.hyosim.hamkkae.presentation.main.trip_continue.TripDetailActivity
import com.hyosim.hamkkae.presentation.main.trip_records.TripRecordsActivity
import com.hyosim.hamkkae.presentation.main.upload_photo.UploadPhotoActivity
import com.hyosim.hamkkae.presentation.main.setting.SettingActivity
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import timber.log.Timber
import kotlin.jvm.java

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
                    setVisibilityPlan(true)
                }
            }

        binding.btnPlan.text = getString(R.string.main_plan_first_btn)
        binding.btnPlan.isSelected = true

        getTodaySchedule()
        //getRecents()
        getTripRecords()

        setVisibilityPlan(false)
        showQuestion(false)
        clickPlan()
        clickSetting()
        clickTripRecords()
    }

    private fun setVisibilityPlan(plan: Boolean) {
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

                setTrip()
                clickTripDetail()
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
            }

            lifecycleScope.launch {
                delay(3000) // 3초 대기
                showQuestion(true)
            }

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

    private fun setTrip(){
        with(binding){
            tvTripCurrentLocationTitle.text = getString(R.string.main_trip_current_location, "불국사")
            tvTripDay.text = getString(R.string.main_trip_current_day, 1)
        }
    }


    private fun getTodaySchedule() {
        val todayScheduleAdapter = TodayScheduleAdapter(false)
        binding.rvMainSchedule.adapter = todayScheduleAdapter
        todayScheduleAdapter.submitList(mainViewModel.todayScheduleList)
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

    private fun getTripRecords() {
        val tripRecordAdapter = TripRecordAdapter()
        binding.rvMainTripRecord.adapter = tripRecordAdapter
        tripRecordAdapter.submitList(mainViewModel.tripRecordList)
        with(binding) {
            rvMainTripRecord.visibility = View.GONE
            tvTripRecordNoContent.visibility = View.VISIBLE
        }
    }

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

    private fun clickTripDetail() {
        binding.btnTripDetail.setOnClickListener {
            // 새로 만든 메서드 호출
            navigateTo(TripDetailActivity::class.java)
        }
    }

    private fun clickUpload() {
        binding.btnUpload.setOnClickListener {
            // 새로 만든 메서드 호출
            navigateTo(UploadPhotoActivity::class.java)
        }
    }

    private fun clickPhotoAlbum(){
        binding.clAlbum.setOnClickListener {
            navigateTo(PhotoAlbumActivity::class.java)
        }
    }

    private fun clickSetting() {
        binding.btnSetting.setOnClickListener {
            Timber.d("setting click!")

            val intent = Intent(it.context, SettingActivity::class.java)
            startActivity(intent)
        }
    }

    private fun clickTripRecords(){
        binding.btnTripRecordAll.setOnClickListener {
            Timber.d("click trip detail!")
            navigateTo(TripRecordsActivity::class.java)
        }
    }

    private fun navigateTo(destination: Class<*>){
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