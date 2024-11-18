package com.rodrigomiranda.demoapp.service

import com.rodrigomiranda.demoapp.model.Question
import retrofit2.Response
import retrofit2.http.GET

/**
 * Created by rodrigomiranda on 17/11/24.
 */
interface QuestionApi {

    @GET("qa.json")
    suspend fun getQuestions(): Response<List<Question>>
}