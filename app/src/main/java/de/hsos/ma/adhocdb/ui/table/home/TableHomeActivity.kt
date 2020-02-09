package de.hsos.ma.adhocdb.ui.table.home

import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.view.Menu
import android.widget.SearchView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.customview.customView
import com.afollestad.materialdialogs.customview.getCustomView
import com.afollestad.materialdialogs.input.input
import com.google.android.material.button.MaterialButton
import de.hsos.ma.adhocdb.R
import de.hsos.ma.adhocdb.entities.table.Table
import de.hsos.ma.adhocdb.framework.persistence.database.TablesDatabase
import de.hsos.ma.adhocdb.framework.persistence.tables.TablesMockDataSource
import de.hsos.ma.adhocdb.ui.BaseCoroutineBaseMenuAppCompactActivity
import de.hsos.ma.adhocdb.ui.INTENTCONSTS
import de.hsos.ma.adhocdb.ui.table.show.TableShowActivity
import de.hsos.ma.adhocdb.ui.table.create.CreateTableActivity
import de.hsos.ma.adhocdb.ui.table.view.recycler.OnRecyclerItemClickListener
import de.hsos.ma.adhocdb.ui.table.view.table.recycler.TableRecyclerAdapter
import de.hsos.ma.adhocdb.ui.table.view.recycler.RecyclerTopSpacingItemDecoration
import kotlinx.android.synthetic.main.activity_table_home.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TableHomeActivity : BaseCoroutineBaseMenuAppCompactActivity(R.layout.activity_table_home, R.string.table_overview, showBackButton = false, selectedMenuItem = R.id.nav_table),
    OnRecyclerItemClickListener<Table> {
    private lateinit var tableAdapter: TableRecyclerAdapter
    var tablesFilterable: MutableList<Table> = ArrayList()
    var tablesFullList: MutableList<Table> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initRecyclerView()
    }

    private fun initRecyclerView() {
        launch {
            tablesFilterable = TablesMockDataSource.getDataSet(true, applicationContext).toMutableList()
            tablesFullList = tablesFilterable.toCollection(mutableListOf())

            launch(Dispatchers.Main) {
                recyclerView.apply {
                    layoutManager = LinearLayoutManager(this@TableHomeActivity)
                    val topSpacingDecorator =
                        RecyclerTopSpacingItemDecoration(
                            30
                        )
                    addItemDecoration(topSpacingDecorator)
                    tableAdapter =
                        TableRecyclerAdapter(
                            this@TableHomeActivity,
                            tablesFilterable
                        )
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
        intent.putExtra(INTENTCONSTS.itemId, item.id)
        startActivity(intent)
    }

    override fun onItemLongClick(item: Table, pos: Int): Boolean {
        val dialog = MaterialDialog(this@TableHomeActivity)
            .customView(R.layout.view_dialog_table_edit_table, scrollable = true)
        dialog.cornerRadius(res = R.dimen.my_corner_radius)

        val dialogView = dialog.getCustomView()

        val changeNameView = dialogView.findViewById<MaterialButton>(R.id.change_table_name)
        val changeDescriptionButton =
            dialogView.findViewById<MaterialButton>(R.id.change_table_description)
        val deleteTableButton = dialogView.findViewById<MaterialButton>(R.id.change_table_delete)

        changeNameView?.setOnClickListener {
            editTableName(item)
            dialog.dismiss()
        }
        changeDescriptionButton?.setOnClickListener {
            editTableDescription(item)
            dialog.dismiss()
        }
        deleteTableButton?.setOnClickListener {
            editTableDelete(item)
            dialog.dismiss()
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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        super.onCreateOptionsMenu(menu)
        if(menu==null) return false
        menuInflater.inflate(R.menu.filter_query_menu, menu)
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
            .setOnMenuItemClickListener {
                loadAddTableView()
                true
            }
        return true
    }

    private fun loadAddTableView() {
        val intent = Intent(this, CreateTableActivity::class.java)
        startActivity(intent)
    }
}
