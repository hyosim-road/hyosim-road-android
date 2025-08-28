package com.hyosim.hamkkae.util

import android.util.Base64
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey

object SessionManager {
    private var secretKey: SecretKey? = null
    private var encryptedToken: String? = null

    init {
        // 앱 실행 중에만 유지될 임시 AES 키 생성
        val keyGen = KeyGenerator.getInstance("AES")
        keyGen.init(256)
        secretKey = keyGen.generateKey()
    }

    fun setToken(token: String) {
        val cipher = Cipher.getInstance("AES")
        cipher.init(Cipher.ENCRYPT_MODE, secretKey)
        val encrypted = cipher.doFinal(token.toByteArray(Charsets.UTF_8))
        encryptedToken = Base64.encodeToString(encrypted, Base64.DEFAULT)
    }

    fun getToken(): String? {
        return try {
            if (encryptedToken == null) return null
            val cipher = Cipher.getInstance("AES")
            cipher.init(Cipher.DECRYPT_MODE, secretKey)
            val decoded = Base64.decode(encryptedToken, Base64.DEFAULT)
            String(cipher.doFinal(decoded), Charsets.UTF_8)
        } catch (e: Exception) {
            null
        }
    }

    fun clearToken() {
        encryptedToken = null
    }
}