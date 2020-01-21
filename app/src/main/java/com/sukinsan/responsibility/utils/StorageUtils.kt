package com.sukinsan.responsibility.utils

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.sukinsan.responsibility.entities.StorageEntity
import com.sukinsan.responsibility.entities.TaskEntity
import com.sukinsan.responsibility.entities.taskFromJson
import java.lang.Exception

fun newStorageUtils(ctx: Context, timeUtils: TimeUtils): StorageUtils {
    return SharedPrefUtilsImpl(ctx, timeUtils)
}

interface StorageUtils {

    fun lock(body: () -> Unit)

    fun save(task: TaskEntity): Boolean

    fun getById(taskId: String): TaskEntity?

    fun saveLastMessage(task: TaskEntity, lastMessage: String): Boolean

    fun getLastMessage(task: TaskEntity): String?

    fun getDB(): StorageEntity

    fun lockDB(save: (storage: StorageEntity) -> Boolean) //todo move StorageEntity logic here, and remove StorageEntity completely
}

class SharedPrefUtilsImpl(ctx: Context, val timeUtils: TimeUtils) : StorageUtils {

    val sharedPrefTasks: SharedPreferences
    val sharedPrefTemps: SharedPreferences
    val storageKey = "StorageEntity"
    val gson = Gson()

    init {
        sharedPrefTasks = ctx.getSharedPreferences("Tasks", Context.MODE_PRIVATE)
        sharedPrefTemps = ctx.getSharedPreferences("Temps", Context.MODE_PRIVATE)
    }

    override fun lock(body: () -> Unit) {
        synchronized(true) {
            return body()
        }
    }

    override fun save(task: TaskEntity): Boolean {
        val editor = sharedPrefTasks.edit()
        editor.putString(task.id, task.toJson())
        return editor.commit()
    }

    override fun getById(taskId: String): TaskEntity? {
        val json = sharedPrefTasks.getString(taskId, null)
        return taskFromJson(json)
    }

    override fun saveLastMessage(task: TaskEntity, lastMessage: String): Boolean {
        val editor = sharedPrefTemps.edit()
        editor.putString("${task.getNotoficationId()}-${timeUtils.friendlyDate()}", lastMessage)
        return editor.commit()
    }

    override fun getLastMessage(task: TaskEntity): String? {
        return sharedPrefTemps.getString("${task.getNotoficationId()}-${timeUtils.friendlyDate()}", null)
    }

    override fun getDB(): StorageEntity {
        val json = sharedPrefTasks.getString(storageKey, null)
        try {
            return Gson().fromJson(json, StorageEntity::class.java)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return StorageEntity()
    }

    override fun lockDB(save: (storage: StorageEntity) -> Boolean) {
        var r = false
        lock {
            val db = getDB()
            if (save(db)) {
                val editor = sharedPrefTasks.edit()
                editor.putString(storageKey, gson.toJson(db))
                r = editor.commit()
            }
        }
//        return r;
    }

}