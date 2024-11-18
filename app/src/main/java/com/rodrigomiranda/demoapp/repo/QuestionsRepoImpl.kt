package com.rodrigomiranda.demoapp.repo

import com.rodrigomiranda.demoapp.model.Question
import com.rodrigomiranda.demoapp.service.QuestionApi
import javax.inject.Inject

/**
 * Created by rodrigomiranda on 17/11/24.
 */
class QuestionsRepoImpl @Inject constructor(
    val api: QuestionApi
) : QuestionsRepo {

    override suspend fun getQuestions(): List<Question> {
        val response = api.getQuestions()
        return if (response.isSuccessful) {
            response.body() ?: emptyList()
        } else {
            emptyList()
        }
    }
}