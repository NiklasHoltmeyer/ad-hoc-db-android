package de.hsos.ma.adhocdb

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.callbacks.onCancel

class TodoDialoagTestActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_todo_dialoag_test)
    }

    fun showDialoag(view: View) {
        MaterialDialog(this).show {
            cancelOnTouchOutside(false)
            title(R.string.create_table_dialog_header)
            message(R.string.create_table_dialog_body)
            positiveButton(R.string.dialog_aggree){
                dialoagPositiveCallBack()
            }
            negativeButton(R.string.dialog_disaggree){
                dialoagNegativeCallBack()
            }
        }
    }

    fun dialoagPositiveCallBack(){
        Log.e("ERROR", "POSITIVE DIALOG3")
        Toast.makeText(this, "POSITIVE3", Toast.LENGTH_SHORT)
    }

    fun dialoagNegativeCallBack(){
        Log.e("ERROR", "SHORT DIALOG2")
        Toast.makeText(this, "SHORT2", Toast.LENGTH_SHORT)
    }
}
