package com.sukinsan.responsibility.utils

import com.google.gson.annotations.Expose
import com.sukinsan.responsibility.entities.TaskEntity

class DBUtilsSharedPrefImpls(val tu: TimeUtils) : DBUtils {
    @Expose
    val tasks: MutableMap<String, TaskEntity> = HashMap()
    @Expose
    val keyValue: MutableMap<String, String> = HashMap()

    override fun save(task: TaskEntity): Boolean {
        tasks.put(task.id, task)
        return false
    }

    override fun getTasksAll(): MutableMap<String, TaskEntity> {
        return tasks
    }

    override fun getTaskById(taskId: String): TaskEntity? {
        if (tasks.containsKey(taskId)) {
            return tasks.get(taskId)
        }
        return null
    }

    override fun saveLastMessage(task: TaskEntity, lastMessage: String): Boolean {
        keyValue.put("${task.getNotoficationId()}-${tu.friendlyDate()}", lastMessage)
        return true
    }

    override fun getLastMessage(task: TaskEntity): String? {
        val key = "${task.getNotoficationId()}-${tu.friendlyDate()}"
        if (keyValue.containsKey(key)) {
            return keyValue.get(key)
        }
        return null
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as DBUtilsSharedPrefImpls

        if (tu != other.tu) return false
        if (tasks != other.tasks) return false
        if (keyValue != other.keyValue) return false

        return true
    }

    override fun hashCode(): Int {
        var result = tu.hashCode()
        result = 31 * result + tasks.hashCode()
        result = 31 * result + keyValue.hashCode()
        return result
    }


}