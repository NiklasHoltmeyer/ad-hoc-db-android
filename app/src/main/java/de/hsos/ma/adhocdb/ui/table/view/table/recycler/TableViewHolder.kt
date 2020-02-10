package de.hsos.ma.adhocdb.ui.table.view.table.recycler

import android.util.Log
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import de.hsos.ma.adhocdb.R
import kotlinx.android.synthetic.main.view_table_list_item.view.*
import de.hsos.ma.adhocdb.entities.table.Table
import de.hsos.ma.adhocdb.ui.table.view.recycler.OnRecyclerItemClickListener
import java.lang.Exception

class TableViewHolder constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val tableImage = itemView.table_image
    private val tableTitle = itemView.table_title
    private val tableDescription = itemView.table_description

    fun bind(tableEntity: Table, onClick: OnRecyclerItemClickListener<Table>) {

        val requestOptions = RequestOptions()
            .placeholder(R.drawable.ic_launcher_background)
            .error(R.drawable.ic_launcher_background)


        var imageToLoad  = if(tableEntity.image.contains("media")) tableEntity.image else {
            try{
                tableEntity.image.toInt()
            }catch (exception: Exception){
                tableEntity.image
            }
        }

        Glide.with(itemView.context)
            .applyDefaultRequestOptions(requestOptions)
            .load(imageToLoad)
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