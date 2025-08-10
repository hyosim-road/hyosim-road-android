package com.hyosimroad.hamkkae.presentation.main.photo_album.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import coil.load
import com.hyosimroad.hamkkae.R
import com.hyosimroad.hamkkae.databinding.DialogPhotoDetailBinding
import com.hyosimroad.hamkkae.presentation.main.plan.recommend.detail.tip.TextAdapter
import timber.log.Timber

class PhotoAlbumDetailDialogFragment : DialogFragment() {
    private var _binding: DialogPhotoDetailBinding? = null
    private val binding get() = _binding!!
    private val photoAlbumDetailViewModel: PhotoAlbumDetailViewModel by activityViewModels()

    companion object {
        private const val ARG_PHOTO_ID = "photo_id"

        fun newInstance(photoId: Int): PhotoAlbumDetailDialogFragment {
            val fragment = PhotoAlbumDetailDialogFragment()
            val args = Bundle().apply {
                putInt(ARG_PHOTO_ID, photoId)
            }
            fragment.arguments = args
            return fragment
        }
    }

    override fun onStart() {
        super.onStart()
        // 다이얼로그의 배경을 투명하게 설정
        dialog?.window?.setBackgroundDrawableResource(android.R.color.transparent)
        // 다이얼로그의 레이아웃 크기 설정 (원하는 크기로 조절)
        dialog?.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DialogPhotoDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setting()
    }

    private fun setting() {
        val photoId = arguments?.getInt(ARG_PHOTO_ID)
        Timber.d("photoId: $photoId")

        val photoPagerAdapter = PhotoPagerAdapter()
        binding.vpPhotos.adapter = photoPagerAdapter
        photoPagerAdapter.submitList(photoAlbumDetailViewModel.album.photos)

        val clickedPhotoIndex = photoAlbumDetailViewModel.album.photos.indexOfFirst { it.id == photoId }
        binding.vpPhotos.setCurrentItem(clickedPhotoIndex, false)

        binding.vpPhotos.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                binding.tvNumOfPhoto.text = getString(R.string.photo_album_detail_num_of_photo, position + 1, photoPagerAdapter.itemCount)
            }
        })

        binding.btnClose.setOnClickListener {
            dismiss() // 팝업 닫기
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}