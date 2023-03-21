package com.android.pokhodai.expensemanagement.di.network.client

import com.android.pokhodai.expensemanagement.BuildConfig
import com.android.pokhodai.expensemanagement.data.settings.ApiSettings
import com.android.pokhodai.expensemanagement.di.annotations.HttpClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object HttpClientModule {

    @Provides
    @Singleton
    @HttpClient
    fun httpClient(
        httpLoggingInterceptor: HttpLoggingInterceptor,
    ): OkHttpClient {
        return OkHttpClient.Builder().apply {
            connectTimeout(ApiSettings.HTTP_CONNECT_TIMEOUT, TimeUnit.SECONDS)
            readTimeout(ApiSettings.HTTP_READ_TIMEOUT, TimeUnit.SECONDS)
            writeTimeout(ApiSettings.HTTP_WRITE_TIMEOUT, TimeUnit.SECONDS)
            if (BuildConfig.DEBUG) addInterceptor(httpLoggingInterceptor)
        }.build()
    }
}