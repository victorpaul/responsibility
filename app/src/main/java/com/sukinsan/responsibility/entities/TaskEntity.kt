package com.sukinsan.responsibility.entities

import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
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
    val id: String,
    val remindRule: RemindRuleEnum,
    val description: String,
    val createdAt: Date,
    var doneAt: Date? = null,
    var failedAt: Date? = null,
    var workerManagerId: String? = null
) {

    init {

    }

    fun getNotoficationId(): Int {
        return remindRule.getNotificationId()
    }

    fun toJson(): String {
        return Gson().toJson(this)
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
        if (description != other.description) return false
        if (createdAt != other.createdAt) return false
        if (doneAt != other.doneAt) return false
        if (failedAt != other.failedAt) return false
        if (workerManagerId != other.workerManagerId) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + remindRule.hashCode()
        result = 31 * result + description.hashCode()
        result = 31 * result + createdAt.hashCode()
        result = 31 * result + (doneAt?.hashCode() ?: 0)
        result = 31 * result + (failedAt?.hashCode() ?: 0)
        result = 31 * result + (workerManagerId?.hashCode() ?: 0)
        return result
    }

    override fun toString(): String {
        return "TaskEntity(id='$id', remindRule=$remindRule, description='$description', createdAt=$createdAt, doneAt=$doneAt, failedAt=$failedAt, workerManagerId=$workerManagerId)"
    }


}