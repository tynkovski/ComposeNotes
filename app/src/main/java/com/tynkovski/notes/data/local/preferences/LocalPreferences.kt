package com.tynkovski.notes.data.local.preferences

import android.content.SharedPreferences
import com.google.gson.Gson

internal class LocalPreferences(
    private val preferences: SharedPreferences,
    private val gson: Gson,
) {
    var token: String by PreferencesDelegate(preferences, ::token.name, "")

    fun clear() = preferences.edit().clear().apply()

    inline fun <reified T : Any> put(obj: T) {
        val jsonString = gson.toJson(obj)
        preferences.edit().putString(T::class.java.name, jsonString).apply()
    }

    inline fun <reified T : Any> get(): T? {
        val value = preferences.getString(T::class.java.name, null)
        return gson.fromJson(value, T::class.java)
    }
}