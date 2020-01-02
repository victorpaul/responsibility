package com.sukinsan.responsibility.workmanagers

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.sukinsan.responsibility.R
import com.sukinsan.responsibility.entities.TaskEntity
import com.sukinsan.responsibility.entities.fromJson
import com.sukinsan.responsibility.newTU

class ReminderWorker(appContext: Context, workerParams: WorkerParameters) :
    Worker(appContext, workerParams) {

    val LOG_TAG = this.javaClass.toString()

    override fun doWork(): Result { // todo, test it

        val taskId = inputData.keyValueMap.get("taskId")

        // todo, SaveUtils to load data
        val sharedPref = applicationContext.getSharedPreferences(
            "PairTasksIdJson",
            Context.MODE_PRIVATE
        )
        val task = fromJson(sharedPref.getString(taskId as String, null))

        Log.i(LOG_TAG, "Description: ${task.description}")


        if (newTU().getCurrentHour() >= 8 && newTU().getCurrentHour() <= 23) {

            createNotificationChannel()

            var builder = NotificationCompat.Builder(applicationContext, "reminderID")
                .setSmallIcon(R.drawable.ic_notifications_black_24dp)
                .setContentTitle("Reminder")
                .setContentText(task.description)
                .setPriority(NotificationCompat.PRIORITY_HIGH)

            with(NotificationManagerCompat.from(applicationContext)) {
                // notificationId is a unique int for each notification that you must define
                notify(1, builder.build())
            }


        } else {
            Log.i(LOG_TAG, "Sleep well ${newTU().getCurrentHour()}")
        }

        return Result.success()
    }

    private fun createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "channelName"
            val descriptionText = "Channel description"
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel("reminderID", name, importance).apply {
                description = descriptionText
            }
            // Register the channel with the system
            val notificationManager: NotificationManager =
                applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

}