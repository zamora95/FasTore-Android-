package com.zamora.fastoreapp;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.zamora.fastoreapp.Clases.ListaCompras;
import com.zamora.fastoreapp.Clases.Producto;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by Zamora on 01/05/2017.
 */

public class NuevoProductoDialog extends Dialog implements View.OnClickListener{
    private ListaCompras listaCompras;
    private EditText txtNombre, txtPrecio;
    private Button btnAgregar;
    public Context context;

    public NuevoProductoDialog(Context context, ListaCompras listaCompras) {
        super(context);
        this.context = context;
        this.listaCompras = listaCompras;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.dialog_nuevo_producto);

        txtNombre = (EditText) findViewById(R.id.nombre_input);
        txtPrecio = (EditText) findViewById(R.id.precio_input);
        btnAgregar = (Button) findViewById(R.id.btnCrear);

        btnAgregar.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.btnCrear:
                Producto nuevoProducto = new Producto();
                nuevoProducto.setNombre(txtNombre.getText().toString());
                if (!txtPrecio.getText().toString().equals("")) {
                    nuevoProducto.setPrecio(Double.parseDouble(txtPrecio.getText().toString()));
                }
                long idRetorno = nuevoProducto.insertar(context);
                if (idRetorno != -1) {
                    Toast.makeText(context, "Se insertó " + nuevoProducto.toString(), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "Error al insertar el producto", Toast.LENGTH_SHORT).show();
                }
                idRetorno = nuevoProducto.insertarDetalle(context, listaCompras.getId());
                if (idRetorno != -1) {
                    Toast.makeText(context, "Se insertó el detalle relacionado", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "Error al insertar el detalle", Toast.LENGTH_SHORT).show();
                }
                ArrayList<Producto> listaProductos = listaCompras.getDetalle();
                listaProductos.add(nuevoProducto);
                listaCompras.setDetalle(listaProductos);

                this.dismiss();
                break;

            default:
                break;
        }

    }
}
