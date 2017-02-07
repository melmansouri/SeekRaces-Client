package com.mel.seekraces.sqlite;

/**
 * Created by void on 07/02/2017.
 */

public class TablesDB {

    private TablesDB(){}

    private interface ITableCities{
        String TABLE_NAME="cities";
        String ID="id";
        String NAME="name";
        String COUNTRY_CODE="contryCode";
    }

    private interface ITableCountries{
        String TABLE_NAME="countries";
        String ID="id";
        String CODE="code";
        String NAME="name";
    }

    public static class TableCities implements ITableCities{
        public static final String CREATE=String.format("CREATE TABLE %s (%s INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "%s TEXT NOT NULL ,%s TEXT NOT NULL )",
                        TABLE_NAME, ID,
                NAME, COUNTRY_CODE);
    }

    public static class TableCountries implements ITableCountries{
        public static final String CREATE=String.format("CREATE TABLE %s (%s INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "%s TEXT NOT NULL ,%s TEXT NOT NULL )",
                TABLE_NAME, ID,
                CODE, NAME);
    }

}
