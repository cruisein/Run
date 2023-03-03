package com.example.run.ui.screens.history

import androidx.lifecycle.ViewModel
import com.example.run.data.RunRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(
    private val repository: RunRepository
) : ViewModel() {
    val sessions = repository.getAllSessions()
}