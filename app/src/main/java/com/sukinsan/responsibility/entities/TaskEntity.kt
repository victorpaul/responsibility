package com.sukinsan.responsibility.entities

import com.google.gson.annotations.Expose
import com.sukinsan.responsibility.enums.RemindRuleEnum
import java.util.*

fun createEveryHourWeekly(id: String, desc: String): TaskEntity {
    return TaskEntity(
        id,
        RemindRuleEnum.WEEKLY_DAYS,
        listOf(8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23),
        listOf(1, 2, 3, 4, 5, 6, 7),
        emptyList(),
        listOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12),
        null,
        desc, null
    )
}

fun createEveryHourDaily(id: String, desc: String): TaskEntity { // todo test it
    return TaskEntity(
        id,
        RemindRuleEnum.MONTHLY_DAYS,
        listOf(8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23),
        emptyList(),
        listOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31),
        listOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12),
        null,
        desc, null
    )
}

class TaskEntity(
    @Expose
    val id: String,
    @Expose
    val remindRule: RemindRuleEnum,
    @Expose
    val rulesHours: List<Int>, // 0-23
    @Expose
    val rulesWeek: List<Int>, // 1-7
    @Expose
    val rulesDays: List<Int>, // 1-31
    @Expose
    val rulesMonths: List<Int>, // 1-12
    @Expose
    val rulesExactDate: Date?,
    @Expose
    val description: String,
    @Expose
    var workerManagerId: String?
) {

    fun getNotoficationId(): Int {
        //return remindRule.getNotificationId()
        return id.hashCode()
    }

    fun getWorkerUUID(): UUID? {
        if (workerManagerId == null) {
            return null
        }
        return UUID.fromString(workerManagerId)
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as TaskEntity

        if (id != other.id) return false
        if (remindRule != other.remindRule) return false
        if (rulesHours != other.rulesHours) return false
        if (rulesWeek != other.rulesWeek) return false
        if (rulesDays != other.rulesDays) return false
        if (rulesMonths != other.rulesMonths) return false
        if (rulesExactDate != other.rulesExactDate) return false
        if (description != other.description) return false
        if (workerManagerId != other.workerManagerId) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + remindRule.hashCode()
        result = 31 * result + rulesHours.hashCode()
        result = 31 * result + rulesWeek.hashCode()
        result = 31 * result + rulesDays.hashCode()
        result = 31 * result + rulesMonths.hashCode()
        result = 31 * result + (rulesExactDate?.hashCode() ?: 0)
        result = 31 * result + description.hashCode()
        result = 31 * result + (workerManagerId?.hashCode() ?: 0)
        return result
    }

    override fun toString(): String {
        return "TaskEntity(id='$id', remindRule=$remindRule, rulesHours=$rulesHours, rulesWeek=$rulesWeek, rulesDays=$rulesDays, rulesMonths=$rulesMonths, rulesExactDate=$rulesExactDate, description='$description', workerManagerId=$workerManagerId)"
    }

}