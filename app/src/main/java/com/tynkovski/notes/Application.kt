package com.tynkovski.notes

import android.app.Application
import com.tynkovski.notes.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class Application : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@Application)
            modules(viewModelModule)
        }
    }
}