package com.hyosimroad.hamkkae.presentation.main

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.hyosimroad.hamkkae.R
import com.hyosimroad.hamkkae.databinding.ActivityMainBinding
import com.hyosimroad.hamkkae.presentation.main.adapter.RecentAdapter
import com.hyosimroad.hamkkae.presentation.main.adapter.TripRecordAdapter
import timber.log.Timber

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val mainViewModel: MainViewModel by viewModels()

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
        binding.btnPlan.text = getString(R.string.main_plan_first_btn)
        getRecents()
        getTripRecords()
    }

    private fun getRecents(){
        val recentAdapter = RecentAdapter()
        binding.rvMainRecent.adapter = recentAdapter
        val list = mainViewModel.recentActivityList
        Timber.d("recentActivityList: $list")
        recentAdapter.submitList(list)

        with(binding){
            rvMainRecent.visibility= View.GONE
            tvRecentNoContent.visibility= View.VISIBLE
        }
    }

    private fun getTripRecords(){
        val tripRecordAdapter = TripRecordAdapter()
        binding.rvMainTripRecord.adapter = tripRecordAdapter
        tripRecordAdapter.submitList(mainViewModel.tripRecordList)
        with(binding){
            rvMainTripRecord.visibility= View.GONE
            tvTripRecordNoContent.visibility= View.VISIBLE
        }
    }

}