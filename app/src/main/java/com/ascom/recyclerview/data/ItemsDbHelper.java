package com.ascom.recyclerview.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


public class ItemsDbHelper extends SQLiteOpenHelper {

    private static final String TAG = "ItemsDbHelper";

    private static final String DB_NAME = "";

    private static final int DB_VERSION = 1;

    public ItemsDbHelper(Context aContext) {
        super(aContext, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String SQL_CREATE_TABLE = "CREATE TABLE " + DataControlContract.ViewsData.TABLE_NAME + " ("
                + DataControlContract.ViewsData._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + DataControlContract.ViewsData.COLUMN_LABEL + " TEXT NOT NULL DEFAULT 'labelHere', "
                + DataControlContract.ViewsData.COLUMN_URI + " TEXT NOT NULL DEFAULT 'data://', "
                + DataControlContract.ViewsData.COLUMN_TIMESTAMP + " TEXT NOT NULL, "
                + DataControlContract.ViewsData.COLUMN_TEXT + " TEXT NOT NULL DEFAULT 'someTextHere');";

        // Запускаем создание таблицы
        db.execSQL(SQL_CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Запишем в журнал
        Log.w("SQLite", "Обновляемся с версии " + oldVersion + " на версию " + newVersion);

        // Удаляем старую таблицу и создаём новую
        db.execSQL("DROP TABLE IF EXISTS " + DB_NAME);
        // Создаём новую таблицу
        onCreate(db);
    }
}
