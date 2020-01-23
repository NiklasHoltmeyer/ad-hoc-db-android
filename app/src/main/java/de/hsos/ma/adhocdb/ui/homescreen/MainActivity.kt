package de.hsos.ma.adhocdb.ui.homescreen

import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import de.hsos.ma.adhocdb.R
import de.hsos.ma.adhocdb.entities.TableEntity
import de.hsos.ma.adhocdb.framework.persistence.database.TablesDatabase
import de.hsos.ma.adhocdb.ui.createtable.CreateTableActivity
import kotlinx.android.synthetic.main.activity_main.*
import de.hsos.ma.adhocdb.ui.tablelist.TableRecyclerAdapter
import de.hsos.ma.adhocdb.framework.persistence.tables.DataSource
import de.hsos.ma.adhocdb.ui.CONSTS
import de.hsos.ma.adhocdb.ui.TableShow.TableShowActivity
import de.hsos.ma.adhocdb.ui.tablelist.OnTableClickListener
import de.hsos.ma.adhocdb.ui.tablelist.TopSpacingItemDecoration

class MainActivity : AppCompatActivity(), OnTableClickListener {
    private lateinit var tableAdapter: TableRecyclerAdapter
    var  tables : List<TableEntity> = emptyList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //loadTables()

        initRecyclerView()
        addDataSet()
    }

    fun addButtonOnClick (view: View){
        val intent = Intent(this, CreateTableActivity::class.java)
        startActivity(intent)
    }

    private fun onTablesLoadCallBack(tables : List<TableEntity>){
        for (table in tables) {
            Log.e("Error", table.name)
        }
    }

    private fun loadTables(){ //TODO callback function
        class SaveClass : AsyncTask<Void, Void, Void>(){
            override fun doInBackground(vararg p0: Void?): Void? {
                val db = TablesDatabase(applicationContext).tableDao()
                tables = db.getAll()
                return null
            }

            override fun onPostExecute(result: Void?) {
                onTablesLoadCallBack(tables)
            }
        }
        SaveClass().execute()
    }

    private fun addDataSet(){
        tables = DataSource.createMockDataSet()
        tableAdapter.submitList(tables)
    }

    private fun initRecyclerView(){ //TODO
        recycler_view.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            val topSpacingDecorator =
                TopSpacingItemDecoration(30)
            addItemDecoration(topSpacingDecorator)
            tableAdapter = TableRecyclerAdapter(this@MainActivity)
            adapter = tableAdapter
        }
    }

    override fun onItemClick(item: TableEntity, pos: Int) {
        if(tables.size <= pos){
            Toast.makeText(this, "Tables.Size < Pos", Toast.LENGTH_SHORT).show() //TODO richtige fehlermeldung
            return
        }
        val intent = Intent(this, TableShowActivity::class.java)
        val item = tables[pos]
        intent.putExtra(CONSTS.itemId, item.id)
        intent.putExtra(CONSTS.itemName, item.name)
        intent.putExtra(CONSTS.itemImage, item.image)
        intent.putExtra(CONSTS.itemDescription, item.description)
        startActivity(intent)
    }
}
