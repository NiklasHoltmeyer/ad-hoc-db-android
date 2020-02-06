package de.hsos.ma.adhocdb.ui.table.create

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import android.widget.Toast
import de.hsos.ma.adhocdb.R
import de.hsos.ma.adhocdb.ui.table.view.table.column.TableAddColumn
import de.hsos.ma.adhocdb.entities.table.Column
import de.hsos.ma.adhocdb.entities.table.Table
import de.hsos.ma.adhocdb.framework.persistence.database.TablesDatabase
import de.hsos.ma.adhocdb.ui.BaseCoroutineBaseMenuAppCompactActivity
import de.hsos.ma.adhocdb.ui.INTENTCONSTS
import de.hsos.ma.adhocdb.ui.table.show.TableShowActivity
import kotlinx.coroutines.launch

class CreateTableColumnNamesActivity :
    BaseCoroutineBaseMenuAppCompactActivity(R.layout.activity_create_table, R.string.set_column_names, true, selectedMenuItem = R.id.nav_table) {
    private var columnCount: Int = -1//-1
    private var tableName = ""
    private var tableDescription = ""
    private var imageURL = ""
    //private var textViews: ArrayList<TextInputEditText> = ArrayList<TextInputEditText>()
    private var columnViews: ArrayList<TableAddColumn> = ArrayList<TableAddColumn>()
    //TableAddColumn

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
            //1312
            val view = TableAddColumn(
                this,
                "Column $i"
            )
            columnViews.add(view)
            container.addView(view)

            /*
            val view = inflater.inflate(R.layout.view_input, LinearLayout(this))
            val textInputLayout = view.findViewById<TextInputLayout>(R.id.textInputLayoutTableName)
            val textView = view.findViewById<TextInputEditText>(R.id.textInputName)

            textInputLayout.placeholderText = "Column $i"
            textInputLayout.hint = textInputLayout.placeholderText

            textViews.add(textView)

            container.addView(view)
            */
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

        return Table(
            tableName,
            tableDescription,
            imageURL
        )
    }

    private fun loadIntents() {
        Log.e("ERROR", "LOAD")
        if (intent.hasExtra(INTENTCONSTS.tableName)) {
            tableName = intent.getStringExtra(INTENTCONSTS.tableName)
        }

        if (intent.hasExtra(INTENTCONSTS.tableDescription)) {
            tableDescription = intent.getStringExtra(INTENTCONSTS.tableDescription)
        }

        if (intent.hasExtra(INTENTCONSTS.imageURL)) {
            imageURL = intent.getStringExtra(INTENTCONSTS.imageURL)
        }

        if (intent.hasExtra(INTENTCONSTS.columnCount)) {
            columnCount = intent.getIntExtra(INTENTCONSTS.columnCount, -1)
            if (columnCount >= 0) {
                Log.e("ERROR", "Count $columnCount")
            } else {
                Log.e("ERROR", "ELSE2")
            }
        }
    }

    private fun onSuccessCallback(tableId: Long) {
        val intent = Intent(this, TableShowActivity::class.java)
        intent.putExtra(INTENTCONSTS.itemId, tableId)
        startActivity(intent)
    }

    fun saveTableColNames(view: View) {
        Log.e("ERROR", "saveTableColNames")

        for (textView in this.columnViews) {
            var input = textView.getTextInput()
            if (input == null || input.isEmpty()) {
                Toast.makeText(this, "Please Fill each Col. Name", Toast.LENGTH_SHORT).show()
                return
            }
        }

        Log.e("ERROR", "Counter: ${this.columnViews.size}")

        var table = createTable(this.tableName, this.tableDescription, this.imageURL)

        if (table == null) {
            Toast.makeText(this, "ERROR: Could not Create Table", Toast.LENGTH_SHORT).show()
            return
        }

        launch {
            val db = TablesDatabase(applicationContext).tableDao()
            val tableId = db.insert(table)

            for (textView in columnViews) {
                val colName = textView.getTextInput()
                val column =
                    Column(tableId, colName)
                db.insert(column)
            }

            onSuccessCallback(tableId)
        }


        /*
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
        }*/

    }
}
