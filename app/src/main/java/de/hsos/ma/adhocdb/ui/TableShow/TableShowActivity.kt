package de.hsos.ma.adhocdb.ui.TableShow

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.widget.LinearLayout
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.list.isItemChecked
import com.afollestad.materialdialogs.list.listItemsMultiChoice
import de.hsos.ma.adhocdb.R
import de.hsos.ma.adhocdb.entities.Cell
import de.hsos.ma.adhocdb.entities.Column
import de.hsos.ma.adhocdb.entities.Table
import de.hsos.ma.adhocdb.framework.persistence.database.TablesDatabase
import de.hsos.ma.adhocdb.ui.BaseCoroutine
import de.hsos.ma.adhocdb.ui.CONSTS
import de.hsos.ma.adhocdb.ui.custom.compound.table.row.CellView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class TableShowActivity : BaseCoroutine(R.layout.activity_table_show, "Table View", true) {
    private var table: Table? = null
    private var columns: List<Column> = emptyList()
    private var colDTOs: List<ColumnDTO> = emptyList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_table_show) //TODO falsche klasse

        loadIntentExtras()
    }

    private fun drawTable() {
        if (table == null) {
            Toast.makeText(this, "ERROR: Table Nullpointer", Toast.LENGTH_LONG)
            return
        }

        val container = findViewById<LinearLayout>(R.id.linearLayout)
        container.removeAllViews()

        val cellLayoutParams = ConstraintLayout.LayoutParams(
            ConstraintLayout.LayoutParams.WRAP_CONTENT,
            ConstraintLayout.LayoutParams.WRAP_CONTENT
        )

        val columnLayoutParams = ConstraintLayout.LayoutParams(
            ConstraintLayout.LayoutParams.WRAP_CONTENT,
            ConstraintLayout.LayoutParams.WRAP_CONTENT
        )

        columnLayoutParams.width = calcDps(150f)

        for ((x, col) in this.colDTOs.withIndex()) {
            if (col.visible) drawColumn(columnLayoutParams, col, x, cellLayoutParams, container)
        }
    }

    private fun drawColumn(
        columnLayoutParams: ConstraintLayout.LayoutParams,
        col: ColumnDTO,
        x: Int,
        cellLayoutParams: ConstraintLayout.LayoutParams,
        container: LinearLayout
    ) {
        val columnLayout = LinearLayout(this)

        columnLayout.apply {
            orientation = LinearLayout.VERTICAL
            layoutParams = columnLayoutParams
        }

        val headerCell = CellView(
            context = this,
            value = col.colName,
            unit = "",
            pos_x = x,
            pos_y = 0,
            dividerRight = (colDTOs.size != x),
            dividerLeft = false,
            dividerTop = false,
            dividerBottom = true
        )

        headerCell.layoutParams = cellLayoutParams
        columnLayout.addView(headerCell)

        for ((y, cell) in col.cells.withIndex()) {
            drawCell(cell, x, y, cellLayoutParams, columnLayout)
        }

        container.addView(columnLayout)
    }

    private fun drawCell(
        cell: Cell,
        x: Int,
        y: Int,
        cellLayoutParams: ConstraintLayout.LayoutParams,
        columnLayout: LinearLayout
    ) {
        val cellView = CellView(
            context = this,
            value = cell.value,
            unit = cell.type,
            pos_x = x,
            pos_y = y,
            dividerRight = (x < 0),
            dividerLeft = false,
            dividerTop = false,
            dividerBottom = false
        )
        cellView.layoutParams = cellLayoutParams
        columnLayout.addView(cellView)
    }

    private inline fun calcDps(dps: Float): Int {
        //Quelle: https://stackoverflow.com/a/5255256/5026265
        val scale = applicationContext.resources.displayMetrics.density
        return (dps * scale + 0.5f).toInt()
    }

    private fun loadIntentExtras() {
        if (!intent.hasExtra(CONSTS.itemId)) {
            Toast.makeText(this, "Couldnt Retriev TableID", Toast.LENGTH_LONG)
            return
        }

        val tableId = intent.getLongExtra(CONSTS.itemId, -1)
        if (tableId < 0) {
            Toast.makeText(this, "Couldnt Retriev TableID", Toast.LENGTH_LONG)
            return
        }

        launch {
            val db = TablesDatabase(applicationContext).tableDao()

            val tableId: String = tableId.toString()
            val table = db.getTableById(tableId)
            val columns = db.getColumnsByTableId(tableId)


            val colDTOs = columns.map {
                ColumnDTO(it.name, db.getCellsByTableIdandColumnId(tableId, it.id.toString()), true)
            }.toList()

            Log.e("ERROR", "ColDTOS ${colDTOs.size}")
            for (colDTO in colDTOs) {
                Log.e("ERROR", colDTO.toString())
            }

            callBack(table, columns, colDTOs)
        }
    }

    private fun callBack(
        table: Table?,
        columns: List<Column>,
        colDTOs: List<ColumnDTO>
    ) {
        this.table = table
        this.columns = columns
        this.colDTOs = colDTOs
        //TODO else filtern welche columns angezeigt werden sollen

        launch(Dispatchers.Main) {
            drawTable()
        }

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        menu.findItem(R.id.action_search_with_query).isVisible = false
        var searchView = menu.findItem(R.id.action_search)
            .setOnMenuItemClickListener { filterTable() }
        searchView.isVisible = true

        menu.findItem(R.id.action_add)
            .setOnMenuItemClickListener { addDataSet() }
        return true
    }

    fun filterTable(): Boolean {
        val myItems = this.colDTOs.map { it.colName }.toList()

        MaterialDialog(this).show {
            listItemsMultiChoice(items = myItems)
            negativeButton(R.string.cancel)
            positiveButton(R.string.submit) {
                for (i in colDTOs.indices) {
                    colDTOs[i].visible = isItemChecked(i)
                }
                drawTable()
            }
        }
        return true
    }

    fun addDataSet(): Boolean {
        val tableID = table?.id
        if (tableID == null) {
            Toast.makeText(this, "Table ID NPE", Toast.LENGTH_LONG)
            return true
        } else {
            val intent = Intent(this@TableShowActivity, TableAddDataSet::class.java)
            intent.putExtra(CONSTS.itemId, tableID)
            startActivity(intent)
        }
        return true
    }
}

data class ColumnDTO(
    val colName: String,
    val cells: List<Cell>,
    var visible: Boolean
)