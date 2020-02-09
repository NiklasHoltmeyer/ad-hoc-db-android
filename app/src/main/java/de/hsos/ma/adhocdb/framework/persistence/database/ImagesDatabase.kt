package de.hsos.ma.adhocdb.framework.persistence.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import de.hsos.ma.adhocdb.controller.images.ImageDao
import de.hsos.ma.adhocdb.entities.image.Image

@Database(entities = [Image::class], version = 1)
abstract class ImagesDatabase : RoomDatabase(){
    abstract fun imageDao(): ImageDao

    companion object {
        @Volatile private var instance: ImagesDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK){
            instance ?: buildDataBase(context).also{
                instance = it
            }
        }


        private fun buildDataBase(context: Context) =  Room.databaseBuilder(context.applicationContext, ImagesDatabase::class.java, "imagesv011.db").build()
    }
}
