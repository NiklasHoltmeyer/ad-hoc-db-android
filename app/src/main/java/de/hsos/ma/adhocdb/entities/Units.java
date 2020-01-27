package de.hsos.ma.adhocdb.entities;

public class Units {
    public static final Unit EURO = new Unit("Euro", "€");
    public static final Unit STRING = new Unit("String", "");

    public static final Unit[] UNITS = new Unit[]{EURO, STRING};
}
//TODO converter / aggregationen etc
class Unit{
    String name;
    String symbol;

    public Unit(String name, String symbol) {
        this.name = name;
        this.symbol = symbol;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }
}



