package com.android.pokhodai.expensemanagement.base.source

import android.content.SharedPreferences
import androidx.core.content.edit
import com.android.pokhodai.expensemanagement.utils.fromJson
import com.android.pokhodai.expensemanagement.utils.toJson
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

abstract class PrefDelegateModel(val prefs: SharedPreferences) {

    fun stringPref(key: String = "", defaultValue: String = "") =
        defaultPref(key, defaultValue, prefs::getString, prefs.edit()::putString)

    fun booleanPref(key: String = "", defaultValue: Boolean = false) =
        defaultPref(key, defaultValue, prefs::getBoolean, prefs.edit()::putBoolean)

    fun intPref(key: String = "", defaultValue: Int = 0) =
        defaultPref(key, defaultValue, prefs::getInt, prefs.edit()::putInt)

    fun stringSetPref(key: String = "", defaultValue: Set<String> = emptySet()) =
        defaultPref(key, defaultValue, prefs::getStringSet, prefs.edit()::putStringSet)

    fun longPref(key: String = "", defaultValue: Long = 0L) =
        defaultPref(key, defaultValue, prefs::getLong, prefs.edit()::putLong)

    protected inline fun <reified T> objPref(defaultKey: String = "", defaultValue: T? = null) =
        PrefDelegate(
            key = defaultKey,
            defaultValue = defaultValue,
            get = { key, value ->
                prefs.getString(key, null)?.fromJson() ?: value
            },
            set = { key, value ->
                prefs.edit().putString(key, value.toJson())
            }
        )

    private fun <T> defaultPref(
        key: String,
        defaultValue: T,
        get: (String, T) -> T?,
        set: (String, T) -> SharedPreferences.Editor
    ) = PrefDelegate(key, defaultValue, get, set)

    class PrefDelegate<T>(
        private val key: String,
        private val defaultValue: T,
        private val get: (String, T) -> T?,
        private val set: (String, T) -> SharedPreferences.Editor
    ) : ReadWriteProperty<Any?, T> {

        private fun KProperty<*>.key() = key.ifEmpty { this.name }

        override fun getValue(
            thisRef: Any?,
            property: KProperty<*>
        ): T {
            return get(property.key(), defaultValue) ?: defaultValue
        }

        override fun setValue(
            thisRef: Any?,
            property: KProperty<*>,
            value: T
        ) {
            set(property.key(), value).apply()
        }
    }
}