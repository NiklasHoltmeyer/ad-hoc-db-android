package de.hsos.ma.adhocdb.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tables")
data class TableEntity(
    val name : String,
    val description : String,
    val image : String
){
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0

    override fun toString(): String {
        return "TableEntity(name='$name', description='$description', image='$image', id=$id)"
    }

}