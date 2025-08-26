package com.hyosimroad.hamkkae.presentation.main.setting.user

import android.content.Context
import androidx.lifecycle.viewModelScope
import com.hyosimroad.hamkkae.core.BaseViewModel
import com.hyosimroad.hamkkae.domain.repository.SettingRepository
import com.hyosimroad.hamkkae.extension.setting.ChangePwState
import com.hyosimroad.hamkkae.extension.setting.CheckPwState
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import okhttp3.ResponseBody
import retrofit2.HttpException
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class ChangePwViewModel @Inject constructor(
    private val settingRepository: SettingRepository,
    @ApplicationContext private val context: Context
) : BaseViewModel() {
    private val _checkPwState = MutableSharedFlow<CheckPwState>()
    val checkPwState: MutableSharedFlow<CheckPwState> = _checkPwState

    private val _changePwState = MutableSharedFlow<ChangePwState>()
    val changePwState: MutableSharedFlow<ChangePwState> = _changePwState

    fun checkPw(password: String) {
        viewModelScope.launch {
            settingRepository.checkPw(password).onSuccess {
                _checkPwState.emit(CheckPwState.Success(it.data.isCorrect))
            }.onFailure {
                _checkPwState.emit(CheckPwState.Error("Error response failure: ${it.message}"))
                if (it is HttpException) {
                    try {
                        val errorBody: ResponseBody? = it.response()?.errorBody()
                        val errorBodyString = errorBody?.string() ?: ""
                        parseHttpError(errorBodyString)
                    } catch (e: Exception) {
                        // JSON 파싱 실패 시 로깅
                        Timber.e("Error parsing error body: ${e}")
                        _checkPwState.emit(CheckPwState.Error("알 수 없는 에러가 발생했습니다."))
                    }
                } else {
                    _checkPwState.emit(CheckPwState.Error("네트워크 에러 또는 알 수 없는 오류: ${it.message}"))
                }
            }
        }
    }

    fun changePw(password: String) {
        viewModelScope.launch {
            settingRepository.resetPw( password).onSuccess {
                _changePwState.emit(ChangePwState.Success(it.status))
            }.onFailure {
                _changePwState.emit(ChangePwState.Error("Error response failure: ${it.message}"))
                if (it is HttpException) {
                    try {
                        val errorBody: ResponseBody? = it.response()?.errorBody()
                        val errorBodyString = errorBody?.string() ?: ""
                        parseHttpError(errorBodyString)
                    } catch (e: Exception) {
                        // JSON 파싱 실패 시 로깅
                        Timber.e("Error parsing error body: ${e}")
                        _changePwState.emit(ChangePwState.Error("알 수 없는 에러가 발생했습니다."))
                    }
                } else {
                    _changePwState.emit(ChangePwState.Error("네트워크 에러 또는 알 수 없는 오류: ${it.message}"))
                }
            }
        }
    }
}