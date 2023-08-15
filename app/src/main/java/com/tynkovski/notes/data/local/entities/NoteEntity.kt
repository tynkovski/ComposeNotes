package com.tynkovski.notes.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "notes")
data class NoteEntity(
    @PrimaryKey(autoGenerate = false) val id: String,
    val text: String,
    val title: String?,
    val color: Long?,
    val createdAt: Long,
    val updatedAt: Long,
)