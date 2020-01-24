package de.hsos.ma.adhocdb.framework.persistence.tables

import android.content.Context
import de.hsos.ma.adhocdb.entities.Table
import de.hsos.ma.adhocdb.framework.persistence.database.TablesDatabase

class DataSource{
    companion object {
        suspend fun getDataSet(useMockIfDatabaseEmpty : Boolean, context : Context) : List<Table> {
            val db = TablesDatabase(context).tableDao()
            var list = db.getAll()
            if(list.isEmpty() && useMockIfDatabaseEmpty){
                fillDB(context)
                list = db.getAll()
            }
            return list
        }

        private suspend fun fillDB(context : Context){
            val db = TablesDatabase(context).tableDao()
            createMockDataSet().forEach {
                db.insert(it)
            }
        }

        fun createMockDataSet(): ArrayList<Table> {
            val list = ArrayList<Table>()
            list.add(
                Table(
                    "onsetetur sadipscing elit! Lorem ipsum  Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum ",
                    "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet. Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet.",
                    "https://picsum.photos/id/237/200/300.jpg"
                )
            )
            list.add(
                Table(
                    "Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum ",
                    "Duis autem vel eum iriure dolor in hendrerit in vulputate velit esse molestie consequat, vel illum dolore eu feugiat nulla facilisis at vero eros et accumsan et iusto odio dignissim qui blandit praesent luptatum zzril delenit augue duis dolore te feugait nulla facilisi. Lorem ipsum dolor sit amet, consectetuer adipiscing elit, sed diam nonummy nibh euismod tincidunt ut laoreet dolore magna aliquam erat volutpat.   \n",
                    "https://picsum.photos/id/238/200/300.jpg"
                )
            )
            list.add(
                Table(
                    "Title",
                    "Ut wisi enim ad minim veniam, quis nostrud exerci tation ullamcorper suscipit lobortis nisl ut aliquip ex ea commodo consequat. Duis autem vel eum iriure dolor in hendrerit in vulputate velit esse molestie consequat, vel illum dolore eu feugiat nulla facilisis at vero eros et accumsan et iusto odio dignissim qui blandit praesent luptatum zzril delenit augue duis dolore te feugait nulla facilisi.   \n",
                    "https://picsum.photos/id/239/200/300.jpg"
                )
            )
            list.add(
                Table(
                    "velit esse molestie consequat, vel illum dolore eu",
                    "Duis autem vel eum iriure dolor in hendrerit in vulputate velit esse molestie consequat, vel illum dolore eu feugiat nulla facilisis.   \n",
                    "https://picsum.photos/id/240/200/300.jpg"
                )
            )

            list.add(
                Table(
                    "onsetetur sadipscing elit! Lorem ipsum  Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum ",
                    "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet. Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet.",
                    "https://picsum.photos/id/241/200/300.jpg"
                )
            )
            list.add(
                Table(
                    "Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum ",
                    "Duis autem vel eum iriure dolor in hendrerit in vulputate velit esse molestie consequat, vel illum dolore eu feugiat nulla facilisis at vero eros et accumsan et iusto odio dignissim qui blandit praesent luptatum zzril delenit augue duis dolore te feugait nulla facilisi. Lorem ipsum dolor sit amet, consectetuer adipiscing elit, sed diam nonummy nibh euismod tincidunt ut laoreet dolore magna aliquam erat volutpat.   \n",
                    "https://picsum.photos/id/242/200/300.jpg"
                )
            )
            list.add(
                Table(
                    "Title",
                    "Ut wisi enim ad minim veniam, quis nostrud exerci tation ullamcorper suscipit lobortis nisl ut aliquip ex ea commodo consequat. Duis autem vel eum iriure dolor in hendrerit in vulputate velit esse molestie consequat, vel illum dolore eu feugiat nulla facilisis at vero eros et accumsan et iusto odio dignissim qui blandit praesent luptatum zzril delenit augue duis dolore te feugait nulla facilisi.   \n",
                    "https://picsum.photos/id/243/200/300.jpg"
                )
            )
            list.add(
                Table(
                    "velit esse molestie consequat, vel illum dolore eu",
                    "Duis autem vel eum iriure dolor in hendrerit in vulputate velit esse molestie consequat, vel illum dolore eu feugiat nulla facilisis.   \n",
                    "https://picsum.photos/id/244/200/300.jpg"
                )
            )

            return list
        }
    }
}