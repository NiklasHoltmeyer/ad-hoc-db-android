package de.hsos.ma.adhocdb.framework.persistence.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import de.hsos.ma.adhocdb.controller.tables.TableDao
import de.hsos.ma.adhocdb.entities.table.Cell
import de.hsos.ma.adhocdb.entities.table.Column
import de.hsos.ma.adhocdb.entities.table.Table
@Database(entities = [Table::class, Column::class, Cell::class], version = 1)
abstract class TablesDatabase : RoomDatabase(){
    abstract fun tableDao(): TableDao

    companion object {
        @Volatile private var instance: TablesDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK){
            instance ?: buildDataBase(context).also{
                instance = it
            }
        }


        private fun buildDataBase(context: Context) =  Room.databaseBuilder(context.applicationContext, TablesDatabase::class.java, "tablesv110.db").build()
    }
}
