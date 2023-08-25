package com.example.bcreatingproyect.home.data.alarm

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import com.example.bcreatingproyect.R
import com.example.bcreatingproyect.home.data.estension.goAsync
import com.example.bcreatingproyect.home.domain.alarm.AlarmHandler
import com.example.bcreatingproyect.home.domain.models.Habit
import com.example.bcreatingproyect.home.domain.repository.HomeRepository
import dagger.hilt.android.AndroidEntryPoint
import java.time.LocalDate
import javax.inject.Inject

@AndroidEntryPoint
class AlarmReceiver: BroadcastReceiver() {
    companion object {
        const val HABIT_ID="habit_id"
        private const val CHANNEL_ID= "habits_channel2"
    }

    @Inject
    lateinit var repository: HomeRepository
    @Inject
    lateinit var alarmHandler: AlarmHandler

    override fun onReceive(p0: Context?, p1: Intent?)=goAsync {
        if (p0==null || p1==null)return@goAsync
        val id= p1.getStringExtra(HABIT_ID)?:return@goAsync
        val habit =repository.getHabitById(id)
        createNotificationChannel(p0)
        habit.completedDates?.let {
            if(!it.contains(LocalDate.now())){
                showNotification(habit,p0)
            }
        }

        alarmHandler.setRecurringAlarm(habit)
    }

    private fun showNotification(habit: Habit, context: Context) {
        val notificationManager= context.getSystemService(NotificationManager::class.java)
        val  notification= NotificationCompat.Builder(context, CHANNEL_ID).setContentTitle(habit.name).setSmallIcon(
            R.drawable.notification_icon
        ).setAutoCancel(true).build()
        notificationManager.notify(habit.id.hashCode(),notification)
    }

    private fun createNotificationChannel(context: Context) {
        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.O){
            val channel=NotificationChannel(
                CHANNEL_ID,
                "Habit reminder chanel",
                NotificationManager.IMPORTANCE_HIGH
            )
            channel.description="Get your habits Reminder"
            val notificationManager= context.getSystemService(NotificationManager::class.java)
            notificationManager.createNotificationChannel(channel)
        }
    }
}