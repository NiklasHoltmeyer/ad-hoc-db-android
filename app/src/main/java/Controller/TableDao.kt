package Controller

import androidx.room.*
import androidx.sqlite.db.SupportSQLiteQuery
import de.hsos.ma.adhocdb.entities.TableEntity
import java.util.*

@Dao
interface TableDao{
    @Insert
    fun insert(table: TableEntity)
    @Update
    fun update(table: TableEntity)
    @Delete
    fun delete(table: TableEntity)
    @Query("SELECT * FROM TABLES")
    fun getAll() : List<TableEntity>
    /*@Query("SELECT * FROM tables" +
                 " WHERE blabla")
    fun findTablesByBlabla(name: String) : List<TableEntity>*/

    @RawQuery
    fun getTableViaQuery(query: SupportSQLiteQuery) : Optional<TableEntity>
    @RawQuery
    fun getTablesViaQuery(query: SupportSQLiteQuery) : List<TableEntity>
}