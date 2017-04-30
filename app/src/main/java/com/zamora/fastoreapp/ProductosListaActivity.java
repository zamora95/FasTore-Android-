package com.zamora.fastoreapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.zamora.fastoreapp.Adapters.AdapterProductosCompra;
import com.zamora.fastoreapp.Clases.ListaCompras;
import com.zamora.fastoreapp.Clases.Producto;

import java.util.ArrayList;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.L;
import static android.os.Build.VERSION_CODES.M;
import static com.zamora.fastoreapp.ListasCompraActivity.arregloListasCompra;

/**
 * Created by Sergio on 13/04/2017.
 */

public class ProductosListaActivity extends AppCompatActivity {
    private AdapterProductosCompra adapter;
    String idLista;
    ListaCompras listaCompras;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lista_productos_compra);

        Intent intent = getIntent();
        idLista = intent.getStringExtra("idLista");

        listaCompras = new ListaCompras();
        listaCompras.leer(this, idLista);
        String nombre = listaCompras.getNombre();
        System.out.println("Nombre de la lista:" + nombre);

        System.out.println("Productos de la lista " + idLista);

        cargarListas();
        getSupportActionBar().setTitle(listaCompras.getNombre());
    }

    public void cargarListas(){
        ArrayList<Producto> productos = listaCompras.getDetalle();

        for (int i = 0; i < productos.size(); i++) {
            System.out.println(productos.get(i).toString());
        }

        ListView lv = (ListView) findViewById(R.id.productList);
        adapter = new AdapterProductosCompra(this, productos);
        lv.setAdapter(adapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //int productoSeleccionado = productos.indexOf(parent.getAdapter().getItem(position));
                Toast.makeText(getApplicationContext(), parent.getAdapter().getItem(position).toString(), Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_productos_lista, menu);
        return true;
    }

    @Override
    protected void onRestart(){
        super.onRestart();
        Intent i = getIntent();
        finish();
        startActivity(i);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.itemAdd:
                /*
                Intent intento = new Intent(getApplicationContext(), NuevoProductoActivity.class);
                intento.putParcelableArrayListExtra("listaProductos", (ArrayList<? extends Parcelable>) arregloListaProductosCompra);
                startActivity(intento);*/

                /*Intent intento = new Intent(getApplicationContext(), NuevoProductoActivity.class);
                intento.putExtra("idLista", idLista);
                intento.putExtra("usuario", usuario);
                startActivity(intento);*/

                return true;


            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
