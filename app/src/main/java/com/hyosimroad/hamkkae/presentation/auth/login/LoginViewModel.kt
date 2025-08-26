package com.hyosimroad.hamkkae.presentation.auth.login

import androidx.lifecycle.viewModelScope
import com.hyosimroad.hamkkae.core.BaseViewModel
import com.hyosimroad.hamkkae.domain.repository.AuthRepository
import com.hyosimroad.hamkkae.extension.auth.LoginState
import com.hyosimroad.hamkkae.util.SessionManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import okhttp3.ResponseBody
import retrofit2.HttpException
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : BaseViewModel() {
    private var _loginState = MutableStateFlow<LoginState>(LoginState.Loading)
    val loginState: StateFlow<LoginState> = _loginState.asStateFlow()

    fun login(email:String, pw:String){
        viewModelScope.launch {
            authRepository.login(email, pw).onSuccess { response->
                _loginState.value=LoginState.Success(response)
                SessionManager.setToken(response.data.accessToken)
                Timber.d("login state success")
            }.onFailure {
                _loginState.value=LoginState.Error("Error response failure: ${it.message}")
                if (it is HttpException) {
                    try {
                        val errorBody: ResponseBody? = it.response()?.errorBody()
                        val errorBodyString = errorBody?.string() ?: ""
                        parseHttpError(errorBodyString)
                    } catch (e: Exception) {
                        // JSON 파싱 실패 시 로깅
                        Timber.e("Error parsing error body: ${e}")
                        _loginState.value = LoginState.Error("알 수 없는 에러가 발생했습니다.")
                    }
                }else {
                    _loginState.value = LoginState.Error("네트워크 에러 또는 알 수 없는 오류: ${it.message}")
                }
            }
        }
    }
}