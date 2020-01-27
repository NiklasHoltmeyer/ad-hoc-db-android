package de.hsos.ma.adhocdb.ui.homescreen

import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.view.Menu
import android.view.MenuItem
import android.widget.SearchView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.customview.customView
import com.afollestad.materialdialogs.customview.getCustomView
import com.afollestad.materialdialogs.input.input
import com.google.android.material.button.MaterialButton
import de.hsos.ma.adhocdb.R
import de.hsos.ma.adhocdb.entities.Table
import de.hsos.ma.adhocdb.framework.persistence.database.TablesDatabase
import de.hsos.ma.adhocdb.framework.persistence.tables.DataSource
import de.hsos.ma.adhocdb.ui.BaseCoroutine
import de.hsos.ma.adhocdb.ui.CONSTS
import de.hsos.ma.adhocdb.ui.TableShow.TableShowActivity
import de.hsos.ma.adhocdb.ui.createtable.CreateTableActivity
import de.hsos.ma.adhocdb.ui.tablelist.OnTableClickListener
import de.hsos.ma.adhocdb.ui.tablelist.TableRecyclerAdapter
import de.hsos.ma.adhocdb.ui.tablelist.TopSpacingItemDecoration
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : BaseCoroutine(R.layout.activity_main, "Table Overview"), OnTableClickListener {
    private lateinit var tableAdapter: TableRecyclerAdapter
    var tablesFilterable: MutableList<Table> = ArrayList()
    var tablesFullList: MutableList<Table> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initRecyclerView()
    }

    private fun initRecyclerView() {
        launch {
            tablesFilterable = DataSource.getDataSet(true, applicationContext).toMutableList()
            tablesFullList = tablesFilterable.toCollection(mutableListOf())

            launch(Dispatchers.Main) {
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
    }

    override fun onItemClick(item: Table, pos: Int) {
        if (tablesFilterable.size <= pos) {
            Toast.makeText(this, "Tables.Size < Pos", Toast.LENGTH_SHORT)
                .show() //TODO richtige fehlermeldung
            return
        }
        val intent = Intent(this, TableShowActivity::class.java)
        val item = tablesFilterable[pos]
        intent.putExtra(CONSTS.itemId, item.id)
        startActivity(intent)
    }

    override fun onItemLongClick(item: Table, pos: Int): Boolean {
        val dialog = MaterialDialog(this@MainActivity)
            .customView(R.layout.view_edit_table, scrollable = true)
        dialog.cornerRadius(res = R.dimen.my_corner_radius)

        val dialogView = dialog.getCustomView()

        val changeNameView = dialogView.findViewById<MaterialButton>(R.id.change_table_name)
        val changeDescriptionButton =
            dialogView.findViewById<MaterialButton>(R.id.change_table_description)
        val deleteTableBUtton = dialogView.findViewById<MaterialButton>(R.id.change_table_delete)

        changeNameView?.setOnClickListener {
            editTableName(item)
        }
        changeDescriptionButton?.setOnClickListener {
            editTableDescription(item)
        }
        deleteTableBUtton?.setOnClickListener {
            editTableDelete(item)
        }

        dialog.show()
        return true
    }

    private fun editTableDescription(table: Table) {
        val type = InputType.TYPE_CLASS_TEXT
        MaterialDialog(this)
            .title(R.string.editTable_Description)
            .show {
                positiveButton(R.string.submit)
                negativeButton(R.string.cancel)
                input(
                    allowEmpty = false,
                    inputType = type,
                    prefill = table.description
                ) { dialog, text ->
                    table.description = text.toString()
                    updateTable(table)
                }
            }
    }

    private fun editTableName(table: Table) {
        val type = InputType.TYPE_CLASS_TEXT
        MaterialDialog(this)
            .title(R.string.editTableName)
            .show {
                positiveButton(R.string.submit)
                negativeButton(R.string.cancel)
                input(allowEmpty = false, inputType = type, prefill = table.name) { dialog, text ->
                    table.name = text.toString()
                    updateTable(table)
                }
            }
    }

    private fun editTableDelete(table: Table) {
        MaterialDialog(this)
            .title(R.string.editTableDelete)
            .show{
                message(R.string.editTableDeleteWarning)
                positiveButton(R.string.submit){
                    deleteTable(table)
                }
                negativeButton(R.string.cancel)
            }
    }

    private fun reloadView(){
        launch(Dispatchers.Main){
            recreate()
        }
    }

    private fun updateTable(table: Table) {
        launch {
            val db = TablesDatabase(applicationContext).tableDao()
            db.update(table)
            reloadView()
        }
    }

    private fun deleteTable(table: Table) {
        launch {
            val db = TablesDatabase(applicationContext).tableDao()
            db.delete(table)
            reloadView()
        }
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        var searchView = menu.findItem(R.id.action_search_with_query)?.actionView as SearchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(queryFilter: String?): Boolean {
                tableAdapter.filter.filter(queryFilter)
                return true
            }
        })

        var addView = menu.findItem(R.id.action_add)
            .setOnMenuItemClickListener(object : MenuItem.OnMenuItemClickListener {
                override fun onMenuItemClick(item: MenuItem?): Boolean {
                    loadAddTableView()
                    return true
                }
            })
        return true
    }

    private fun loadAddTableView() {
        val intent = Intent(this, CreateTableActivity::class.java)
        startActivity(intent)
    }
}
