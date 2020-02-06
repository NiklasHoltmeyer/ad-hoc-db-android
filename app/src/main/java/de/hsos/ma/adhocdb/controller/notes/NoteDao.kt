package de.hsos.ma.adhocdb.controller.notes

import androidx.room.*
import de.hsos.ma.adhocdb.entities.note.Note
import de.hsos.ma.adhocdb.entities.table.Table

@Dao
interface NoteDao {
    @Insert
    suspend fun insert(note: Note): Long

    @Update
    suspend fun update(note: Note)

    @Delete
    suspend fun delete(note: Note)

    @Query("SELECT * FROM NOTES")
    suspend fun getAll(): List<Note>

    @Query("SELECT * FROM notes WHERE id=:id")
    fun getNoteById(id: String): Note
}