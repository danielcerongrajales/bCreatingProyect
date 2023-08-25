package com.example.bcreatingproyect.home.domain.home.usecase

import com.example.bcreatingproyect.home.domain.repository.HomeRepository

class SyncHabitUseCase(private val repository: HomeRepository) {
    suspend  operator fun invoke(){
        repository.syncHabits()
    }
}