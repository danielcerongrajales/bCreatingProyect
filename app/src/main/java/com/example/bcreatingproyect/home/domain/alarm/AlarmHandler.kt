package com.example.bcreatingproyect.home.domain.alarm

import com.example.bcreatingproyect.home.domain.models.Habit

interface AlarmHandler {
    fun setRecurringAlarm(habit: Habit)
    fun cancel(habit: Habit)

}