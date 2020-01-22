package com.sukinsan.responsibility.broadcastreceivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.sukinsan.responsibility.entities.TaskEntity
import com.sukinsan.responsibility.enums.RemindRuleEnum
import com.sukinsan.responsibility.providers.newSharedPrefDB
import com.sukinsan.responsibility.services.newLogicFlowService
import com.sukinsan.responsibility.services.newNotificationService
import com.sukinsan.responsibility.utils.newTU
import java.util.*

class AlarmReceiver : BroadcastReceiver() {

    val LOG_TAG = this.javaClass.toString()

    override fun onReceive(context: Context, intent: Intent) {
        val tu = newTU()
        val dbProvider = newSharedPrefDB(context, tu)
        val notifySv = newNotificationService(context, tu, dbProvider)

        val flowService = newLogicFlowService(tu, notifySv)

        val task = TaskEntity(
            "task id5", RemindRuleEnum.DAILY,
            "Age is just a number", Date(), null, null
        )

        if (flowService.isItNotificationWindow()) {
            notifySv.registerChannel()
            dbProvider.write { db ->
                val r = flowService.remindUserAboutTask(task, db)
                Log.i(LOG_TAG, r.message)
                return@write r.success
            }
        }
        Log.i(LOG_TAG, "onReceive")
    }
}
