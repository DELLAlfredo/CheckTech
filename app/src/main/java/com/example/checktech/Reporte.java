package com.example.checktech;

import androidx.appcompat.app.AppCompatActivity;

import com.example.checktech.databinding.ActivityReporteBinding;
import com.example.checktech.db.DbHelper;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class Reporte extends menu {

    ActivityReporteBinding activityReporteBinding;
    Spinner SpAction, SpHORA;
    TableLayout tabla;
    DbHelper dbHelper;
    DatePicker dpfecha;
    EditText txtfecha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityReporteBinding = ActivityReporteBinding.inflate(getLayoutInflater());
        setContentView(activityReporteBinding.getRoot());
        allocateActivityTitle("Reporte");

        tabla = findViewById(R.id.tabla);
        SpAction = findViewById(R.id.spopcion);
        txtfecha = findViewById(R.id.etfecha);
        dpfecha = findViewById(R.id.dtfecha);
        SpHORA = findViewById(R.id.sphora);

        txtfecha.setText(getfecha());
///////////////////////////
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            dpfecha.setOnDateChangedListener(new DatePicker.OnDateChangedListener() {
                @Override
                public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                    String fechaSeleccionada = String.format("%04d-%02d-%02d", year, monthOfYear + 1, dayOfMonth);
                    txtfecha.setText(fechaSeleccionada);
                    dpfecha.setVisibility(View.GONE);
                }
            });
        }
///////////////////////////////Acciones//////////////////////////////////////////////////////////////////
        String[] crud = {"IMPARTIDA", "NO IMPARTIDA", "CLASE INCOMPLETA", "SUSPENCION"};
        ArrayAdapter<String> AdapterCrud = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, crud);
        SpAction.setAdapter(AdapterCrud);
/////////////////////////////////////////////////////////
        String[] horas = {"7AM-8AM", "8AM-9AM", "9AM-10AM", "10AM-11AM", "11AM-12AM", "12AM-1PM", "1PM-2PM", "2PM-3PM"};
        ArrayAdapter<String> Adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, horas);
        SpHORA.setAdapter(Adapter);
        ////////////////////////////////////////////////////////
        SpAction.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedAction = parent.getItemAtPosition(position).toString();
                llenaCampos(selectedAction);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        TableLayout tabla = findViewById(R.id.tabla);
        dbHelper = new DbHelper(this);

        // Obtener una instancia de la base de datos
        SQLiteDatabase db = new DbHelper(this).getReadableDatabase();

        // Definir las columnas que deseas obtener de la tabla chequeo_clases
        String[] columnas = {"nombreDocente", "nombreAula","Hora", "Accion"};

        // Realizar la consulta a la base de datos
        Cursor reporte = db.query("chequeo_clases", columnas, null, null, null, null, null);

        // Iterar sobre el cursor para obtener los datos y mostrarlos en la tabla
        if (reporte.moveToFirst()) {
            do {
                // Obtener los valores de las columnas
                int nomAula = reporte.getColumnIndex("nombreDocente");
                String nombreAula = reporte.getString(nomAula);
                int nomDocente = reporte.getColumnIndex("nombreAula");
                String nombreDocente = reporte.getString(nomDocente);

                int DocenteHora = reporte.getColumnIndex("Hora");
                String DocentHora = reporte.getString(DocenteHora);

                int nomAccion = reporte.getColumnIndex("Accion");
                String accion = reporte.getString(nomAccion);

                // Crear una nueva fila en la tabla
                TableRow fila = new TableRow(this);

                // Crear los TextViews para mostrar los datos en la fila
                TextView txtNombreAula = new TextView(this);
                txtNombreAula.setText(nombreAula);
                TextView txthora = new TextView(this);
                txthora.setText(DocentHora);
                TextView txtNombreDocente = new TextView(this);
                txtNombreDocente.setText(nombreDocente);
                TextView txtAccion = new TextView(this);
                txtAccion.setText(accion);



                // Agregar los TextViews a la fila
                fila.addView(txtNombreAula);
                fila.addView(txtNombreDocente);
                fila.addView(txthora);
                fila.addView(txtAccion);

                // Agregar la fila a la tabla
                tabla.addView(fila);

            } while (reporte.moveToNext());
        }

        // Cerrar el cursor y la conexión a la base de datos
        reporte.close();
        db.close();
    }
    private void llenaCampos(String action) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String query = "SELECT nombreDocente, nombreAula,Hora, Accion FROM chequeo_clases WHERE Accion = ?";
        String[] selectionArgs = { action };
        Cursor llena = db.rawQuery(query, selectionArgs);

        // Limpiar la tabla antes de agregar los nuevos datos
        tabla.removeAllViews();

        // Crear la fila de encabezados
        TableRow headerRow = new TableRow(this);
        String[] headers = { "Nombre de Docente", "Aula","Hora","Acción","Fecha" };
        for (String header : headers) {
            TextView textView = new TextView(this);
            textView.setText(header);
            textView.setPadding(10, 5, 5, 5);
            textView.setBackgroundResource(R.color.tabla);
            textView.setTypeface(null, Typeface.BOLD);
            headerRow.addView(textView);
        }
        tabla.addView(headerRow);

        // Agregar las filas con los datos
        while (llena.moveToNext()) {
            TableRow dataRow = new TableRow(this);
            List<String> rowData = new ArrayList<>();
            int nomDocente = llena.getColumnIndex("nombreDocente");
            rowData.add(llena.getString(nomDocente));
            int nomAula = llena.getColumnIndex("nombreAula");
            rowData.add(llena.getString(nomAula));
            int docentehora = llena.getColumnIndex("Hora");
            rowData.add(llena.getString(docentehora));
            int accionnom = llena.getColumnIndex("Accion");
            rowData.add(llena.getString(accionnom));

            for (int i = 0; i < rowData.size(); i++) {
                TextView textView = new TextView(this);
                textView.setText(rowData.get(i));
                textView.setPadding(10, 5, 10, 5);

                // Establece el color de fondo según la acción
                if (i == rowData.size() - 1) { // Última columna (acción)
                    String accion = rowData.get(i);
                    if ("IMPARTIDA".equals(accion)) {
                        textView.setBackgroundResource(R.color.colorImpartida);
                    } else if ("NO IMPARTIDA".equals(accion)) {
                        textView.setBackgroundResource(R.color.colorNoImpartida);
                    } else if ("CLASE INCOMPLETA".equals(accion)) {
                        textView.setBackgroundResource(R.color.colorRetardo); // Define el color deseado
                    } else if ("SUSPENCION".equals(accion)) {
                        textView.setBackgroundResource(R.color.colorsuspencion); // Define el color deseado
                    } else {
                        textView.setBackgroundResource(R.color.campostabla); // Color predeterminado si no coincide con ninguna acción
                    }
                } else {
                    textView.setBackgroundResource(R.color.campostabla);
                }

                dataRow.addView(textView);
            }
            tabla.addView(dataRow);
        }

        llena.close();
        db.close();
    }
    public String getfecha(){

        String dia = "";
        if (dpfecha != null) {
            dia = String.format("%02d", dpfecha.getDayOfMonth()-1);
        }

        String mes = "";
        if (dpfecha != null) {
            mes = String.format("%02d", dpfecha.getMonth() + 1);
        }

        String año = "";
        if (dpfecha != null) {
            año = String.format("%04d", dpfecha.getYear());
        }

        return año + "-" + mes + "-" + dia;


    }

    public void muestraCalendario(View View){
        dpfecha.setVisibility(View.VISIBLE);
    }
}