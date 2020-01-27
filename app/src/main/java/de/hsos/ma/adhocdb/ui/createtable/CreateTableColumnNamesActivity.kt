package de.hsos.ma.adhocdb.ui.createtable

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import android.widget.Toast
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import de.hsos.ma.adhocdb.R
import de.hsos.ma.adhocdb.entities.Column
import de.hsos.ma.adhocdb.entities.Table
import de.hsos.ma.adhocdb.framework.persistence.database.TablesDatabase
import de.hsos.ma.adhocdb.ui.BaseCoroutine
import de.hsos.ma.adhocdb.ui.CONSTS
import de.hsos.ma.adhocdb.ui.TableShow.TableShowActivity
import de.hsos.ma.adhocdb.ui.homescreen.MainActivity
import kotlinx.coroutines.launch

class CreateTableColumnNamesActivity :
    BaseCoroutine(R.layout.activity_create_table, "Set Column Names", true) {
    private var columnCount: Int = -1//-1
    private var tableName = ""
    private var tableDescription = ""
    private var imageURL = ""
    private var textViews: ArrayList<TextInputEditText> = ArrayList<TextInputEditText>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_table_column_names)

        loadIntents()
        displayInputs()
    }

    private fun displayInputs() {
        if (columnCount >= 0) {
            initLayout()
        } else {
            Toast.makeText(this, "Error, Please go back", Toast.LENGTH_LONG).show()
        }
    }

    private fun initLayout() {
        val container = findViewById<LinearLayout>(R.id.columnNameInputContainer)
        val inflater = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        for (i in 1..columnCount) {
            val view = inflater.inflate(R.layout.view_input, LinearLayout(this))
            val textInputLayout = view.findViewById<TextInputLayout>(R.id.textInputLayoutTableName)
            val textView = view.findViewById<TextInputEditText>(R.id.textInputName)

            textInputLayout.placeholderText = "Column $i"
            textInputLayout.hint = textInputLayout.placeholderText

            textViews.add(textView)

            container.addView(view)
        }
    }

    fun createTable(tableName: String, tableDescription: String, imageURL: String): Table? {
        if (tableName == null || tableName.isEmpty() ||
            tableDescription == null || tableDescription.isEmpty() ||
            imageURL == null || imageURL.isEmpty()
        ) {
            Toast.makeText(this, "[ERROR] Data-Null or Empty", Toast.LENGTH_LONG).show()
            return null
        }

        return Table(tableName, tableDescription, imageURL)
    }

    private fun loadIntents() {
        Log.e("ERROR", "LOAD")
        if (intent.hasExtra(CONSTS.tableName)) {
            tableName = intent.getStringExtra(CONSTS.tableName)
        }

        if (intent.hasExtra(CONSTS.tableDescription)) {
            tableDescription = intent.getStringExtra(CONSTS.tableDescription)
        }

        if (intent.hasExtra(CONSTS.imageURL)) {
            imageURL = intent.getStringExtra(CONSTS.imageURL)
        }

        if (intent.hasExtra(CONSTS.columnCount)) {
            columnCount = intent.getIntExtra(CONSTS.columnCount, -1)
            if (columnCount >= 0) {
                Log.e("ERROR", "Count $columnCount")
            } else {
                Log.e("ERROR", "ELSE2")
            }
        }
    }

    private inline fun calcDps(dps: Float): Int {
        //Quelle: https://stackoverflow.com/a/5255256/5026265
        val scale = applicationContext.resources.displayMetrics.density
        return (dps * scale + 0.5f).toInt()
    }

    private fun onSuccessCallback(tableId: Long) {
        val intent = Intent(this, TableShowActivity::class.java)
        intent.putExtra(CONSTS.itemId, tableId)
        startActivity(intent)
    }

    fun saveTableColNames(view: View) {
        Log.e("ERROR", "saveTableColNames")

        for (textView in this.textViews) {
            if (textView.text == null || textView!!.text!!.isEmpty()) {
                Toast.makeText(this, "Please Fill each Col. Name", Toast.LENGTH_SHORT).show()
                return
            }
        }

        Log.e("ERROR", "Counter: ${this.textViews.size}")

        var table = createTable(this.tableName, this.tableDescription, this.imageURL)

        if (table == null) {
            Toast.makeText(this, "ERROR: Could not Create Table", Toast.LENGTH_SHORT).show()
            return
        }

        launch {
            val db = TablesDatabase(applicationContext).tableDao()
            val tableId = db.insert(table)

            for (textView in textViews) {
                val colName = textView.text.toString()
                val column = Column(tableId, colName)
                db.insert(column)
            }

            onSuccessCallback(tableId)
        }

    }
}
