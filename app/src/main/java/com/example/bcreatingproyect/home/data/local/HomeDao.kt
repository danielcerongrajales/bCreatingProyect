package com.example.bcreatingproyect.home.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.bcreatingproyect.home.data.local.entity.HabitEntity
import com.example.bcreatingproyect.home.data.local.entity.SyncEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface HomeDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHabit(habitEntity: HabitEntity)


    @Query("SELECT * FROM HabitEntity WHERE id=:id")
    suspend fun getHabitById(id: String): HabitEntity

    @Query("SELECT * FROM HabitEntity WHERE startDate <=:date")
    fun getHabitForSelectedDate(date:Long):Flow<List<HabitEntity>>

    @Query("SELECT * FROM HabitEntity")
    fun getAllHabits():List<HabitEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHabitsSync(syncEntity: SyncEntity)

    @Query("SELECT * FROM SyncEntity")
    fun getHabitSync():List<SyncEntity>
    @Delete
    suspend fun deleteHabitSync(syncEntity: SyncEntity)
}