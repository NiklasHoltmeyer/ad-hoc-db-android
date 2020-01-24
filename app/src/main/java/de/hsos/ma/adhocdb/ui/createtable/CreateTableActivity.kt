package de.hsos.ma.adhocdb.ui.createtable

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.Toast
import com.google.android.material.textfield.TextInputEditText
import de.hsos.ma.adhocdb.R
import de.hsos.ma.adhocdb.entities.TableEntity
import de.hsos.ma.adhocdb.framework.persistence.database.TablesDatabase
import de.hsos.ma.adhocdb.ui.BaseCoroutine
import de.hsos.ma.adhocdb.ui.CONSTS
import de.hsos.ma.adhocdb.ui.TableShow.TableShowActivity
import de.hsos.ma.adhocdb.ui.homescreen.MainActivity
import kotlinx.coroutines.launch

class CreateTableActivity : BaseCoroutine(R.layout.activity_create_table, "Create Table", true) {
    var columnCount = -1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initColumnInput()
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
            columnCount = tableColumnCount.toString().toInt()
            val entity = TableEntity(tableName.toString(), tableDescription.toString(), "todo")
            saveTable(entity)

            Toast.makeText(applicationContext,"Saving",Toast.LENGTH_SHORT).show()
        }
    }

    private fun saveTable(table : TableEntity){ //TODO callback function
        launch{
            val id = TablesDatabase(applicationContext).tableDao().insert(table)
            onSuccessCallback(0) //TODO ^ muss wert returnen
        }
    }

    private fun onSuccessCallback(id : Long){
        val intent = Intent(this, CreateTableColumnNamesActivity::class.java)
        intent.putExtra(CONSTS.itemId, id)
        intent.putExtra(CONSTS.columnCount, columnCount)
        startActivity(intent)
    }

    fun initColumnInput() {
        var textInputColumns = findViewById<TextInputEditText>(R.id.textInputColumns)
        textInputColumns!!.addTextChangedListener(object : TextWatcher{
            override fun afterTextChanged(p0: Editable?) {
                Log.e("Error", "AFTER")
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
        })
    }
}
