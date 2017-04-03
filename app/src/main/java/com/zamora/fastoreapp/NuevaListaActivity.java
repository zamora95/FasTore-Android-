package com.zamora.fastoreapp;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import static com.google.android.gms.internal.zzxo.hh;

/**
 * Created by Zamora on 02/04/2017.
 */

public class NuevaListaActivity extends AppCompatActivity{
    private int cantListas;
    private Button btnFecha;
    private EditText txtFecha, txtHora, txtNombre;
    private int dd, mm, yyyy, hora, minuto;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_nueva_lista);
        getSupportActionBar().setTitle("Nueva lista");

        cantListas = getIntent().getExtras().getInt("cantListas");

        //btnFecha = (Button) findViewById(R.id.btnFecha);
        txtFecha = (EditText) findViewById(R.id.fecha_input);
        txtHora = (EditText) findViewById(R.id.hora_input);
        txtNombre = (EditText) findViewById(R.id.nombre_input);

        txtNombre.setText("Lista de compras " + cantListas);


        txtFecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar calendar = Calendar.getInstance();
                final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
                try{
                    calendar.setTime(sdf.parse(txtFecha.getText().toString()));
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                dd = calendar.get(Calendar.DAY_OF_MONTH);
                mm = calendar.get(Calendar.MONTH);
                yyyy = calendar.get(Calendar.YEAR);

                DatePickerDialog datePickerDialog = new DatePickerDialog(NuevaListaActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        calendar.set(year, monthOfYear, dayOfMonth);
                        Date fecha = calendar.getTime();


                        //String date = dayOfMonth + "-" + (monthOfYear+1) +"-" + year;
                        txtFecha.setText(sdf.format(fecha));
                    }
                }, yyyy, mm, dd);
                datePickerDialog.show();
            }
        });

        txtHora.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar calendar = Calendar.getInstance();
                hora = calendar.get(Calendar.HOUR_OF_DAY);
                minuto = calendar.get(Calendar.MINUTE);

                TimePickerDialog timePickerDialog = new TimePickerDialog(NuevaListaActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        int hora = hourOfDay % 12;
                        if (hora == 0)
                            hora = 12;
                        txtHora.setText(String.format(Locale.ENGLISH, "%02d:%02d %s", hora, minute,
                                hourOfDay < 12 ? "am" : "pm"));
                    }
                }, hora, minuto, false);
                timePickerDialog.show();
            }
        });
    }
}
