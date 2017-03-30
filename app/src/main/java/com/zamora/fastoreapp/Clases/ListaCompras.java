package com.zamora.fastoreapp.Clases;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.zamora.fastoreapp.Database.DatabaseContract;
import com.zamora.fastoreapp.Database.DatabaseHelper;

import java.text.SimpleDateFormat;
import java.util.Date;

import static android.R.attr.id;

/**
 * Created by Zamora on 29/03/2017.
 */

public class ListaCompras {
    private String id;
    private String idUsuario;
    private Date fechaCompra;
    private Double montoTotal;

    public ListaCompras(){}

    public ListaCompras(String id, String idUsuario, Date fechaCompra, Double montoTotal) {
        this.id = id;
        this.idUsuario = idUsuario;
        this.fechaCompra = fechaCompra;
        this.montoTotal = montoTotal;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    public Date getFechaCompra() {
        return fechaCompra;
    }

    public void setFechaCompra(String fechaCompra) {
        Date fecha = SimpleDateFormat.parse(fechaCompra);
        this.fechaCompra = fecha;
    }

    public Double getMontoTotal() {
        return montoTotal;
    }

    public void setMontoTotal(Double montoTotal) {
        this.montoTotal = montoTotal;
    }

    /**
     * Función que inserta una lista de compras en la base de datos
     */
    public long insertar(Context context) {
        DatabaseHelper DatabaseHelper = new DatabaseHelper(context);
        SQLiteDatabase db = DatabaseHelper.getWritableDatabase();

        // Crear un mapa de valores donde las columnas son las llaves
        ContentValues values = new ContentValues();
        values.put(DatabaseContract.DataBaseEntry._ID, getId());
        values.put(DatabaseContract.DataBaseEntry.COLUMN_NAME_ID_USUARIO, getIdUsuario());
        values.put(DatabaseContract.DataBaseEntry.COLUMN_NAME_FECHA_COMPRA, getFechaCompra().toString());
        values.put(DatabaseContract.DataBaseEntry.COLUMN_NAME_MONTO_TOTAL, getMontoTotal());

        // Insertar la nueva fila
        return db.insert(DatabaseContract.DataBaseEntry.TABLE_NAME_LISTA_COMPRA, null, values);
    }


    /**
     * Leer una lista de compras desde la base de datos
     */
    public void leer (Context context, String identificacion){
        DatabaseHelper DatabaseHelper = new DatabaseHelper(context);

        // Obtiene la base de datos en modo lectura
        SQLiteDatabase db = DatabaseHelper.getReadableDatabase();

        // Define cuales columnas quiere solicitar // en este caso todas las de la clase
        String[] projection = {
                DatabaseContract.DataBaseEntry._ID,
                DatabaseContract.DataBaseEntry.COLUMN_NAME_ID_USUARIO,
                DatabaseContract.DataBaseEntry.COLUMN_NAME_FECHA_COMPRA,
                DatabaseContract.DataBaseEntry.COLUMN_NAME_MONTO_TOTAL,
        };

        // Filtro para el WHERE
        String selection = DatabaseContract.DataBaseEntry._ID + " = ?";
        String[] selectionArgs = {identificacion};

        // Resultados en el cursor
        Cursor cursor = db.query(
                DatabaseContract.DataBaseEntry.TABLE_NAME_LISTA_COMPRA, // tabla
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
            setIdUsuario(cursor.getString(cursor.getColumnIndexOrThrow(
                    DatabaseContract.DataBaseEntry.COLUMN_NAME_ID_USUARIO)));
            setFechaCompra(cursor.getString(cursor.getColumnIndexOrThrow(
                    DatabaseContract.DataBaseEntry.COLUMN_NAME_FECHA_COMPRA)));
            setMontoTotal(cursor.getDouble(cursor.getColumnIndexOrThrow(
                    DatabaseContract.DataBaseEntry.COLUMN_NAME_MONTO_TOTAL)));
        }
    }


    /**
     * Actualizar una lista de compras en la base de datos
     */
    public int actualizar(Context context) {
        DatabaseHelper DatabaseHelper = new DatabaseHelper(context);
        SQLiteDatabase db = DatabaseHelper.getReadableDatabase();

        ContentValues values = new ContentValues();
        values.put(DatabaseContract.DataBaseEntry.COLUMN_NAME_ID_USUARIO, getIdUsuario());
        values.put(DatabaseContract.DataBaseEntry.COLUMN_NAME_FECHA_COMPRA, getFechaCompra().toString());
        values.put(DatabaseContract.DataBaseEntry.COLUMN_NAME_MONTO_TOTAL, getMontoTotal());

        // Criterio de actualizacion
        String selection = DatabaseContract.DataBaseEntry._ID + " LIKE ?";
        // Se detallan los argumentos
        String[] selectionArgs = {getId()};
        // Actualizar la base de datos
        return db.update(DatabaseContract.DataBaseEntry.TABLE_NAME_LISTA_COMPRA, values, selection, selectionArgs);
    }
}
