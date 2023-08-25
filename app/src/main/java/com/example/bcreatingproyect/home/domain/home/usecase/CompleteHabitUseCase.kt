package com.example.bcreatingproyect.home.domain.home.usecase

import com.example.bcreatingproyect.home.domain.models.Habit
import com.example.bcreatingproyect.home.domain.repository.HomeRepository
import java.time.ZonedDateTime

class CompleteHabitUseCase(private val repository: HomeRepository) {
    suspend operator fun invoke(habit: Habit, date: ZonedDateTime) {
        habit.completedDates?.let {


        val newHabit = if (habit.completedDates.contains(date.toLocalDate())) {
            habit.copy(
                completedDates = habit.completedDates - date.toLocalDate()
            )
        } else {
            habit.copy(
                completedDates = habit.completedDates + date.toLocalDate()
            )
        }
        repository.insertHabit(newHabit)
        }
    }
}