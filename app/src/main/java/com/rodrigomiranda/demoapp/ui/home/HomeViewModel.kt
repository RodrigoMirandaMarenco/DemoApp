package com.rodrigomiranda.demoapp.ui.home

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rodrigomiranda.demoapp.di.Module
import com.rodrigomiranda.demoapp.repo.QuestionsRepo
import kotlinx.coroutines.launch

class HomeViewModel(val questionsRepo: QuestionsRepo = Module.questionsRepo) : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is home Fragment"
    }
    val text: LiveData<String> = _text

    fun getQuestions(context: Context) {
        viewModelScope.launch {
            val questions = questionsRepo.getQuestions()
            Toast.makeText(context, questions.toString(), Toast.LENGTH_SHORT).show()
        }
    }
}