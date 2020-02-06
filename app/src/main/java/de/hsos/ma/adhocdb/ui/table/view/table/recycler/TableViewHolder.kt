package de.hsos.ma.adhocdb.ui.table.view.table.recycler

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import de.hsos.ma.adhocdb.R
import kotlinx.android.synthetic.main.view_table_list_item.view.*
import de.hsos.ma.adhocdb.entities.table.Table
import de.hsos.ma.adhocdb.ui.table.view.recycler.OnRecyclerItemClickListener

class TableViewHolder constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val tableImage = itemView.table_image
    private val tableTitle = itemView.table_title
    private val tableDescription = itemView.table_description

    fun bind(tableEntity: Table, onClick: OnRecyclerItemClickListener<Table>) {

        val requestOptions = RequestOptions()
            .placeholder(R.drawable.ic_launcher_background)
            .error(R.drawable.ic_launcher_background)

        Glide.with(itemView.context)
            .applyDefaultRequestOptions(requestOptions)
            .load(tableEntity.image)
            .into(tableImage)
        tableTitle.text = tableEntity.name
        tableDescription.text = tableEntity.description

        itemView.setOnClickListener{
            onClick.onItemClick(tableEntity, adapterPosition)
        }

        itemView.setOnLongClickListener{
            onClick.onItemLongClick(tableEntity, adapterPosition)
        }
    }

}