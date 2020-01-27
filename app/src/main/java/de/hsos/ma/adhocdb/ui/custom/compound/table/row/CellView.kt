package de.hsos.ma.adhocdb.ui.custom.compound.table.row

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.material.card.MaterialCardView
import de.hsos.ma.adhocdb.R

class CellView : ConstraintLayout {
    private var value: String? = null
    private var unit: String? = null
    private var posX: Int? = null
    private var posY: Int? = null
    private var dividerRight: Boolean = false
    private var dividerLeft: Boolean = false
    private var dividerTop: Boolean = false
    private var dividerBottom: Boolean = false

    constructor(context: Context) : super(context) {
        init(context, null)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(context, attrs)
    }

    constructor(
        context: Context,
        value: String?,
        unit: String?,
        pos_x: Int?,
        pos_y: Int?,
        dividerRight: Boolean,
        dividerLeft: Boolean,
        dividerTop: Boolean,
        dividerBottom: Boolean
    ) : super(context) {
        this.value = value
        this.unit = unit
        this.posX = pos_x
        this.posY = pos_y
        this.dividerRight = dividerRight
        this.dividerLeft = dividerLeft
        this.dividerTop = dividerTop
        this.dividerBottom = dividerBottom
        init(context, null)
    }


    private fun init(context: Context, attrs: AttributeSet?) {
        Log.e("ERROR", "INIT")
        val inflater = context
            .getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        inflater.inflate(R.layout.view_table_cell, this)
        loadStyleAttributes(attrs, context)
        initDividers()
        initCell()
    }

    @SuppressLint("SetTextI18n")
    private fun initCell() {
        if (value != null && unit != null) {
            val textView = findViewById<TextView>(R.id.cellview_text)
            if(unit == null || unit!!.isEmpty()) textView.text = "$value"
            else textView.text = "$value $unit"
        }

        val card: MaterialCardView = findViewById(R.id.cellview_card_view)
        if (posX != null && posY != null && posX!! >= 0 && posY!! >= 0) {
            var rgb = ((posX!! * 25) + posY!! * 10) % 255
            card.setCardBackgroundColor(Color.rgb(rgb, rgb, rgb))

            if(rgb > 127) card.findViewById<TextView>(R.id.cellview_text)?.setTextColor(Color.BLACK)
            else card.findViewById<TextView>(R.id.cellview_text)?.setTextColor(Color.WHITE)
        }

        card.setOnLongClickListener {
            onLongClickCallBack(posX, posY)
        }
    }

    private fun initDividers() {
        if (dividerRight) initDivider(R.id.dividerRight)
        if (dividerBottom) initDivider(R.id.dividerBottom)
        if (dividerTop) initDivider(R.id.dividerTop)
        if (dividerLeft) initDivider(R.id.dividerLeft)
    }

    private fun loadStyleAttributes(attrs: AttributeSet?, context: Context) {
        if (attrs == null) return
        val styleAttributes = context.obtainStyledAttributes(attrs, R.styleable.CellView)
        if (styleAttributes.hasValue(R.styleable.CellView_unit)) {
            unit = styleAttributes.getString(R.styleable.CellView_unit)
        }
        if (styleAttributes.hasValue(R.styleable.CellView_value)) {
            value = styleAttributes.getString(R.styleable.CellView_value)
        }
        if (styleAttributes.hasValue(R.styleable.CellView_pos_x)) {
            posX = styleAttributes.getInteger(R.styleable.CellView_pos_x, -1)
        }
        if (styleAttributes.hasValue(R.styleable.CellView_pos_y)) {
            posY = styleAttributes.getInteger(R.styleable.CellView_pos_y, -1)
        }

        dividerRight = dividerRight || (styleAttributes.hasValue(R.styleable.CellView_divierRight) && styleAttributes.getBoolean(R.styleable.CellView_divierRight,false))
        dividerLeft = dividerLeft || (styleAttributes.hasValue(R.styleable.CellView_divierLeft) && styleAttributes.getBoolean(R.styleable.CellView_divierLeft,false))
        dividerBottom = dividerBottom || (styleAttributes.hasValue(R.styleable.CellView_divierBottom) && styleAttributes.getBoolean(R.styleable.CellView_divierBottom,false))
        dividerTop = dividerTop || (styleAttributes.hasValue(R.styleable.CellView_divierTop) && styleAttributes.getBoolean(R.styleable.CellView_divierTop,false))

        styleAttributes.recycle()
    }

    private fun initDivider(viewId: Int) {
        val view = findViewById<View>(viewId)
        view.visibility = View.VISIBLE
    }

    private fun onLongClickCallBack(posX: Int?, posY: Int?): Boolean {
        Log.e("ERROR", "POS_X $posX")
        Log.e("ERROR", "POS_Y $posY")
        return true
    }
}
