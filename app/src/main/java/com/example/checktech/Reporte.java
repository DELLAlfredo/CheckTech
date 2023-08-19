package com.example.checktech;

import androidx.appcompat.app.AppCompatActivity;

import com.example.checktech.databinding.ActivityReporteBinding;
import com.example.checktech.db.DbHelper;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.os.Bundle;
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

    TableLayout tabla;
    DbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityReporteBinding = ActivityReporteBinding.inflate(getLayoutInflater());
        setContentView(activityReporteBinding.getRoot());
        tabla = findViewById(R.id.tabla);



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
        String[] headers = { "Nombre de Docente", "Aula","Hora","Acción", "Fecha" };
        for (String header : headers) {
            TextView textView = new TextView(this);
            textView.setText(header);
            textView.setPadding(10, 5, 5, 5);
            textView.setBackgroundResource(R.color.purple_200);
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
            int fecha = llena.getColumnIndex("Fecha");
            rowData.add(llena.getString(fecha));

            for (int i = 0; i < rowData.size(); i++) {
                TextView textView = new TextView(this);
                textView.setText(rowData.get(i));
                textView.setPadding(10, 5, 10, 5); // Ajusta el padding según tus necesidades
                textView.setBackgroundResource(R.color.teal_200);
                dataRow.addView(textView);
            }
            tabla.addView(dataRow);
        }

        llena.close();
        db.close();
    }

}