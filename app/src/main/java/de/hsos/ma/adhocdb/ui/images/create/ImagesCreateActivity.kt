package de.hsos.ma.adhocdb.ui.images.create

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import com.camerakit.CameraKitView
import de.hsos.ma.adhocdb.R
import de.hsos.ma.adhocdb.entities.image.Image
import de.hsos.ma.adhocdb.framework.persistence.database.ImagesDatabase
import de.hsos.ma.adhocdb.ui.BaseCoroutineBaseMenuAppCompactActivity
import de.hsos.ma.adhocdb.ui.images.home.ImagesHomeActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream
import java.io.IOException


class ImagesCreateActivity : BaseCoroutineBaseMenuAppCompactActivity(
    R.layout.activity_images_create,
    R.string.image_home,
    showBackButton = true,
    selectedMenuItem = R.id.nav_images
) {
    private var cameraKitView: CameraKitView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        cameraKitView = findViewById(R.id.camera)
    }

    override fun onStart() {
        super.onStart()
        cameraKitView!!.onStart()
    }

    override fun onResume() {
        super.onResume()
        cameraKitView!!.onResume()
    }

    override fun onPause() {
        cameraKitView!!.onPause()
        super.onPause()
    }

    override fun onStop() {
        cameraKitView!!.onStop()
        super.onStop()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        cameraKitView!!.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    fun takePicture(view: View) {
        cameraKitView!!.captureImage { cameraKitView, capturedImage ->
            //getExternalMediaDir
            val savedPhoto = File(externalMediaDirs[0], "${System.currentTimeMillis()}.jpg")
            //val savedPhoto = File(Environment.getExternalStorageDirectory(), "${System.currentTimeMillis()}.jpg")

            try {
                val outputStream = FileOutputStream(savedPhoto.path, false)
                outputStream.write(capturedImage)
                outputStream.close()
                savePicture(Image(savedPhoto.absolutePath.toString()))
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

    private fun savePicture(image: Image){
        launch {
            val db = ImagesDatabase(applicationContext).imageDao()
            db.insert(image)
            launch(Dispatchers.Main) {
                Log.e("1312", image.absolutePath)
                onSuccessCallback()
            }
        }
    }

    private fun onSuccessCallback() {
        val intent = Intent(this, ImagesHomeActivity::class.java)
        startActivity(intent)
    }
}
