package com.example.run.ui.screens

import android.annotation.SuppressLint
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.run.data.RunRepository
import com.example.run.data.entities.Duration
import com.example.run.data.entities.Session
import dagger.hilt.android.lifecycle.HiltViewModel
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

data class HomeUiState(
    val sessionsDone: Int = 0,
    val hoursRan: Double = 0.0,
    val isTimerRunning: Boolean = false,
    val sessionDuration: Int = 0,
    val timerValue: Int = 0,
)

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: RunRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(HomeUiState())
    private var _duration: Duration? = null

    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()
    val durations = repository.getAllDurations()
    val sessions = repository.getAllSessions()

    private fun getDuration(minutes: Int) : Duration? {
        viewModelScope.launch {
            repository.getDuration(minutes).let {
                this@HomeViewModel._duration = it
            }
        }
        return _duration
    }

    fun onAddDuration(minutes: Int) {
        _duration = null
        if (minutes < 1) {
            return
        }
        if (getDuration(minutes) != null) {
            return
        }
        viewModelScope.launch {
            repository.insertDuration(
                Duration(seconds = minutes * 60)
            )
        }
    }

    fun onClickTimerButton(isRunning: Boolean) {
        _uiState.update {
            it.copy(isTimerRunning = isRunning)
        }
    }

    fun onUpdateTimer(newValue: Int) {
        _uiState.update {
            it.copy(timerValue = newValue)
        }
    }

    fun onSetUpTimer(time: Int) {
        _uiState.update {
            it.copy(
                timerValue = time,
                sessionDuration = time,
            )
        }
    }

    fun onFinishSession() {
        viewModelScope.launch {
            repository.insertSession(
                Session(
                    date = simpleDateFormat.format(Date()),
                    duration = uiState.value.sessionDuration
                )
            )
        }
    }
}

@SuppressLint("SimpleDateFormat")
private val simpleDateFormat = SimpleDateFormat("dd/M/yyyy")