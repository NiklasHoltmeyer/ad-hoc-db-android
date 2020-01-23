package de.hsos.ma.adhocdb.ui.tablelist

import de.hsos.ma.adhocdb.entities.TableEntity

interface OnTableClickListener{
    fun onItemClick(item: TableEntity, pos : Int)
}