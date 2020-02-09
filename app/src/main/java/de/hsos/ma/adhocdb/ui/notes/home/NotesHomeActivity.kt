package de.hsos.ma.adhocdb.ui.notes.home

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
import de.hsos.ma.adhocdb.entities.note.Note
import de.hsos.ma.adhocdb.framework.persistence.database.NotesDatabase
import de.hsos.ma.adhocdb.framework.persistence.notes.NotesMockDataSource
import de.hsos.ma.adhocdb.ui.BaseCoroutineBaseMenuAppCompactActivity
import de.hsos.ma.adhocdb.ui.INTENTCONSTS
import de.hsos.ma.adhocdb.ui.notes.create.NoteCreateActivity
import de.hsos.ma.adhocdb.ui.notes.show.NotesShowAcivity
import de.hsos.ma.adhocdb.ui.notes.view.recycler.NoteRecyclerAdapter
import de.hsos.ma.adhocdb.ui.table.view.recycler.OnRecyclerItemClickListener
import de.hsos.ma.adhocdb.ui.table.view.recycler.RecyclerTopSpacingItemDecoration
import kotlinx.android.synthetic.main.activity_notes_home.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NotesHomeActivity : BaseCoroutineBaseMenuAppCompactActivity(R.layout.activity_notes_home, R.string.notes_home, showBackButton = false,selectedMenuItem = R.id.nav_notes),
    OnRecyclerItemClickListener<Note> {
    private lateinit var noteAdapter: NoteRecyclerAdapter
    private var notesFilterable: MutableList<Note> = ArrayList()
    private var notesFullList: MutableList<Note> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initRecyclerView()
    }

    private fun initRecyclerView() {
        launch {
            notesFilterable = NotesMockDataSource.getDataSet(true, applicationContext).toMutableList()
            notesFullList = notesFilterable.toCollection(mutableListOf())

            launch(Dispatchers.Main) {
                recyclerView.apply {
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
                noteAdapter.filter.filter(queryFilter)
                return true
            }
        })

        menu.findItem(R.id.action_add)
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
        val dialog = MaterialDialog(this@NotesHomeActivity)
            .customView(R.layout.view_dialog_note_edit, scrollable = true)
        dialog.cornerRadius(res = R.dimen.my_corner_radius)

        val dialogView = dialog.getCustomView()

        val changeNameView = dialogView.findViewById<MaterialButton>(R.id.change_note_name)
        val deleteNoteButton = dialogView.findViewById<MaterialButton>(R.id.change_note_delete)

        changeNameView?.setOnClickListener {
            editNoteName(item)
            dialog.dismiss()
        }

        deleteNoteButton?.setOnClickListener {
            editNoteDelete(item)
            dialog.dismiss()
        }

        dialog.show()
        return true
    }

    private fun editNoteName(note: Note) {
        val type = InputType.TYPE_CLASS_TEXT
        MaterialDialog(this)
            .title(R.string.editNoteName)
            .show {
                positiveButton(R.string.submit)
                negativeButton(R.string.cancel)
                input(allowEmpty = false, inputType = type, prefill = note.name) { dialog, text ->
                    note.name = text.toString()
                    updateNote(note)
                }
            }
    }

    private fun editNoteDelete(note: Note) {
        MaterialDialog(this)
            .title(R.string.editNoteDelete)
            .show{
                message(R.string.editNoteDeleteWarning)
                positiveButton(R.string.submit){
                    deleteNote(note)
                }
                negativeButton(R.string.cancel)
            }
    }

    private fun updateNote(note: Note) {
        launch {
            val db = NotesDatabase(applicationContext).noteDao()
            db.update(note)
            reloadView()
        }
    }

    private fun deleteNote(note: Note) {
        launch {
            val db = NotesDatabase(applicationContext).noteDao()
            db.delete(note)
            reloadView()
        }
    }

}
