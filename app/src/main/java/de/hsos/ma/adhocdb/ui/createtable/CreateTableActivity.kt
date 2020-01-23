package de.hsos.ma.adhocdb.ui.createtable

import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.view.View
import android.widget.NumberPicker.OnValueChangeListener
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText
import de.hsos.ma.adhocdb.MainActivity
import de.hsos.ma.adhocdb.R
import de.hsos.ma.adhocdb.entities.TableEntity
import de.hsos.ma.adhocdb.framework.persistence.database.TablesDatabase


class CreateTableActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.create_table_activity)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, CreateTableFragment.newInstance())
                .commitNow()
        }

        //actionbar
        val actionbar = supportActionBar
        //set actionbar title
        actionbar!!.title = "Create Table"
        //set back button
        actionbar.setDisplayHomeAsUpEnabled(true)
        actionbar.setDisplayHomeAsUpEnabled(true)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    var onValueChangeListener =
        OnValueChangeListener { numberPicker, i, i1 ->
            Toast.makeText(
                this@CreateTableActivity,
                "selected number " + numberPicker.value, Toast.LENGTH_SHORT
            ).show()
        }

    fun clearButtonOnClick(view: View) {
        //TODO erst fragen ob sicher
        Toast.makeText(applicationContext,"CLEAR",Toast.LENGTH_SHORT).show()
        val clearAbl = arrayOf(findViewById<TextInputEditText>(R.id.textInputName).text,
            findViewById<TextInputEditText>(R.id.textInputDescription).text,
            findViewById<TextInputEditText>(R.id.textInputColumns).text)

        clearAbl.forEach { it?.clear() }
    }
    fun saveButtonOnClick(view: View) {
        val tableName = findViewById<TextInputEditText>(R.id.textInputName).text
        val tableDescription = findViewById<TextInputEditText>(R.id.textInputDescription).text
        val tableColumnCount = findViewById<TextInputEditText>(R.id.textInputColumns).text

        if(tableName==null || tableName.isEmpty()){
            Toast.makeText(applicationContext,"Missing Table Name",Toast.LENGTH_SHORT).show()
        }else if(tableDescription==null || tableDescription.isEmpty()){
            Toast.makeText(applicationContext,"Missing Table Description",Toast.LENGTH_SHORT).show()
        }else if(tableColumnCount==null || tableColumnCount.isEmpty()){
            Toast.makeText(applicationContext,"Missing Table Column Count",Toast.LENGTH_SHORT).show()
        }else{
            val tableCount : Int = tableColumnCount.toString().toInt()
            val entity = TableEntity(tableName.toString(), tableDescription.toString(), "todo")
            saveTable(entity)

            Toast.makeText(applicationContext,"Saving",Toast.LENGTH_SHORT).show()
        }
    }

    private fun saveTable(table : TableEntity){ //TODO callback function
        class SaveClass : AsyncTask<Void, Void, Void>(){
            override fun doInBackground(vararg p0: Void?): Void? {
                val db = TablesDatabase(applicationContext).tableDao()
                db.insert(table)
                return null
            }

            override fun onPostExecute(result: Void?) {
                onSuccessCallback()
            }
        }
        SaveClass().execute()
    }

    private fun onSuccessCallback(){
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}
