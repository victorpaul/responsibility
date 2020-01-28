package com.sukinsan.responsibility.services

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.SystemClock
import androidx.work.*
import com.sukinsan.responsibility.broadcastreceivers.AlarmReceiver
import com.sukinsan.responsibility.entities.TaskEntity
import com.sukinsan.responsibility.providers.DBProvider
import com.sukinsan.responsibility.workmanagers.ReminderWorker
import java.util.concurrent.TimeUnit

fun newReminderService(ctx: Context, dbProvider: DBProvider): ReminderService {
    return ReminderServiceImpl(ctx, dbProvider)
}

interface ReminderService {

    fun getWorkManager(): WorkManager

    fun createPeriodicWorkRequestBuilder(repeatInterval: Long, repeatIntervalTimeUnit: TimeUnit): PeriodicWorkRequest.Builder

    fun createOneOffWorkRequestBuilder(): OneTimeWorkRequest.Builder

    fun runRecurringWorker(task: TaskEntity): Operation

    fun runOneTimeWorker(task: TaskEntity, delayMinutes: Long): Operation

    fun runRecurringAlarm(): Boolean

}

class ReminderServiceImpl(val ctx: Context, val dbProvider: DBProvider) : ReminderService {

    override fun getWorkManager(): WorkManager {
        return WorkManager.getInstance(ctx)
    }

    override fun createPeriodicWorkRequestBuilder(repeatInterval: Long, repeatIntervalTimeUnit: TimeUnit): PeriodicWorkRequest.Builder {
        return PeriodicWorkRequestBuilder<ReminderWorker>(repeatInterval, repeatIntervalTimeUnit)
    }

    override fun createOneOffWorkRequestBuilder(): OneTimeWorkRequest.Builder {
        return OneTimeWorkRequest.Builder(ReminderWorker::class.java)
    }

    override fun runRecurringWorker(task: TaskEntity): Operation {
        val cronJob = createPeriodicWorkRequestBuilder(1, TimeUnit.HOURS)
            .setInputData(workDataOf(Pair("taskId", task.id)))
            .build()

        dbProvider.write { se ->
            task.workerManagerId = cronJob.id.toString()
            se.save(task)
            return@write true
        }

        return getWorkManager().enqueueUniquePeriodicWork(task.id, ExistingPeriodicWorkPolicy.REPLACE, cronJob)
    }

    override fun runOneTimeWorker(task: TaskEntity, delayMinutes: Long): Operation { // todo, test it
        val cronJob = createOneOffWorkRequestBuilder()
            .setInputData(workDataOf(Pair("taskId", task.id)))
            .setInitialDelay(delayMinutes, TimeUnit.MINUTES)
            .build()

        dbProvider.write { se ->
            task.workerManagerId = cronJob.id.toString()
            se.save(task)
            return@write true
        }

        return getWorkManager().enqueueUniqueWork(
            task.id,
            ExistingWorkPolicy.REPLACE,
            cronJob
        )
    }

    override fun runRecurringAlarm(): Boolean {
        val alarmMgr = ctx.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val alarmIntent = Intent(ctx, AlarmReceiver::class.java).let { intent ->
            PendingIntent.getBroadcast(ctx, 0, intent, 0)
        }

        alarmMgr?.setInexactRepeating(
            AlarmManager.ELAPSED_REALTIME_WAKEUP,
            SystemClock.elapsedRealtime(),
            AlarmManager.INTERVAL_HOUR,
            alarmIntent
        )
        return true
    }

}