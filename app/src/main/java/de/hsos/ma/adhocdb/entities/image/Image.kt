package de.hsos.ma.adhocdb.entities.image

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "images")
data class Image(
    var absolutePath : String
){
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
}