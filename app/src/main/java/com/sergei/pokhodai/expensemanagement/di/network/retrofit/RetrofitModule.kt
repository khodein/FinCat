package com.sergei.pokhodai.expensemanagement.di.network.retrofit

import com.sergei.pokhodai.expensemanagement.data.settings.ApiSettings
import com.sergei.pokhodai.expensemanagement.di.annotations.ApiRetrofitClient
import com.sergei.pokhodai.expensemanagement.di.annotations.HttpClient
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule {

    @Provides
    fun provideGsonFactory(): GsonConverterFactory = GsonConverterFactory.create(
        GsonBuilder().setLenient().create()
    )

    @Provides
    @Singleton
    @ApiRetrofitClient
    fun apiRetrofit(
        @HttpClient okHttpClient: OkHttpClient,
        factory: GsonConverterFactory
    ): Retrofit =
        Retrofit.Builder()
            .baseUrl(ApiSettings.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(factory)
            .build()
}