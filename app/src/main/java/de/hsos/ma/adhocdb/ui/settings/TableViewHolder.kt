package de.hsos.ma.adhocdb.ui.settings

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import de.hsos.ma.adhocdb.R
import kotlinx.android.synthetic.main.layout_table_list_item.view.*
import todoordnenrecycler.TableEntity

class TableViewHolder constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {

    val blog_image = itemView.table_image
    val blog_title = itemView.table_title
    val blog_author = itemView.table_description

    fun bind(tableEntity: TableEntity) {

        val requestOptions = RequestOptions()
            .placeholder(R.drawable.ic_launcher_background)
            .error(R.drawable.ic_launcher_background)

        Glide.with(itemView.context)
            .applyDefaultRequestOptions(requestOptions)
            .load(tableEntity.image)
            .into(blog_image)
        blog_title.setText(tableEntity.name)
        blog_author.setText(tableEntity.description)
    }
}