package com.sukinsan.responsibility.utils

import com.sukinsan.responsibility.entities.TaskEntity

interface DBUtils {
    fun save(task: TaskEntity): Boolean
    fun getTasksMap(): MutableMap<String, TaskEntity>
    fun getTasksList(): List<TaskEntity>
    fun getTaskById(taskId: String): TaskEntity?
}