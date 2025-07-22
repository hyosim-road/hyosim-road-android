package com.hyosimroad.hamkkae

import android.app.Application
import com.kakao.vectormap.KakaoMapSdk
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class MyApp: Application() {
    companion object {
        lateinit var instance: MyApp
            private set
    }

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        KakaoMapSdk.init(this, BuildConfig.KAKAO_NATIVE_APP_KEY);
    }
}