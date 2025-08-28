package com.hyosim.hamkkae.di.service

import com.hyosim.hamkkae.data.service.SettingService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ServiceModule {
    @Provides
    @Singleton
    fun provideService(
        retrofit: Retrofit
    ): SettingService = retrofit.create(SettingService::class.java)
}