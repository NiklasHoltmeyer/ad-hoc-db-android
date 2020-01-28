package de.hsos.ma.adhocdb

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.material.button.MaterialButton

class Table_Show_Add_Dialog : ConstraintLayout {
    val onAddRowClick: de.hsos.ma.adhocdb.OnClickListener
    val onAddColumnClick: de.hsos.ma.adhocdb.OnClickListener

    constructor(
        context: Context,
        onAddRowClick: de.hsos.ma.adhocdb.OnClickListener,
        onAddColumnClick: de.hsos.ma.adhocdb.OnClickListener
    ) : super(context) {
        this.onAddRowClick = onAddRowClick
        this.onAddColumnClick = onAddColumnClick

        init(context)
    }

    private fun init(attrs: Context) {
        val inflater = context
            .getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val container = inflater.inflate(R.layout.view_dialog_edit_add, this)

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
