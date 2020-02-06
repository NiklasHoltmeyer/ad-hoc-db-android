package de.hsos.ma.adhocdb.ui.notes.create

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.afollestad.materialdialogs.MaterialDialog
import com.google.android.material.textfield.TextInputEditText
import de.hsos.ma.adhocdb.R
import de.hsos.ma.adhocdb.entities.note.Note
import de.hsos.ma.adhocdb.entities.table.Column
import de.hsos.ma.adhocdb.framework.persistence.database.NotesDatabase
import de.hsos.ma.adhocdb.framework.persistence.database.TablesDatabase
import de.hsos.ma.adhocdb.ui.BaseCoroutineBaseMenuAppCompactActivity
import de.hsos.ma.adhocdb.ui.notes.home.NotesHomeActivity
import kotlinx.coroutines.launch

class NoteCreateActivity : BaseCoroutineBaseMenuAppCompactActivity(R.layout.activity_note_create, R.string.notes_create, showBackButton = false,selectedMenuItem = R.id.nav_notes) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    fun clearInputs(){
        val textInputName = findViewById<TextInputEditText>(R.id.textInputDescription)
        val textInputDescription = findViewById<TextInputEditText>(R.id.textInputDescription)

        textInputName.text?.clear()
        textInputDescription.text?.clear()

    }

    private fun onSuccessCallback() {
        val intent = Intent(this, NotesHomeActivity::class.java)
        startActivity(intent)
    }

    fun onSubmitButtonClick(view: View) {
        val textInputName = findViewById<TextInputEditText>(R.id.textInputDescription)
        val textInputDescription = findViewById<TextInputEditText>(R.id.textInputDescription)

        val noteName = textInputName?.text.toString()
        val description = textInputDescription?.text.toString()

        if(noteName == null){
            Toast.makeText(this, "Please fill in Note Name", Toast.LENGTH_LONG)
        }else if(description == null){
            Toast.makeText(this, "Please fill in Note", Toast.LENGTH_LONG)
        }else{
            val note = Note(noteName, description)
            launch {
                val db = NotesDatabase(applicationContext).noteDao()
                db.insert(note)
                onSuccessCallback()
            }
        }
    }

    fun onCancelButtonClick(view: View) {
        MaterialDialog(this)
            .title(R.string.editTableDelete)
            .show{
                message(R.string.create_table_clear_input_dialog_body)
                positiveButton(R.string.submit){
                    clearInputs()
                }
                negativeButton(R.string.cancel)
            }
    }
}
