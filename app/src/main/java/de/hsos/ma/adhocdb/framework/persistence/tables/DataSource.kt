package de.hsos.ma.adhocdb.framework.persistence.tables

import android.content.Context
import de.hsos.ma.adhocdb.entities.Cell
import de.hsos.ma.adhocdb.entities.Column
import de.hsos.ma.adhocdb.entities.Table
import de.hsos.ma.adhocdb.framework.persistence.database.TablesDatabase

class DataSource {
    companion object {
        suspend fun getDataSet(useMockIfDatabaseEmpty: Boolean, context: Context): List<Table> {
            val db = TablesDatabase(context).tableDao()
            var list = db.getAll()
            if (list.isEmpty() && useMockIfDatabaseEmpty) {
                fillDB(context)
                list = db.getAll()
            }
            return list
        }

        private suspend fun fillDB(context: Context) {
            val db = TablesDatabase(context).tableDao()

            for (tableMock in createTableMockData()) {
                val tableId = db.insert(tableMock.table)
                tableMock.columns.forEach {
                    val colID = db.insert(Column(tableId, it.name))
                    for(cell in it.cells){
                        val _cell : Cell = Cell(tableId, colID, cell.value, cell.type)
                        db.insert(_cell)
                    }

                }

            }
        }

        fun createTableMockData(): List<MockData> {
            val list = ArrayList<MockData>()
            list.add(
                MockData(
                    Table(
                        "Kassenbuch",
                        "(Bareinzahlungen, Barauszahlungen, Einlagen und Entnahmen)",
                        ""
                    ),
                    arrayListOf(
                        MockCol("Einnahme & Ausgaben",
                            arrayListOf(
                                Cell(0, 0, "-5.55", "€"),
                                Cell(0, 0, "+1.23", "€"),
                                Cell(0, 0, "-10.10", "€")
                            )
                        ),
                        MockCol(
                            "Beschreibung",
                            arrayListOf(
                                Cell(0,0, "Ausgabe A", "String"),
                                Cell(0,0, "Einnahme X", "String"),
                                Cell(0,0, "Ausgabe B", "String")
                            )
                        )

                    )
                )

            )
            return list.toList()
        }
    }
}

data class MockData(
    val table: Table,
    val columns: List<MockCol>
)

data class MockCol(
    val name: String,
    val cells: List<Cell>
)