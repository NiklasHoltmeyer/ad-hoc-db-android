package de.hsos.ma.adhocdb.ui.notes.home

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.widget.SearchView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import de.hsos.ma.adhocdb.R
import de.hsos.ma.adhocdb.entities.note.Note
import de.hsos.ma.adhocdb.framework.persistence.notes.NotesMockDataSource
import de.hsos.ma.adhocdb.ui.BaseCoroutineBaseMenuAppCompactActivity
import de.hsos.ma.adhocdb.ui.INTENTCONSTS
import de.hsos.ma.adhocdb.ui.notes.create.NoteCreateActivity
import de.hsos.ma.adhocdb.ui.notes.show.NotesShowAcivity
import de.hsos.ma.adhocdb.ui.table.view.note.recycler.NoteRecyclerAdapter
import de.hsos.ma.adhocdb.ui.table.view.recycler.OnRecyclerItemClickListener
import de.hsos.ma.adhocdb.ui.table.view.recycler.RecyclerTopSpacingItemDecoration
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NotesHomeActivity : BaseCoroutineBaseMenuAppCompactActivity(R.layout.activity_notes_home, R.string.notes_home, showBackButton = false,selectedMenuItem = R.id.nav_notes),
    OnRecyclerItemClickListener<Note>{
    private lateinit var noteAdapter: NoteRecyclerAdapter
    var notesFilterable: MutableList<Note> = ArrayList()
    var notesFullList: MutableList<Note> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initRecyclerView()
    }

    private fun initRecyclerView() {
        launch {
            notesFilterable = NotesMockDataSource.getDataSet(true, applicationContext).toMutableList()
            notesFullList = notesFilterable.toCollection(mutableListOf())

            launch(Dispatchers.Main) {
                recycler_view.apply {
                    layoutManager = LinearLayoutManager(this@NotesHomeActivity)
                    val topSpacingDecorator =
                        RecyclerTopSpacingItemDecoration(
                            30
                        )
                    addItemDecoration(topSpacingDecorator)
                    noteAdapter =
                        NoteRecyclerAdapter(
                            this@NotesHomeActivity,
                            notesFilterable
                        )
                    adapter = noteAdapter
                }
            }
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
                //tableAdapter.filter.filter(queryFilter)
                return true
            }
        })

        var addView = menu.findItem(R.id.action_add)
            .setOnMenuItemClickListener {
                loadAddNoteView()
                true
            }
        return true
    }

    private fun loadAddNoteView() {
        val intent = Intent(this, NoteCreateActivity::class.java)
        startActivity(intent)
    }

    override fun onItemClick(item: Note, pos: Int) {
        if (notesFilterable.size <= pos) {
            Toast.makeText(this, "Notes.Size < Pos", Toast.LENGTH_SHORT)
                .show() //TODO richtige fehlermeldung
            return
        }
        val intent = Intent(this, NotesShowAcivity::class.java)
        val item = notesFilterable[pos]
        intent.putExtra(INTENTCONSTS.itemId, item.id)
        startActivity(intent)
    }

    override fun onItemLongClick(item: Note, pos: Int): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
/*
class TableHomeActivity : {
    override fun onItemClick(item: Table, pos: Int) {
        if (notesFilterable.size <= pos) {
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
            .customView(R.layout.view_dialog_edit_table, scrollable = true)
        dialog.cornerRadius(res = R.dimen.my_corner_radius)

        val dialogView = dialog.getCustomView()

        val changeNameView = dialogView.findViewById<MaterialButton>(R.id.change_table_name)
        val changeDescriptionButton =
            dialogView.findViewById<MaterialButton>(R.id.change_table_description)
        val deleteTableButton = dialogView.findViewById<MaterialButton>(R.id.change_table_delete)

        changeNameView?.setOnClickListener {
            editTableName(item)
        }
        changeDescriptionButton?.setOnClickListener {
            editTableDescription(item)
        }
        deleteTableButton?.setOnClickListener {
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

    override fun onCreateOptionsMenu(filter_query_menu: Menu?): Boolean {
        super.onCreateOptionsMenu(filter_query_menu)
        if(filter_query_menu==null) return false
        menuInflater.inflate(R.filter_query_menu.filter_query_menu, filter_query_menu)
        var searchView = filter_query_menu.findItem(R.id.action_search_with_query)?.actionView as SearchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(queryFilter: String?): Boolean {
                tableAdapter.filter.filter(queryFilter)
                return true
            }
        })

        var addView = filter_query_menu.findItem(R.id.action_add)
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
*/