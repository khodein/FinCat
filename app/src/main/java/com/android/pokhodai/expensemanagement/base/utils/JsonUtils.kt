package com.android.pokhodai.expensemanagement.base.utils

import android.util.Log
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory

object JsonUtils {
    val moshi: Moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()
}

inline fun <reified T> T.toJson(): String = JsonUtils.moshi.adapter(T::class.java).toJson(this)
inline fun <reified T> String.fromJson(): T? = try {
    JsonUtils.moshi.adapter(T::class.java).fromJson(this)
} catch (e: Exception){
    Log.e("JsonConvertLogs", e.localizedMessage ?: "null error")
    null
}