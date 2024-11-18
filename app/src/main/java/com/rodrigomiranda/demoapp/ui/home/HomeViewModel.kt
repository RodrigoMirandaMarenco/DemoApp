package com.rodrigomiranda.demoapp.ui.home

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rodrigomiranda.demoapp.repo.QuestionsRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val questionsRepo: QuestionsRepo
) : ViewModel() {
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