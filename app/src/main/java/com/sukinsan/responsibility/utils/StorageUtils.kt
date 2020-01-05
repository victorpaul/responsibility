package com.sukinsan.responsibility.utils

import android.content.Context
import android.content.SharedPreferences
import com.sukinsan.responsibility.entities.TaskEntity
import com.sukinsan.responsibility.entities.taskFromJson

fun newStorageUtils(ctx: Context): StorageUtils {
    return SharedPrefUtilsImpl(ctx)
}

interface StorageUtils {

    fun save(task: TaskEntity): Boolean

    fun getById(taskId: String): TaskEntity
}

class SharedPrefUtilsImpl(ctx: Context) : StorageUtils {

    val sharedPref: SharedPreferences

    init {
        sharedPref = ctx.getSharedPreferences("PairTasksIdJson", Context.MODE_PRIVATE)
    }

    override fun save(task: TaskEntity): Boolean {
        val editor = sharedPref.edit()
        editor.putString(task.id, task.toJson())
        return editor.commit()
    }

    override fun getById(taskId: String): TaskEntity {
        return taskFromJson(sharedPref.getString(taskId, null))

    }

}