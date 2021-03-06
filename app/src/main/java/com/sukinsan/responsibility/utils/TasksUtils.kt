package com.sukinsan.responsibility.utils

import com.sukinsan.responsibility.entities.FunFeedback
import com.sukinsan.responsibility.entities.TaskEntity
import com.sukinsan.responsibility.enums.RemindRuleEnum
import com.sukinsan.responsibility.services.NotificationService

fun newTaskUtils(): TasksUtils {
    return TasksUtilsImpl()
}

interface TasksUtils {


    fun doesTimeMuch(task: TaskEntity, tu: TimeUtils): FunFeedback

    fun remind(
        task: TaskEntity,
        storage: DBUtils,
        tu: TimeUtils,
        ns: NotificationService
    ): FunFeedback
}

class TasksUtilsImpl : TasksUtils {

    private fun doesHourMuch(task: TaskEntity, tu: TimeUtils): FunFeedback {
        if (!task.rulesHours.contains(tu.getCurrentHour())) {
            return FunFeedback(
                false,
                "Hourly rules are not met for date ${tu.friendlyDateTimeYear()}"
            )
        }
        return FunFeedback(true, "Hourly rules are met for date ${tu.friendlyDateTime()}")
    }

    private fun doesMonthMuch(task: TaskEntity, tu: TimeUtils): FunFeedback {
        if (!task.rulesMonths.contains(tu.getCurrentMonth())) {
            return FunFeedback(
                false,
                "Monthly rules are not met for date ${tu.friendlyDateTimeYear()}"
            )
        }
        return FunFeedback(true, "Remind rules are met for date ${tu.friendlyDateTime()}")
    }

    private fun doesDayMuch(task: TaskEntity, tu: TimeUtils): FunFeedback {
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
                return FunFeedback(
                    false,
                    "Unknown task remind ruls for date ${tu.friendlyDateTime()}"
                )
            }
        }
        return FunFeedback(true, "Daily rules are met for date ${tu.friendlyDateTime()}")
    }

    override fun doesTimeMuch(task: TaskEntity, tu: TimeUtils): FunFeedback { // todo test
        val succ = doesMonthMuch(task, tu).success &&
                doesHourMuch(task, tu).success &&
                doesDayMuch(task, tu).success
        return FunFeedback(succ, "")

    }

    override fun remind( // todo test it
        task: TaskEntity,
        db: DBUtils,
        tu: TimeUtils,
        ns: NotificationService
    ): FunFeedback {

        if (!doesTimeMuch(task, tu).success) {
            return FunFeedback(false, "Time rules were not met")
        }

        ns.showNotification(
            tu.friendlyTime(),
            task.description,
            task.getNotoficationId(),
            task.description
        )

        return FunFeedback(true, "Task notified")
    }

}