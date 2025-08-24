package com.hyosimroad.hamkkae.data.network

import android.content.Context
import android.content.SharedPreferences
import com.hyosimroad.hamkkae.util.TokenManager
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class HeaderInterceptor @Inject constructor(
    private val context: Context
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()

        // 토큰 가져오기
        val token = TokenManager.getToken(context)

        // 토큰이 없으면 그냥 원래 요청 진행
        // 있으면 Bearer 붙여서 저장
        val newRequest = if (!token.isNullOrEmpty()) {
            originalRequest.newBuilder()
                .addHeader("Authorization", "Bearer $token")
                .build()
        } else {
            originalRequest
        }

        return chain.proceed(newRequest)
    }
    /* override fun intercept(chain: Interceptor.Chain): Response {
         if(chain.request().headers["Auth"] == "false"){
             val newRequest = chain.request().newBuilder()
                 .removeHeader("Auth")
                 .build()
             return chain.proceed(newRequest)
         }

         var token = ""
         runBlocking {
             token = ("Bearer " + prefs.getString("kakaoToken", ""))
         }
         val newRequest = chain.request().newBuilder()
             .addHeader("Authorization", token)
             .build()
         val response = chain.proceed(newRequest)


         return response
     }*/
}