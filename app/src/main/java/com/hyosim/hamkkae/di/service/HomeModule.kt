package com.hyosim.hamkkae.di.service

import com.hyosim.hamkkae.data.service.HomeService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object HomeModule {
    @Provides
    @Singleton
    fun provideService(
        @Named("base") retrofit: Retrofit
    ): HomeService =
        retrofit.create(HomeService::class.java)

}