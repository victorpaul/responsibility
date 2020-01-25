package com.sukinsan.responsibility.utils

import com.sukinsan.responsibility.entities.TaskEntity

interface DBUtils {
    fun save(task: TaskEntity): Boolean
    fun getTasksAll(): MutableMap<String, TaskEntity>
    fun getTaskById(taskId: String): TaskEntity?
    fun saveLastMessage(task: TaskEntity, lastMessage: String, tu: TimeUtils): Boolean
    fun getLastMessage(task: TaskEntity, tu: TimeUtils): String?
}