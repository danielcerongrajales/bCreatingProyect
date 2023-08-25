package com.example.bcreatingproyect.home.data.repostory

import androidx.work.BackoffPolicy
import androidx.work.Constraints
import androidx.work.ExistingWorkPolicy
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.example.bcreatingproyect.home.data.estension.toStartOfDateTimestamp
import com.example.bcreatingproyect.home.data.sync.HabitSyncWorker
import com.example.bcreatingproyect.home.data.local.HomeDao
import com.example.bcreatingproyect.home.data.mapper.toEntity
import com.example.bcreatingproyect.home.data.mapper.toDomain
import com.example.bcreatingproyect.home.data.mapper.toDto
import com.example.bcreatingproyect.home.data.mapper.toSyncEntity
import com.example.bcreatingproyect.home.data.remote.HomeApi
import com.example.bcreatingproyect.home.data.remote.util.resultOf
//import com.example.bcreatingproyect.home.data.sync.HabitSyncWorker
import com.example.bcreatingproyect.home.domain.alarm.AlarmHandler
import com.example.bcreatingproyect.home.domain.models.Habit
import com.example.bcreatingproyect.home.domain.repository.HomeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import java.time.Duration
import java.time.ZonedDateTime

class HomeRepositoryImpl (private  val dao: HomeDao,
                          private val api: HomeApi,
                          private val alarmHandler: AlarmHandler,
                          private val workManager: WorkManager): HomeRepository {



    override fun getAllHabitsSelectedDate(date: ZonedDateTime): Flow<List<Habit>> {
        val localFlow=  dao.getHabitForSelectedDate(date.toStartOfDateTimestamp()).map {
            it.map {
                it.toDomain()
            }
        }
        val apiFlow=getAllHabitFromApi()
        return localFlow.combine(apiFlow) { db, _ ->
            db
        }

     return   flow<List<Habit>> {emit(emptyList())  }
    }

    private fun getAllHabitFromApi(): Flow<List<Habit>> {
        return flow {
            resultOf {
                val habits = api.getAllHabits().toDomain()
                insertHabits(habits)
            }
            emit(emptyList<Habit>())
        }.onStart {
            emit(emptyList())
        }
    }

    private suspend fun insertHabits(habits: List<Habit>) {
        habits.forEach {
            handleAlarm(it)
            dao.insertHabit(it.toEntity())
        }
    }

    private suspend fun handleAlarm(habit: Habit){
        try {
            val previous= dao.getHabitById(habit.id)
//            alarmHandler.cancel(previous.toDomain())
        }catch (e:Exception){ }
        alarmHandler.setRecurringAlarm(habit)
    }

    override suspend fun insertHabit(habit: Habit) {
        handleAlarm(habit = habit)
        dao.insertHabit(habit.toEntity())
        resultOf {
            api.insertHabit(habit.toDto())
        }.onFailure {
            dao.insertHabitsSync(habit.toSyncEntity())
        }
    }

    override suspend fun getHabitById(id: String): Habit {
        return dao.getHabitById(id).toDomain()
    }

    override suspend fun syncHabits() {
        val worker = OneTimeWorkRequestBuilder<HabitSyncWorker>().setConstraints(
            Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED).build()
        ).setBackoffCriteria(BackoffPolicy.EXPONENTIAL, Duration.ofMinutes(5))
            .build()

        workManager.beginUniqueWork("sync_habit_id", ExistingWorkPolicy.REPLACE, worker).enqueue()
    }
}