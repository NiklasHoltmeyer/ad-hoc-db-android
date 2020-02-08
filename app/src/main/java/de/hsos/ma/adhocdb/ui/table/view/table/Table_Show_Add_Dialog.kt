package de.hsos.ma.adhocdb.ui.table.view.table

import android.content.Context
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.material.button.MaterialButton
import de.hsos.ma.adhocdb.R

class Table_Show_Add_Dialog : ConstraintLayout {
    val onAddRowClick: de.hsos.ma.adhocdb.ui.table.view.table.OnClickListener
    val onAddColumnClick: de.hsos.ma.adhocdb.ui.table.view.table.OnClickListener

    constructor(
        context: Context,
        onAddRowClick: de.hsos.ma.adhocdb.ui.table.view.table.OnClickListener,
        onAddColumnClick: de.hsos.ma.adhocdb.ui.table.view.table.OnClickListener
    ) : super(context) {
        this.onAddRowClick = onAddRowClick
        this.onAddColumnClick = onAddColumnClick

        init(context)
    }

    private fun init(attrs: Context) {
        val inflater = context
            .getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val container = inflater.inflate(R.layout.view_dialog_table_edit_add, this)

        container.findViewById<MaterialButton>(R.id.change_table_add_data_set).setOnClickListener {
            this.onAddRowClick.onButtonClick()
        }

        container.findViewById<MaterialButton>(R.id.change_table_add_column).setOnClickListener {
            this.onAddColumnClick.onButtonClick()
        }

    }
}

interface OnClickListener{
    fun onButtonClick(): Boolean
}
