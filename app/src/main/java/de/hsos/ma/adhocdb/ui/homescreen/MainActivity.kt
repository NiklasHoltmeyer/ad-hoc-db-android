package de.hsos.ma.adhocdb.ui.homescreen

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.View
import android.widget.Filter
import android.widget.Filterable
import android.widget.SearchView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import de.hsos.ma.adhocdb.R
import de.hsos.ma.adhocdb.entities.TableEntity
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

class MainActivity : BaseCoroutine(R.layout.activity_main), OnTableClickListener, Filterable {
    private lateinit var tableAdapter: TableRecyclerAdapter
    var  tablesFilterable : List<TableEntity> = ArrayList()
    var  tablesFullList : List<TableEntity> = emptyList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initRecyclerView()
    }

    fun addButtonOnClick (view: View){
        val intent = Intent(this, CreateTableActivity::class.java)
        startActivity(intent)
    }

    private fun initRecyclerView(){
        Log.e("error", "addDataSet")
        launch{
            tablesFilterable = DataSource.getDataSet(true, applicationContext)
            tablesFullList = tablesFilterable.toCollection(mutableListOf())
            Log.e("error", "Table-Size: " + tablesFilterable.size)
            recycler_view.apply {
                layoutManager = LinearLayoutManager(this@MainActivity)
                val topSpacingDecorator =
                    TopSpacingItemDecoration(30)
                addItemDecoration(topSpacingDecorator)
                tableAdapter = TableRecyclerAdapter(this@MainActivity, tablesFilterable)
                adapter = tableAdapter
            }
        }
    }

    override fun onItemClick(item: TableEntity, pos: Int) {
        if(tablesFilterable.size <= pos){
            Toast.makeText(this, "Tables.Size < Pos", Toast.LENGTH_SHORT).show() //TODO richtige fehlermeldung
            return
        }
        val intent = Intent(this, TableShowActivity::class.java)
        val item = tablesFilterable[pos]
        intent.putExtra(CONSTS.itemId, item.id)
        intent.putExtra(CONSTS.itemName, item.name)
        intent.putExtra(CONSTS.itemImage, item.image)
        intent.putExtra(CONSTS.itemDescription, item.description)
        startActivity(intent)
    }

    override fun getFilter(): Filter {
        return object : Filter(){
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                var filterdList = arrayListOf<TableEntity>()

                if(constraint == null || constraint.isEmpty()){
                    filterdList.addAll(tablesFilterable)
                }else{
                    val filterPattern = constraint.toString().toLowerCase().trim()
                    tablesFullList.forEach {
                        if(it.name.toLowerCase().contains(filterPattern)){
                            filterdList.add(it)
                        }
                    }
                }
                var result = FilterResults()
                result.values = filterdList
                return result
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                tablesFilterable = results?.values as (List<TableEntity>)
                tableAdapter.notifyDataSetChanged()
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        var searchView = menu?.findItem(R.id.action_search)?.actionView as SearchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(queryFilter: String?): Boolean {
                filter.filter(queryFilter)
                return false
            }

        })


        return true;
    }
}
