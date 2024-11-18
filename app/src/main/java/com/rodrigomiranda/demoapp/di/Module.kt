package com.rodrigomiranda.demoapp.di

import com.rodrigomiranda.demoapp.repo.QuestionsRepo
import com.rodrigomiranda.demoapp.repo.QuestionsRepoImpl
import com.rodrigomiranda.demoapp.service.QuestionApi
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by rodrigomiranda on 17/11/24.
 */
object Module {

    private val retrofitInstance: Retrofit =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    val questionApiInstance: QuestionApi =
        retrofitInstance.create(QuestionApi::class.java)

    val questionsRepo: QuestionsRepo =
        QuestionsRepoImpl(questionApiInstance)
}

const val BASE_URL = "https://rodrigomirandamarenco.github.io"