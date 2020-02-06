package de.hsos.ma.adhocdb.ui.table.view.recycler

interface OnRecyclerItemClickListener<T>{
    fun onItemClick(item: T, pos : Int)
    fun onItemLongClick(item: T, pos : Int) : Boolean
}