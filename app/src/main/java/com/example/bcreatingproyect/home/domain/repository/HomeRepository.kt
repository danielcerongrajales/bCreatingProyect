package com.example.bcreatingproyect.home.domain.repository

import com.example.bcreatingproyect.home.domain.models.Habit
import kotlinx.coroutines.flow.Flow
import java.time.ZonedDateTime

interface HomeRepository {
    fun getAllHabitsSelectedDate(date:ZonedDateTime): Flow<List<Habit>>
    suspend fun insertHabit(habit: Habit)
    suspend fun getHabitById(id: String): Habit
    suspend fun syncHabits()
}