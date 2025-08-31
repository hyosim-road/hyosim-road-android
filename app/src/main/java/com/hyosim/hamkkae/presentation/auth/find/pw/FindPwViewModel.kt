package com.hyosim.hamkkae.presentation.auth.find.pw

import androidx.lifecycle.viewModelScope
import com.hyosim.hamkkae.core.BaseViewModel
import com.hyosim.hamkkae.domain.repository.AuthRepository
import com.hyosim.hamkkae.extension.auth.CodeState
import com.hyosim.hamkkae.extension.auth.SendEmailState
import com.hyosim.hamkkae.extension.auth.SendTempPwState
import com.hyosim.hamkkae.extension.auth.VerifyIdEmailState
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
class FindPwViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : BaseViewModel() {
    private val _verifyIdEmailState = MutableSharedFlow<VerifyIdEmailState>()
    val verifyIdEmailState: MutableSharedFlow<VerifyIdEmailState> = _verifyIdEmailState
    private val _sendEmailState = MutableSharedFlow<SendEmailState>()
    val sendEmailState: MutableSharedFlow<SendEmailState> = _sendEmailState
    private val _codeState = MutableSharedFlow<CodeState>()
    val codeState: MutableSharedFlow<CodeState> = _codeState

    private val _sendTempPwState = MutableStateFlow<SendTempPwState>(SendTempPwState.Loading)
    val sendTempPwState: StateFlow<SendTempPwState> = _sendTempPwState

    fun verifyIdEmail(id: String, email: String) {
        viewModelScope.launch {
            authRepository.verifyIdEmail(id, email).onSuccess { response ->
                _verifyIdEmailState.emit(VerifyIdEmailState.Success(response, email))
            }.onFailure {
                _verifyIdEmailState.emit(VerifyIdEmailState.Error("Error response failure: ${it.message}"))
                if (it is HttpException) {
                    try {
                        val errorBody: ResponseBody? = it.response()?.errorBody()
                        val errorBodyString = errorBody?.string() ?: ""
                        parseHttpError(errorBodyString)
                    } catch (e: Exception) {
                        // JSON 파싱 실패 시 로깅
                        Timber.e("Error parsing error body: ${e}")
                        _verifyIdEmailState.emit(VerifyIdEmailState.Error("알 수 없는 에러가 발생했습니다."))
                    }
                } else {
                    _verifyIdEmailState.emit(VerifyIdEmailState.Error("네트워크 에러 또는 알 수 없는 오류: ${it.message}"))
                }
            }
        }
    }

    fun sendEmail(email: String) {
        viewModelScope.launch {
            authRepository.send(email).onSuccess {
                _sendEmailState.emit(SendEmailState.Success(it.message))
            }.onFailure {
                if (it is HttpException) {
                    try {
                        val errorBody: ResponseBody? = it.response()?.errorBody()
                        val errorBodyString = errorBody?.string() ?: ""
                        val apiError = parseStatusCode(errorBodyString)

                        _sendEmailState.emit(
                            SendEmailState.Error(
                                status = apiError.status,
                                message = apiError.message,
                            )
                        )
                    } catch (e: Exception) {
                        Timber.e("Error parsing error body: $e")
                        _sendEmailState.emit(
                            SendEmailState.Error(
                                status = null,
                                message = "알 수 없는 에러가 발생했습니다.",
                            )
                        )
                    }
                } else {
                    _sendEmailState.emit(
                        SendEmailState.Error(
                            status = null,
                            message = it.message,
                        )
                    )
                }
            }
        }
    }

    fun verifyCode(email: String, code: String) {
        viewModelScope.launch {
            authRepository.verify(email, code).onSuccess { response ->
                _codeState.emit(CodeState.Success(response.data!!.success, email))
                Timber.d("email: ${email}, code: ${code}")
            }.onFailure {
                _codeState.emit(CodeState.Error("Error response failure: ${it.message}"))
                if (it is HttpException) {
                    try {
                        val errorBody: ResponseBody? = it.response()?.errorBody()
                        val errorBodyString = errorBody?.string() ?: ""
                        parseHttpError(errorBodyString)
                    } catch (e: Exception) {
                        // JSON 파싱 실패 시 로깅
                        Timber.e("Error parsing error body: ${e}")
                        _codeState.emit(CodeState.Error("알 수 없는 에러가 발생했습니다."))
                    }
                } else {
                    _codeState.emit(CodeState.Error("네트워크 에러 또는 알 수 없는 오류: ${it.message}"))
                }
            }
        }
    }

    fun sendTempPw(email: String) {
        viewModelScope.launch {
            authRepository.sendTempPw(email).onSuccess {
                _sendTempPwState.value = SendTempPwState.Success(it.message)
            }.onFailure {
                _sendTempPwState.value =
                    SendTempPwState.Error("Error response failure: ${it.message}")
                if (it is HttpException) {
                    try {
                        val errorBody: ResponseBody? = it.response()?.errorBody()
                        val errorBodyString = errorBody?.string() ?: ""
                        parseHttpError(errorBodyString)
                    } catch (e: Exception) {
                        // JSON 파싱 실패 시 로깅
                        Timber.e("Error parsing error body: ${e}")
                        _sendTempPwState.value = SendTempPwState.Error("알 수 없는 에러가 발생했습니다.")
                    }
                } else {
                    _sendTempPwState.value =
                        SendTempPwState.Error("네트워크 에러 또는 알 수 없는 오류: ${it.message}")
                }
            }
        }
    }
}