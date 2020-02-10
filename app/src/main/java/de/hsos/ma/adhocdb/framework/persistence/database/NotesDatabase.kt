package de.hsos.ma.adhocdb.framework.persistence.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import de.hsos.ma.adhocdb.controller.notes.NoteDao
import de.hsos.ma.adhocdb.entities.note.Note

@Database(entities = [Note::class], version = 1)
abstract class NotesDatabase : RoomDatabase(){
    abstract fun noteDao(): NoteDao

    companion object {
        @Volatile private var instance: NotesDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK){
            instance ?: buildDataBase(context).also{
                instance = it
            }
        }


        private fun buildDataBase(context: Context) =  Room.databaseBuilder(context.applicationContext, NotesDatabase::class.java, "notesv110.db").build()
    }
}
