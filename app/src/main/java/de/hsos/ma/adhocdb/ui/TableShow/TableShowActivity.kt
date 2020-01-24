package de.hsos.ma.adhocdb.ui.TableShow

import android.os.Bundle
import android.widget.LinearLayout
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import de.hsos.ma.adhocdb.R
import de.hsos.ma.adhocdb.ui.custom.compound.table.row.CellView


class TableShowActivity : AppCompatActivity() {
    private var  actionbar : ActionBar? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_table_show) //TODO falsche klasse
        loadIntentExtras()

        actionbar = supportActionBar
        if(actionbar != null){
            //set actionbar title
            actionbar!!.title = "Table View"
            //set back button
            actionbar!!.setDisplayHomeAsUpEnabled(true)
            actionbar!!.setDisplayHomeAsUpEnabled(true)
        }

        loadTable()
    }

    private fun loadTable(){
        val container = findViewById<LinearLayout>(R.id.linearLayout)

        val rowLength = 20
        val colLength = 5
        val cellLayoutParams = ConstraintLayout.LayoutParams(
            ConstraintLayout.LayoutParams.WRAP_CONTENT,
            ConstraintLayout.LayoutParams.WRAP_CONTENT
        )

        val columnLayoutParams = ConstraintLayout.LayoutParams(
            ConstraintLayout.LayoutParams.WRAP_CONTENT,
            ConstraintLayout.LayoutParams.WRAP_CONTENT
        )

        columnLayoutParams.width = calcDps(150f)

        for(column in 0 .. colLength){
            //Column Layout
            val columnLayout = LinearLayout(this)
            columnLayout.apply {
                orientation = LinearLayout.VERTICAL
                layoutParams = columnLayoutParams
            }

            for(row in 0 .. rowLength){
                //CELL
                val value = "$column, $row"
                val unit = ""
                val cell = CellView(
                    context = this,
                    value = value,
                    unit = unit,
                    pos_x = column,
                    pos_y = row,
                    dividerRight = (column < 1),
                    dividerLeft = false,
                    dividerTop = false,
                    dividerBottom = (row < 1)
                )

                cell.layoutParams = cellLayoutParams
                columnLayout.addView(cell)
            }

            container.addView(columnLayout)
        }
    }

    private inline fun calcDps(dps : Float) : Int{
        //Quelle: https://stackoverflow.com/a/5255256/5026265
        val scale = applicationContext.resources.displayMetrics.density
        return (dps * scale + 0.5f).toInt()
    }

    private fun loadIntentExtras() {
        return //todo
        /*
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
        */
    }
}

