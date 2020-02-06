package de.hsos.ma.adhocdb.ui.table.view.table.column

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import de.hsos.ma.adhocdb.R
//
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
