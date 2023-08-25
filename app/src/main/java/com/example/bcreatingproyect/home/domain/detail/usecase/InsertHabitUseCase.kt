package com.example.bcreatingproyect.home.domain.detail.usecase

import com.example.bcreatingproyect.home.domain.models.Habit
import com.example.bcreatingproyect.home.domain.repository.HomeRepository

class InsertHabitUseCase(private val repository: HomeRepository) {
    suspend operator fun invoke(habit: Habit) {
        repository.insertHabit(habit)
    }
}