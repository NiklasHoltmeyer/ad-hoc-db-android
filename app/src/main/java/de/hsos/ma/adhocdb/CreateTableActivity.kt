package de.hsos.ma.adhocdb

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.NumberPicker.OnValueChangeListener
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText
import de.hsos.ma.adhocdb.ui.createtable.CreateTableFragment


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
/*
        var np  : NumberPicker = findViewById(R.id.numberPicker)
        np.minValue = 2
        np.maxValue = 20

        np.setOnValueChangedListener(onValueChangeListener);*/
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
        Toast.makeText(getApplicationContext(),"CLEAR",Toast.LENGTH_SHORT).show()
        var clearAbl = arrayOf(findViewById<TextInputEditText>(R.id.textInputName).text,
            findViewById<TextInputEditText>(R.id.textInputDescription).text,
            findViewById<TextInputEditText>(R.id.textInputColumns).text)

        clearAbl.forEach { it?.clear() }
    }
    fun saveButtonOnClick(view: View) {
        var _tableName = findViewById<TextInputEditText>(R.id.textInputName).text
        var _tableDescription = findViewById<TextInputEditText>(R.id.textInputDescription).text
        var _tableColumnCount = findViewById<TextInputEditText>(R.id.textInputColumns).text

        if(_tableName==null || _tableName.isEmpty()){
            Toast.makeText(applicationContext,"Missing Table Name",Toast.LENGTH_SHORT).show()
        }else if(_tableDescription==null || _tableDescription.isEmpty()){
            Toast.makeText(applicationContext,"Missing Table Description",Toast.LENGTH_SHORT).show()
        }else if(_tableColumnCount==null || _tableColumnCount.isEmpty()){
            Toast.makeText(applicationContext,"Missing Table Column Count",Toast.LENGTH_SHORT).show()
        }else{
            var tableCount : Int = _tableColumnCount.toString().toInt()
            Log.e("ERROR", "Table-Name: $_tableName")
            Log.e("ERROR", "Table-Descr: $_tableDescription")
            Log.e("ERROR", "Table-Colm: $tableCount")
        }

        Log.e("ERROR", "HIHI")
    }
}
