package de.hsos.ma.adhocdb.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "columns",
    foreignKeys = [ForeignKey(
        entity = Table::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("tableId"),
        onDelete = ForeignKey.CASCADE
    )]
)
data class Column(
    @PrimaryKey(autoGenerate = true) var id: Long = 0,
    val tableId: Long,
    val name: String
)
