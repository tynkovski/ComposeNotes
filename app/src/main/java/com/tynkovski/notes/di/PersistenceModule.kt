package com.tynkovski.notes.di

import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import com.google.gson.Gson
import com.tynkovski.notes.data.local.dao.NoteDao
import com.tynkovski.notes.data.local.databases.AppDatabase
import com.tynkovski.notes.data.local.preferences.LocalPreferences
import org.koin.dsl.module

const val DATABASE_NAME = "databaseMain"
const val PREFERENCES_NAME = "sharedPreferencesMain"

val persistenceModule = module {
    single<SharedPreferences> { get<Context>().getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE) }

    single<LocalPreferences> { LocalPreferences(get<SharedPreferences>(), get<Gson>()) }

    single<AppDatabase> {
        Room.databaseBuilder(
            context = get<Context>(),
            klass = AppDatabase::class.java,
            name = DATABASE_NAME
        ).build() // todo add fallbackToDestructiveMigration()
    }

    single<NoteDao> { get<AppDatabase>().noteDao() }
}