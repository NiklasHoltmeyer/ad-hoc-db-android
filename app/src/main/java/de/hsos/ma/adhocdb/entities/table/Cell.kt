package de.hsos.ma.adhocdb.entities.table

import androidx.room.*

@Entity(
    tableName = "cells",
    foreignKeys = [ForeignKey(
        entity = Table::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("tableId"),
        onDelete = ForeignKey.CASCADE
    ),
        ForeignKey(
            entity = Column::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("columnId"),
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index(value=["id", "tableId"]), Index(value=["id", "tableId", "columnId"], unique = true)]
)
data class Cell(
    val tableId: Long,
    val columnId: Long,
    var value: String,
    var type: String,
    @ColumnInfo(name = "_row")var row: Long = -1

) {
    @PrimaryKey(autoGenerate = true) var id: Long = 0
    override fun toString(): String {
        return "Cell(id=$id, tableId=$tableId, columnId=$columnId, value='$value', type='$type')"
    }
}