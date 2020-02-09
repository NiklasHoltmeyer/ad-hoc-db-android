package de.hsos.ma.adhocdb.controller.images

import androidx.room.*
import de.hsos.ma.adhocdb.entities.image.Image

@Dao
interface ImageDao {
    @Insert
    suspend fun insert(image: Image): Long

    @Update
    suspend fun update(image: Image)

    @Delete
    suspend fun delete(image: Image)

    @Query("SELECT * FROM IMAGES")
    suspend fun getAll(): List<Image>

    @Query("SELECT * FROM images WHERE id=:id")
    fun getImageById(id: String): Image
}