package com.rodrigomiranda.demoapp.repo

import com.rodrigomiranda.demoapp.model.Question

/**
 * Created by rodrigomiranda on 17/11/24.
 */
interface QuestionsRepo {
    suspend fun getQuestions(): List<Question>
}