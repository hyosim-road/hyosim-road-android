package com.hyosimroad.hamkkae.presentation.main.trip_records.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.RoundedCornersTransformation
import com.hyosimroad.hamkkae.R
import com.hyosimroad.hamkkae.databinding.ItemTripRecordsDetailBinding
import com.hyosimroad.hamkkae.domain.model.TripRecord
import com.hyosimroad.hamkkae.presentation.main.photo_album.TextAdapter

class TripRecordsAdapter(
    private val clickDetail: ()->Unit,
): ListAdapter<TripRecord, TripRecordsAdapter.TripRecordsViewHolder>(TripRecordsDiffCallback) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TripRecordsViewHolder {
        val binding = ItemTripRecordsDetailBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TripRecordsViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: TripRecordsViewHolder,
        position: Int
    ) {
        holder.bind(getItem(position))
    }

    inner class TripRecordsViewHolder(private val binding: ItemTripRecordsDetailBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(tripRecord: TripRecord) {
            with(binding){
                tvTripName.text=tripRecord.content
                tvDuring.text=binding.root.context.getString(R.string.main_trip_record_during, tripRecord.startDate, tripRecord.endDate)
                tvTripInfo.text=tripRecord.place
                tvPhotoCount.text=tripRecord.photos.size.toString()
                tvAnswerCount.text=tripRecord.answers.size.toString()

                ivImage.load(if(tripRecord.photos.isNotEmpty()) tripRecord.photos[0].url else "") {
                    crossfade(true)
                    placeholder(R.drawable.ic_hamkkae) // 로딩 중 표시할 이미지
                    error(R.drawable.ic_hamkkae) // 에러 시 표시할 이미지
                    transformations(RoundedCornersTransformation(16f))
                }

                val textAdapter = com.hyosimroad.hamkkae.presentation.main.trip_records.adapter.TextAdapter()
                val modifyList = tripRecord.keywords.map{
                    binding.root.context.getString(R.string.photo_album_tag, it)
                }
                textAdapter.submitList(modifyList)
                rvKeywords.adapter = textAdapter

                btnDetail.setOnClickListener {
                    clickDetail()
                }
            }
        }
    }
}

object TripRecordsDiffCallback: DiffUtil.ItemCallback<TripRecord>() {
    override fun areItemsTheSame(oldItem: TripRecord, newItem: TripRecord): Boolean {
        return oldItem.id == newItem.id // id로 비교 (식별자)
    }

    override fun areContentsTheSame(oldItem: TripRecord, newItem: TripRecord): Boolean {
        return oldItem == newItem // 데이터 클래스라면 자동 equals 비교 가능
    }
}