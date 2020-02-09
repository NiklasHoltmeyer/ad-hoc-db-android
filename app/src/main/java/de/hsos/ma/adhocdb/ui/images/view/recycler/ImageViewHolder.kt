package de.hsos.ma.adhocdb.ui.images.view.recycler

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import de.hsos.ma.adhocdb.R
import de.hsos.ma.adhocdb.entities.image.Image
import de.hsos.ma.adhocdb.ui.table.view.recycler.OnRecyclerItemClickListener
import kotlinx.android.synthetic.main.view_image_list_item.view.*

class ImageViewHolder constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val image = itemView.imageView

    fun bind(imageEntity: Image, onClick: OnRecyclerItemClickListener<Image>) {
        val requestOptions = RequestOptions()
            .placeholder(R.drawable.ic_launcher_background)
            .error(R.drawable.ic_launcher_background)

        val imageToLoad =
            if (imageEntity.absolutePath.contains("media")) imageEntity.absolutePath else imageEntity.absolutePath.toInt()
        //.contains("/") => real pic (storage/emulated/0/Android/media/de.hsos.ma.adhocdb/1581269245757.jpg) || else => ressource dummy picture => R.id. ... = Int

        Glide.with(itemView.context)
            .applyDefaultRequestOptions(requestOptions)
            .load(imageToLoad)
            .into(image)

        itemView.setOnClickListener {
            onClick.onItemClick(imageEntity, adapterPosition)
        }

        itemView.setOnLongClickListener {
            onClick.onItemLongClick(imageEntity, adapterPosition)
        }
    }
}
