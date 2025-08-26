package com.hyosimroad.hamkkae.domain.repository

import android.content.Context
import com.hyosimroad.hamkkae.util.SessionManager
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TokenRepository @Inject constructor(
) {
    fun saveToken(token: String) {
        SessionManager.setToken(token)
    }

    fun getToken(): String? {
        return SessionManager.getToken()
    }

    fun clearToken() {
        SessionManager.clearToken()
    }
}