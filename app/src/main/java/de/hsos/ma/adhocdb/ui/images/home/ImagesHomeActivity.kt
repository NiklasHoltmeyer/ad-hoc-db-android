package de.hsos.ma.adhocdb.ui.images.home

import android.os.Bundle
import de.hsos.ma.adhocdb.R
import de.hsos.ma.adhocdb.ui.BaseCoroutineBaseMenuAppCompactActivity

class ImagesHomeActivity : BaseCoroutineBaseMenuAppCompactActivity(R.layout.activity_images_home, R.string.image_home, showBackButton = false, selectedMenuItem = R.id.nav_images) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }
}
