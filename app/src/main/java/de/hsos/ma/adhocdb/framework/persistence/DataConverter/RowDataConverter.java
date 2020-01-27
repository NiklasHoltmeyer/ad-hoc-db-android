package de.hsos.ma.adhocdb.framework.persistence.DataConverter;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import de.hsos.ma.adhocdb.entities.Row;

//Quelle https://stackoverflow.com/a/45071364/5026265
public class RowDataConverter {
    @TypeConverter
    public static List<Row> fromString(String value) {
        Type listType = new TypeToken<List<Row>>() {}.getType();
        return new Gson().fromJson(value, listType);
    }

    @TypeConverter
    public static String fromArrayList(List<Row> list) {
        Gson gson = new Gson();
        String json = gson.toJson(list);
        return json;
    }
}