package de.hsos.ma.adhocdb.controller.tables

import androidx.room.*
import androidx.sqlite.db.SupportSQLiteQuery
import de.hsos.ma.adhocdb.entities.TableEntity
import java.util.*

@Dao
interface TableDao{
    @Insert
    suspend fun insert(table: TableEntity)
    @Update
    suspend fun update(table: TableEntity)
    @Delete
    suspend fun delete(table: TableEntity)
    @Query("SELECT * FROM TABLES")
    suspend fun getAll() : List<TableEntity>
    /*@Query("SELECT * FROM tables" +
                 " WHERE blabla")
    fun findTablesByBlabla(name: String) : List<TableEntity>*/

    @RawQuery
    suspend fun getTableViaQuery(query: SupportSQLiteQuery) : Optional<TableEntity>
    @RawQuery
    suspend fun getTablesViaQuery(query: SupportSQLiteQuery) : List<TableEntity>
}