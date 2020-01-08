package com.sukinsan.responsibility.broadcastreceivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.sukinsan.responsibility.entities.TaskEntity
import com.sukinsan.responsibility.enums.RemindRuleEnum
import com.sukinsan.responsibility.services.newNotificationService
import com.sukinsan.responsibility.utils.newStorageUtils
import com.sukinsan.responsibility.utils.newTU
import java.util.*

class AlarmReceiver : BroadcastReceiver() {

    val LOG_TAG = this.javaClass.toString()

    override fun onReceive(context: Context, intent: Intent) {
        val tu = newTU()
        val storage = newStorageUtils(context, tu)
        val notifySv = newNotificationService(context, tu, storage)

        val task = TaskEntity(
            "task id5", RemindRuleEnum.DAILY,
            "Age is just a number", Date(), null, null
        )

        if (newTU().getCurrentHour() in 7..23) {
            notifySv.registerChannel()
            task?.let {
                notifySv.showNotification(it)
            }
        }
        Log.i(LOG_TAG, "onReceive")
    }
}
