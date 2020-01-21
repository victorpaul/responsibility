package com.sukinsan.responsibility.services

import com.sukinsan.responsibility.entities.Rslt
import com.sukinsan.responsibility.entities.StorageEntity
import com.sukinsan.responsibility.entities.TaskEntity
import com.sukinsan.responsibility.utils.TimeUtils

fun newLogicFlowService(tu: TimeUtils, ns: NotificationService): LogicFlowService {
    return LogicFlowServiceImpl(tu, ns)
}

interface LogicFlowService {

    fun remindUserAboutTask(task: TaskEntity?, storage: StorageEntity): Rslt
    fun isItNotificationWindow(): Boolean
}

class LogicFlowServiceImpl(val tu: TimeUtils, val ns: NotificationService) : LogicFlowService {

    override fun remindUserAboutTask(
        task: TaskEntity?,
        storage: StorageEntity
    ): Rslt {
        if (task == null) {
            return Rslt(false, "Task is null")
        }

        val lastMessage = storage.getLastMessage(tu, task)
        storage.saveLastMessage(
            tu,
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

        return Rslt(true, "Task notified")
    }

    override fun isItNotificationWindow(): Boolean {
        return tu.getCurrentHour() in 7..23
    }

}