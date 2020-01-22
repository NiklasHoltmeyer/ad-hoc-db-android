package de.hsos.ma.adhocdb

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import de.hsos.ma.adhocdb.ui.createtable.CreateTableFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun addButtonOnClick (view: View){
        var intent = Intent(this, CreateTableActivity::class.java)
        startActivity(intent)
    }
}
