package de.hsos.ma.adhocdb.ui.tablelist

import de.hsos.ma.adhocdb.entities.Table

interface OnTableClickListener{
    fun onItemClick(item: Table, pos : Int)
}