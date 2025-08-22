package com.hyosimroad.hamkkae.di.service

import com.hyosimroad.hamkkae.data.service.AuthService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AuthModule {
    @Provides
    @Singleton
    fun provideService(
        retrofit: Retrofit
    ): AuthService =
        retrofit.create(AuthService::class.java)
}