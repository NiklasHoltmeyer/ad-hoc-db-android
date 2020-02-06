package de.hsos.ma.adhocdb.entities.table

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "columns",
    foreignKeys = [ForeignKey(
        entity = Table::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("tableId"),
        onDelete = ForeignKey.CASCADE
    )],
    indices = [Index(value=["id", "tableId"])]
)
data class Column(
    val tableId: Long,
    var name: String
) {
    @PrimaryKey(autoGenerate = true) var id: Long = 0
    override fun toString(): String {
        return "Column(id=$id, tableId=$tableId, name='$name')"
    }
}
