package de.hsos.ma.adhocdb

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.drawable.Drawable
import android.text.TextPaint
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.material.button.MaterialButton

class AggregationDialogView : ConstraintLayout {
    var sumButtonOnClickListener: de.hsos.ma.adhocdb.OnClickListener? = null
    var avgButtonOnClickListener: de.hsos.ma.adhocdb.OnClickListener? = null
    constructor(context: Context,
                sumButtonOnClickListener: de.hsos.ma.adhocdb.OnClickListener,
                avgButtonOnClickListener: de.hsos.ma.adhocdb.OnClickListener) : super(context) {
        this.sumButtonOnClickListener = sumButtonOnClickListener
        this.avgButtonOnClickListener = avgButtonOnClickListener
        init(context)
    }

    private fun init(context: Context) {
        val inflater = context
            .getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val container = inflater.inflate(R.layout.view_dialog_aggregation_view, this)


        container.findViewById<MaterialButton>(R.id.sum_action).setOnClickListener{
            this.sumButtonOnClickListener?.onButtonClick()
        }

        container.findViewById<MaterialButton>(R.id.avg_action).setOnClickListener{
            this.avgButtonOnClickListener?.onButtonClick()
        }
    }
}


interface OnClickListener{
    fun onButtonClick(): Boolean
}
