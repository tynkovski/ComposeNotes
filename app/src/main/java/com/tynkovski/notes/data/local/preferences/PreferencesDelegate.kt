package com.tynkovski.notes.data.local.preferences

import android.content.SharedPreferences
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

@Suppress("UNCHECKED_CAST")
class PreferencesDelegate<T>(
    private val preferences: SharedPreferences,
    private val name: String,
    private val default: T,
) : ReadWriteProperty<Any?, T> {

    override fun getValue(thisRef: Any?, property: KProperty<*>): T {
        with(preferences) {
            return when (default) {
                is Boolean -> (getBoolean(name, default) as T) ?: default
                is Int -> (getInt(name, default) as T) ?: default
                is Float -> (getFloat(name, default) as T) ?: default
                is Long -> (getLong(name, default) as T) ?: default
                is String -> (getString(name, default) as T) ?: default
                else -> throw NotFoundRealizationException(default)
            }
        }
    }

    override fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
        with(preferences.edit()) {
            when (value) {
                is Boolean -> putBoolean(name, value)
                is Int -> putInt(name, value)
                is Float -> putFloat(name, value)
                is Long -> putLong(name, value)
                is String -> putString(name, value)
                else -> throw NotFoundRealizationException(value)
            }
            apply()
        }
    }

    class NotFoundRealizationException(defValue: Any?) : Exception("not found realization for $defValue")
}