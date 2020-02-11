package de.hsos.ma.adhocdb.ui.notes.show

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.InputType
import android.view.Menu
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.customview.customView
import com.afollestad.materialdialogs.customview.getCustomView
import com.afollestad.materialdialogs.input.input
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import de.hsos.ma.adhocdb.R
import de.hsos.ma.adhocdb.entities.note.Note
import de.hsos.ma.adhocdb.framework.persistence.database.NotesDatabase
import de.hsos.ma.adhocdb.ui.BaseCoroutineBaseMenuAppCompactActivity
import de.hsos.ma.adhocdb.ui.INTENTCONSTS
import de.hsos.ma.adhocdb.ui.notes.home.NotesHomeActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class NotesShowAcivity : BaseCoroutineBaseMenuAppCompactActivity(
    R.layout.activity_notes_show_acivity,
    R.string.notes_show,
    showBackButton = true,
    selectedMenuItem = R.id.nav_notes
) {
    private var noteId = -1L
    private var note: Note? = null
    private var textInputDescription: TextInputEditText? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        textInputDescription = findViewById<TextInputEditText>(R.id.textInputDescription)

        loadIntentExtras()
    }

    override fun onStop() {
        updateDescription()
        super.onStop()
    }

    private fun loadIntentExtras() {
        if (!intent.hasExtra(INTENTCONSTS.itemId)) {
            Toast.makeText(this, "Couldnt Retriev NoteID", Toast.LENGTH_LONG)
            return
        }

        noteId = intent.getLongExtra(INTENTCONSTS.itemId, -1)
        if (noteId < 0) {
            Toast.makeText(this, "Couldnt Retriev NoteID", Toast.LENGTH_LONG)
            return
        }

        loadNote(noteId)
    }

    private fun loadNote(noteId: Long) {
        launch {
            val db = NotesDatabase(applicationContext).noteDao()

            val noteId: String = noteId.toString()
            note = db.getNoteById(noteId)

            callBack(note)
        }
    }
    private fun deleteNote() {
        launch {
            val db = NotesDatabase(applicationContext).noteDao()

            if(note!=null) db.delete(note!!)


            val intent = Intent(this@NotesShowAcivity,NotesHomeActivity::class.java)
            startActivity(intent)
        }
    }

    private fun callBack(note: Note?) {
        launch(Dispatchers.Main) {
            if (note != null){
                titleBarTitle(note.name)
                textInputDescription?.text =
                    Editable.Factory.getInstance().newEditable(note.description)
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        super.onCreateOptionsMenu(menu)
        if(menu==null) return false
        menuInflater.inflate(R.menu.edit_menu, menu)
        menu.findItem(R.id.action_edit)
            .setOnMenuItemClickListener {
                loadEditDialog()
                true
            }

        menu.findItem(R.id.action_save).setOnMenuItemClickListener {
            updateDescription()
            hideKeyboard()
            val current = currentFocus
            current?.clearFocus()
            true
        }
        return true
    }

    fun loadEditDialog(){
        val dialog = MaterialDialog(this@NotesShowAcivity)
            .customView(R.layout.view_dialog_note_edit, scrollable = true)
        dialog.cornerRadius(res = R.dimen.my_corner_radius)

        val dialogView = dialog.getCustomView()

        val changeNameView = dialogView.findViewById<MaterialButton>(R.id.change_note_name)
        val deleteNoteButton = dialogView.findViewById<MaterialButton>(R.id.change_note_delete)

        changeNameView?.setOnClickListener {
            editNoteName()
            dialog.dismiss()
        }
        deleteNoteButton?.setOnClickListener {
            editNoteDelete()
            dialog.dismiss()
        }

        dialog.show()
    }

    private fun editNoteDelete() {
        MaterialDialog(this)
            .title(R.string.editNoteDelete)
            .show{
                message(R.string.editNoteDeleteWarning)
                positiveButton(R.string.submit){
                    deleteNote()
                }
                negativeButton(R.string.cancel)
            }
    }

    private fun editNoteName() {
        val type = InputType.TYPE_CLASS_TEXT

        MaterialDialog(this)
            .title(R.string.editNoteName)
            .show {
                positiveButton(R.string.submit)
                negativeButton(R.string.cancel)
                input(allowEmpty = false, inputType = type, prefill = note?.name?: "") { dialog, text ->
                    if(note != null){
                        note!!.name = text.toString()
                        updateNote(note!!)
                    }
                }
            }
    }

    private fun updateNote(note: Note) {
        launch {
            val db = NotesDatabase(applicationContext).noteDao()

            db.update(note)

            reloadView()
        }
    }

    fun updateDescription() {
        if (note == null) return
        val textInputDescription = findViewById<TextInputEditText>(R.id.textInputDescription)
        note!!.description = textInputDescription.text.toString()

        launch {
            val db = NotesDatabase(applicationContext).noteDao()
            db.update(note!!)
        }

    }

    fun clearButtonOnClick(view: View) {
        MaterialDialog(this).show {
            cancelOnTouchOutside(false)
            title(R.string.create_table_clear_title)
            message(R.string.create_table_clear_input_dialog_body)
            positiveButton(R.string.dialog_agree) {
                recreate()
            }

            negativeButton(R.string.dialog_disagree) {
            }
        }
    }

    fun View.hideKeyboard() {
        //Quelle: https://stackoverflow.com/a/44500926
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(windowToken, 0)
    }

    fun Activity.hideKeyboard() {
        hideKeyboard(currentFocus ?: View(this))
    }

    fun Context.hideKeyboard(view: View) {
        val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }
}
