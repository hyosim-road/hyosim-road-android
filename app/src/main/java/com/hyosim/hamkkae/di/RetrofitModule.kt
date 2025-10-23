package com.hyosim.hamkkae.di

import android.content.Context
import android.content.SharedPreferences
import com.hyosim.hamkkae.BuildConfig
import com.hyosim.hamkkae.data.network.HeaderInterceptor
import com.hyosim.hamkkae.data.repository_impl.TokenRepositoryImpl
import com.hyosim.hamkkae.domain.repository.TokenRepository
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.Retrofit
import timber.log.Timber
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule {

    @Provides
    @Singleton
    fun provideSharedPreferences(
        @ApplicationContext context: Context
    ): SharedPreferences {
        return context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
    }

    @Provides
    @Singleton
    fun provideHeaderInterceptor(
        tokenRepository: TokenRepository
    ): HeaderInterceptor =
        HeaderInterceptor(tokenRepository)

    @Singleton
    @Provides
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor { message ->
            Timber.tag("OkHttp").d(message) // 간단하게 출력
        }.apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(
        headerInterceptor: HeaderInterceptor, // 토큰 붙이는 인터셉터
        loggingInterceptor: HttpLoggingInterceptor
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(headerInterceptor) // 먼저 토큰 붙이기
            .addInterceptor(loggingInterceptor) // 그다음 로깅
            .connectTimeout(180, TimeUnit.SECONDS)
            .readTimeout(180, TimeUnit.SECONDS)
            .writeTimeout(180, TimeUnit.SECONDS)
            .build()
    }

    @Provides
    @Singleton
    @Named("base")
    fun provideBaseRetrofit(
        jsonConverter: Converter.Factory,
        client: OkHttpClient,
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(jsonConverter)
            .client(client)
            .build()
    }

    @Provides
    @Singleton
    @Named("ai")
    fun provideAiRetrofit(
        jsonConverter: Converter.Factory,
        client: OkHttpClient,
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.AI_URL) // 새로 추가한 AI 서버
            .addConverterFactory(jsonConverter)
            .client(client)
            .build()
    }

    @Provides
    @Singleton
    fun provideJsonConverterFactory(): Converter.Factory {
        return Json.asConverterFactory("application/json".toMediaType())
    }
}