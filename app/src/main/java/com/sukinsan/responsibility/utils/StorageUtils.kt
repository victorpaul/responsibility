package com.sukinsan.responsibility.utils

import android.content.Context
import android.content.SharedPreferences
import com.sukinsan.responsibility.entities.TaskEntity
import com.sukinsan.responsibility.entities.taskFromJson

fun newStorageUtils(ctx: Context, timeUtils: TimeUtils): StorageUtils {
    return SharedPrefUtilsImpl(ctx, timeUtils)
}

interface StorageUtils {

    fun lock(body: () -> Unit)

    fun save(task: TaskEntity): Boolean

    fun getById(taskId: String): TaskEntity

    fun saveLastMessage(task: TaskEntity, lastMessage: String): Boolean

    fun getLastMessage(task: TaskEntity): String?
}

class SharedPrefUtilsImpl(ctx: Context, val timeUtils: TimeUtils) : StorageUtils {

    val sharedPref: SharedPreferences

    init {
        sharedPref = ctx.getSharedPreferences("PairTasksIdJson", Context.MODE_PRIVATE)
    }

    override fun lock(body: () -> Unit) {
        synchronized(true) {
            body()
        }
    }

    override fun save(task: TaskEntity): Boolean {
        val editor = sharedPref.edit()
        editor.putString(task.id, task.toJson())
        return editor.commit()
    }

    override fun getById(taskId: String): TaskEntity {
        return taskFromJson(sharedPref.getString(taskId, null))
    }

    override fun saveLastMessage(task: TaskEntity, lastMessage: String): Boolean {
        val editor = sharedPref.edit()
        editor.putString("${task.getNotoficationId()}-${timeUtils.friendlyDate()}", lastMessage)
        return editor.commit()
    }

    override fun getLastMessage(task: TaskEntity): String? {
        return sharedPref.getString("${task.getNotoficationId()}-${timeUtils.friendlyDate()}", null)
    }

}