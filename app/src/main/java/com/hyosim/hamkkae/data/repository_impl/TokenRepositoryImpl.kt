package com.hyosim.hamkkae.data.repository_impl

import com.hyosim.hamkkae.domain.repository.TokenRepository
import com.hyosim.hamkkae.util.SessionManager
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TokenRepositoryImpl @Inject constructor(): TokenRepository {
    override fun saveToken(token: String) {
        SessionManager.setToken(token)
    }

    override fun getToken(): String? {
        return SessionManager.getToken()
    }

    override fun clearToken() {
        SessionManager.clearToken()
    }
}