package de.hsos.ma.adhocdb.ui.custom.compound.table.row

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import de.hsos.ma.adhocdb.R
import kotlinx.android.synthetic.main.view_table_cell.view.*

class RowView : ConstraintLayout{
    constructor(context: Context) : super(context) {
        init(context, null)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(context, attrs)
    }

    private fun init(context: Context, attrs: AttributeSet?) {
        val inflater = context
            .getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        inflater.inflate(R.layout.view_table_column, this)
        if (attrs != null) {
            val styledattrs = context.obtainStyledAttributes(attrs, R.styleable.ColumnView)

            if(styledattrs.hasValue(R.styleable.ColumnView_firstCol) && styledattrs.getBoolean(R.styleable.ColumnView_firstCol, false)){
                findViewById<CellView>(R.id.header).dividerRight.visibility = View.VISIBLE
            }

            if(styledattrs.hasValue(R.styleable.ColumnView_lastCol) && styledattrs.getBoolean(R.styleable.ColumnView_lastCol, false)){
                findViewById<CellView>(R.id.header).dividerRight.visibility = View.INVISIBLE
            }

        }

    }
}