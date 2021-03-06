package de.hsos.ma.adhocdb.ui.images.show

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.github.chrisbanes.photoview.PhotoView
import de.hsos.ma.adhocdb.R
import de.hsos.ma.adhocdb.entities.image.Image
import de.hsos.ma.adhocdb.framework.persistence.database.ImagesDatabase
import de.hsos.ma.adhocdb.framework.persistence.database.TablesDatabase
import de.hsos.ma.adhocdb.ui.BaseCoroutineBaseMenuAppCompactActivity
import de.hsos.ma.adhocdb.ui.INTENTCONSTS
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File

class ImageShowActivity : BaseCoroutineBaseMenuAppCompactActivity(
    R.layout.activity_image_show,
    R.string.image_show,
    showBackButton = true,
    selectedMenuItem = R.id.nav_images
) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val id = if(intent.hasExtra(INTENTCONSTS.itemId)) intent.getLongExtra(INTENTCONSTS.itemId, -1L) else -1L
        if(id >= 0){
            loadImage(id)
        }
    }

    private fun loadImage(id: Long) {
        launch{
            val db = ImagesDatabase(applicationContext).imageDao()
            val image = db.getImageById(id.toString())

            launch(Dispatchers.Main){
                initImageView(image)
            }
        }
    }

    private fun initImageView(image: Image) {
        val photoView = findViewById<PhotoView>(R.id.photo_view)

        if (image.absolutePath.contains("media")){
            photoView.setImageURI(Uri.fromFile(File(image.absolutePath)))
        }else{
            photoView.setImageResource(image.absolutePath.toInt())
        }
    }
}
