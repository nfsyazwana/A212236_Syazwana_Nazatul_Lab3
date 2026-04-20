package com.example.a212236_syazwana_nazatul_lab4

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class RecyclingViewModel : ViewModel() {
    private val _currentSubmission = MutableStateFlow(Submission())
    val currentSubmission: StateFlow<Submission> = _currentSubmission.asStateFlow()

    private val _userStats = MutableStateFlow(UserStats())
    val userStats: StateFlow<UserStats> = _userStats.asStateFlow()

    fun updateCurrentSubmission(submission: Submission) {
        viewModelScope.launch {
            _currentSubmission.emit(submission)
        }
    }

    fun saveSubmission(submission: Submission) {
        val points = if (submission.actionType == "Recycle") {
            (10 * submission.quantity).toInt()
        } else {
            (15 * submission.quantity).toInt()
        }
        val submissionWithPoints = submission.copy(pointsEarned = points)

        viewModelScope.launch {
            val currentStats = _userStats.value
            val updatedStats = when (submission.actionType) {
                "Recycle" -> currentStats.copy(
                    totalPoints = currentStats.totalPoints + points,
                    recycledItems = currentStats.recycledItems + 1,
                    submissions = currentStats.submissions + submissionWithPoints
                )
                "Donate" -> currentStats.copy(
                    totalPoints = currentStats.totalPoints + points,
                    donatedItems = currentStats.donatedItems + 1,
                    submissions = currentStats.submissions + submissionWithPoints
                )
                else -> currentStats
            }
            _userStats.emit(updatedStats)
            _currentSubmission.emit(Submission())
        }
    }

    fun resetSubmission() {
        viewModelScope.launch {
            _currentSubmission.emit(Submission())
        }
    }
}