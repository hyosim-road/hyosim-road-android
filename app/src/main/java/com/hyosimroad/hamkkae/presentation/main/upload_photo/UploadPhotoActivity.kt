package com.hyosimroad.hamkkae.presentation.main.upload_photo

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Rect
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.hyosimroad.hamkkae.R
import com.hyosimroad.hamkkae.databinding.ActivityUploadPhotoBinding
import com.hyosimroad.hamkkae.presentation.main.trip_continue.album.TextAdapter
import timber.log.Timber

class UploadPhotoActivity : AppCompatActivity() {
    private lateinit var binding: ActivityUploadPhotoBinding
    private val uploadPhotoViewModel: UploadPhotoViewModel by viewModels()

    private lateinit var galleryLauncher: ActivityResultLauncher<Intent>


    // 권한 요청을 위한 ActivityResultLauncher (선택적 개선: 더 명확한 권한 처리)
    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
            if (isGranted) {
                // 권한이 허용된 경우 갤러리 열기
                openGalleryPicker()
            } else {
                // 권한이 거부된 경우 사용자에게 알림
                Toast.makeText(this, "갤러리 접근 권한이 필요합니다.", Toast.LENGTH_SHORT).show()
            }
        }

    companion object {
        private const val GALLERY_PERMISSION_CODE = 101
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initBinds()
        setting()
    }

    private fun initBinds() {
        binding = ActivityUploadPhotoBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    private fun setting() {
        // 갤러리에서 이미지 선택하기 위한 launcher
        galleryLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == Activity.RESULT_OK) {
                    val selectedImageUri: Uri? = result.data?.data // Uri 타입 명시
                    selectedImageUri?.let { uri ->
                        Timber.d("갤러리에서 선택된 URI: $uri")
                        with(binding){
                            clPhotoUpload.visibility = View.GONE
                            clPhoto.visibility = View.VISIBLE
                        }
                        // 선택된 이미지를 ivPhoto에 표시
                        binding.ivPhoto.load(uri) {
                            crossfade(true) // 부드러운 이미지 로딩 효과 (선택 사항)
                            placeholder(R.drawable.image_trip) // 로딩 중 표시될 이미지 (선택 사항, drawable 리소스 필요)
                            error(R.drawable.image_trip) // 오류 시 표시될 이미지 (선택 사항, drawable 리소스 필요)
                        }

                        uploadPhotoViewModel.image = uri.toString()
                    }
                }
            }

        initTexts()
    }

    private fun initTexts(){
        with(binding){
            tvCurrent.text = getString(R.string.main_trip_current_location, "불국사")
            tvTime.text = getString(R.string.main_time, "10:00", "11:00")
        }

        val tipAdapter = TextAdapter()
        binding.rvTip.adapter = tipAdapter
        tipAdapter.submitList(uploadPhotoViewModel.tipsList)

        val gap6 = resources.getDimensionPixelSize(R.dimen.dp6)
        binding.rvTip.addItemDecoration(SimpleGapDecoration(gap6))

        initClickListeners()
    }

    // 클릭 리스너 초기화 함수 추가
    private fun initClickListeners() {
        binding.btnSelectPhoto.setOnClickListener {
            checkGalleryPermissionAndOpenPicker()
        }

        binding.btnReselect.setOnClickListener {
            checkGalleryPermissionAndOpenPicker()
        }

        binding.btnComplete.setOnClickListener {
            val intent = Intent(this, UploadPhotoCompleteActivity::class.java)
            intent.putExtra("image", uploadPhotoViewModel.image)
            startActivity(intent)
            finish()
        }
    }

    private fun checkGalleryPermissionAndOpenPicker() {
        val permission = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            // Android 13 (API 33) 이상에서는 세분화된 미디어 권한 사용
            Manifest.permission.READ_MEDIA_IMAGES
        } else {
            // Android 13 미만에서는 외부 저장소 읽기 권한 사용
            Manifest.permission.READ_EXTERNAL_STORAGE
        }

        when {
            ContextCompat.checkSelfPermission(
                this,
                permission
            ) == PackageManager.PERMISSION_GRANTED -> {
                // 권한이 이미 허용된 경우 갤러리 열기
                openGalleryPicker()
            }
            shouldShowRequestPermissionRationale(permission) -> {
                // (선택 사항) 사용자가 이전에 권한 요청을 거부한 경우, 왜 권한이 필요한지 설명
                // 예를 들어 다이얼로그를 표시하고, 사용자가 확인하면 requestPermissionLauncher.launch(permission) 호출
                // 여기서는 간단하게 바로 권한 요청
                Toast.makeText(this, "사진을 선택하려면 갤러리 접근 권한이 필요합니다. 권한을 허용해주세요.", Toast.LENGTH_LONG).show()
                requestPermissionLauncher.launch(permission) // ActivityResultLauncher 사용
            }
            else -> {
                // 처음 권한을 요청하거나, 사용자가 "다시 묻지 않음"을 선택한 후 거부한 경우
                requestPermissionLauncher.launch(permission) // ActivityResultLauncher 사용
                // 또는 기존 방식: requestPermissions(arrayOf(permission), GALLERY_PERMISSION_CODE)
            }
        }
    }

    private fun openGalleryPicker() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        intent.type = "image/*"
        galleryLauncher.launch(intent)
    }

    // requestPermissions()를 직접 사용하는 경우에만 필요한 콜백입니다.
    // ActivityResultContracts.RequestPermission()을 사용하면 이 콜백은 필요하지 않습니다.
    // 여기서는 requestPermissionLauncher를 사용하므로 이 함수는 호출되지 않습니다.
    // 만약 requestPermissions()를 계속 사용하려면 이 함수의 로직을 구현해야 합니다.
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == GALLERY_PERMISSION_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // 권한이 허용된 경우 갤러리 열기
                openGalleryPicker()
            } else {
                // 권한이 거부된 경우 사용자에게 알림
                Toast.makeText(this, "갤러리 접근 권한이 거부되었습니다.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    inner class SimpleGapDecoration(private val gap: Int) : RecyclerView.ItemDecoration() {
        override fun getItemOffsets(outRect: Rect, v: View, p: RecyclerView, s: RecyclerView.State) {
            outRect.set(0, 0, 0, gap)
        }
    }
}