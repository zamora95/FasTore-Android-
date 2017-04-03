package com.zamora.fastoreapp;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import java.util.Calendar;

/**
 * Created by Zamora on 01/04/2017.
 */

public class AlertDialog extends AppCompatActivity {
    Button btnFecha;
    EditText txtFecha, txtHora;
    private int dd, mm, yyyy, hora, minuto;

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        setContentView(R.layout.dialog_nueva_lista);

        //btnFecha = (Button) findViewById(R.id.btnFecha);
        txtFecha = (EditText) findViewById(R.id.fecha_input);
        txtHora = (EditText) findViewById(R.id.hora_input);

        txtFecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar calendar = Calendar.getInstance();
                dd = calendar.get(Calendar.DAY_OF_MONTH);
                mm = calendar.get(Calendar.MONTH);
                yyyy = calendar.get(Calendar.YEAR);

                DatePickerDialog datePickerDialog = new DatePickerDialog(getApplicationContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        String date = dayOfMonth + "-" + (monthOfYear+1) +"-" + year;
                        txtFecha.setText(date);
                    }
                }, yyyy, mm, dd);
                datePickerDialog.show();
            }
        });

    }

}
