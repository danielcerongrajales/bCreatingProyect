package com.example.bcreatingproyect.home.data.repository

import com.example.bcreatingproyect.home.domain.models.Habit
import com.example.bcreatingproyect.home.domain.repository.HomeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import java.time.ZonedDateTime

class FakeHomeRepository:HomeRepository {
    private var habits= emptyList<Habit>()
    private val habitFlow= MutableSharedFlow<List<Habit>>()
    override fun getAllHabitsForSelectedDate(date: ZonedDateTime): Flow<List<Habit>> =habitFlow

    override suspend fun insertHabit(habit: Habit) {
        habits= habits+habit
        habitFlow.emit(habits)
    }

    override suspend fun getHabitById(id: String): Habit =habits.first{id==it.id}
    override suspend fun syncHabits() {}
}