package de.hsos.ma.adhocdb.ui.createtable

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.LinearLayout
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.material.textfield.TextInputLayout
import de.hsos.ma.adhocdb.R
import de.hsos.ma.adhocdb.entities.Table
import de.hsos.ma.adhocdb.framework.persistence.database.TablesDatabase
import de.hsos.ma.adhocdb.ui.BaseCoroutine
import de.hsos.ma.adhocdb.ui.CONSTS
import de.hsos.ma.adhocdb.ui.homescreen.MainActivity
import kotlinx.coroutines.launch

class CreateTableColumnNamesActivity : BaseCoroutine(R.layout.activity_create_table, "Set Column Names", true) {
    var columnCount : Int = 5//-1
    var tableName = ""
    var tableDescription = ""
    var imageURL = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_table_column_names)

        loadIntents()
        displayInputs()
    }

    private fun displayInputs() {
        if(columnCount >= 0){
            initLayout()
        }else{
            Toast.makeText(this, "Error, Please go back", Toast.LENGTH_LONG)
        }
    }

    private fun initLayout() {
        val container = findViewById<LinearLayout>(R.id.columnNameInputContainer)

        val inflater = applicationContext
            .getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = inflater.inflate(R.layout.view_input, LinearLayout(this))
        container.addView(view)


        /*

        var param = LinearLayout.LayoutParams(ConstraintLayout.LayoutParams.WRAP_CONTENT,
            ConstraintLayout.LayoutParams.WRAP_CONTENT)
        param.marginEnd = calcDps(10f)
        param.marginStart = calcDps(10f)


        for(i in 1 .. columnCount){
            //com.google.android.material.textfield
            var textLayout = TextInputLayout(this)
            textLayout.apply{
                layoutParams = param
            }



        }
        */
    }

    fun createTable(colNames : List<String>) : Table?{
        if(tableName == null || tableName.isEmpty() ||
            tableDescription == null || tableDescription.isEmpty() ||
            imageURL == null || imageURL.isEmpty() ||
            colNames == null || colNames.isEmpty()){
            Toast.makeText(this, "[ERROR] Data-Null or Empty", Toast.LENGTH_LONG)
            return null
        }

        return Table(tableName, tableDescription, imageURL, colNames)
    }

    fun loadIntents(){
        Log.e("ERROR", "LOAD")
        if(intent.hasExtra(CONSTS.tableName)){
            tableName = intent.getStringExtra(CONSTS.tableName)
        }

        if(intent.hasExtra(CONSTS.tableDescription)){
            tableDescription = intent.getStringExtra(CONSTS.tableDescription)
        }

        if(intent.hasExtra(CONSTS.imageURL)){
            imageURL = intent.getStringExtra(CONSTS.imageURL)
        }

        if(intent.hasExtra(CONSTS.columnCount)){
            columnCount = intent.getIntExtra(CONSTS.columnCount, -1)
            if(columnCount >= 0){
                Log.e("ERROR", "Count $columnCount")
            }else{
                Log.e("ERROR", "ELSE2")
            }
        }
    }

    private inline fun calcDps(dps : Float) : Int{
        //Quelle: https://stackoverflow.com/a/5255256/5026265
        val scale = applicationContext.resources.displayMetrics.density
        return (dps * scale + 0.5f).toInt()
    }

     private fun saveTable(table : Table){ //TODO callback function
        launch{
            TablesDatabase(applicationContext).tableDao().insert(table)
            onSuccessCallback()
        }
    }

    private fun onSuccessCallback(){
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent);
    }
}
