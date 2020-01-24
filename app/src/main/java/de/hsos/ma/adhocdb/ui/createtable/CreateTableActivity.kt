package de.hsos.ma.adhocdb.ui.createtable

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.Toast
import com.afollestad.materialdialogs.MaterialDialog
import com.google.android.material.textfield.TextInputEditText
import de.hsos.ma.adhocdb.R
import de.hsos.ma.adhocdb.ui.BaseCoroutine
import de.hsos.ma.adhocdb.ui.CONSTS

class CreateTableActivity : BaseCoroutine(R.layout.activity_create_table, "Create Table", true) {
    private var columnCount = -1
    private var tableName = ""
    private var tableDescription = ""
    private var imageURL = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initColumnInput()
    }

    fun clearButtonOnClick(view: View) {
        MaterialDialog(this).show {
            cancelOnTouchOutside(false)
            title(R.string.create_table_dialog_header)
            message(R.string.create_table_dialog_body)
            positiveButton(R.string.dialog_aggree){
                clearInput()
            }

            negativeButton(R.string.dialog_disaggree){
                //callBackFunction
            }
        }
    }

    private fun clearInput(){
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
            this.tableName = tableName.toString()
            this.tableDescription = tableDescription.toString()
            this.imageURL = "TODO"

            onSuccessCallback()
        }
    }



    private fun onSuccessCallback(){
        val intent = Intent(this, CreateTableColumnNamesActivity::class.java)
        intent.putExtra(CONSTS.columnCount, columnCount)

        intent.putExtra(CONSTS.tableName, tableName)
        intent.putExtra(CONSTS.tableDescription, tableDescription)
        intent.putExtra(CONSTS.imageURL, imageURL)
        startActivity(intent)
    }

    private fun initColumnInput() {
        val textInputColumns = findViewById<TextInputEditText>(R.id.textInputColumns)
        textInputColumns!!.addTextChangedListener(object : TextWatcher{
            override fun afterTextChanged(p0: Editable?) {
                Log.e("Error", "AFTER")
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
        })
    }
}
