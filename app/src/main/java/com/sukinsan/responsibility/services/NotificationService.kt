package com.sukinsan.responsibility.services

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.sukinsan.responsibility.R
import com.sukinsan.responsibility.entities.TaskEntity
import com.sukinsan.responsibility.utils.StorageUtils
import com.sukinsan.responsibility.utils.TimeUtils
import kotlin.concurrent.thread

fun newNotificationService(ctx: Context, tu: TimeUtils, storageUtils: StorageUtils): NotificationService {
    return NotificationServiceImpl(ctx, tu, storageUtils)
}

interface NotificationService {

    fun registerChannel(): Boolean

    fun showNotification(title: String, body: String, notId: Int, collapsedText: String?): Boolean

    fun showNotification(task: TaskEntity): Boolean
}

class NotificationServiceImpl(val ctx: Context, val tu: TimeUtils, val storageUtils: StorageUtils) : NotificationService {

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

    override fun showNotification(title: String, body: String, notId: Int, collapsedText: String?): Boolean {
        val builder = NotificationCompat.Builder(ctx, "reminderID")
            .setSmallIcon(R.drawable.ic_notifications_black_24dp)
            .setContentTitle(title)
            .setContentText(body)
            .setPriority(NotificationCompat.PRIORITY_HIGH)

        if (collapsedText != null) {
            builder.setStyle(
                NotificationCompat.BigTextStyle().bigText(collapsedText)
            )
        }

        with(NotificationManagerCompat.from(ctx)) {
            // notificationId is a unique int for each notification that you must define
            cancel(notId)
            notify(notId, builder.build())
        }
        return true
    }

    override fun showNotification(task: TaskEntity): Boolean {
        var r = false
        storageUtils.lock {

            val lastMessage = storageUtils.getLastMessage(task)
            storageUtils.saveLastMessage(
                task,
                arrayOf("${tu.friendlyTime()} ${task.description}", lastMessage).filterNotNull().joinToString(".\r\n")
            )

            r = showNotification(
                tu.friendlyDate(),
                task.description,
                task.getNotoficationId(),
                arrayOf("${task.description}", lastMessage).filterNotNull().joinToString(".\r\n")
            )
        }
        return r
    }

}