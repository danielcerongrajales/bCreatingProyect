package com.example.bcreatingproyect.home.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.bcreatingproyect.home.data.local.entity.HabitEntity
import com.example.bcreatingproyect.home.data.local.entity.SyncEntity
import com.example.bcreatingproyect.home.data.local.typeconverter.HomeTypeConverter

@Database(entities = [HabitEntity::class,SyncEntity::class], version = 2)
@TypeConverters(
    HomeTypeConverter::class
)
abstract class HomeDatabase():RoomDatabase() {
    abstract val dao:HomeDao
}