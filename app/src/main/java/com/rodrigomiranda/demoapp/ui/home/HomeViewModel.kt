package com.rodrigomiranda.demoapp.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rodrigomiranda.demoapp.model.Question
import com.rodrigomiranda.demoapp.repo.QuestionsRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val questionsRepo: QuestionsRepo
) : ViewModel() {

    private val _questions = MutableLiveData<List<Question>>()
    val questions: LiveData<List<Question>> = _questions

    fun getQuestions() {
        viewModelScope.launch {
            val repoQuestions = questionsRepo.getQuestions()
            _questions.value = repoQuestions
        }
    }
}