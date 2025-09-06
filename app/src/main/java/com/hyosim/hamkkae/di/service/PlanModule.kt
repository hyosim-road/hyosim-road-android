package com.hyosim.hamkkae.di.service

import com.hyosim.hamkkae.data.service.PlanService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PlanModule {
    @Provides
    @Singleton
    fun provideService(
        @Named("base") retrofit: Retrofit
    ): PlanService = retrofit.create(PlanService::class.java)
}