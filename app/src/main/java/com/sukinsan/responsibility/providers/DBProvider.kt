package com.sukinsan.responsibility.providers

import android.content.Context
import com.sukinsan.responsibility.utils.DBUtils
import com.sukinsan.responsibility.utils.TimeUtils

fun newSharedPrefDB(ctx: Context): DBProvider {
    return DBProviderSharedPrefImpl(ctx)
}

interface DBProvider {

    fun read(): DBUtils
    fun write(save: (storage: DBUtils) -> Boolean): Boolean
}