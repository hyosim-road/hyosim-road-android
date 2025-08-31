package com.hyosim.hamkkae.core

import androidx.lifecycle.ViewModel
import org.json.JSONObject
import timber.log.Timber

open class BaseViewModel : ViewModel() {

    protected fun parseHttpError(errorBody: String): String {
        return try {
            val jsonObject = JSONObject(errorBody)
            jsonObject.optString("message", "알 수 없는 오류입니다.")
        } catch (e: Exception) {
            Timber.e("JSON 파싱 실패: $e")
            "에러 응답을 처리할 수 없습니다."
        }
    }

    data class ApiError(
        val status: String?,
        val message: String?
    )

    protected fun parseStatusCode(errorBody: String): ApiError {
        return try {
            val jsonObject = JSONObject(errorBody)
            ApiError(
                status = jsonObject.optString("status", null),
                message = jsonObject.optString("message", null)
            )
        } catch (e: Exception) {
            Timber.e("JSON 파싱 실패: $e")
            ApiError(status = null, message = "에러 응답을 처리할 수 없습니다.")
        }
    }

}
