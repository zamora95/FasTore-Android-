package com.zamora.fastoreapp.Clases;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.zamora.fastoreapp.Database.DatabaseContract;
import com.zamora.fastoreapp.Database.DatabaseHelper;

/**
 * Created by Zamora on 30/03/2017.
 */

public class Producto {
    private String id;
    private String nombre;
    private Double precio;
    private String imagen;

    public Producto(){}

    public Producto(String id, String nombre, Double precio, String imagen) {
        this.id = id;
        this.nombre = nombre;
        this.precio = precio;
        this.imagen = imagen;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    /**
     * Función que inserta un producto en la base de datos
     */
    public long insertar(Context context) {
        DatabaseHelper DatabaseHelper = new DatabaseHelper(context);
        SQLiteDatabase db = DatabaseHelper.getWritableDatabase();

        // Crear un mapa de valores donde las columnas son las llaves
        ContentValues values = new ContentValues();
        values.put(DatabaseContract.DataBaseEntry._ID, getId());
        values.put(DatabaseContract.DataBaseEntry.COLUMN_NAME_NOMBRE, getNombre());
        values.put(DatabaseContract.DataBaseEntry.COLUMN_NAME_PRECIO, getPrecio());
        values.put(DatabaseContract.DataBaseEntry.COLUMN_NAME_IMAGEN, getImagen());

        // Insertar la nueva fila
        return db.insert(DatabaseContract.DataBaseEntry.TABLE_NAME_PRODUCTO, null, values);
    }


    /**
     * Leer un producto desde la base de datos
     */
    public void leer (Context context, String identificacion){
        DatabaseHelper DatabaseHelper = new DatabaseHelper(context);

        // Obtiene la base de datos en modo lectura
        SQLiteDatabase db = DatabaseHelper.getReadableDatabase();

        // Define cuales columnas quiere solicitar // en este caso todas las de la clase
        String[] projection = {
                DatabaseContract.DataBaseEntry._ID,
                DatabaseContract.DataBaseEntry.COLUMN_NAME_NOMBRE,
                DatabaseContract.DataBaseEntry.COLUMN_NAME_PRECIO,
                DatabaseContract.DataBaseEntry.COLUMN_NAME_IMAGEN,
        };

        // Filtro para el WHERE
        String selection = DatabaseContract.DataBaseEntry._ID + " = ?";
        String[] selectionArgs = {identificacion};

        // Resultados en el cursor
        Cursor cursor = db.query(
                DatabaseContract.DataBaseEntry.TABLE_NAME_PRODUCTO, // tabla
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
            setNombre(cursor.getString(cursor.getColumnIndexOrThrow(
                    DatabaseContract.DataBaseEntry.COLUMN_NAME_NOMBRE)));
            setPrecio(cursor.getDouble(cursor.getColumnIndexOrThrow(
                    DatabaseContract.DataBaseEntry.COLUMN_NAME_PRECIO)));
            setImagen(cursor.getString(cursor.getColumnIndexOrThrow(
                    DatabaseContract.DataBaseEntry.COLUMN_NAME_IMAGEN)));
        }
    }


    /**
     * Actualizar un prodcuto en la base de datos
     */
    public int actualizar(Context context) {
        DatabaseHelper DatabaseHelper = new DatabaseHelper(context);
        SQLiteDatabase db = DatabaseHelper.getReadableDatabase();

        ContentValues values = new ContentValues();
        values.put(DatabaseContract.DataBaseEntry.COLUMN_NAME_NOMBRE, getNombre());
        values.put(DatabaseContract.DataBaseEntry.COLUMN_NAME_PRECIO, getPrecio());
        values.put(DatabaseContract.DataBaseEntry.COLUMN_NAME_IMAGEN, getImagen());

        // Criterio de actualizacion
        String selection = DatabaseContract.DataBaseEntry._ID + " LIKE ?";
        // Se detallan los argumentos
        String[] selectionArgs = { getId() };
        // Actualizar la base de datos
        return db.update(DatabaseContract.DataBaseEntry.TABLE_NAME_PRODUCTO, values, selection, selectionArgs);
    }
}
