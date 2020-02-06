package de.hsos.ma.adhocdb.ui.notes.home

import android.os.Bundle
import de.hsos.ma.adhocdb.R
import de.hsos.ma.adhocdb.ui.BaseCoroutineBaseMenuAppCompactActivity

class NotesHomeActivity : BaseCoroutineBaseMenuAppCompactActivity(R.layout.activity_notes_home, R.string.notes_home, showBackButton = false,selectedMenuItem = R.id.nav_notes) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notes_home)
    }
}
