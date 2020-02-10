package de.hsos.ma.adhocdb.framework.persistence.notes

import android.content.Context
import de.hsos.ma.adhocdb.entities.note.Note
import de.hsos.ma.adhocdb.framework.persistence.database.NotesDatabase

class NotesMockDataSource {
    companion object {
        suspend fun getDataSet(useMockIfDatabaseEmpty: Boolean, context: Context): List<Note> {
            val db = NotesDatabase(context).noteDao()
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
            val db = NotesDatabase(context).noteDao()

            for (noteMock in createNotesMockData()) {
                db.insert(noteMock)
            }
        }

        fun createNotesMockData(): List<Note> {
            val list = ArrayList<Note>()

            list.add(Note(name = "Long Island Ice Tea", description = "Long Island Ice Tea:\r\n\t8 cl Rum\r\n\t" +
                    "8 cl Wodka\r\n\t" +
                    "8 cl Tequila\r\n\t" +
                    "8 cl Orangenlikör\r\n\t" +
                    "8 cl Limettensaft\r\n\t" +
                    "8 Zuckersirup\r\n\t" +
                    "1 Liter Cola\r\n\r\n" +
                    "Alle Zutaten außer der Coca-Cola mit etwa 3-5 Eiswürfeln in einen Cocktail Shaker geben und kräftig durchmixen. Hierfür den Cocktail Shaker mit beiden Händen zwischen Körper und Deckel festhalten, so dass der Shaker beim Mixen nicht aufgeht.\r\n" +

                    "Den Long-Island-Ice-Tea durch ein Sieb in 4 Longdrinkgläser abseihen und mit Cola auffüllen."))
            list.add(Note(name = "Filme", description = "Eine List Filme, die Ich gucken werde \r\n\t- Hass\r\n\t- Der Pate\r\n\t- Der Pate 2\r\n\t- Mafioso\r\n\t- The Dark Knight"))
            list.add(Note(name = "Geschenkideen", description = "Geschenkideen:\r\n\t- Netflix- Gutschein\r\n\t- Massage- Gutschein\r\n\t- Schmuck"))
            list.add(Note(name = "Einkaufsliste", description = "Einkaufsliste\r\n\t- Mehl\r\n\t- Tomaten\r\n\t- Wasser\r\n\t- Vollkorn Nudeln"))
            list.add(Note(name = "Klausurtermine", description = "\t- Big Data\t20.02.20\r\n\t- MA\t17.02.20\r\n\t- IGS\t06.01.20"))
            list.add(Note(name = "Erinnerung: Anmeldung zu den Klausuren", description = "Anmeldung zu den Klausuren.\r\nFrist läuft bis zum 12.12.2020"))
            list.add(Note(name = "Erinnerung: Arzttermin", description = "Arzttermin am Montag den 10.12.2019\r\n- Adresse: Johanstraße 44, 49074 Osnabrück\r\n\tUhrzeit: 15:30 Uhr\r\n\tRaum: A012"))

            return list.toList()
        }
    }
}