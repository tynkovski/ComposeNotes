package com.tynkovski.notes.data.remote.logging

import android.content.Context
import android.os.Build
import android.os.StrictMode
import android.util.Log

object Analytics {
    fun initialize(appContext: Context, isDebug: Boolean) {
        initStrictModeListener(appContext, isDebug)
    }

    fun leaveBreadcrumb(
        breadcrumb: BreadcrumbType,
        text: String? = null,
        tag: String? = null,
    ) {
        val info = buildString {
            append(breadcrumb.name)
            append(": ")
            append(text)
        }
        Log.i("Breadcrumb. ${tag ?: ""}", info)
    }

    private fun initStrictModeListener(appContext: Context, isDebug: Boolean) {
        if (isDebug && Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            val executor = appContext.mainExecutor

            StrictMode.setThreadPolicy(
                StrictMode.ThreadPolicy.Builder()
                    .detectAll()
                    .permitDiskReads()
                    .permitDiskWrites()
                    .penaltyListener(executor) { it?.printStackTrace() }
                    .penaltyLog()
                    .build()
            )

            StrictMode.setVmPolicy(
                StrictMode.VmPolicy.Builder()
                    .detectAll()
                    .penaltyListener(executor) { it?.printStackTrace() }
                    .penaltyLog()
                    .build()
            )
        }
    }
}

enum class BreadcrumbType {
    Navigation,
    State,
    NetworkInfo,
    UserAction,
}
