package com.sukinsan.responsibility.services

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.sukinsan.responsibility.R
import com.sukinsan.responsibility.entities.TaskEntity
import com.sukinsan.responsibility.utils.TimeUtils

fun newNotificationService(ctx: Context, tu: TimeUtils): NotificationService {
    return NotificationServiceImpl(ctx, tu)
}

interface NotificationService {

    fun registerChannel(): Boolean

    fun showNotification(title: String, body: String, notId: Int): Boolean

    fun showNotification(task: TaskEntity): Boolean
}

class NotificationServiceImpl(val ctx: Context, val tu: TimeUtils) : NotificationService {

    override fun registerChannel(): Boolean {
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
                ctx.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
            return true
        }
        return false
    }

    override fun showNotification(title: String, body: String, notId: Int): Boolean {
        val builder = NotificationCompat.Builder(ctx, "reminderID")
            .setSmallIcon(R.drawable.ic_notifications_black_24dp)
            .setContentTitle(tu.friendlyDateTime())
            .setContentText(title)
            .setPriority(NotificationCompat.PRIORITY_HIGH)

        with(NotificationManagerCompat.from(ctx)) {
            // notificationId is a unique int for each notification that you must define
            cancel(notId)
            notify(notId, builder.build())
        }
        return true
    }

    override fun showNotification(task: TaskEntity): Boolean {
        return showNotification(
            tu.friendlyDateTime(),
            "${task.description}\n${task.workerManagerId}",
            task.notificationId
        )
    }

}