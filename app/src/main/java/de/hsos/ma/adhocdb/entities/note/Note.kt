package de.hsos.ma.adhocdb.entities.note

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "notes")
data class Note(
    var name : String,
    var description : String
){
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
}