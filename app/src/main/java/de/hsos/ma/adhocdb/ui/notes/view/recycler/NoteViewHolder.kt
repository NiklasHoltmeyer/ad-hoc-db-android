package de.hsos.ma.adhocdb.ui.notes.view.recycler

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import de.hsos.ma.adhocdb.entities.note.Note
import de.hsos.ma.adhocdb.ui.table.view.recycler.OnRecyclerItemClickListener
import kotlinx.android.synthetic.main.view_note_list_item.view.*

class NoteViewHolder constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val noteTitle = itemView.note_title
    private val noteDescription = itemView.note_description

    fun bind(noteEntity: Note, onClick: OnRecyclerItemClickListener<Note>) {
        noteTitle.text = noteEntity.name
        noteDescription.text = noteEntity.description

        itemView.setOnClickListener {
            onClick.onItemClick(noteEntity, adapterPosition)
        }

        itemView.setOnLongClickListener {
            onClick.onItemLongClick(noteEntity, adapterPosition)
        }
    }
}