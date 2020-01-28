package de.hsos.ma.adhocdb.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

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
    var type: String


) {
    @PrimaryKey(autoGenerate = true) var id: Long = 0
    override fun toString(): String {
        return "Cell(id=$id, tableId=$tableId, columnId=$columnId, value='$value', type='$type')"
    }
}