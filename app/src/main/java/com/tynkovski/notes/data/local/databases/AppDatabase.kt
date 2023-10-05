package com.tynkovski.notes.data.local.databases

import androidx.room.Database
import androidx.room.RoomDatabase
import com.tynkovski.notes.data.local.dao.NoteDao
import com.tynkovski.notes.data.local.entities.NoteEntity

@Database(
    entities = [NoteEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun noteDao(): NoteDao
}