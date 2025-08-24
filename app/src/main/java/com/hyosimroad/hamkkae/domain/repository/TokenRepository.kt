package com.hyosimroad.hamkkae.domain.repository

import android.content.Context
import com.hyosimroad.hamkkae.util.TokenManager
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TokenRepository @Inject constructor(
    @ApplicationContext private val context: Context
) {
    fun saveToken(token: String) {
        TokenManager.saveToken(context, token)
    }

    fun getToken(): String? {
        return TokenManager.getToken(context)
    }

    fun clearToken() {
        TokenManager.clearToken(context)
    }
}