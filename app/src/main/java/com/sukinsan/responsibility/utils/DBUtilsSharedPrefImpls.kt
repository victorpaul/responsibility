package com.sukinsan.responsibility.utils

import com.google.gson.annotations.Expose
import com.sukinsan.responsibility.entities.TaskEntity

class DBUtilsSharedPrefImpls : DBUtils {
    @Expose
    val tasks: MutableMap<String, TaskEntity> = HashMap()
    @Expose
    val keyValue: MutableMap<String, String> = HashMap()

    override fun save(task: TaskEntity): Boolean {
        tasks.put(task.id, task)
        return false
    }

    override fun getTasksList(): List<TaskEntity> {
        return tasks.values.toMutableList()
    }

    override fun getTaskById(taskId: String): TaskEntity? {
        if (tasks.containsKey(taskId)) {
            return tasks.get(taskId)
        }
        return null
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as DBUtilsSharedPrefImpls

        if (tasks != other.tasks) return false
        if (keyValue != other.keyValue) return false

        return true
    }

    override fun hashCode(): Int {
        var result = tasks.hashCode()
        result = 31 * result + keyValue.hashCode()
        return result
    }


}