package com.android.pokhodai.expensemanagement.di.network

import com.android.pokhodai.expensemanagement.data.service.AskQuestService
import com.android.pokhodai.expensemanagement.di.annotations.ApiRetrofitClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiServiceModule {

    @Provides
    @Singleton
    fun provideAskQuestionService(@ApiRetrofitClient retrofit: Retrofit): AskQuestService =
        retrofit.create(AskQuestService::class.java)
}