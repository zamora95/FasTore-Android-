package com.zamora.fastoreapp;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.zamora.fastoreapp.Adapters.AdapterListasComprasUsuario;
import com.zamora.fastoreapp.Clases.ListaCompras;

import java.util.ArrayList;

/**
 * Created by Zamora on 01/04/2017.
 */

public class ListasCompraActivity extends AppCompatActivity{
    public static ArrayList<ListaCompras> arregloListasCompra;
    private static String usuario;
    private AdapterListasComprasUsuario adapter;
    private int listaSeleccionada;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lista_compras);
        //usuario = getIntent().getExtras().getString("usuario");
        usuario = "10";
        cargarListas();
    }

    public void cargarListas(){
        ListaCompras instancia = new ListaCompras();
        arregloListasCompra = instancia.leer(this, usuario);

        ListView lv = (ListView) findViewById(R.id.listaCompras);
        adapter = new AdapterListasComprasUsuario(this, arregloListasCompra);
        lv.setAdapter(adapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                listaSeleccionada = arregloListasCompra.indexOf(parent.getAdapter().getItem(position));
                Toast.makeText(getApplicationContext(), parent.getAdapter().getItem(position).toString(), Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_search, menu);
        MenuItem item = menu.findItem(R.id.menuSearch);

        SearchView searchView = (SearchView) item.getActionView();
        searchView.setQueryHint("Buscar...");

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }
}
