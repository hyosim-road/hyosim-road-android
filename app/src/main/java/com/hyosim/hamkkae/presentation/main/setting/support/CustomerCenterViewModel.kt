package com.hyosim.hamkkae.presentation.main.setting.support

import androidx.lifecycle.viewModelScope
import com.hyosim.hamkkae.core.BaseViewModel
import com.hyosim.hamkkae.data.request_dto.setting.InquiryRequestDto
import com.hyosim.hamkkae.domain.model.AskType
import com.hyosim.hamkkae.domain.repository.SettingRepository
import com.hyosim.hamkkae.extension.setting.InquiryState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import okhttp3.ResponseBody
import retrofit2.HttpException
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class CustomerCenterViewModel @Inject constructor(
    private val settingRepository: SettingRepository
): BaseViewModel() {
    private val _inquiryState = MutableSharedFlow<InquiryState>()
    val inquiryState: MutableSharedFlow<InquiryState> = _inquiryState

    fun inquiry(inquiryType: AskType, title:String, content:String, replyEmail:String){
        viewModelScope.launch {
            settingRepository.inquiry(InquiryRequestDto(inquiryType, title, content, replyEmail)).onSuccess {
                _inquiryState.emit(InquiryState.Success(it.message))
            }.onFailure {
                _inquiryState.emit(InquiryState.Error("Error response failure: ${it.message}"))
                if (it is HttpException) {
                    try {
                        val errorBody: ResponseBody? = it.response()?.errorBody()
                        val errorBodyString = errorBody?.string() ?: ""
                        parseHttpError(errorBodyString)
                    } catch (e: Exception) {
                        // JSON 파싱 실패 시 로깅
                        Timber.e("Error parsing error body: ${e}")
                        _inquiryState.emit(InquiryState.Error("알 수 없는 에러가 발생했습니다."))
                    }
                } else {
                    _inquiryState.emit(InquiryState.Error("네트워크 에러 또는 알 수 없는 오류: ${it.message}"))
                }
            }
        }
    }
}