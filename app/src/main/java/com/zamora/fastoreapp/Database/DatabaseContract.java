package com.zamora.fastoreapp.Database;

import android.provider.BaseColumns;

/**
 * Created by Zamora on 17/03/2017.
 */

public class DatabaseContract {
    // Implementa la interfaz BaseColumns para heredar campos estandar del marco de Android _ID

    public static class DataBaseEntry implements BaseColumns {

        // Clase Usuario
        public static final String TABLE_NAME_USUARIO = "Usuario";
        // private String identificacion; Utilizamos DataBaseEntry._ID de BaseColumns
        public static final String COLUMN_NAME_EMAIL = "email";
        public static final String COLUMN_NAME_NOMBRE = "nombre";
        public static final String COLUMN_NAME_APELLIDO = "apellido";
        public static final String COLUMN_NAME_FOTO_USUARIO = "foto_usuario";


        // Clase Lista Compra
        public static final String TABLE_NAME_LISTA_COMPRA = "Lista_compras";
        public static final String COLUMN_NAME_ID_USUARIO = "id_usuario";
        public static final String COLUMN_NAME_FECHA_COMPRA = "fecha_compra";
        public static final String COLUMN_NAME_MONTO_TOTAL = "monto_total";


    }

// Construir las tablas de la base de datos
    private static final String TEXT_TYPE = " TEXT";
    private static final String INTEGER_TYPE = " INTEGER";
    private static final String REAL_TYPE = " REAL";
    private static final String COMMA_SEP = ",";


    /**
     * Creación de tablas
     */

    public static final String SQL_CREATE_USUARIO =
            "CREATE TABLE " + DataBaseEntry.TABLE_NAME_USUARIO + " (" +
                    DataBaseEntry._ID + TEXT_TYPE + "PRIMARY KEY," +
                    DataBaseEntry.COLUMN_NAME_APELLIDO + TEXT_TYPE + COMMA_SEP +
                    DataBaseEntry.COLUMN_NAME_NOMBRE + TEXT_TYPE + COMMA_SEP +
                    DataBaseEntry.COLUMN_NAME_EMAIL + TEXT_TYPE + COMMA_SEP +
                    DataBaseEntry.COLUMN_NAME_FOTO_USUARIO + TEXT_TYPE + " )";

    public static final String SQL_DELETE_USUARIO =
            "DROP TABLE IF EXISTS " + DataBaseEntry.TABLE_NAME_USUARIO;



    public static final String SQL_CREATE_LISTA_COMPRAS =
            "CREATE TABLE " + DataBaseEntry.TABLE_NAME_LISTA_COMPRA + " (" +
                    DataBaseEntry._ID + TEXT_TYPE + "PRIMARY KEY," +
                    DataBaseEntry.COLUMN_NAME_ID_USUARIO + TEXT_TYPE + COMMA_SEP +
                    DataBaseEntry.COLUMN_NAME_FECHA_COMPRA + TEXT_TYPE + COMMA_SEP +
                    DataBaseEntry.COLUMN_NAME_MONTO_TOTAL + REAL_TYPE + " )";

    public static final String SQL_DELETE_LISTA_COMPRAS =
            "DROP TABLE IF EXISTS " + DataBaseEntry.TABLE_NAME_LISTA_COMPRA;
}
