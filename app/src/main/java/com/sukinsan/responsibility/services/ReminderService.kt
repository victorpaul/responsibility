package com.sukinsan.responsibility.services

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.SystemClock
import androidx.work.*
import com.sukinsan.responsibility.broadcastreceivers.AlarmReceiver
import com.sukinsan.responsibility.entities.TaskEntity
import com.sukinsan.responsibility.utils.StorageUtils
import com.sukinsan.responsibility.workmanagers.ReminderWorker
import java.util.concurrent.TimeUnit

fun newReminderService(ctx: Context, storageUtils: StorageUtils): ReminderService {
    return ReminderServiceImpl(ctx, storageUtils)
}

interface ReminderService {

    fun getWorkManager(): WorkManager

    fun createPeriodicWorkRequestBuilder(repeatInterval: Long, repeatIntervalTimeUnit: TimeUnit): PeriodicWorkRequest.Builder

    fun runRecurringWorker(task: TaskEntity): Operation

    fun runRecurringAlarm(): Boolean

}

class ReminderServiceImpl(val ctx: Context, val storageUtils: StorageUtils) : ReminderService {

    override fun getWorkManager(): WorkManager {
        return WorkManager.getInstance(ctx)
    }

    override fun createPeriodicWorkRequestBuilder(repeatInterval: Long, repeatIntervalTimeUnit: TimeUnit): PeriodicWorkRequest.Builder {
        return PeriodicWorkRequestBuilder<ReminderWorker>(repeatInterval, repeatIntervalTimeUnit)
    }

    override fun runRecurringWorker(task: TaskEntity): Operation {
        val cronJob = createPeriodicWorkRequestBuilder(1, TimeUnit.HOURS)
            .setInputData(workDataOf(Pair("taskId", task.id)))

            .build()
        storageUtils.lock {
            task.workerManagerId = cronJob.id.toString()
            storageUtils.save(task)
        }
        return getWorkManager().enqueueUniquePeriodicWork(task.id, ExistingPeriodicWorkPolicy.REPLACE, cronJob)
    }

    override fun runRecurringAlarm(): Boolean {
        val alarmMgr = ctx.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val alarmIntent = Intent(ctx, AlarmReceiver::class.java).let { intent ->

            PendingIntent.getBroadcast(ctx, 0, intent, 0)
        }
        // Hopefully your alarm will have a lower frequency than this!
        alarmMgr?.setInexactRepeating(
            AlarmManager.ELAPSED_REALTIME_WAKEUP,
            SystemClock.elapsedRealtime(),
            AlarmManager.INTERVAL_HOUR,
            alarmIntent
        )
        return true
    }

}