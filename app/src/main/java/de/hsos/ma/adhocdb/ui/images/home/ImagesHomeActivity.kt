package de.hsos.ma.adhocdb.ui.images.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.widget.SearchView
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.customview.customView
import com.afollestad.materialdialogs.customview.getCustomView
import com.google.android.material.button.MaterialButton
import de.hsos.ma.adhocdb.R
import de.hsos.ma.adhocdb.entities.image.Image
import de.hsos.ma.adhocdb.framework.persistence.database.ImagesDatabase
import de.hsos.ma.adhocdb.framework.persistence.database.NotesDatabase
import de.hsos.ma.adhocdb.framework.persistence.images.ImagesMockDataSource
import de.hsos.ma.adhocdb.ui.BaseCoroutineBaseMenuAppCompactActivity
import de.hsos.ma.adhocdb.ui.images.create.ImagesCreateActivity
import de.hsos.ma.adhocdb.ui.images.view.recycler.ImageRecyclerAdapter
import de.hsos.ma.adhocdb.ui.table.view.recycler.OnRecyclerItemClickListener
import de.hsos.ma.adhocdb.ui.table.view.recycler.RecyclerTopSpacingItemDecoration
import kotlinx.android.synthetic.main.activity_images_home.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File

class ImagesHomeActivity : BaseCoroutineBaseMenuAppCompactActivity(
    R.layout.activity_images_home,
    R.string.image_home,
    showBackButton = false,
    selectedMenuItem = R.id.nav_images
),
    OnRecyclerItemClickListener<Image> {
    private lateinit var imageAdapter: ImageRecyclerAdapter
    private var imagesFilterable: MutableList<Image> = ArrayList()
    private var imagesFullList: MutableList<Image> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initRecyclerView()
    }

    private fun initRecyclerView() {
        launch {
            imagesFilterable =
                ImagesMockDataSource.getDataSet(true, applicationContext).toMutableList()
            imagesFullList = imagesFilterable.toCollection(mutableListOf())

            launch(Dispatchers.Main) {
                recyclerView.apply {
                    layoutManager = LinearLayoutManager(this@ImagesHomeActivity)
                    val topSpacingDecorator =
                        RecyclerTopSpacingItemDecoration(
                            15
                        )
                    addItemDecoration(topSpacingDecorator)
                    imageAdapter =
                        ImageRecyclerAdapter(
                            this@ImagesHomeActivity,
                            imagesFilterable
                        )
                    adapter = imageAdapter
                }
            }
        }
    }

    override fun onItemClick(item: Image, pos: Int) {
        Toast.makeText(this, item.absolutePath, Toast.LENGTH_LONG)
    }

    override fun onItemLongClick(item: Image, pos: Int): Boolean {
        val dialog = MaterialDialog(this@ImagesHomeActivity)
            .customView(R.layout.view_dialog_image_edit, scrollable = true)
        dialog.cornerRadius(res = R.dimen.my_corner_radius)

        val dialogView = dialog.getCustomView()

        val deleteNoteButton = dialogView.findViewById<MaterialButton>(R.id.chang_image_delete)

        deleteNoteButton?.setOnClickListener {
            dialog.dismiss()
            editImageDelete(item)
        }

        dialog.show()
        return true
    }

    private fun editImageDelete(image: Image) {
        MaterialDialog(this)
            .title(R.string.editImageDelete)
            .show{
                message(R.string.editImageDeleteWarning)
                positiveButton(R.string.submit){
                    deleteImage(image)
                }
                negativeButton(R.string.cancel)
            }
    }

    private fun deleteImage(image: Image) {
        launch {
            val file = File(image.absolutePath)
            if(file.exists()) file.delete()

            val db = ImagesDatabase(applicationContext).imageDao()
            db.delete(image)
            reloadView()
        }
    }

    private fun loadAddImageView() {
        val intent = Intent(this@ImagesHomeActivity, ImagesCreateActivity::class.java)
        startActivity(intent)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        super.onCreateOptionsMenu(menu)
        if (menu == null) return false
        menuInflater.inflate(R.menu.add_menu, menu)


        menu.findItem(R.id.action_add)
            .setOnMenuItemClickListener {
                loadAddImageView()
                true
            }
        return true
    }
}

