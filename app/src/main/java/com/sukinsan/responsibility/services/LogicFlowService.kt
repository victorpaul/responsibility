package com.sukinsan.responsibility.services

import com.sukinsan.responsibility.entities.FunFeedback
import com.sukinsan.responsibility.utils.DBUtils
import com.sukinsan.responsibility.entities.TaskEntity
import com.sukinsan.responsibility.enums.RemindRuleEnum
import com.sukinsan.responsibility.utils.TimeUtils

fun newLogicFlowService(): LogicFlowService {
    return LogicFlowServiceImpl()
}

interface LogicFlowService {

    fun getNextRemindMessage(task: TaskEntity, tu: TimeUtils): String

    fun isItOkToRemindNow(task: TaskEntity, tu: TimeUtils): FunFeedback

    fun remindUserAboutTask(
        task: TaskEntity,
        storage: DBUtils,
        tu: TimeUtils,
        ns: NotificationService
    ): FunFeedback
}

class LogicFlowServiceImpl : LogicFlowService {

    override fun getNextRemindMessage(task: TaskEntity, tu: TimeUtils): String {
        val hourMessage = task.describeNextReminding(tu.getCurrentHour())
        if (hourMessage != null) {
            return hourMessage
        }
        return "error"
    }

    override fun isItOkToRemindNow(task: TaskEntity, tu: TimeUtils): FunFeedback {
        if (!task.rulesHours.contains(tu.getCurrentHour())) {
            return FunFeedback(
                false,
                "Hourly rules are not met for date ${tu.friendlyDateTimeYear()}"
            )
        }
        if (!task.rulesMonths.contains(tu.getCurrentMonth())) {
            return FunFeedback(
                false,
                "Monthly rules are not met for date ${tu.friendlyDateTimeYear()}"
            )
        }

        when (task.remindRule) {
            RemindRuleEnum.WEEKLY_DAYS ->
                if (!task.rulesWeek.contains(tu.getCurrentWeekDay())) {
                    return FunFeedback(
                        false,
                        "Day of week rules are not met for date ${tu.friendlyDateTimeYear()}"
                    )
                }
            RemindRuleEnum.MONTHLY_DAYS ->
                if (!task.rulesDays.contains(tu.getCurrentMonthDay())) {
                    return FunFeedback(
                        false,
                        "Day of month rules are not met for date ${tu.friendlyDateTimeYear()}"
                    )
                }
            else -> {
            }
        }

        return FunFeedback(true, "Remind rules are met for date ${tu.friendlyDateTime()}")
    }

    override fun remindUserAboutTask(
        task: TaskEntity,
        db: DBUtils,
        tu: TimeUtils,
        ns: NotificationService
    ): FunFeedback {

        val rulesCheck = isItOkToRemindNow(task, tu)
        if (!rulesCheck.success) {
            return rulesCheck
        }

        task.notifiedAt.add(tu.getDate())
        db.save(task)
        val times = task.notifiedAt.sortedByDescending { it.time }.map { tu.friendlyTime(it) }
            .joinToString(", ")

        ns.showNotification(
            tu.friendlyDate(),
            task.description,
            task.getNotoficationId(),
            arrayOf(
                "${task.description}",
                "next ${getNextRemindMessage(task,tu)}",
                "prev ${times}",
                null
            ).filterNotNull().joinToString(".\n")
        )

        return FunFeedback(true, "Task notified")
    }

}