package de.hsos.ma.adhocdb

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatDialogFragment

class Dialog(val _context : Context) : AppCompatDialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(_context)
        builder.setTitle("Information")
            .setMessage("This is the message")
            .setPositiveButton("OK"){
                dialog, x ->
                this.onPositiveButtonCallBack(x)
            }
            .setNegativeButton("Cancel"){_, x ->
                onNegativButtonCallBack(x)
            }
        return builder.create()
    }

    fun onPositiveButtonCallBack(x: Int) {

    }

    fun onNegativButtonCallBack(x: Int) {

    }

}