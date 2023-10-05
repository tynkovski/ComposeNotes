package com.tynkovski.notes.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.tynkovski.notes.data.local.entities.NoteEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {
    @Query("SELECT * FROM notes ORDER BY createdAt DESC")
    fun getNotesFlow(): Flow<List<NoteEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE) // should i?
    fun addNotes(notes: List<NoteEntity>)

    @Delete
    fun deleteNotes(notes: List<NoteEntity>)

    @Update
    fun updateNote(note: NoteEntity)
}