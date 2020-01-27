package de.hsos.ma.adhocdb.controller.tables

import androidx.room.*
import androidx.sqlite.db.SupportSQLiteQuery
import de.hsos.ma.adhocdb.entities.Table
import java.util.*

@Dao
interface TableDao{
    @Insert
    suspend fun insert(table: Table): Long
    @Update
    suspend fun update(table: Table)
    @Delete
    suspend fun delete(table: Table)
    @Query("SELECT * FROM TABLES")
    suspend fun getAll() : List<Table>
    /*@Query("SELECT * FROM tables" +
                 " WHERE blabla")
    fun findTablesByBlabla(name: String) : List<TableEntity>*/

    @RawQuery
    suspend fun getTableViaQuery(query: SupportSQLiteQuery) : Optional<Table>
    @RawQuery
    suspend fun getTablesViaQuery(query: SupportSQLiteQuery) : List<Table>
}