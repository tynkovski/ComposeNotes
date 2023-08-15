package com.tynkovski.notes

import android.app.Application
import com.tynkovski.notes.data.remote.logging.Analytics
import com.tynkovski.notes.di.clientModule
import com.tynkovski.notes.di.persistenceModule
import com.tynkovski.notes.di.repositoryModule
import com.tynkovski.notes.di.serializationModule
import com.tynkovski.notes.di.serviceModule
import com.tynkovski.notes.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class Application : Application() {
    override fun onCreate() {
        super.onCreate()

        Analytics.initialize(appContext = this@Application, isDebug = false)

        startKoin {
            androidContext(this@Application)
            modules(
                serializationModule,
                clientModule,
                serviceModule,
                repositoryModule,
                persistenceModule,
                viewModelModule
            )
        }
    }
}