package de.hsos.ma.adhocdb.ui.table.view.table.recycler

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import de.hsos.ma.adhocdb.R
import de.hsos.ma.adhocdb.entities.table.Table
import de.hsos.ma.adhocdb.ui.table.view.recycler.OnRecyclerItemClickListener

class TableRecyclerAdapter(var onClick: OnRecyclerItemClickListener<Table>, var items: MutableList<Table>) : RecyclerView.Adapter<RecyclerView.ViewHolder>(), Filterable {
    var itemsAll : List<Table> = arrayListOf()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        itemsAll = items.toMutableList()
        return TableViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.view_table_list_item,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {

            is TableViewHolder -> {
                holder.bind(items[position], onClick)
            }

        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                var filterdItems : MutableList<Table> = arrayListOf()
                if(constraint != null && constraint.isNotEmpty()){
                    val filterPattern = constraint.toString().toLowerCase().trim()
                    itemsAll.forEach {
                        if(it.name.toLowerCase().contains(filterPattern)){
                            filterdItems.add(it)
                        }
                    }
                }else{
                    filterdItems.addAll(itemsAll)
                }
                var result = FilterResults()
                result.values = filterdItems
                return result
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                var filterdItems  = performFiltering(constraint).values as MutableList<Table>

                items.clear()
                items.addAll(filterdItems)

                notifyDataSetChanged()
            }

        }
    }
}