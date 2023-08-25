package com.example.bcreatingproyect.home.data.startup

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.example.bcreatingproyect.home.data.estension.goAsync
import com.example.bcreatingproyect.home.data.local.HomeDao
import com.example.bcreatingproyect.home.data.mapper.toDomain
import com.example.bcreatingproyect.home.domain.alarm.AlarmHandler
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class BootReceiver:BroadcastReceiver() {
    @Inject
    lateinit var alarmHandler: AlarmHandler
    @Inject
    lateinit var homeDao: HomeDao

    override fun onReceive(p0: Context?, p1: Intent?) =goAsync{
        if(p0==null || p1== null) return@goAsync
        if(p1.action != Intent.ACTION_BOOT_COMPLETED) return@goAsync
        val items = homeDao.getAllHabits()
        items.forEach {
            alarmHandler.setRecurringAlarm(it.toDomain())
        }
    }
}