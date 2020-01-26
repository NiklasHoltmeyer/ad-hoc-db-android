package de.hsos.ma.adhocdb.entities

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity(tableName = "tables")
data class Table(
    var name : String = "",
    var description : String = "",
    var image : String = "",
    @Ignore var colNames : List<String> = emptyList(),
    @Ignore var rows : List<Row> = emptyList()
){
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0

    override fun toString(): String {
        return "TableEntity(name='$name', description='$description', image='$image', id=$id)"
    }
}