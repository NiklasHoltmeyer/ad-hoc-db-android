package de.hsos.ma.adhocdb.entities

interface Unit{
    fun getValue() : Object
    fun getType() : String
}


/*data class Unit<T>(
    val value : T,
    val type : String
){}*/