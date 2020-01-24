package com.sukinsan.responsibility.services

import com.sukinsan.responsibility.entities.FunFeedback
import com.sukinsan.responsibility.utils.DBUtils
import com.sukinsan.responsibility.entities.TaskEntity
import com.sukinsan.responsibility.utils.TimeUtils

fun newLogicFlowService(tu: TimeUtils, ns: NotificationService): LogicFlowService {
    return LogicFlowServiceImpl(tu, ns)
}

interface LogicFlowService {

    fun remindUserAboutTask(task: TaskEntity?, storage: DBUtils): FunFeedback
    fun isItNotificationWindow(): Boolean
}

class LogicFlowServiceImpl(val tu: TimeUtils, val ns: NotificationService) : LogicFlowService {

    override fun remindUserAboutTask(
        task: TaskEntity?,
        db: DBUtils
    ): FunFeedback {
        if (task == null) {
            return FunFeedback(false, "Task is null")
        }

        val lastMessage = db.getLastMessage(task)
        db.saveLastMessage(
            task,
            arrayOf(
                "${tu.friendlyTime()} ${task.description}",
                lastMessage
            ).filterNotNull().joinToString(".\r\n")
        )
        ns.showNotification(
            tu.friendlyDate(),
            task.description,
            task.getNotoficationId(),
            arrayOf(
                "${task.description}",
                lastMessage
            ).filterNotNull().joinToString(".\r\n")
        )

        return FunFeedback(true, "Task notified")
    }

    override fun isItNotificationWindow(): Boolean {
        return tu.getCurrentHour() in 7..23
    }

}