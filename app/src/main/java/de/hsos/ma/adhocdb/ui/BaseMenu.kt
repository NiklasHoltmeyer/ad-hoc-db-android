package de.hsos.ma.adhocdb.ui

import android.content.Intent
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import de.hsos.ma.adhocdb.R
import de.hsos.ma.adhocdb.ui.images.home.ImagesHomeActivity
import de.hsos.ma.adhocdb.ui.notes.home.NotesHomeActivity
import de.hsos.ma.adhocdb.ui.table.home.TableHomeActivity

abstract class BaseMenu(open val selectedMenuItem: Int) : AppCompatActivity() {
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        super.onCreateOptionsMenu(menu)

        val bottomNav = findViewById<BottomNavigationView>(R.id.bottom_nav)

        bottomNav?.selectedItemId = selectedMenuItem

        bottomNav?.setOnNavigationItemSelectedListener {
            onOptionsItemSelected(it)
        }

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_table -> openTable()
            R.id.nav_images -> openImages()
            R.id.nav_notes -> openNotes()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun openNotes(): Boolean {
        val intent = Intent(this, NotesHomeActivity::class.java)
        startActivity(intent)
        return true
    }

    private fun openImages(): Boolean {
        Toast.makeText(this, "WUHU", Toast.LENGTH_LONG)
        val intent = Intent(this, ImagesHomeActivity::class.java)
        startActivity(intent)
        return true
    }

    private fun openTable(): Boolean {
        val intent = Intent(this, TableHomeActivity::class.java)
        startActivity(intent)
        return true
    }
}

