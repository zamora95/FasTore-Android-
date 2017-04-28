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

import java.util.ArrayList;

/**
 * Created by Zamora on 01/04/2017.
 */

public class ListasCompraActivity extends AppCompatActivity{
    public static ArrayList<ListaCompras> arregloListasCompra;
    private static String usuario;
    private AdapterListasComprasUsuario adapter;
    private int listaSeleccionada;
    public static String fechaSeleccionada;
    private LinearLayout ProfSection;
    private Button SingOut;
    private TextView Name,Email;
    private ImageView ProfPic;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lista_compras);
        ProfSection = (LinearLayout) findViewById(R.id.profSection);
        SingOut = (Button) findViewById(R.id.btnLogOut);
        Name = (TextView) findViewById(R.id.name);
        Email = (TextView) findViewById(R.id.email);
        ProfPic = (ImageView) findViewById(R.id.profPic);
        String nombre = getIntent().getExtras().getString("nombre");
        String email = getIntent().getExtras().getString("email");
        String imagen = getIntent().getExtras().getString("image");
        Name.setText(nombre);
        Email.setText(email);
        if(imagen != "") {
            Glide.with(this).load(imagen).into(ProfPic);
        } else {
            ProfPic.setImageResource(R.drawable.photo);
        }
        getSupportActionBar().setTitle("Mis listas de compra");
        //usuario = getIntent().getExtras().getString("usuario");
        usuario = "10";
        cargarListas();
        SingOut.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                MainActivity main = new MainActivity();
                main.singOut();
            }
        });
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

                NuevaListaDialog nla = new NuevaListaDialog(ListasCompraActivity.this, usuario, arregloListasCompra.size()+1);
                nla.show();

                return true;


            default:
                return super.onOptionsItemSelected(item);
        }
    }



}
