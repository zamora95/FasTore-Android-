package com.zamora.fastoreapp.Clases;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.zamora.fastoreapp.Database.DatabaseContract;
import com.zamora.fastoreapp.Database.DatabaseHelper;

import java.util.ArrayList;

/**
 * Created by Zamora on 29/03/2017.
 */

public class ListaCompras {
    private String id;
    private String nombre;
    private String idUsuario;
    private String fechaCompra;
    private Double montoTotal;
    private ArrayList<Producto> detalle;
    private ArrayList<Usuario> usuariosPermitidos;

    public ListaCompras(){}

    public ListaCompras(String id, String nombre,String idUsuario, String fechaCompra, Double montoTotal) {
        this.id = id;
        this.nombre = nombre;
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

    public String getFechaCompra() {
        return fechaCompra;
    }

    public void setFechaCompra(String fechaCompra) {
       this.fechaCompra = fechaCompra;
    }

    public Double getMontoTotal() {
        return montoTotal;
    }

    public void setMontoTotal(Double montoTotal) {
        this.montoTotal = montoTotal;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public ArrayList<Producto> getDetalle() {
        return detalle;
    }

    public void setDetalle(ArrayList<Producto> detalle) {
        this.detalle = detalle;
    }

    public ArrayList<Usuario> getUsuariosPermitidos() {
        return usuariosPermitidos;
    }

    public void setUsuariosPermitidos(ArrayList<Usuario> usuariosPermitidos) {
        this.usuariosPermitidos = usuariosPermitidos;
    }

    @Override
    public String toString() {
        return "ListaCompras{" +
                "id='" + id + '\'' +
                ", nombre='" + nombre + '\'' +
                ", idUsuario='" + idUsuario + '\'' +
                ", fechaCompra='" + fechaCompra + '\'' +
                ", montoTotal=" + montoTotal +
                '}';
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
        values.put(DatabaseContract.DataBaseEntry.COLUMN_NAME_NOMBRE, getNombre());
        values.put(DatabaseContract.DataBaseEntry.COLUMN_NAME_ID_USUARIO, getIdUsuario());
        values.put(DatabaseContract.DataBaseEntry.COLUMN_NAME_FECHA_COMPRA, getFechaCompra());
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
                DatabaseContract.DataBaseEntry.COLUMN_NAME_NOMBRE,
                DatabaseContract.DataBaseEntry.COLUMN_NAME_ID_USUARIO,
                DatabaseContract.DataBaseEntry.COLUMN_NAME_FECHA_COMPRA,
                DatabaseContract.DataBaseEntry.COLUMN_NAME_MONTO_TOTAL,
        };

        // Filtro para el WHERE
        //String selection = DatabaseContract.DataBaseEntry._ID + " = ?";
        String selection = DatabaseContract.DataBaseEntry.COLUMN_NAME_ID_USUARIO + " = ?";
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

        System.out.println(String.valueOf(cursor.getCount()));
        if(cursor.moveToFirst() && cursor.getCount() > 0) {
            this.setId(cursor.getString(cursor.getColumnIndexOrThrow(
                    DatabaseContract.DataBaseEntry._ID)));
            this.setNombre(cursor.getString(cursor.getColumnIndexOrThrow(
                    DatabaseContract.DataBaseEntry.COLUMN_NAME_NOMBRE)));
            this.setIdUsuario(cursor.getString(cursor.getColumnIndexOrThrow(
                    DatabaseContract.DataBaseEntry.COLUMN_NAME_ID_USUARIO)));
            this.setFechaCompra(cursor.getString(cursor.getColumnIndexOrThrow(
                    DatabaseContract.DataBaseEntry.COLUMN_NAME_FECHA_COMPRA)));
            this.setMontoTotal(cursor.getDouble(cursor.getColumnIndexOrThrow(
                    DatabaseContract.DataBaseEntry.COLUMN_NAME_MONTO_TOTAL)));
        }
        this.setDetalle(leerProductosCompra(context, identificacion));
    }


    /**
     * Leer los productos grabados en cada lista del usuario
     */
    public ArrayList<Producto> leerProductosCompra (Context context, String listaID){
        DatabaseHelper DatabaseHelper = new DatabaseHelper(context);

        // Obtiene la base de datos en modo lectura
        SQLiteDatabase db = DatabaseHelper.getReadableDatabase();

        // Define cuales columnas quiere solicitar // en este caso todas las de la clase
        String[] projection = {
                DatabaseContract.DataBaseEntry._ID,
                DatabaseContract.DataBaseEntry.COLUMN_NAME_NOMBRE,
                DatabaseContract.DataBaseEntry.COLUMN_NAME_PRECIO,
                DatabaseContract.DataBaseEntry.COLUMN_NAME_IMAGEN
        };

        // Filtro para el WHERE
        String selection = DatabaseContract.DataBaseEntry.COLUMN_NAME_ID_LISTA + " = ?";
        String[] selectionArgs = {listaID};

        // Resultados en el cursor
        Cursor cursor = db.query(
                DatabaseContract.DataBaseEntry.TABLE_NAME_DETALLE_LISTA, // tabla
                projection, // columnas
                selection, // where
                selectionArgs, // valores del where
                null, // agrupamiento
                null, // filtros por grupo
                null // orden
        );

        ArrayList<Producto> misProductos = new ArrayList<>();
        System.out.println(String.valueOf(cursor.getCount()));
        if(cursor.moveToFirst()) {
            do {
                /*Producto miProducto = new Producto();
                miProducto.setId(cursor.getString(cursor.getColumnIndexOrThrow(
                        DatabaseContract.DataBaseEntry._ID)));
                miProducto.setNombre(cursor.getString(cursor.getColumnIndexOrThrow(
                        DatabaseContract.DataBaseEntry.COLUMN_NAME_NOMBRE)));
                miProducto.set(cursor.getString(cursor.getColumnIndexOrThrow(
                        DatabaseContract.DataBaseEntry.COLUMN_NAME_ID_USUARIO)));
                miProducto.setFechaCompra(cursor.getString(cursor.getColumnIndexOrThrow(
                        DatabaseContract.DataBaseEntry.COLUMN_NAME_FECHA_COMPRA)));
                misProductos.add(miProducto);*/
            } while (cursor.moveToNext());
        }
        return misProductos;
    }


    /**
     * Actualizar una lista de compras en la base de datos
     */
    public int actualizar(Context context) {
        DatabaseHelper DatabaseHelper = new DatabaseHelper(context);
        SQLiteDatabase db = DatabaseHelper.getReadableDatabase();

        ContentValues values = new ContentValues();
        values.put(DatabaseContract.DataBaseEntry.COLUMN_NAME_NOMBRE, getNombre());
        values.put(DatabaseContract.DataBaseEntry.COLUMN_NAME_ID_USUARIO, getIdUsuario());
        values.put(DatabaseContract.DataBaseEntry.COLUMN_NAME_FECHA_COMPRA, getFechaCompra());
        values.put(DatabaseContract.DataBaseEntry.COLUMN_NAME_MONTO_TOTAL, getMontoTotal());

        // Criterio de actualizacion
        String selection = DatabaseContract.DataBaseEntry._ID + " LIKE ?";
        // Se detallan los argumentos
        String[] selectionArgs = {getId()};
        // Actualizar la base de datos
        return db.update(DatabaseContract.DataBaseEntry.TABLE_NAME_LISTA_COMPRA, values, selection, selectionArgs);
    }
}
