package com.hyosimroad.hamkkae.core

import androidx.lifecycle.ViewModel
import org.json.JSONObject
import timber.log.Timber

open class BaseViewModel : ViewModel() {

    protected fun parseHttpError(errorBody: String): String {
        return try {
            val jsonObject = JSONObject(errorBody)
            jsonObject.optString("msg", "알 수 없는 오류입니다.")
        } catch (e: Exception) {
            Timber.e("JSON 파싱 실패: $e")
            "에러 응답을 처리할 수 없습니다."
        }
    }
}
