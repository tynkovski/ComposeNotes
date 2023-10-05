package com.tynkovski.notes.data.remote.interceptors

import android.util.Log
import com.tynkovski.notes.data.local.holders.TokenHolder
import okhttp3.Interceptor
import okhttp3.Response

class TokenInterceptor(
    private val tokenHolder: TokenHolder,
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        val token = tokenHolder.getToken()
        request = if (token.isNotEmpty()) {
            Log.d("JWT TOKEN", token)
            request.newBuilder().addHeader("Authorization", "Bearer $token").build()
        } else {
            request.newBuilder().build()
        }
        return chain.proceed(request)
    }
}
