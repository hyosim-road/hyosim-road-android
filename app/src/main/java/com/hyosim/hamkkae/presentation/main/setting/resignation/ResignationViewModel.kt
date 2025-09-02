package com.hyosim.hamkkae.presentation.main.setting.resignation

import androidx.compose.runtime.MutableState
import androidx.lifecycle.viewModelScope
import com.hyosim.hamkkae.core.BaseViewModel
import com.hyosim.hamkkae.domain.repository.SettingRepository
import com.hyosim.hamkkae.extension.setting.InquiryState
import com.hyosim.hamkkae.extension.setting.ResignationState
import com.hyosim.hamkkae.util.SessionManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import okhttp3.ResponseBody
import retrofit2.HttpException
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class ResignationViewModel @Inject constructor(
    private val settingRepository: SettingRepository
): BaseViewModel() {
    private val _resignationState = MutableStateFlow<ResignationState>(ResignationState.Loading)
    val resignationState: StateFlow<ResignationState> = _resignationState

    fun resignation(){
        viewModelScope.launch {
            settingRepository.resignation().onSuccess {
                _resignationState.value = ResignationState.Success
                SessionManager.clearToken()
            }.onFailure {
                _resignationState.value = ResignationState.Error("Error response failure: ${it.message}")
                if (it is HttpException) {
                    try {
                        val errorBody: ResponseBody? = it.response()?.errorBody()
                        val errorBodyString = errorBody?.string() ?: ""
                        parseHttpError(errorBodyString)
                    } catch (e: Exception) {
                        // JSON 파싱 실패 시 로깅
                        Timber.e("Error parsing error body: ${e}")
                        _resignationState.emit(ResignationState.Error("알 수 없는 에러가 발생했습니다."))
                    }
                } else {
                    _resignationState.emit(ResignationState.Error("네트워크 에러 또는 알 수 없는 오류: ${it.message}"))
                }
            }
        }
    }
}
