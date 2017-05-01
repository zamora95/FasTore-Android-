package com.zamora.fastoreapp;

import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.v7.app.AlertDialog;
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
import java.util.Locale;

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
    private final int REQ_CODE_SPEECH_INPUT = 100;
    private ArrayList<Producto> productos;

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

        productos = listaCompras.getDetalle();
        cargarListas();
        getSupportActionBar().setTitle(listaCompras.getNombre());
    }

    public void cargarListas(){
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
                Producto nuevoProducto = new Producto();
                nuevoProducto.setNombre("Bolsa de frijoles");
                //long numero = nuevoProducto.insertar(getApplicationContext());
                nuevoProducto.insertar(getApplicationContext());
                //System.out.println("El id del nuevo registro es: " + numero);
                //nuevoProducto.leerUltimoProducto(getApplicationContext());
                nuevoProducto.insertarDetalle(getApplicationContext(), idLista);
                onRestart();

                return true;

            case R.id.itemAddByVoice:
                promptSpeechInput();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * Showing google speech input dialog
     * */
    private void promptSpeechInput() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,
                "Habla ahora");
        try {
            startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);
        } catch (ActivityNotFoundException a) {
            Toast.makeText(getApplicationContext(),
                    "Disculpa, tu dispositivo no soporta esta función",
                    Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Receiving speech input
     * */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQ_CODE_SPEECH_INPUT: {
                if (resultCode == RESULT_OK && null != data) {

                    ArrayList<String> result = data
                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    //txtSpeechInput.setText(result.get(0));
                    //Toast.makeText(this, result.get(0), Toast.LENGTH_LONG).show();
                    confirmSpeechText(result.get(0));
                }
                break;
            }

        }
    }


    public AlertDialog confirmSpeechText(final String speechText) {
        final String capText = speechText.substring(0, 1).toUpperCase() + speechText.substring(1);
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Agregar")
                .setMessage(capText)
                .setPositiveButton("Sí",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Producto nuevoProducto = new Producto();
                                nuevoProducto.setNombre(capText);
                                nuevoProducto.insertar(getApplicationContext());
                                nuevoProducto.leerUltimoProducto(getApplicationContext());
                                nuevoProducto.insertarDetalle(getApplicationContext(), idLista);
                                dialog.cancel();
                            }
                        })
                .setNegativeButton("No",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //listener.onNegativeButtonClick();
                                dialog.cancel();
                                promptSpeechInput();
                            }
                        });

        return builder.show();
    }

}
