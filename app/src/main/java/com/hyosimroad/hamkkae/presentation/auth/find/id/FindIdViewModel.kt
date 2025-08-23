package com.hyosimroad.hamkkae.presentation.auth.find.id

import androidx.lifecycle.viewModelScope
import com.hyosimroad.hamkkae.core.BaseViewModel
import com.hyosimroad.hamkkae.domain.repository.AuthRepository
import com.hyosimroad.hamkkae.extension.auth.CodeState
import com.hyosimroad.hamkkae.extension.auth.GetIdState
import com.hyosimroad.hamkkae.extension.auth.SendEmailState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import okhttp3.ResponseBody
import retrofit2.HttpException
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class FindIdViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : BaseViewModel() {
    private val _sendEmailState = MutableSharedFlow<SendEmailState>()
    val sendEmailState: MutableSharedFlow<SendEmailState> = _sendEmailState
    private val _codeState = MutableSharedFlow<CodeState>()
    val codeState: MutableSharedFlow<CodeState> = _codeState

    private val _getIdState = MutableStateFlow<GetIdState>(GetIdState.Loading)
    val getIdState: MutableStateFlow<GetIdState> = _getIdState

    fun sendEmail(email: String) {
        viewModelScope.launch {
            authRepository.send(email).onSuccess {
                _sendEmailState.emit(SendEmailState.Success(it.message))
            }.onFailure {
                _sendEmailState.emit(SendEmailState.Error("Error response failure: ${it.message}"))
                if (it is HttpException) {
                    try {
                        val errorBody: ResponseBody? = it.response()?.errorBody()
                        val errorBodyString = errorBody?.string() ?: ""
                        parseHttpError(errorBodyString)
                    } catch (e: Exception) {
                        // JSON 파싱 실패 시 로깅
                        Timber.e("Error parsing error body: ${e}")
                        _sendEmailState.emit(SendEmailState.Error("알 수 없는 에러가 발생했습니다."))
                    }
                } else {
                    _sendEmailState.emit(SendEmailState.Error("네트워크 에러 또는 알 수 없는 오류: ${it.message}"))
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

    fun getId(email: String){
        viewModelScope.launch {
            authRepository.getMyId(email).onSuccess {
                _getIdState.value = GetIdState.Success(it.data.success)
            }.onFailure {
                _getIdState.value = GetIdState.Error("Error response failure: ${it.message}")
                if (it is HttpException) {
                    try {
                        val errorBody: ResponseBody? = it.response()?.errorBody()
                        val errorBodyString = errorBody?.string() ?: ""
                        parseHttpError(errorBodyString)
                    } catch (e: Exception) {
                        // JSON 파싱 실패 시 로깅
                        Timber.e("Error parsing error body: ${e}")
                        _getIdState.emit(GetIdState.Error("알 수 없는 에러가 발생했습니다."))
                    }
                } else {
                    _getIdState.emit(GetIdState.Error("네트워크 에러 또는 알 수 없는 오류: ${it.message}"))
                }
            }
        }
    }
}