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
import de.hsos.ma.adhocdb.ui.BaseCoroutine
import de.hsos.ma.adhocdb.ui.CONSTS
import de.hsos.ma.adhocdb.ui.TableShow.TableShowActivity
import de.hsos.ma.adhocdb.ui.tablelist.OnTableClickListener
import de.hsos.ma.adhocdb.ui.tablelist.TopSpacingItemDecoration
import kotlinx.coroutines.launch

class MainActivity : BaseCoroutine(R.layout.activity_main), OnTableClickListener {
    private lateinit var tableAdapter: TableRecyclerAdapter
    var  tables : List<TableEntity> = emptyList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initRecyclerView()
        addDataSet()
    }

    fun addButtonOnClick (view: View){
        val intent = Intent(this, CreateTableActivity::class.java)
        startActivity(intent)
    }

    private fun addDataSet(){
        Log.e("error", "addDataSet")
        launch{
            tables = DataSource.getDataSet(true, applicationContext)
            Log.e("error", "Table-Size: " + tables.size)
            tableAdapter.submitList(tables)
        }
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

    fun addButtonDebugTestOnClick(view: View) {

    }
}
