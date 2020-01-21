package com.sukinsan.responsibility.providers

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.sukinsan.responsibility.utils.DBUtils
import com.sukinsan.responsibility.utils.DBUtilsSharedPrefImpls
import com.sukinsan.responsibility.utils.TimeUtils
import java.lang.Exception

class DBProviderSharedPrefImpl(ctx: Context, val timeUtils: TimeUtils) : DBProvider {

    val sharedPrefDB: SharedPreferences
    val storageKey = "StorageEntity"
    val gson = GsonBuilder()
        .excludeFieldsWithoutExposeAnnotation()
        .create()

    init {
        sharedPrefDB = ctx.getSharedPreferences("Tasks", Context.MODE_PRIVATE)
    }

    override fun read(): DBUtils {
        val json = sharedPrefDB.getString(storageKey, null)
        try {
            return Gson().fromJson(json, DBUtilsSharedPrefImpls::class.java)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return DBUtilsSharedPrefImpls(timeUtils)
    }

    override fun write(save: (storage: DBUtils) -> Boolean): Boolean = synchronized(true) {
        val db = read()
        if (save(db)) {
            val editor = sharedPrefDB.edit()
            editor.putString(storageKey, gson.toJson(db))
            return editor.commit()
        }
        return false
    }

}