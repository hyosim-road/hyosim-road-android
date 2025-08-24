package com.hyosimroad.hamkkae.presentation.auth.signup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hyosimroad.hamkkae.core.BaseViewModel
import com.hyosimroad.hamkkae.domain.model.PasswordRule
import com.hyosimroad.hamkkae.domain.repository.AuthRepository
import com.hyosimroad.hamkkae.extension.auth.CheckIdState
import com.hyosimroad.hamkkae.extension.auth.CodeState
import com.hyosimroad.hamkkae.extension.auth.LoginState
import com.hyosimroad.hamkkae.extension.auth.SendEmailState
import com.hyosimroad.hamkkae.extension.auth.SignUpState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import okhttp3.ResponseBody
import retrofit2.HttpException
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class SignupInfoViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : BaseViewModel() {
    private val _checkIdState = MutableSharedFlow<CheckIdState>()
    val checkIdState: MutableSharedFlow<CheckIdState> = _checkIdState
    private val _sendEmailState = MutableSharedFlow<SendEmailState>()
    val sendEmailState: MutableSharedFlow<SendEmailState> = _sendEmailState
    private val _codeState = MutableSharedFlow<CodeState>()
    val codeState: MutableSharedFlow<CodeState> = _codeState
    var verifiedId: String? = null
        private set // 외부에서 함부로 바꾸지 못하게 setter는 private
    var verifiedEmail: String? = null
        private set
    var verifiedCode: String? = null
        private set

    private val _signUpState = MutableStateFlow<SignUpState>(SignUpState.Loading)
    val signUpState: MutableStateFlow<SignUpState> = _signUpState

    fun checkId(id: String) {
        viewModelScope.launch {
            authRepository.checkId(id).onSuccess { response ->
                _checkIdState.emit(CheckIdState.Success(response.data.isExist))
                if (!response.data.isExist) verifiedId = id
                Timber.d("checkId success!")
            }.onFailure {
                _checkIdState.emit(CheckIdState.Error("Error response failure: ${it.message}"))
                if (it is HttpException) {
                    try {
                        val errorBody: ResponseBody? = it.response()?.errorBody()
                        val errorBodyString = errorBody?.string() ?: ""
                        parseHttpError(errorBodyString)
                    } catch (e: Exception) {
                        // JSON 파싱 실패 시 로깅
                        Timber.e("Error parsing error body: ${e}")
                        _checkIdState.emit(CheckIdState.Error("알 수 없는 에러가 발생했습니다."))
                    }
                } else {
                    _checkIdState.emit(CheckIdState.Error("네트워크 에러 또는 알 수 없는 오류: ${it.message}"))
                }
            }
        }
    }

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
                verifiedEmail = email
                verifiedCode = code
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

    fun signUp(password: String) {
        viewModelScope.launch {
            authRepository.signUp(verifiedId!!, password, verifiedEmail!!).onSuccess {
                if (it.status == "MEMBER409")
                    _signUpState.value = SignUpState.Error("이미 존재하는 아이디입니다.")
                else
                    _signUpState.value = SignUpState.Success(it.message)
            }.onFailure {
                _signUpState.emit(SignUpState.Error("Error response failure: ${it.message}"))
                if (it is HttpException) {
                    try {
                        val errorBody: ResponseBody? = it.response()?.errorBody()
                        val errorBodyString = errorBody?.string() ?: ""
                        parseHttpError(errorBodyString)
                    } catch (e: Exception) {
                        // JSON 파싱 실패 시 로깅
                        Timber.e("Error parsing error body: ${e}")
                        _signUpState.value = SignUpState.Error("알 수 없는 에러가 발생했습니다.")
                    }
                } else {
                    _signUpState.value = SignUpState.Error("네트워크 에러 또는 알 수 없는 오류: ${it.message}")
                }
            }
        }
    }

    suspend fun sendStateLoading(){
        sendEmailState.emit(SendEmailState.Loading)
    }
}