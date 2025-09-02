package com.hyosim.hamkkae.di

import com.hyosim.hamkkae.data.datasource.AuthDataSource
import com.hyosim.hamkkae.data.datasource.SettingDataSource
import com.hyosim.hamkkae.data.datasource_impl.AuthDataSourceImpl
import com.hyosim.hamkkae.data.datasource_impl.SettingDataSourceImpl
import com.hyosim.hamkkae.data.repository_impl.AuthRepositoryImpl
import com.hyosim.hamkkae.data.repository_impl.SettingRepositoryImpl
import com.hyosim.hamkkae.data.repository_impl.TokenRepositoryImpl
import com.hyosim.hamkkae.domain.repository.AuthRepository
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
}