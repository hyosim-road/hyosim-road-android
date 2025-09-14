package com.hyosim.hamkkae.di

import com.hyosim.hamkkae.data.datasource.AiPlanDataSource
import com.hyosim.hamkkae.data.datasource.AuthDataSource
import com.hyosim.hamkkae.data.datasource.ConversationDataSource
import com.hyosim.hamkkae.data.datasource.HomeDataSource
import com.hyosim.hamkkae.data.datasource.PlanDataSource
import com.hyosim.hamkkae.data.datasource.SettingDataSource
import com.hyosim.hamkkae.data.datasource_impl.AiPlanDataSourceImpl
import com.hyosim.hamkkae.data.datasource_impl.AuthDataSourceImpl
import com.hyosim.hamkkae.data.datasource_impl.ConversationDataSourceImpl
import com.hyosim.hamkkae.data.datasource_impl.HomeDataSourceImpl
import com.hyosim.hamkkae.data.datasource_impl.PlanDataSourceImpl
import com.hyosim.hamkkae.data.datasource_impl.SettingDataSourceImpl
import com.hyosim.hamkkae.data.repository_impl.AiPlanRepositoryImpl
import com.hyosim.hamkkae.data.repository_impl.AuthRepositoryImpl
import com.hyosim.hamkkae.data.repository_impl.ConversationRepositoryImpl
import com.hyosim.hamkkae.data.repository_impl.HomeRepositoryImpl
import com.hyosim.hamkkae.data.repository_impl.PlanRepositoryImpl
import com.hyosim.hamkkae.data.repository_impl.SettingRepositoryImpl
import com.hyosim.hamkkae.data.repository_impl.TokenRepositoryImpl
import com.hyosim.hamkkae.domain.repository.AiPlanRepository
import com.hyosim.hamkkae.domain.repository.AuthRepository
import com.hyosim.hamkkae.domain.repository.ConversationRepository
import com.hyosim.hamkkae.domain.repository.HomeRepository
import com.hyosim.hamkkae.domain.repository.PlanRepository
import com.hyosim.hamkkae.domain.repository.SettingRepository
import com.hyosim.hamkkae.domain.repository.TokenRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class BindModule {

    // token\
    @Binds
    @Singleton
    abstract fun bindTokenRepository(tokenRepositoryImpl: TokenRepositoryImpl): TokenRepository

    // auth
    @Binds
    @Singleton
    abstract fun bindAuthRepository(authRepositoryImpl: AuthRepositoryImpl): AuthRepository

    @Binds
    @Singleton
    abstract fun bindAuthDataSource(authDataSourceImpl: AuthDataSourceImpl): AuthDataSource

    // setting
    @Binds
    @Singleton
    abstract fun bindSettingRepository(settingRepositoryImpl: SettingRepositoryImpl): SettingRepository

    @Binds
    @Singleton
    abstract fun bindSettingDataSource(settingDataSourceImpl: SettingDataSourceImpl): SettingDataSource

    // plan
    @Binds
    @Singleton
    abstract fun bindPlanRepository(planRepositoryImpl: PlanRepositoryImpl): PlanRepository

    @Binds
    @Singleton
    abstract fun bindPlanDataSource(planDataSourceImpl: PlanDataSourceImpl): PlanDataSource

    // home
    @Binds
    @Singleton
    abstract fun bindHomeRepository(homeRepositoryImpl: HomeRepositoryImpl): HomeRepository

    @Binds
    @Singleton
    abstract fun bindHomeDataSource(homeDataSourceImpl: HomeDataSourceImpl): HomeDataSource

    // conversation
    @Binds
    @Singleton
    abstract fun bindConversationRepository(conversationRepositoryImpl: ConversationRepositoryImpl): ConversationRepository

    @Binds
    @Singleton
    abstract fun bindConversationDataSource(conversationDataSourceImpl: ConversationDataSourceImpl): ConversationDataSource


    // temp
    @Binds
    @Singleton
    abstract fun bindAiPlanRepository(aiPlanRepositoryImpl: AiPlanRepositoryImpl): AiPlanRepository

    @Binds
    @Singleton
    abstract fun bindAiPlanDataSource(aiPlanDataSourceImpl: AiPlanDataSourceImpl): AiPlanDataSource
}