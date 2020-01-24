package de.hsos.ma.adhocdb.framework.persistence.database

import RowDataConverter
import StringDataConverter
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import de.hsos.ma.adhocdb.controller.tables.TableDao
import de.hsos.ma.adhocdb.entities.Table
@Database(entities = [Table::class], version = 1)
@TypeConverters(StringDataConverter::class, RowDataConverter::class)
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


        private fun buildDataBase(context: Context) =  Room.databaseBuilder(context.applicationContext, TablesDatabase::class.java, "tablesx2.db").build()
    }
}
