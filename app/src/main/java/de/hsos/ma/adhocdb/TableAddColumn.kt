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
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class TableAddColumn : ConstraintLayout {
    var placeHolder: String = ""


    constructor(context: Context, placeHolder: String) : super(context) {
        this.placeHolder = placeHolder
        init(null, 0)
    }

    private fun init(attrs: AttributeSet?, defStyle: Int) {
        val inflater = context
            .getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        inflater.inflate(R.layout.view_input, this)

        val textLabelView = findViewById<TextInputLayout>(R.id.textInputLayoutTableName)
        textLabelView.placeholderText = placeHolder
        textLabelView.hint = placeHolder
    }

    public fun getTextInput() : String{
        return findViewById<TextInputEditText>(R.id.textInputName)?.text.toString()
    }
}
