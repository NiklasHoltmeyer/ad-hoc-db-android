package de.hsos.ma.adhocdb.ui.createtable

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.LinearLayout
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.material.textfield.TextInputLayout
import de.hsos.ma.adhocdb.R
import de.hsos.ma.adhocdb.ui.BaseCoroutine
import de.hsos.ma.adhocdb.ui.CONSTS

class CreateTableColumnNamesActivity : BaseCoroutine(R.layout.activity_create_table, "Set Column Names", true) {
    var id : Long = 10//-1
    var columnCount : Int = 5//-1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_table_column_names)

        //loadIntents()
        displayInputs()
    }

    private fun displayInputs() {
        if(id >= 0 && columnCount >= 0){
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

    fun loadIntents(){
        Log.e("ERROR", "LOAD")
        if(intent.hasExtra(CONSTS.itemId)){
            id = intent.getLongExtra(CONSTS.itemId, -1)
            if(id >= 0){
                Log.e("ERROR", "ID $id")
            }else{
                Log.e("ERROR", "ELSE")
            }
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
}
