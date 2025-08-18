package com.hyosimroad.hamkkae.presentation.main.trip_records.detail

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import com.hyosimroad.hamkkae.R
import com.hyosimroad.hamkkae.databinding.ActivityTripRecordsDetailBinding
import com.hyosimroad.hamkkae.presentation.main.map.MapActivity
import com.hyosimroad.hamkkae.presentation.main.photo_album.detail.PhotoAlbumDetailActivity
import com.hyosimroad.hamkkae.presentation.main.plan.recommend.detail.RecommendDetailAdapter
import com.hyosimroad.hamkkae.presentation.main.trip_records.TripRecordsViewModel
import timber.log.Timber
import kotlin.getValue

class TripRecordsDetailActivity: AppCompatActivity() {
    private lateinit var binding: ActivityTripRecordsDetailBinding

    private var pageChangeCallback: ViewPager2.OnPageChangeCallback? = null
    private var dataObserver: RecyclerView.AdapterDataObserver? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initBinds()
        setting()
    }

    private fun initBinds() {
        binding = ActivityTripRecordsDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    private fun setting() {
        setTrip()
        showTab()

        clickAlbum()
        clickMap()
    }
    
    private fun setTrip(){
        with(binding){
            tvTripName.text = getString(R.string.main_trip_title_mock)
            tvLocationAndDays.text = getString(R.string.trip_record_detail_location_and_days, "경주", 2, 3)
            tvDuring.text = getString(R.string.main_trip_record_during, "2025.06.14", "2025.06.16")

            tvTripPhotoCount.text = "6"
            tvTripAnswerCount.text = "3"
            tvTripParticipantsCount.text = "3"
        }
    }

    private fun showTab() {
        val pagerAdapter = TripRecordsDetailAdapter(this)
        binding.vpDetail.adapter = pagerAdapter
        binding.vpDetail.offscreenPageLimit = 3
        binding.vpDetail.setCurrentItem(0, false)

        val titles = listOf(
            getString(R.string.bar_preview),
            getString(R.string.bar_visited),
            //getString(R.string.bar_transit),
            getString(R.string.bar_answers)
        )

        TabLayoutMediator(binding.tlDetail, binding.vpDetail) { tab, pos ->
            tab.text = titles[pos]
        }.attach()

        Timber.d("vpDetail adapter set? ${binding.vpDetail.adapter != null}, itemCount=${binding.vpDetail.adapter?.itemCount}")

        // 2) 첫 그리기 후 높이 맞추기
        binding.root.post { updateVpHeightForCurrentPage() }

        // 3) 페이지 바뀔 때마다 높이 갱신
        pageChangeCallback = object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                binding.root.post { updateVpHeightForCurrentPage() }
            }
        }.also { binding.vpDetail.registerOnPageChangeCallback(it) }

        // 4) 어댑터 데이터 변경 시에도 높이 갱신
        binding.vpDetail.adapter?.let { adapter ->
            dataObserver = object : RecyclerView.AdapterDataObserver() {
                override fun onChanged()                   { binding.root.post { updateVpHeightForCurrentPage() } }
                override fun onItemRangeInserted(s:Int,c:Int){ onChanged() }
                override fun onItemRangeRemoved(s:Int,c:Int) { onChanged() }
            }.also { adapter.registerAdapterDataObserver(it) }
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

    private fun clickAlbum(){
        binding.btnAlbum.setOnClickListener {
            val intent = Intent(this, PhotoAlbumDetailActivity::class.java)
            startActivity(intent)
        }
    }

    private fun clickMap(){
        binding.btnMap.setOnClickListener {
            val intent = Intent(this, MapActivity::class.java)
            startActivity(intent)
        }
    }

}