package com.example.bcreatingproyect.home.data.alarm

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import com.example.bcreatingproyect.home.data.alarm.AlarmReceiver.Companion.HABIT_ID
import com.example.bcreatingproyect.home.data.estension.toTimeStamp
import com.example.bcreatingproyect.home.domain.alarm.AlarmHandler
import com.example.bcreatingproyect.home.domain.models.Habit
import java.time.DayOfWeek
import java.time.ZonedDateTime

class AlarmHandlerImp(private val context: Context):AlarmHandler {
    private val alarmManager= context.getSystemService(AlarmManager::class.java)
    override fun setRecurringAlarm(habit: Habit) {
        val nextOcurrence= calculateNextOcurrence(habit)
        alarmManager.setExactAndAllowWhileIdle(
             AlarmManager.RTC_WAKEUP,
             nextOcurrence.toTimeStamp(),
             createPendingIntent(habit,nextOcurrence.dayOfWeek)
        )
    }

    private fun createPendingIntent(habit: Habit,dayOfWeek: DayOfWeek): PendingIntent {
        val intent= Intent(context,AlarmReceiver::class.java).apply {
            putExtra(HABIT_ID,habit.id)
        }
        return PendingIntent.getBroadcast(
            context,
            habit.id.hashCode() * 10+ dayOfWeek.value,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
    }

    private fun calculateNextOcurrence(habit: Habit):ZonedDateTime{
        val today= ZonedDateTime.now()
        var nextOcurrence= ZonedDateTime.of(today.toLocalDate(),habit.reminder,today.zone)
        if(habit.frequency.contains(today.dayOfWeek) && today.isBefore(nextOcurrence)){
            return  nextOcurrence
        }
        do {
            nextOcurrence= nextOcurrence.plusDays(1)
        }
        while (habit.frequency.contains(nextOcurrence.dayOfWeek))
        return nextOcurrence
    }

    override fun cancel(habit: Habit) {
        val nextOcurrence= calculateNextOcurrence(habit = habit)
        val pending= createPendingIntent(habit = habit,nextOcurrence.dayOfWeek)
        alarmManager.cancel(pending)
    }
}