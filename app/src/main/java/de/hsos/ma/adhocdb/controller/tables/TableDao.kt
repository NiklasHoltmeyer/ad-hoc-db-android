package de.hsos.ma.adhocdb.controller.tables

import androidx.room.*
import androidx.sqlite.db.SupportSQLiteQuery
import de.hsos.ma.adhocdb.entities.table.Cell
import de.hsos.ma.adhocdb.entities.table.Column
import de.hsos.ma.adhocdb.entities.table.Table
import java.util.*

@Dao
interface TableDao{
    @Insert
    suspend fun insert(table: Table) : Long

    @Update
    suspend fun update(table: Table)
    @Delete
    suspend fun delete(table: Table)
    @Query("SELECT * FROM TABLES")
    suspend fun getAll() : List<Table>

    @Update
    suspend fun update(cell: Cell)
    @Delete
    suspend fun delete(cell: Cell)

    @Delete
    suspend fun delete(column: Column)

    /*@Query("SELECT * FROM tables" +
                 " WHERE blabla")
    fun findTablesByBlabla(name: String) : List<TableEntity>*/

    @RawQuery
    suspend fun getTableViaQuery(query: SupportSQLiteQuery) : Optional<Table>
    @RawQuery
    suspend fun getTablesViaQuery(query: SupportSQLiteQuery) : List<Table>

    @Query("SELECT * FROM tables WHERE id=:id")
    fun getTableById(id: String): Table

    @Insert
    suspend fun insert(column: Column): Long

    @Update
    suspend fun update(column: Column)

    @Insert
    suspend fun insert(cell: Cell): Long

    @Insert
    suspend fun insert(cells: List<Cell>): List<Long>

    @Query("SELECT * FROM columns WHERE tableId=:tablId ")
    fun getColumnsByTableId(tablId: String): List<Column>


    @Query("SELECT * FROM cells WHERE tableId=:tablId ")
    fun getCellsByTableId(tablId: String): List<Cell>

    @Query("SELECT * FROM cells WHERE tableId=:tablId and columnId=:columnId")
    fun getCellsByTableIdandColumnId(tablId: String, columnId: String): List<Cell>

    @Query("SELECT * FROM cells WHERE Id=:id and tableId=:tableId")
    fun getCellsByIdandTableId(id: String, tableId: String): List<Cell>

    @Query("SELECT * FROM cells WHERE _row=:row and tableId=:tableId")
    fun getCellsTableIdAndRow(tableId: String, row: String): List<Cell>

    @Query("SELECT * FROM cells")
    suspend fun getAllCells() : List<Cell>
}