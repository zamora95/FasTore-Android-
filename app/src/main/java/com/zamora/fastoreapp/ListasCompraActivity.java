package com.zamora.fastoreapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.zamora.fastoreapp.Adapters.AdapterListasComprasUsuario;
import com.zamora.fastoreapp.Clases.ListaCompras;
import com.zamora.fastoreapp.Clases.Usuario;

import java.util.ArrayList;

/**
 * Created by Zamora on 01/04/2017.
 */

public class ListasCompraActivity extends AppCompatActivity{
    public static ArrayList<ListaCompras> arregloListasCompra;
    private static String idUsuario;
    private Usuario usuario;
    private AdapterListasComprasUsuario adapter;
    private int listaSeleccionada;
    public static String fechaSeleccionada;

    public ArrayList<ListaCompras> getListas() {
        return usuario.getListasCompras();
    }
    String nombre;
    String email;
    String imagen;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lista_compras);
         nombre = getIntent().getExtras().getString("nombre");
         email = getIntent().getExtras().getString("email");
         imagen = getIntent().getExtras().getString("image");
        getSupportActionBar().setTitle("Mis listas de compra");
        //idUsuario = getIntent().getExtras().getString("idUsuario");
        idUsuario = "10";
        leerUsuario(idUsuario);
        cargarListas();
    }

    
    public void leerUsuario(String identificacion) {
        this.usuario = new Usuario();
        this.usuario.leer(getApplicationContext(), identificacion);
    }

    
    public void cargarListas(){
        arregloListasCompra = usuario.getListasCompras();

        ListView lv = (ListView) findViewById(R.id.listaCompras);
        adapter = new AdapterListasComprasUsuario(this, arregloListasCompra);
        lv.setAdapter(adapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                listaSeleccionada = arregloListasCompra.indexOf(parent.getAdapter().getItem(position));
                //Toast.makeText(getApplicationContext(), String.valueOf(listaSeleccionada), Toast.LENGTH_SHORT).show();
                //Toast.makeText(getApplicationContext(), parent.getAdapter().getItem(position).toString(), Toast.LENGTH_LONG).show();

                ListaCompras selectedList = (ListaCompras) parent.getAdapter().getItem(position);
                //String idLista = (ListaCompras) parent.getAdapter().getItem(position);
                Intent intent = new Intent(getApplicationContext(), ProductosListaActivity.class);
                intent.putExtra("idLista", selectedList.getId());
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Intent intent = getIntent();
        finish();
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_search, menu);
        /*MenuItem item = menu.findItem(R.id.itemSearch);
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
        });*/
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.itemAdd:
                //Intent nuevaLista = new Intent(this, NuevaListaActivity.class);
                //nuevaLista.putExtra("cantListas", arregloListasCompra.size()+1);
                //startActivity(nuevaLista);

                NuevaListaDialog nla = new NuevaListaDialog(ListasCompraActivity.this, idUsuario, arregloListasCompra.size()+1);
                nla.show();
                return true;
            case R.id.itemProfile:
                DialogProfile dpf = new DialogProfile(ListasCompraActivity.this,nombre,email,imagen);
                dpf.show();
                return true;


            default:
                return super.onOptionsItemSelected(item);
        }
    }



}
