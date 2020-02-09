package de.hsos.ma.adhocdb.ui.table.view.recycler

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class RecyclerTopSpacingItemDecoration(
    private val topPadding: Int,
    private val leftPadding: Int = 0,
    private val rightPadding: Int = 0
) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)
        outRect.top = topPadding
        if (leftPadding != null) outRect.left = leftPadding
        if (rightPadding != null) outRect.right = rightPadding
    }
}