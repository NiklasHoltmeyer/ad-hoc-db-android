package de.hsos.ma.adhocdb.ui.images.view.recycler

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import de.hsos.ma.adhocdb.R
import de.hsos.ma.adhocdb.entities.image.Image
import de.hsos.ma.adhocdb.ui.images.view.recycler.ImageViewHolder
import de.hsos.ma.adhocdb.ui.table.view.recycler.OnRecyclerItemClickListener

class ImageRecyclerAdapter (var onClick: OnRecyclerItemClickListener<Image>, var items: MutableList<Image>) : RecyclerView.Adapter<RecyclerView.ViewHolder>(){
    var itemsAll : List<Image> = arrayListOf()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        itemsAll = items.toMutableList()
        return ImageViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.view_image_list_item,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {

            is ImageViewHolder -> {
                holder.bind(items[position], onClick)
            }

        }
    }

    override fun getItemCount(): Int {
        return items.size
    }
}