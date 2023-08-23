package com.tynkovski.notes.data.local.holder

import com.tynkovski.notes.data.local.preferences.LocalPreferences

interface TokenHolder {
    fun setToken(token: String)

    fun logout()

    fun getToken(): String
}

internal class TokenHolderImpl(
    private val preferences: LocalPreferences
) : TokenHolder {

    override fun setToken(token: String) {
        preferences.token = token
    }

    override fun logout() {
        preferences.clear()
    }

    override fun getToken(): String {
        return preferences.token
    }

}