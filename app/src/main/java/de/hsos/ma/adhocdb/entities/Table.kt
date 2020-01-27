package de.hsos.ma.adhocdb.entities

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity(tableName = "tables")
data class Table(
    val name : String = "",
    val description : String = "",
    val image : String = "",
    val colNames : List<String> = emptyList()
){
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
    override fun toString(): String {
        return "TableEntity(name='$name', description='$description', image='$image', id=$id)"
    }
}