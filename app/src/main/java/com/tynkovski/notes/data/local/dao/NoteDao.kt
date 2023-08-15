package com.tynkovski.notes.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import com.tynkovski.notes.data.local.entities.NoteEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {
    @Query("SELECT * FROM notes ORDER BY createdAt DESC")
    fun getNotesFlow(): Flow<List<NoteEntity>>
}