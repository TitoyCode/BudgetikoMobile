package com.budgetiko.budgetikomobile.data.network

import android.content.Context

class TokenStore(context: Context) {
    private val prefs = context.getSharedPreferences("budgetiko_auth", Context.MODE_PRIVATE)

    fun saveAccessToken(token: String) {
        prefs.edit().putString(KEY_ACCESS_TOKEN, token).apply()
    }

    fun getAccessToken(): String? = prefs.getString(KEY_ACCESS_TOKEN, null)

    fun clear() {
        prefs.edit().remove(KEY_ACCESS_TOKEN).apply()
    }

    fun hasToken(): Boolean = !getAccessToken().isNullOrBlank()

    private companion object {
        const val KEY_ACCESS_TOKEN = "access_token"
    }
}
