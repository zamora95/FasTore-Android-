package com.zamora.fastoreapp.Clases;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import com.zamora.fastoreapp.Database.DatabaseContract;
import com.zamora.fastoreapp.Database.DatabaseHelper;


/**
 * Created by Zamora on 29/03/2017.
 */

public class Usuario {
    private String apellido;
    private String nombre;
    private String email;
    private String id;
    private Uri fotoURL;

    public Usuario(){}

    public Usuario(String apellido, String nombre, String email, String id, Uri fotoURL) {
        this.apellido = apellido;
        this.nombre = nombre;
        this.email = email;
        this.id = id;
        this.fotoURL = fotoURL;
    }

    public String getApellido() {
        return apellido;
    }

    public String getNombre() {
        return nombre;
    }

    public String getEmail() {
        return email;
    }

    public String getId() {
        return id;
    }

    public Uri getFotoURL() {
        return fotoURL;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setFotoURL(Uri fotoURL) {
        this.fotoURL = fotoURL;
    }

    /**
     * Función que inserta un usuario en la base de datos
     */
    public long insertar(Context context) {
        DatabaseHelper DatabaseHelper = new DatabaseHelper(context);
        SQLiteDatabase db = DatabaseHelper.getWritableDatabase();

        // Crear un mapa de valores donde las columnas son las llaves
        ContentValues values = new ContentValues();
        values.put(DatabaseContract.DataBaseEntry._ID, getId());
        values.put(DatabaseContract.DataBaseEntry.COLUMN_NAME_APELLIDO, getApellido());
        values.put(DatabaseContract.DataBaseEntry.COLUMN_NAME_NOMBRE, getNombre());
        values.put(DatabaseContract.DataBaseEntry.COLUMN_NAME_EMAIL, getEmail());
        values.put(DatabaseContract.DataBaseEntry.COLUMN_NAME_FOTO_USUARIO, getFotoURL().toString());

        // Insertar la nueva fila
        return db.insert(DatabaseContract.DataBaseEntry.TABLE_NAME_USUARIO, null, values);
    }


    /**
     * Leer una persona desde la base de datos
     */
    public void leer (Context context, String identificacion){
        DatabaseHelper DatabaseHelper = new DatabaseHelper(context);

        // Obtiene la base de datos en modo lectura
        SQLiteDatabase db = DatabaseHelper.getReadableDatabase();

        // Define cuales columnas quiere solicitar // en este caso todas las de la clase
        String[] projection = {
                DatabaseContract.DataBaseEntry._ID,
                DatabaseContract.DataBaseEntry.COLUMN_NAME_APELLIDO,
                DatabaseContract.DataBaseEntry.COLUMN_NAME_NOMBRE,
                DatabaseContract.DataBaseEntry.COLUMN_NAME_EMAIL,
                DatabaseContract.DataBaseEntry.COLUMN_NAME_FOTO_USUARIO,
        };

        // Filtro para el WHERE
        String selection = DatabaseContract.DataBaseEntry._ID + " = ?";
        String[] selectionArgs = {identificacion};

        // Resultados en el cursor
        Cursor cursor = db.query(
                DatabaseContract.DataBaseEntry.TABLE_NAME_USUARIO, // tabla
                projection, // columnas
                selection, // where
                selectionArgs, // valores del where
                null, // agrupamiento
                null, // filtros por grupo
                null // orden
        );

        // recorrer los resultados y asignarlos a la clase // aca podria implementarse un ciclo si es necesario
        System.out.println(String.valueOf(cursor.getCount()));
        if(cursor.moveToFirst() && cursor.getCount() > 0) {
            setId(cursor.getString(cursor.getColumnIndexOrThrow(
                    DatabaseContract.DataBaseEntry._ID)));
            setApellido(cursor.getString(cursor.getColumnIndexOrThrow(
                    DatabaseContract.DataBaseEntry.COLUMN_NAME_APELLIDO)));
            setNombre(cursor.getString(cursor.getColumnIndexOrThrow(
                    DatabaseContract.DataBaseEntry.COLUMN_NAME_NOMBRE)));
            setEmail(cursor.getString(cursor.getColumnIndexOrThrow(
                    DatabaseContract.DataBaseEntry.COLUMN_NAME_EMAIL)));
        }
    }


    /**
     * Actualizar una persona en la base de datos
     */
    public int actualizar(Context context) {
        DatabaseHelper DatabaseHelper = new DatabaseHelper(context);
        SQLiteDatabase db = DatabaseHelper.getReadableDatabase();

        ContentValues values = new ContentValues();
        values.put(DatabaseContract.DataBaseEntry.COLUMN_NAME_APELLIDO, getApellido());
        values.put(DatabaseContract.DataBaseEntry.COLUMN_NAME_NOMBRE, getNombre());
        values.put(DatabaseContract.DataBaseEntry.COLUMN_NAME_EMAIL, getEmail());

        // Criterio de actualizacion
        String selection = DatabaseContract.DataBaseEntry._ID + " LIKE ?";
        // Se detallan los argumentos
        String[] selectionArgs = {getId()};
        // Actualizar la base de datos
        return db.update(DatabaseContract.DataBaseEntry.TABLE_NAME_USUARIO, values, selection, selectionArgs);
    }
}
