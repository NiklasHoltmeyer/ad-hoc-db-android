package de.hsos.ma.adhocdb.ui.tablelist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import de.hsos.ma.adhocdb.R
import de.hsos.ma.adhocdb.ui.settings.TableViewHolder
import de.hsos.ma.adhocdb.entities.TableEntity

class TableRecyclerAdapter(var onClick: OnTableClickListener, var items: List<TableEntity>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return TableViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.layout_table_list_item,
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
}