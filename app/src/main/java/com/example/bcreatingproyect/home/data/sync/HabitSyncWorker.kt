package com.example.bcreatingproyect.home.data.sync

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.bcreatingproyect.home.data.local.HomeDao
import com.example.bcreatingproyect.home.data.local.entity.SyncEntity
import com.example.bcreatingproyect.home.data.mapper.toDomain
import com.example.bcreatingproyect.home.data.mapper.toDto
import com.example.bcreatingproyect.home.data.remote.HomeApi
import com.example.bcreatingproyect.home.data.remote.util.resultOf
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.supervisorScope

@HiltWorker
class HabitSyncWorker @AssistedInject constructor(
    @Assisted val context: Context,
    @Assisted val workerParameters: WorkerParameters,
    private val api:HomeApi,
    private val dao: HomeDao):CoroutineWorker(context,workerParameters) {


    override suspend fun doWork(): Result {
        if (runAttemptCount >= 3) {
            return Result.failure()
        }
        val items=dao.getHabitSync()
        return try {
            supervisorScope {
                val jobs = items.map { items -> async { sync(items) } }
                jobs.awaitAll()
            }
            Result.success()
        } catch (e: Exception) {
            Result.retry()
        }
    }

    private suspend fun sync(entity: SyncEntity) {
        val habit = dao.getHabitById(entity.id).toDomain().toDto()
        resultOf {
            api.insertHabit(habit)
        }.onSuccess {
            dao.deleteHabitSync(entity)
        }.onFailure {
            throw it
        }
    }
}