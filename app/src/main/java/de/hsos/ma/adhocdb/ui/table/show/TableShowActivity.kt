package de.hsos.ma.adhocdb.ui.table.show

import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.view.Menu
import android.widget.LinearLayout
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.customview.customView
import com.afollestad.materialdialogs.customview.getCustomView
import com.afollestad.materialdialogs.input.input
import com.afollestad.materialdialogs.list.isItemChecked
import com.afollestad.materialdialogs.list.listItems
import com.afollestad.materialdialogs.list.listItemsMultiChoice
import com.google.android.material.button.MaterialButton
import de.hsos.ma.adhocdb.AggregationDialogView
import de.hsos.ma.adhocdb.R
import de.hsos.ma.adhocdb.ui.table.view.table.unit.UnitChooserView
import de.hsos.ma.adhocdb.entities.table.Cell
import de.hsos.ma.adhocdb.entities.table.Column
import de.hsos.ma.adhocdb.entities.table.Table
import de.hsos.ma.adhocdb.framework.persistence.database.TablesDatabase
import de.hsos.ma.adhocdb.ui.BaseCoroutineBaseMenuAppCompactActivity
import de.hsos.ma.adhocdb.ui.INTENTCONSTS
import de.hsos.ma.adhocdb.ui.UNITCONSTS
import de.hsos.ma.adhocdb.ui.table.home.TableAddDataSet
import de.hsos.ma.adhocdb.ui.table.view.table.Table_Show_Add_Dialog
import de.hsos.ma.adhocdb.ui.table.view.table.cell.CellView
import de.hsos.ma.adhocdb.ui.table.view.table.cell.OnClickListener
import de.hsos.ma.adhocdb.ui.table.view.table.column.TableAddColumn
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TableShowActivity :
    BaseCoroutineBaseMenuAppCompactActivity(R.layout.activity_table_show, R.string.table_view, showBackButton = true, selectedMenuItem = R.id.nav_table){
    private var table: Table? = null
    private var columns: List<Column> = emptyList()
    private var colDTOs: List<ColumnDTO> = emptyList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

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
            value = col.col.name,
            unit = "",
            pos_x = x,
            pos_y = 0,
            dividerRight = (colDTOs.size != x),
            dividerLeft = false,
            dividerTop = false,
            dividerBottom = true,
            listener = object :
                OnClickListener {
                override fun onLongItemClick(posX: Int?, posY: Int?): Boolean {
                    editColumnDialog(col)
                    return true
                }

            }
        )

        headerCell.layoutParams = cellLayoutParams
        columnLayout.addView(headerCell)

        for ((y, cell) in col.cells.withIndex()) {
            drawCell(col, cell, x, y, cellLayoutParams, columnLayout)
        }

        container.addView(columnLayout)
    }

    private fun addColumn(table: Table, colName: String, colSize: Int) {
        launch{
            val db = TablesDatabase(applicationContext).tableDao()
            val colId = db.insert(
                Column(
                    table.id,
                    colName
                )
            )

            for(i in 1 .. colSize){
                db.insert(
                    Cell(
                        table.id,
                        colId,
                        "",
                        "",
                        (i - 1).toLong()
                    )
                )
            }

            reloadView()
        }
    }

    private fun deleteColumn(col: ColumnDTO) {
        launch{
            val db = TablesDatabase(applicationContext).tableDao()
            for (cell in col.cells) {
                db.delete(cell)
            }
            db.delete(col.col)
            reloadView()
        }
    }

    private fun updateColumn(col: Column) {
        launch {
            val db = TablesDatabase(applicationContext).tableDao()
            db.update(col)
            reloadView()
        }
    }

    private fun updateCell(cell: Cell) {
        launch {
            val db = TablesDatabase(applicationContext).tableDao()
            db.update(cell)
            reloadView()
        }
    }

    private fun deleteRow(cell: Cell){
        launch{
            val db =  TablesDatabase(applicationContext).tableDao()
            val tableID = table?.id.toString()

            db.getCellsTableIdAndRow(tableID, cell.row.toString()).forEach{
                db.delete(it)
            }

            reloadView()
        }
    }

    private fun editColumnDialog(col: ColumnDTO) {
        val dialog = MaterialDialog(this@TableShowActivity)
            .customView(R.layout.view_dialog_table_edit_column, scrollable = true)
        val customView = dialog.getCustomView()


        customView.findViewById<MaterialButton>(R.id.change_column_aggregation_sum).setOnClickListener {
            showAggregation(col, sumAggregationHelper())
        }

        customView.findViewById<MaterialButton>(R.id.change_column_aggregation_avg).setOnClickListener {
            showAggregation(col, averageAggregationHelper())
        }

        customView.findViewById<MaterialButton>(R.id.change_column_delete).setOnClickListener {
            deleteColumnWarningDialog(col)
            dialog.dismiss()
        }

        customView.findViewById<MaterialButton>(R.id.change_column_add).setOnClickListener {
            addColumnDialog(table, col.cells.size)
            dialog.dismiss()
        }

        customView.findViewById<MaterialButton>(R.id.change_column_name).setOnClickListener{
            changeColNameDialog(col.col)
            dialog.dismiss()
        }


        dialog.show()
    }

    private fun addColumnDialog(table: Table?, colSize: Int) {
        val view: TableAddColumn = TableAddColumn(this, getString(R.string.addColumn))
        MaterialDialog(this)
            .title(R.string.addColumn)
            .show {
                customView(view = view)
                negativeButton(R.string.cancel)
                positiveButton(R.string.submit){
                    if(table != null){
                        addColumn(table, view.getTextInput(), colSize)
                    }

                }
            }
    }

    private fun deleteColumnWarningDialog(col: ColumnDTO) {
        MaterialDialog(this@TableShowActivity).show {
            positiveButton(R.string.submit) {
                deleteColumn(col)
            }
            negativeButton(R.string.cancel)
            title(R.string.delete_column)
            message(R.string.delete_column_dialog_body)
        }
    }

    private fun changeColNameDialog(col: Column){
        val type = InputType.TYPE_CLASS_TEXT
        MaterialDialog(this)
            .title(R.string.editColumnName)
            .show {
                positiveButton(R.string.submit)
                negativeButton(R.string.cancel)
                input(allowEmpty = false, inputType = type, prefill = col.name) { dialog, text ->
                    col.name = text.toString()
                    updateColumn(col)
                }
            }
    }

    private fun changeCellValue(cell: Cell) {
        val type = InputType.TYPE_CLASS_TEXT
        MaterialDialog(this)
            .title(R.string.editCellValue)
            .show {
                positiveButton(R.string.submit)
                negativeButton(R.string.cancel)
                input(allowEmpty = false, inputType = type, prefill = cell.value) { dialog, text ->
                    cell.value = text.toString()
                    updateCell(cell)
                }
            }
    }

    private fun changeCellType(cell: Cell) {
        val chooserView = UnitChooserView(
            this,
            R.array.units_array
        )
        chooserView.layoutParams = ConstraintLayout.LayoutParams(
            ConstraintLayout.LayoutParams.WRAP_CONTENT,
            ConstraintLayout.LayoutParams.WRAP_CONTENT
        )

        MaterialDialog(this)
            .title(R.string.editCellType)
            .show {
                positiveButton(R.string.submit){
                    val spinnerValue = chooserView.getSpinnerValue()
                    val type = UNITCONSTS.UNITS[spinnerValue].orEmpty()
                    cell.type = type
                    updateCell(cell)
                }
                negativeButton(R.string.cancel)
                customView(view = chooserView)
            }
    }

    private fun changeCellDialog(cell: Cell) {
        val dialog = MaterialDialog(this@TableShowActivity)
            .customView(R.layout.view_dialog_table_edit_cell, scrollable = true)
        val customView = dialog.getCustomView()

        customView.findViewById<MaterialButton>(R.id.change_cell_value).setOnClickListener {
            changeCellValue(cell)
            dialog.dismiss()
        }

        customView.findViewById<MaterialButton>(R.id.change_cell_type).setOnClickListener {
            changeCellType(cell)
            dialog.dismiss()
        }

        customView.findViewById<MaterialButton>(R.id.chang_row_delete).setOnClickListener {
            changeRowDelete(cell)
            dialog.dismiss()
        }

        dialog.show()

    }

    private fun changeRowDelete(cell: Cell) {
        MaterialDialog(this)
            .title(R.string.editRowDelete)
            .show {
                message(R.string.editRowDeleteWarning)
                positiveButton(R.string.submit) {
                    deleteRow(cell)
                }
                negativeButton(R.string.cancel)
            }
    }

    private fun drawCell(
        col: ColumnDTO,
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
            dividerBottom = false,
            listener = object :
                OnClickListener {
                override fun onLongItemClick(posX: Int?, posY: Int?): Boolean {
                    changeCellDialog(cell)
                    return true
                }

            }
        )
        cellView.layoutParams = cellLayoutParams
        columnLayout.addView(cellView)
    }

    private fun loadIntentExtras() {
        if (!intent.hasExtra(INTENTCONSTS.itemId)) {
            Toast.makeText(this, "Couldnt Retriev TableID", Toast.LENGTH_LONG)
            return
        }

        val tableId = intent.getLongExtra(INTENTCONSTS.itemId, -1)
        if (tableId < 0) {
            Toast.makeText(this, "Couldnt Retriev TableID", Toast.LENGTH_LONG)
            return
        }

        loadColumns(tableId)
    }

    private fun loadColumns(tableId: Long) {
        launch {
            val db = TablesDatabase(applicationContext).tableDao()

            val tableId: String = tableId.toString()
            val table = db.getTableById(tableId)
            val columns = db.getColumnsByTableId(tableId)


            val colDTOs = columns.map {
                ColumnDTO(
                    it,
                    db.getCellsByTableIdandColumnId(tableId, it.id.toString()),
                    true
                )
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

        launch(Dispatchers.Main) {
            drawTable()
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        super.onCreateOptionsMenu(menu)
        if(menu==null) return false
        menuInflater.inflate(R.menu.filter_query_menu, menu)
        menu.findItem(R.id.action_search_with_query).isVisible = false
        var searchView = menu.findItem(R.id.action_search)
            .setOnMenuItemClickListener { filterTable() }
        searchView.isVisible = true

        var funcView = menu.findItem(R.id.action_func)
            .setOnMenuItemClickListener { openAggregationDialog() }
        funcView.isVisible = true

        menu.findItem(R.id.action_add)
            .setOnMenuItemClickListener { addButtonCallBack() }
        return true
    }

    private fun addButtonCallBack(): Boolean {
        val view = Table_Show_Add_Dialog(
            this@TableShowActivity,
            object : de.hsos.ma.adhocdb.ui.table.view.table.OnClickListener {
                override fun onButtonClick(): Boolean {
                    addDataSet()
                    return true
                }

            },
            object : de.hsos.ma.adhocdb.ui.table.view.table.OnClickListener {
                override fun onButtonClick(): Boolean {
                    addColumnDialog(table, this@TableShowActivity.colDTOs[0].cells.size)
                    return true
                }
            })

        val dialog = MaterialDialog(this@TableShowActivity)
            .title(R.string.add)
            .show {
                customView(view = view)
            }
        return true
    }

    private fun filterTable(): Boolean {
        val myItems = this.colDTOs.map { it.col.name }.toList()

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

    private fun addDataSet(): Boolean {
        val tableID = table?.id
        if (tableID == null) {
            Toast.makeText(this, "Table ID NPE", Toast.LENGTH_LONG)
            return true
        } else {
            val intent = Intent(this@TableShowActivity, TableAddDataSet::class.java)
            intent.putExtra(INTENTCONSTS.itemId, tableID)
            intent.putExtra(INTENTCONSTS.itemRow, this@TableShowActivity.colDTOs[0].cells.size)
            startActivity(intent)
        }
        return true
    }

    fun showAggregation(aggregationHelper: AggregationHelper){
        val items = this.colDTOs.map { it.col.name }.toList()
        MaterialDialog(this).show {
            listItems(items = items){ dialog, index, text ->
                val cells = this@TableShowActivity.colDTOs[index].cells
                val result = aggregationHelper.aggregate(cells)
                MaterialDialog(this@TableShowActivity).show {
                    title(R.string.aggregationTitle)
                    message(text = "Result: $result")
                }
            }
        }
    }

    fun showAggregation(col: ColumnDTO, aggregationHelper: AggregationHelper){
        val cells = col.cells
        val result = aggregationHelper.aggregate(cells)
        MaterialDialog(this@TableShowActivity).show {
            title(R.string.aggregationTitle)
            message(text = "Result: $result")
        }
    }



    fun onSumClick(){
        //showAggregation(null)
        val sumAggregationHelper = sumAggregationHelper()
        showAggregation(sumAggregationHelper)
    }

    private fun sumAggregationHelper(): AggregationHelper {
        return object : AggregationHelper {
            override fun aggregate(cells: List<Cell>): String {
                var f = 0f
                for (cell in cells) {
                    try {
                        f += cell.value.toFloat()
                    } catch (e: Exception) {
                    }
                }
                return (f).toString()
            }
        }
    }

    private fun averageAggregationHelper(): AggregationHelper {
        return object : AggregationHelper {
            override fun aggregate(cells: List<Cell>): String {
                var f = 0f
                var c = 0
                for (cell in cells) {
                    try {
                        f += cell.value.toFloat()
                        ++c
                    } catch (e: Exception) {
                    }
                }
                return (f / c).toString()
            }
        }
    }

    fun onAvgClick(){
        val avgAggregationHelper = averageAggregationHelper()

        showAggregation(avgAggregationHelper)
    }

    fun openAggregationDialog(): Boolean {
        val onSumButton = object: de.hsos.ma.adhocdb.OnClickListener {
            override fun onButtonClick(): Boolean {
                this@TableShowActivity.onSumClick()
                return true
            }
        }

        val onAvgButton = object: de.hsos.ma.adhocdb.OnClickListener {
            override fun onButtonClick(): Boolean {
                this@TableShowActivity.onAvgClick()
                return true
            }
        }


        val view = AggregationDialogView(this, onSumButton, onAvgButton)
        MaterialDialog(this@TableShowActivity)
            .show {
                customView(view = view)
            }

        return false
    }

}

data class ColumnDTO(
    val col: Column,
    val cells: List<Cell>,
    var visible: Boolean
)

interface AggregationHelper {
    fun aggregate(cells : List<Cell>) : String
}