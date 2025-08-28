package com.hyosim.hamkkae.presentation.main.trip_records

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.hyosim.hamkkae.databinding.ActivityTripRecordsBinding
import com.hyosim.hamkkae.presentation.main.trip_records.adapter.TripRecordsAdapter
import com.hyosim.hamkkae.presentation.main.trip_records.detail.TripRecordsDetailActivity
import kotlin.getValue

class TripRecordsActivity: AppCompatActivity() {
    private lateinit var binding: ActivityTripRecordsBinding
    private val tripRecordsViewModel: TripRecordsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initBinds()
        setting()
    }

    private fun initBinds() {
        binding = ActivityTripRecordsBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    private fun setting() {
       with(binding){
           tvTotalTripsCount.text="3"
           tvTotalDaysCount.text="9"
           tvTotalAnswersCount.text="9"

           rvTrips.visibility= View.VISIBLE
       }

        setRecords()
    }

    private fun setRecords(){
        val adapter = TripRecordsAdapter(
            clickDetail = {
                val intent = Intent(this, TripRecordsDetailActivity::class.java)
                startActivity(intent)
            }
        )
        binding.rvTrips.adapter = adapter
        adapter.submitList(tripRecordsViewModel.tripRecordList)
    }
}