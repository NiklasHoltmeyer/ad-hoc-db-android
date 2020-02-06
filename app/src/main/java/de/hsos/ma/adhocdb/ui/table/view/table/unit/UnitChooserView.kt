package de.hsos.ma.adhocdb.ui.table.view.table.unit

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.ArrayAdapter
import android.widget.FrameLayout
import android.widget.Spinner
import de.hsos.ma.adhocdb.R


class UnitChooserView : FrameLayout {
    var container: View? = null

    constructor(context: Context, spinnerArray: Int) : super(context) {
        init(null, 0, spinnerArray)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(attrs, 0, null)
    }

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(
        context,
        attrs,
        defStyle
    ) {
        init(attrs, defStyle, null)
    }

    private fun init(attrs: AttributeSet?, defStyle: Int, spinnerArray: Int?) {
        val inflater = context
            .getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        container = inflater.inflate(R.layout.view_unit_chooser, this)

        var arrayAdapter: ArrayAdapter<CharSequence>? = null

        if (spinnerArray != null) {
            arrayAdapter = ArrayAdapter.createFromResource(
                context,
                spinnerArray,
                android.R.layout.simple_spinner_item
            )
        } else if (attrs != null) {
            val styleAttributes = context.obtainStyledAttributes(attrs,
                R.styleable.UnitChooserView
            )
            if (styleAttributes.hasValue(R.styleable.UnitChooserView_array)) {
                var spinnerArray = styleAttributes.getTextArray(R.styleable.UnitChooserView_array)
                arrayAdapter = ArrayAdapter(
                    context,
                    android.R.layout.simple_spinner_item,
                    spinnerArray
                )
            }
        }

        if (arrayAdapter != null) initSpinner(arrayAdapter)
    }

    private fun initSpinner(arrayAdapter: ArrayAdapter<CharSequence>) {
        val spinner = container?.findViewById<Spinner>(R.id.spinner)
        arrayAdapter.also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            spinner?.adapter = adapter
        }
    }

    public fun getSpinnerValue(): String {
        return container?.findViewById<Spinner>(R.id.spinner)?.selectedItem.toString()
    }
}
