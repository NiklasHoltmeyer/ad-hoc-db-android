package de.hsos.ma.adhocdb.ui.custom.compound.table.row

import android.content.Context
import android.content.res.TypedArray
import android.graphics.Color
import android.graphics.Typeface
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.material.card.MaterialCardView
import de.hsos.ma.adhocdb.R

class CellView : ConstraintLayout {
    constructor(context: Context) : super(context) {
        init(context, null)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(context, attrs)
    }


    private fun init(context: Context, attrs: AttributeSet?) {
        val inflater = context
            .getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        inflater.inflate(R.layout.view_table_cell, this)


        var value : String? = null
        var unit  : String? = null
        var pos_x : Int? = null
        var pos_y : Int? = null
///android:textStyle="bold"
        if (attrs != null) {
            val styledattrs = context.obtainStyledAttributes(attrs, R.styleable.CellView)
            if(styledattrs.hasValue(R.styleable.CellView_unit)){
                unit = styledattrs.getString(R.styleable.CellView_unit)
            }
            if(styledattrs.hasValue(R.styleable.CellView_value)){
                value = styledattrs.getString(R.styleable.CellView_value)
            }
            if(styledattrs.hasValue(R.styleable.CellView_pos_x)){
                pos_x = styledattrs.getInteger(R.styleable.CellView_pos_x, -1)
            }
            if(styledattrs.hasValue(R.styleable.CellView_pos_y)){
                pos_y = styledattrs.getInteger(R.styleable.CellView_pos_y, -1)
            }
            var styleables: Array<Int> = arrayOf(R.styleable.CellView_divierBottom, R.styleable.CellView_divierTop, R.styleable.CellView_divierLeft, R.styleable.CellView_divierRight)
            var viewIds: Array<Int>    = arrayOf(R.id.dividerBottom, R.id.dividerTop, R.id.dividerLeft, R.id.dividerRight)

            initDivider(styleables, viewIds, styledattrs)


            if(value != null && unit != null){
                val textView = findViewById<TextView>(R.id.cellview_text)
                /*if(unit.isEmpty()) textView.text = "$value"
                else */textView.text = "$value $unit"
            }

            var card : MaterialCardView = findViewById<MaterialCardView>(R.id.cellview_card_view)
            if(pos_x != null && pos_y != null && pos_x >= 0 && pos_y >= 0){
                val r = (pos_x * 20) %255
                val g = (pos_y * 20) %255
                val b = (pos_x * pos_y * 20) %255
                card.setCardBackgroundColor(Color.rgb(r,g,b))
            }

            card.setOnLongClickListener{
                onLongClickCallBack(pos_x, pos_y)
            }
            styledattrs.recycle()
        }
    }

    private fun initDivider(styleables: Array<Int>, viewIds: Array<Int>, styledattrs : TypedArray){
        for(i in 0 .. styleables.size){
            if(styledattrs.hasValue(styleables[i])){
                if(styledattrs.getBoolean(styleables[i], false)){
                    val view = findViewById<View>(viewIds[i])
                    view.visibility = View.VISIBLE
                }
            }
        }
    }

    private fun onLongClickCallBack(posX: Int?, posY: Int?): Boolean {
        Log.e("ERROR", "POS_X $posX")
        Log.e("ERROR", "POS_Y $posY")
        return true
    }
}
