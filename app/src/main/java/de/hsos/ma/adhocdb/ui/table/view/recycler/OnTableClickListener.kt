package de.hsos.ma.adhocdb.ui.table.view.recycler

import de.hsos.ma.adhocdb.entities.Table

interface OnTableClickListener{
    fun onItemClick(item: Table, pos : Int)
    fun onItemLongClick(item: Table, pos : Int) : Boolean
}