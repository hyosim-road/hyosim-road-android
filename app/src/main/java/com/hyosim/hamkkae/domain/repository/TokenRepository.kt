package com.hyosim.hamkkae.domain.repository

import com.hyosim.hamkkae.util.SessionManager
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