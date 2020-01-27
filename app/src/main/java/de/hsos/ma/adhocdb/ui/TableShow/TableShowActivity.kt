package de.hsos.ma.adhocdb.ui.TableShow

import android.os.Bundle
import android.util.Log
import android.widget.LinearLayout
import android.widget.Toast
import androidx.annotation.MainThread
import androidx.appcompat.app.ActionBar
import androidx.constraintlayout.widget.ConstraintLayout
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
    private var actionbar: ActionBar? = null
    private var table: Table? = null
    private var columns: List<Column> = emptyList()
    private var columnsToDraw: List<Column> = emptyList()
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

        val cellLayoutParams = ConstraintLayout.LayoutParams(
            ConstraintLayout.LayoutParams.WRAP_CONTENT,
            ConstraintLayout.LayoutParams.WRAP_CONTENT
        )

        val columnLayoutParams = ConstraintLayout.LayoutParams(
            ConstraintLayout.LayoutParams.WRAP_CONTENT,
            ConstraintLayout.LayoutParams.WRAP_CONTENT
        )

        columnLayoutParams.width = calcDps(150f)

        for((x, col) in this.colDTOs.withIndex()){
            drawColumn(columnLayoutParams, col, x, cellLayoutParams, container)
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
            dividerRight = (columnsToDraw.size != x),
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
                ColumnDTO(it.name, db.getCellsByTableIdandColumnId(tableId, it.id.toString()))
            }.toList()

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
        if (!intent.hasExtra(CONSTS.columnsToBeDrawn)) {
            this.columnsToDraw = columns
        }
        //TODO else filtern welche columns angezeigt werden sollen

        launch(Dispatchers.Main){
            drawTable()
        }

    }
}

data class ColumnDTO(
    val colName: String,
    val cells: List<Cell>
)