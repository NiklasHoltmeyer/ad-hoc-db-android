package de.hsos.ma.adhocdb.ui.TableShow

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import de.hsos.ma.adhocdb.R
import de.hsos.ma.adhocdb.ui.CONSTS

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
