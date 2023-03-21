package com.android.pokhodai.expensemanagement.di.network.retrofit

import com.google.gson.GsonBuilder
import com.android.pokhodai.expensemanagement.data.settings.ApiSettings
import com.android.pokhodai.expensemanagement.di.annotations.ApiRetrofitClient
import com.android.pokhodai.expensemanagement.di.annotations.HttpClient
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
    fun vitaRetrofit(
        @HttpClient okHttpClient: OkHttpClient,
        factory: GsonConverterFactory
    ): Retrofit =
        Retrofit.Builder()
            .baseUrl(ApiSettings.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(factory)
            .build()
}