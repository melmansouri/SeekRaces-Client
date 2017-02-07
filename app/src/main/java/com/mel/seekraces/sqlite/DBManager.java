package com.mel.seekraces.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by void on 07/02/2017.
 */

public class DBManager extends SQLiteOpenHelper{

    private static final String NAME_DB="seekraces.db";
    private static final int VERSION_DB=1;

    private Context contexto;

    public DBManager(Context context) {
        super(context, NAME_DB, null, VERSION_DB);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TablesDB.TableCountries.CREATE);
        db.execSQL(TablesDB.TableCities.CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TablesDB.TableCities.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + TablesDB.TableCountries.TABLE_NAME);
        onCreate(db);
    }
}
