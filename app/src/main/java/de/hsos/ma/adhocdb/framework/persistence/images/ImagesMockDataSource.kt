package de.hsos.ma.adhocdb.framework.persistence.images

import android.content.Context
import de.hsos.ma.adhocdb.R
import de.hsos.ma.adhocdb.entities.image.Image
import de.hsos.ma.adhocdb.framework.persistence.database.ImagesDatabase

class ImagesMockDataSource {
    companion object {
        suspend fun getDataSet(useMockIfDatabaseEmpty: Boolean, context: Context): List<Image> {
            val db = ImagesDatabase(context).imageDao()
            var list = db.getAll()
            if (list.isEmpty() && useMockIfDatabaseEmpty) {
                fillDB(
                    context
                )
                list = db.getAll()
            }
            return list
        }

        private suspend fun fillDB(context: Context) {
            val db = ImagesDatabase(context).imageDao()

            for (imageMock in createImagesMockData()) {
                db.insert(imageMock)
            }
        }

        fun createImagesMockData(): List<Image> {
            val list = ArrayList<Image>()

            list.add(Image(R.drawable.forest.toString()))
            list.add(Image(R.drawable.mountain.toString()))
            list.add(Image(R.drawable.vulcan.toString()))

            return list.toList()
        }
    }
}
