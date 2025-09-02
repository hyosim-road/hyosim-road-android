package com.hyosim.hamkkae.presentation.auth.login

import androidx.lifecycle.viewModelScope
import com.hyosim.hamkkae.core.BaseViewModel
import com.hyosim.hamkkae.domain.repository.AuthRepository
import com.hyosim.hamkkae.extension.auth.CheckEmailState
import com.hyosim.hamkkae.extension.auth.LoginState
import com.hyosim.hamkkae.util.SessionManager
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
                SessionManager.setToken(response.data!!.accessToken)
                Timber.d("login state success")
            }.onFailure {
                if (it is HttpException) {
                    try {
                        val errorBody: ResponseBody? = it.response()?.errorBody()
                        val errorBodyString = errorBody?.string() ?: ""
                        val apiError = parseStatusCode(errorBodyString)

                        _loginState.value = LoginState.Error(
                            status = apiError.status,
                            message = apiError.message,
                        )
                    } catch (e: Exception) {
                        Timber.e("Error parsing error body: $e")
                        _loginState.value = LoginState.Error(
                            status = null,
                            message = "알 수 없는 에러가 발생했습니다.",
                        )
                    }
                } else {
                    _loginState.value = LoginState.Error(
                        status = null,
                        message = it.message,
                    )
                }
            }
        }
    }
}