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
                        val _cell : Cell = Cell(tableId, colID, cell.value, cell.type, cell.row)
                        db.insert(_cell)
                    }

                }

            }
        }

        fun createTableMockData(): List<MockData> {
            val list = ArrayList<MockData>()
            //Programming Index
            list.add(
                MockData(
                    Table(
                        "Gewichtstagebuch",
                        "Tagebuch zur Gewichtsanalyse",
                        "https://image.flaticon.com/icons/png/512/86/86122.png"
                    ),
                    arrayListOf(
                        MockCol("KW",
                            arrayListOf(
                                Cell(0, 0, "1", "", 0),
                                Cell(0, 0, "3", "", 1),
                                Cell(0, 0, "5", "", 2),
                                Cell(0, 0, "7", "", 3),
                                Cell(0, 0, "9", "", 4),
                                Cell(0, 0, "11", "", 5),
                                Cell(0, 0, "13", "", 6),
                                Cell(0, 0, "15", "", 7),
                                Cell(0, 0, "17", "", 8),
                                Cell(0, 0, "19", "", 9),
                                Cell(0, 0, "21", "", 10),
                                Cell(0, 0, "23", "", 11),
                                Cell(0, 0, "25", "", 12),
                                Cell(0, 0, "27", "", 13),
                                Cell(0, 0, "29", "", 14),
                                Cell(0, 0, "32", "", 15),
                                Cell(0, 0, "35", "", 16),
                                Cell(0, 0, "38", "", 17),
                                Cell(0, 0, "41", "", 18),
                                Cell(0, 0, "43", "", 19)
                            )
                        ),
                        MockCol(
                            "Gewicht",
                            arrayListOf(
                                Cell(0, 1, "80", "", 0),
                                Cell(0, 1, "78", "", 1),
                                Cell(0, 1, "79", "", 2),
                                Cell(0, 1, "83", "", 3),
                                Cell(0, 1, "82", "", 4),
                                Cell(0, 1, "82", "", 5),
                                Cell(0, 1, "81", "", 6),
                                Cell(0, 1, "80", "", 7),
                                Cell(0, 1, "79", "", 8),
                                Cell(0, 1, "80", "", 9),
                                Cell(0, 1, "81", "", 10),
                                Cell(0, 1, "79", "", 11),
                                Cell(0, 1, "78", "", 12),
                                Cell(0, 1, "77", "", 13),
                                Cell(0, 1, "77", "", 14),
                                Cell(0, 1, "76", "", 15),
                                Cell(0, 1, "75", "", 16),
                                Cell(0, 1, "74", "", 17),
                                Cell(0, 1, "75", "", 18),
                                Cell(0, 1, "75", "", 19)
                            )
                        ),
                        MockCol(
                            "BMI",
                            arrayListOf(
                                Cell(0, 2, "${80/(1.75*1.75).toInt()}", "KG", 0),
                                Cell(0, 2, "${78/(1.75*1.75).toInt()}", "KG", 1),
                                Cell(0, 2, "${79/(1.75*1.75).toInt()}", "KG", 2),
                                Cell(0, 2, "${83/(1.75*1.75).toInt()}", "KG", 3),
                                Cell(0, 2, "${82/(1.75*1.75).toInt()}", "KG", 4),
                                Cell(0, 2, "${82/(1.75*1.75).toInt()}", "KG", 5),
                                Cell(0, 2, "${81/(1.75*1.75).toInt()}", "KG", 6),
                                Cell(0, 2, "${80/(1.75*1.75).toInt()}", "KG", 7),
                                Cell(0, 2, "${79/(1.75*1.75).toInt()}", "KG", 8),
                                Cell(0, 2, "${80/(1.75*1.75).toInt()}", "KG", 9),
                                Cell(0, 2, "${81/(1.75*1.75).toInt()}", "KG", 10),
                                Cell(0, 2, "${79/(1.75*1.75).toInt()}", "KG", 11),
                                Cell(0, 2, "${78/(1.75*1.75).toInt()}", "KG", 12),
                                Cell(0, 2, "${77/(1.75*1.75).toInt()}", "KG", 13),
                                Cell(0, 2, "${77/(1.75*1.75).toInt()}", "KG", 14),
                                Cell(0, 2, "${76/(1.75*1.75).toInt()}", "KG", 15),
                                Cell(0, 2, "${75/(1.75*1.75).toInt()}", "KG", 16),
                                Cell(0, 2, "${74/(1.75*1.75).toInt()}", "KG", 17),
                                Cell(0, 2, "${75/(1.75*1.75).toInt()}", "KG", 18),
                                Cell(0, 2, "${75/(1.75*1.75).toInt()}", "KG", 19)
                            )
                        ),
                        MockCol(
                            "BMI-Klassifikation",
                            arrayListOf(
                                Cell(0, 3, "Übergewicht", "", 0),
                                Cell(0, 3, "Übergewicht", "", 1),
                                Cell(0, 3, "Übergewicht", "", 2),
                                Cell(0, 3, "Übergewicht", "", 3),
                                Cell(0, 3, "Übergewicht", "", 4),
                                Cell(0, 3, "Übergewicht", "", 5),
                                Cell(0, 3, "Übergewicht", "", 6),
                                Cell(0, 3, "Übergewicht", "", 7),
                                Cell(0, 3, "Übergewicht", "", 8),
                                Cell(0, 3, "Übergewicht", "", 9),
                                Cell(0, 3, "Übergewicht", "", 10),
                                Cell(0, 3, "Übergewicht", "", 11),
                                Cell(0, 3, "Übergewicht", "", 12),
                                Cell(0, 3, "Übergewicht", "", 13),
                                Cell(0, 3, "Übergewicht", "", 14),
                                Cell(0, 3, "Übergewicht", "", 15),
                                Cell(0, 3, "Übergewicht", "", 16),
                                Cell(0, 3, "Normalgewicht", "", 17),
                                Cell(0, 3, "Übergewicht", "", 18),
                                Cell(0, 3, "Übergewicht", "", 19)
                            )
                        )

                    )
                )
            )

            //Stefanraab
            list.add(
                MockData(
                    Table(
                        "Beteiligungen am Eurovision Songcontest",
                        "Stefan Raabs Beteiligungen am Eurovision Songcontest",
                        "https://www.aua-sicherheit.de/wp-content/uploads/2016/08/AuA-Personenschutz-stefan-raab.png"
                    ),
                    arrayListOf(
                        MockCol("Jahr",
                            arrayListOf(
                                Cell(1, 0, "1998", "", 0),
                                Cell(1, 0, "2000", "", 1),
                                Cell(1, 0, "2004", "", 2),
                                Cell(1, 0, "2010", "", 3),
                                Cell(1, 0, "2011", "", 4),
                                Cell(1, 0, "2012", "", 5)
                            )
                        ),
                        MockCol(
                            "Künstler",
                            arrayListOf(
                                Cell(1, 1, "Guildo Horn", "", 0),
                                Cell(1, 1, "Stefan Raab", "", 1),
                                Cell(1, 1, "Max Mutzke", "", 2),
                                Cell(1, 1, "Lena", "", 3),
                                Cell(1, 1, "Lena", "", 4),
                                Cell(1, 1, "Roman Lob", "", 5)
                            )
                        ),
                        MockCol(
                            "Raabs Beteiligung",
                            arrayListOf(
                                Cell(1, 2, "Komponist und Texter", "", 0),
                                Cell(1, 2, "Sänger, Komponist und Texter", "", 1),
                                Cell(1, 2, "Entdecker, Komponist und Texter", "", 2),
                                Cell(1, 2, "Initator und Produzent", "", 3),
                                Cell(1, 2, "Produzent", "", 4),
                                Cell(1, 2, "Produzent", "", 5)
                            )
                        ),
                        MockCol("Platz",
                            arrayListOf(
                                Cell(1, 3, "7", "", 0),
                                Cell(1, 3, "5", "", 1),
                                Cell(1, 3, "8", "", 2),
                                Cell(1, 3, "1", "", 3),
                                Cell(1, 3, "10", "", 4),
                                Cell(1, 3, "8", "", 5)
                            )
                        )

                    )
                )

            )
            //
            list.add(
                MockData(
                    Table(
                        "Beste Torschützen 2014",
                        "Beste Torschützen der Fußball-WM 2014",
                        "https://upload.wikimedia.org/wikipedia/de/thumb/1/18/WM-2014-Brasilien.svg/200px-WM-2014-Brasilien.svg.png"
                    ),
                    arrayListOf(
                        MockCol("Rang",
                            arrayListOf(
                                Cell(2, 0, "1", "", 0),
                                Cell(2, 0, "2", "", 1),
                                Cell(2, 0, "3", "", 2),
                                Cell(2, 0, "4", "", 3),
                                Cell(2, 0, "5", "", 4),
                                Cell(2, 0, "6", "", 5),
                                Cell(2, 0, "7", "", 6),
                                Cell(2, 0, "8", "", 7),
                                Cell(2, 0, "9", "", 8),
                                Cell(2, 0, "10", "", 9)
                            )
                        ),
                        MockCol(
                            "Spieler",
                            arrayListOf(
                                Cell(2, 0, "James Rodríguez", "", 0),
                                Cell(2, 0, "Thomas Müller", "", 1),
                                Cell(2, 0, "Neymar", "", 2),
                                Cell(2, 0, "Lionel Messi", "", 3),
                                Cell(2, 0, "Robin van Persi", "", 4),
                                Cell(2, 0, "Karim Benzema", "", 5),
                                Cell(2, 0, "André Schürrle", "", 6),
                                Cell(2, 0, "Arjen Robben", "", 7),
                                Cell(2, 0, "Enner Valencia", "", 8),
                                Cell(2, 0, "Xherdan Shaqiri", "", 9)
                            )
                        ),
                        MockCol(
                            "Tore",
                            arrayListOf(
                                Cell(2, 1, "6", "", 0),
                                Cell(2, 1, "5", "", 1),
                                Cell(2, 1, "4", "", 2),
                                Cell(2, 1, "4", "", 3),
                                Cell(2, 1, "4", "", 4),
                                Cell(2, 1, "3", "", 5),
                                Cell(2, 1, "3", "", 6),
                                Cell(2, 1, "3", "", 7),
                                Cell(2, 1, "3", "", 8),
                                Cell(2, 1, "3", "", 9)
                            )
                        ),
                        MockCol(
                            "Vorlagen",
                            arrayListOf(
                                Cell(2, 2, "2", "", 0),
                                Cell(2, 2, "3", "", 1),
                                Cell(2, 2, "1", "", 2),
                                Cell(2, 2, "1", "", 3),
                                Cell(2, 2, "0", "", 4),
                                Cell(2, 2, "2", "", 5),
                                Cell(2, 2, "1", "", 6),
                                Cell(2, 2, "1", "", 7),
                                Cell(2, 2, "0", "", 8),
                                Cell(2, 2, "0", "", 9)
                            )
                        ),
                        MockCol(
                            "Vorlagen",
                            arrayListOf(
                                Cell(2, 3, "2", "", 0),
                                Cell(2, 3, "3", "", 1),
                                Cell(2, 3, "1", "", 2),
                                Cell(2, 3, "1", "", 3),
                                Cell(2, 3, "0", "", 4),
                                Cell(2, 3, "2", "", 5),
                                Cell(2, 3, "1", "", 6),
                                Cell(2, 3, "1", "", 7),
                                Cell(2, 3, "0", "", 8),
                                Cell(2, 3, "0", "", 9)
                            )
                        )
                    )
                )
            )

            //Bundesländer
            list.add(
                MockData(
                    Table(
                        "Bundesländer",
                        "(Hauptstadt, Fläche, Einwohner pro quadradt km)",
                        "https://upload.wikimedia.org/wikipedia/commons/thumb/b/ba/Flag_of_Germany.svg/800px-Flag_of_Germany.svg.png"
                    ),
                    arrayListOf(
                        MockCol("Land",
                            arrayListOf(
                                Cell(3, 0, "Baden-Württemberg", "", 0),
                                Cell(3, 0, "Bayern", "", 1),
                                Cell(3, 0, "Berlin", "", 2),
                                Cell(3, 0, "Brandenburg", "", 3),
                                Cell(3, 0, "Bremen", "", 4),
                                Cell(3, 0, "Hamburg", "", 5),
                                Cell(3, 0, "Hessen", "", 6),
                                Cell(3, 0, "Mecklenburg-Vorpommern", "", 7),
                                Cell(3, 0, "Niedersachsen", "", 8),
                                Cell(3, 0, "Nordrhein-Westfalen", "", 9),
                                Cell(3, 0, "Rheinland-Pfalz", "", 10),
                                Cell(3, 0, "Saarland", "", 11),
                                Cell(3, 0, "Sachsen", "", 12),
                                Cell(3, 0, "Sachsen-Anhalt", "", 13),
                                Cell(3, 0, "Schleswig-Holstein", "", 14),
                                Cell(3, 0, "Thüringen", "", 15)
                            )
                        ),
                        MockCol(
                            "Hauptstadt",
                            arrayListOf(
                                Cell(3, 1, "Stuttgart", "", 0),
                                Cell(3, 1, "München", "", 1),
                                Cell(3, 1, " - ", "", 2),
                                Cell(3, 1, "Potsdam", "", 3),
                                Cell(3, 1, "Bremen", "", 4),
                                Cell(3, 1, " - ", "", 5),
                                Cell(3, 1, "Wiesbaden", "", 6),
                                Cell(3, 1, "Schwerin", "", 7),
                                Cell(3, 1, "Hannover", "", 8),
                                Cell(3, 1, "Düsseldorf", "", 9),
                                Cell(3, 1, "Mainz", "", 10),
                                Cell(3, 1, "Saarbrücken", "", 11),
                                Cell(3, 1, "Dresden", "", 12),
                                Cell(3, 1, "Magdeburg", "", 13),
                                Cell(3, 1, "Kiel", "", 14),
                                Cell(3, 1, "Erfurt", "", 15)
                            )
                        ),
                        MockCol(
                            "Einwohner pro Quadradt-KM.",
                            arrayListOf(
                                Cell(3, 2, "310", "", 0),
                                Cell(3, 2, "185", "", 1),
                                Cell(3, 2, "4087", "", 2),
                                Cell(3, 2, "85", "", 3),
                                Cell(3, 2, "1629", "", 4),
                                Cell(3, 2, "2397", "", 5),
                                Cell(3, 2, "297", "", 6),
                                Cell(3, 2, "69", "", 7),
                                Cell(3, 2, "168", "", 8),
                                Cell(3, 2, "526", "", 9),
                                Cell(3, 2, "206", "", 10),
                                Cell(3, 2, "386", "", 11),
                                Cell(3, 2, "221", "", 12),
                                Cell(3, 2, "108", "", 13),
                                Cell(3, 2, "183", "", 14),
                                Cell(3, 2, "132", "", 15)
                            )
                        )

                    )
                )
            )
            //Kassenbuch
            list.add(
                MockData(
                    Table(
                        "Kassenbuch",
                        "(Bareinzahlungen, Barauszahlungen, Einlagen und Entnahmen)",
                        "https://img.icons8.com/ios/50/000000/notepad.png"
                    ),
                    arrayListOf(
                        MockCol("Einnahme & Ausgaben",
                            arrayListOf(
                                Cell(4, 0, "-5.55", "€", 0),
                                Cell(4, 0, "+1.23", "€", 1),
                                Cell(4, 0, "-10.10", "€", 2)
                            )
                        ),
                        MockCol(
                            "Beschreibung",
                            arrayListOf(
                                Cell(4,1, "Ausgabe A", "", 0),
                                Cell(4,1, "Einnahme X", "", 1),
                                Cell(4,1, "Ausgabe B", "", 2)
                            )
                        )

                    )
                )
            )
            //Rezept
            list.add(
                MockData(
                    Table(
                        "Long Island Ice Tea - Rezept",
                        "Rezeptur für Long Island Ice Tea",
                        "https://i.imgur.com/ld4GOrf.png"
                    ),
                    arrayListOf(
                        MockCol("Einheit",
                            arrayListOf(
                                Cell(5, 0, "1.5", "cl", 0),
                                Cell(5, 0, "1.5", "cl", 1),
                                Cell(5, 0, "1.5", "cl", 2),
                                Cell(5, 0, "1.5", "cl", 3),
                                Cell(5, 0, "1.5", "cl", 4),
                                Cell(5, 0, "2", "cl", 5),
                                Cell(5, 0, "14", "cl", 6),
                                Cell(5, 0, "", "", 7)
                            )
                        ),
                        MockCol(
                            "Zutat",
                            arrayListOf(
                                Cell(5, 2, "Wodka", "", 0),
                                Cell(5, 2, "weißer Rom", "", 1),
                                Cell(5, 2, "Tequila", "", 2),
                                Cell(5, 2, "Gin", "", 3),
                                Cell(5, 2, "Cointreau", "", 4),
                                Cell(5, 2, "Limettensaft", "", 5),
                                Cell(5, 2, "Cola", "", 6),
                                Cell(5, 2, "Eis", "", 7)
                            )
                        )

                    )
                )
            )

            //Blue Lagoon

            list.add(
                MockData(
                    Table(
                        "Blue Lagoon - Rezept",
                        "Rezeptur für Blue Lagoon",
                        "https://i.imgur.com/8oC9zvP.png"
                    ),
                    arrayListOf(
                        MockCol("Einheit",
                            arrayListOf(
                                Cell(6, 0, "4", "cl", 0),
                                Cell(6, 0, "2", "cl", 1),
                                Cell(6, 0, "1", "cl", 2),
                                Cell(6, 0, "", "cl", 3),
                                Cell(6, 0, "", "cl", 4)
                            )
                        ),
                        MockCol(
                            "Zutat",
                            arrayListOf(
                                Cell(6, 2, "Wodka", "", 0),
                                Cell(6, 2, "Blue Curacao", "", 1),
                                Cell(6, 2, "Zitronensaft", "", 2),
                                Cell(6, 2, "Eiswürfel", "", 3),
                                Cell(6, 2, "Sprite", "", 4)
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