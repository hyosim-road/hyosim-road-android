package com.hyosim.hamkkae.di.service

import com.hyosim.hamkkae.data.service.AiPlanService
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
object AiPlanModule {
    @Provides
    @Singleton
    fun provideAiPlanService(
        @Named("ai") retrofit: Retrofit
    ): AiPlanService = retrofit.create(AiPlanService::class.java)
}
