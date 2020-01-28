package de.hsos.ma.adhocdb.ui.table.home

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import de.hsos.ma.adhocdb.R
import de.hsos.ma.adhocdb.entities.Cell
import de.hsos.ma.adhocdb.entities.Column
import de.hsos.ma.adhocdb.framework.persistence.database.TablesDatabase
import de.hsos.ma.adhocdb.ui.BaseCoroutineAppCompactActivity
import de.hsos.ma.adhocdb.ui.INTENTCONSTS
import de.hsos.ma.adhocdb.ui.UNITCONSTS
import de.hsos.ma.adhocdb.ui.table.show.TableShowActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TableAddDataSet : BaseCoroutineAppCompactActivity(R.layout.activity_table_add_data_set, "Add Dataset", false) {
    var tableId: Long? = null
    var inputFields: ArrayList<TextInputEditText> = ArrayList()
    var spinners: ArrayList<Spinner> = ArrayList()
    var cols: List<Column> = emptyList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_table_add_data_set)

        loadIntents()
        setUpUI()
    }

    private fun setUpUI() {
        val container = findViewById<LinearLayout>(R.id.containerAddDataset) as LinearLayout
        val inflater = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        if(tableId ==null){
            Toast.makeText(this, "Error TableID NPE", Toast.LENGTH_LONG)
            return
        }

        //drawInput(inflater, container)
        launch {
            val db = TablesDatabase(applicationContext).tableDao()
            cols = db.getColumnsByTableId(tableId.toString())

            launch(Dispatchers.Main){
                for (col in cols) {
                    drawInput(inflater, container, col.name)
                }
            }
        }


    }

    private fun drawInput(
        inflater: LayoutInflater,
        container: LinearLayout,
        placeholder: String
    ) {
        val inputView = inflater.inflate(R.layout.view_input_add_dataset, LinearLayout(this))

        val label = inputView.findViewById<TextInputLayout>(R.id.textInputLayoutTableName)
        val inputField = inputView.findViewById<TextInputEditText>(R.id.textInputName)
        val spinner = inputView.findViewById<Spinner>(R.id.spinner)
        inputFields.add(inputField)
        spinners.add(spinner)

        ArrayAdapter.createFromResource(
            this,
            R.array.units_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            spinner.adapter = adapter
        }

        label.placeholderText = placeholder
        label.hint = label.placeholderText

        container.addView(inputView)
    }

    private fun loadIntents() {
        //INTENTCONSTS.itemId
        if (!intent.hasExtra(INTENTCONSTS.itemId)) {
            Toast.makeText(this, "Couldnt Retriev ID", Toast.LENGTH_LONG)
            return
        }
        tableId = intent.getLongExtra(INTENTCONSTS.itemId, -1)
        if (tableId!! < 0) tableId = null
    }

    fun goBackShowTable(){
        val intent = Intent(this, TableShowActivity::class.java)
        intent.putExtra(INTENTCONSTS.itemId, tableId)
        startActivity(intent)
    }

    fun onCancelButtonClick(view: View) {
        goBackShowTable()
    }
    fun onSubmitButtonClick(view: View) {
        if(inputFields.size != spinners.size || spinners.size != cols.size){
            Toast.makeText(this, "Error onSubmitButton != size", Toast.LENGTH_LONG)
        }

        if(tableId == null || tableId!! < 0) Toast.makeText(this, "Table Invalid ID", Toast.LENGTH_LONG)

        var result = ArrayList<Cell>()

        for((i, col) in cols.withIndex()){
            var value = inputFields[i].text.toString()
            var type = spinners[i].selectedItem.toString()
            type = UNITCONSTS.UNITS[type] ?: ""
            val cell = Cell(tableId!!, col.id, value, type)

            result.add(cell)
        }

        launch{
            val db = TablesDatabase(applicationContext).tableDao()
            db.insert(result)
            goBackShowTable()
        }

    }
}
