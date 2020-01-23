package de.hsos.ma.adhocdb.ui.TableShow

import android.os.Bundle
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintSet
import de.hsos.ma.adhocdb.R
import de.hsos.ma.adhocdb.ui.CONSTS
import de.hsos.ma.adhocdb.ui.custom.compound.table.row.CellView


class TableShowActivity : AppCompatActivity() {
    var  actionbar : ActionBar? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_table_show)
        loadIntentExtras()

        actionbar = supportActionBar
        if(actionbar != null){
            //set actionbar title
            actionbar!!.title = "Table View"
            //set back button
            actionbar!!.setDisplayHomeAsUpEnabled(true)
            actionbar!!.setDisplayHomeAsUpEnabled(true)
        }

        loadData()
    }

    private fun loadData() {
        val linearLayout = findViewById<LinearLayout>(R.id.linearLayout)

        for(x in 0 .. 5){
            val _linearLayout = LinearLayout(this)

            _linearLayout.apply {
                orientation = LinearLayout.VERTICAL
                layoutParams.height = LinearLayout.LayoutParams.MATCH_PARENT
                layoutParams.width = LinearLayout.LayoutParams.MATCH_PARENT
            }

            linearLayout.addView(_linearLayout)

            for(y in 0 .. 5){
                val cell = CellView(this)
                _linearLayout.addView(cell)
            }
        }




    }

    private fun loadIntentExtras() {
        val intent = intent
        if(!intent.hasExtra(CONSTS.itemId)){
            Toast.makeText(this, "Couldnt Retriev Table", Toast.LENGTH_SHORT).show()
            return
        }
        val id = intent.getIntExtra(CONSTS.itemId, -1)

        if(id < 0){
            Toast.makeText(this, "Invalid Table ID", Toast.LENGTH_SHORT).show()
            return
        }

        var tableName = ""
        var tableDescription = ""
        var tableImage = ""

        if(intent.hasExtra(CONSTS.itemName)
                && intent.hasExtra(CONSTS.itemImage)
                && intent.hasExtra(CONSTS.itemDescription)){
            tableName = intent.getStringExtra(CONSTS.itemName)
            tableImage = intent.getStringExtra(CONSTS.itemImage)
            tableDescription = intent.getStringExtra(CONSTS.itemDescription)
        }else{
            // haben ID also kann man von db anfragen
        }

        val tableTitleTextView = findViewById<TextView>(R.id.tableTitle)
        val tableDescriptionTextView = findViewById<TextView>(R.id.tableDescription)

        tableTitleTextView.text = tableName
        tableDescriptionTextView.text = tableDescription
        if(actionbar != null) actionbar!!.title = "tableName"
    }
}

