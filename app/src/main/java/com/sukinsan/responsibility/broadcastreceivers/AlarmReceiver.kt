package com.sukinsan.responsibility.broadcastreceivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.sukinsan.responsibility.entities.createEveryHourWeekly
import com.sukinsan.responsibility.providers.newSharedPrefDB
import com.sukinsan.responsibility.services.newLogicFlowService
import com.sukinsan.responsibility.services.newNotificationService
import com.sukinsan.responsibility.utils.newTU

class AlarmReceiver : BroadcastReceiver() {

    val LOG_TAG = this.javaClass.toString()

    override fun onReceive(context: Context, intent: Intent) {
        val tu = newTU()
        val dbProvider = newSharedPrefDB(context)
        val notifySv = newNotificationService(context, tu, dbProvider)

        val flowService = newLogicFlowService()

        val task = createEveryHourWeekly("task id5", "Age is just a number")

        notifySv.registerChannel()
        dbProvider.write { db ->
            val r = flowService.remindUserAboutTask(task, db, tu, notifySv)
            Log.i(LOG_TAG, r.message)
            return@write r.success
        }
        Log.i(LOG_TAG, "onReceive")
    }
}
