package com.sukinsan.responsibility.entities

import com.sukinsan.responsibility.utils.TimeUtils

class StorageEntity {

    val tasks: MutableMap<String, TaskEntity> = HashMap()
    val keyValue: MutableMap<String, String> = HashMap()

    fun save(task: TaskEntity): Boolean {
        tasks.put(task.id, task)
        return false
    }

    fun getById(taskId: String): TaskEntity? {
        if (tasks.containsKey(taskId)) {
            return tasks.get(taskId)
        }
        return null
    }

    fun saveLastMessage(tu: TimeUtils, task: TaskEntity, lastMessage: String): Boolean {
        keyValue.put("${task.getNotoficationId()}-${tu.friendlyDate()}", lastMessage)
        return true
    }

    fun getLastMessage(tu: TimeUtils, task: TaskEntity): String? {
        val key = "${task.getNotoficationId()}-${tu.friendlyDate()}"
        if (keyValue.containsKey(key)) {
            return keyValue.get(key)
        }
        return null
    }
}