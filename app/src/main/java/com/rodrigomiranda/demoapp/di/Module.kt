package com.rodrigomiranda.demoapp.di

import com.rodrigomiranda.demoapp.repo.QuestionsRepo
import com.rodrigomiranda.demoapp.repo.QuestionsRepoImpl
import com.rodrigomiranda.demoapp.service.QuestionApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

/**
 * Created by rodrigomiranda on 17/11/24.
 */
@Module
@InstallIn(SingletonComponent::class)
object Module {

    @Provides
    fun provideBaseUrl() = BASE_URL

    @Provides
    @Singleton
    fun provideRetrofitInstance(baseUrl: String): Retrofit =
        Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    @Provides
    @Singleton
    fun provideQuestionApi(retrofitInstance: Retrofit): QuestionApi =
        retrofitInstance.create(QuestionApi::class.java)

    @Provides
    @Singleton
    fun provideQuestionsRepo(questionApiInstance: QuestionApi): QuestionsRepo =
        QuestionsRepoImpl(questionApiInstance)
}

const val BASE_URL = "https://rodrigomirandamarenco.github.io"