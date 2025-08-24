package com.hyosimroad.hamkkae.di

import com.hyosimroad.hamkkae.data.datasource.AuthDataSource
import com.hyosimroad.hamkkae.data.datasource.SettingDataSource
import com.hyosimroad.hamkkae.data.datasource_impl.AuthDataSourceImpl
import com.hyosimroad.hamkkae.data.datasource_impl.SettingDataSourceImpl
import com.hyosimroad.hamkkae.data.repository_impl.AuthRepositoryImpl
import com.hyosimroad.hamkkae.data.repository_impl.SettingRepositoryImpl
import com.hyosimroad.hamkkae.domain.repository.AuthRepository
import com.hyosimroad.hamkkae.domain.repository.SettingRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class BindModule {

    // auth
    @Binds
    @Singleton
    abstract fun bindAuthRepository(authRepositoryImpl: AuthRepositoryImpl): AuthRepository

    @Binds
    @Singleton
    abstract fun provideAuthDataSource(authDataSourceImpl: AuthDataSourceImpl): AuthDataSource

    // setting
    @Binds
    @Singleton
    abstract fun bindSettingRepository(settingRepositoryImpl: SettingRepositoryImpl): SettingRepository

    @Binds
    @Singleton
    abstract fun provideSettingDataSource(settingDataSourceImpl: SettingDataSourceImpl): SettingDataSource
}