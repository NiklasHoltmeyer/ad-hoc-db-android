package de.hsos.ma.adhocdb

import DB.TablesDatabase
import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import de.hsos.ma.adhocdb.entities.TableEntity
import de.hsos.ma.adhocdb.ui.createtable.CreateTableActivity
import kotlinx.android.synthetic.main.activity_main.*
import todoordnenrecycler.BlogRecyclerAdapter
import todoordnenrecycler.DataSource
import todoordnenrecycler.TopSpacingItemDecoration

class MainActivity : AppCompatActivity() {
    private lateinit var blogAdapter: BlogRecyclerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        loadTables()

        initRecyclerView()
        addDataSet()
    }

    fun addButtonOnClick (view: View){
        var intent = Intent(this, CreateTableActivity::class.java)
        startActivity(intent)
    }

    private fun onTablesLoadCallBack(tables : List<TableEntity>){
        for (table in tables) {
            Log.e("Error", table.name)
        }
    }

    private fun loadTables(){ //TODO callback function
        class SaveClass : AsyncTask<Void, Void, Void>(){
            var  entites : List<TableEntity> = emptyList()
            override fun doInBackground(vararg p0: Void?): Void? {
                var db = TablesDatabase(applicationContext).tableDao()
                entites = db?.getAll()
                return null
            }

            override fun onPostExecute(result: Void?) {
                onTablesLoadCallBack(entites)
            }
        }
        SaveClass().execute()
    }

    private fun addDataSet(){ // TODO
        val data = DataSource.createDataSet()
        blogAdapter.submitList(data)
    }

    private fun initRecyclerView(){ //TODO
        recycler_view.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            val topSpacingDecorator = TopSpacingItemDecoration(30)
            addItemDecoration(topSpacingDecorator)
            blogAdapter = BlogRecyclerAdapter()
            adapter = blogAdapter
        }
    }
}
