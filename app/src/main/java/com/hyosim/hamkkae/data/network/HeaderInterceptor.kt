package com.hyosim.hamkkae.data.network


import com.hyosim.hamkkae.domain.repository.TokenRepository
import okhttp3.Interceptor
import okhttp3.Response

class HeaderInterceptor(
    private val tokenRepository: TokenRepository
): Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        // 토큰 가져오기
        val token = tokenRepository.getToken()
        val originalRequest = chain.request()

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