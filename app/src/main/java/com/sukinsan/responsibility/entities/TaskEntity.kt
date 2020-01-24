package com.sukinsan.responsibility.entities

import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import com.google.gson.annotations.Expose
import com.sukinsan.responsibility.enums.RemindRuleEnum
import java.util.*

fun taskFromJson(json: String?): TaskEntity? {
    if (json == null || json.trim().isEmpty()) {
        return null
    }
    try {
        return Gson().fromJson(json, TaskEntity::class.java)
    } catch (e: JsonSyntaxException) {
        return null
    }
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
    val rulesMonths: List<Int>, // 1-12
    @Expose
    val rulesExactDate: Date,
    @Expose
    val description: String,
    @Expose
    val createdAt: Date,
    @Expose
    var doneAt: List<Date>? = null,
    @Expose
    var workerManagerId: String? = null
) {

    fun getNotoficationId(): Int {
        return remindRule.getNotificationId()
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
        if (rulesMonths != other.rulesMonths) return false
        if (rulesExactDate != other.rulesExactDate) return false
        if (description != other.description) return false
        if (createdAt != other.createdAt) return false
        if (doneAt != other.doneAt) return false
        if (workerManagerId != other.workerManagerId) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + remindRule.hashCode()
        result = 31 * result + rulesHours.hashCode()
        result = 31 * result + rulesWeek.hashCode()
        result = 31 * result + rulesMonths.hashCode()
        result = 31 * result + rulesExactDate.hashCode()
        result = 31 * result + description.hashCode()
        result = 31 * result + createdAt.hashCode()
        result = 31 * result + (doneAt?.hashCode() ?: 0)
        result = 31 * result + (workerManagerId?.hashCode() ?: 0)
        return result
    }

    override fun toString(): String {
        return "TaskEntity(id='$id', remindRule=$remindRule, rulesHours=$rulesHours, rulesWeek=$rulesWeek, rulesMonths=$rulesMonths, rulesExactDate=$rulesExactDate, description='$description', createdAt=$createdAt, doneAt=$doneAt, workerManagerId=$workerManagerId)"
    }

}