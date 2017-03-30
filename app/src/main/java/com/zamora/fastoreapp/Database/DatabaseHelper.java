package com.zamora.fastoreapp.Database;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Zamora on 17/03/2017.
 */

public class DatabaseHelper extends SQLiteOpenHelper{
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "fastore.db";

    public DatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public DatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version,
                          DatabaseErrorHandler errorHandler) {
        super(context, name, factory, version, errorHandler);
    }

    public DatabaseHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Crear la base de datos de la app
        db.execSQL(DatabaseContract.SQL_CREATE_USUARIO);
        db.execSQL(DatabaseContract.SQL_CREATE_LISTA_COMPRAS);
        //db.execSQL(DatabaseContract.SQL_CREATE_PRODUCTO);
       // db.execSQL(DatabaseContract.SQL_CREATE_DETALLE_LISTA);
        //db.execSQL(DatabaseContract.SQL_CREATE_LISTA_COMPARTIDAS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Administracion de actualizaciones
        db.execSQL(DatabaseContract.SQL_DELETE_USUARIO);
        db.execSQL(DatabaseContract.SQL_DELETE_LISTA_COMPRAS);
       // db.execSQL(DatabaseContract.SQL_DELETE_PRODUCTO);
        //db.execSQL(DatabaseContract.SQL_DELETE_DETALLE_LISTA);
       // db.execSQL(DatabaseContract.SQL_DELETE_LISTA_COMPARTIDAS);
        onCreate(db);
    }
}
