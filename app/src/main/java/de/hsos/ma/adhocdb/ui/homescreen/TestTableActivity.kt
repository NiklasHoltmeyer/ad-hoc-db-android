package de.hsos.ma.adhocdb.ui.homescreen

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import de.hsos.ma.adhocdb.R

class TestTableActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test_table)

        //actionbar
        val actionbar = supportActionBar
        //set actionbar title
        actionbar!!.title = "TEST Table"
        //set back button
        actionbar.setDisplayHomeAsUpEnabled(true)
        actionbar.setDisplayHomeAsUpEnabled(true)
    }
}
