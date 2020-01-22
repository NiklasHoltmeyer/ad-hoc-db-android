package de.hsos.ma.adhocdb

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import de.hsos.ma.adhocdb.ui.createtable.CreateTableFragment

class CreateTableActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.create_table_activity)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, CreateTableFragment.newInstance())
                .commitNow()
        }

        //actionbar
        val actionbar = supportActionBar
        //set actionbar title
        actionbar!!.title = "Create Table"
        //set back button
        actionbar.setDisplayHomeAsUpEnabled(true)
        actionbar.setDisplayHomeAsUpEnabled(true)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

}
