package com.example.bcreatingproyect.home.data.mapper

import com.example.bcreatingproyect.home.data.estension.toStartOfDateTimestamp
import com.example.bcreatingproyect.home.data.estension.toTimeStamp
import com.example.bcreatingproyect.home.data.estension.toZonedDateTime
import com.example.bcreatingproyect.home.data.local.entity.HabitEntity
import com.example.bcreatingproyect.home.data.local.entity.SyncEntity
import com.example.bcreatingproyect.home.data.remote.dto.HabitDto
import com.example.bcreatingproyect.home.data.remote.dto.HabitResponse
import com.example.bcreatingproyect.home.domain.models.Habit
import java.time.DayOfWeek
import java.time.ZonedDateTime

fun HabitEntity.toDomain(): Habit {
    return Habit(
        id = this.id,
        name = this.name,
        frequency = this.frequency.map { DayOfWeek.of(it) },
        completedDates = this.completedDates.map { it.toZonedDateTime().toLocalDate() },
        reminder = this.reminder.toZonedDateTime().toLocalTime(),
        startDate = ZonedDateTime.now()
    )
}

fun Habit.toEntity(): HabitEntity {
    return HabitEntity(
        id = this.id,
        name = this.name,
        frequency = this.frequency.map { it.value },
        completedDates = this.completedDates.map { it.toZonedDateTime().toTimeStamp() },
        reminder = this.reminder.toZonedDateTime().toTimeStamp(),
        startDate = this.startDate.toStartOfDateTimestamp()
    )
}

fun HabitResponse.toDomain(): List<Habit> {
    return this.entries.map {
        val id = it.key
        val dto = it.value
        Habit(
            id = id,
            name = dto.name,
            frequency = dto.frequency.map { DayOfWeek.of(it) },
            completedDates = dto.completedDates?.map { it.toZonedDateTime().toLocalDate() }
                ?: emptyList(),
            reminder = dto.reminder.toZonedDateTime().toLocalTime(),
            startDate = dto.startDate.toZonedDateTime()
        )
    }
}

fun Habit.toDto(): HabitResponse {
    val dto = HabitDto(
        name = this.name,
        frequency = this.frequency.map { it.value },
        completedDates = this.completedDates.map { it.toZonedDateTime().toTimeStamp() },
        reminder = this.reminder.toZonedDateTime().toTimeStamp(),
        startDate = this.startDate.toStartOfDateTimestamp()
    )
    return mapOf(id to dto)
}

fun Habit.toSyncEntity(): SyncEntity {
    return SyncEntity(id)
}
