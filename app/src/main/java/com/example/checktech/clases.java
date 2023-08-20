package com.example.checktech;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import com.example.checktech.databinding.ActivityClasesBinding;
import com.example.checktech.db.DbHelper;

import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class clases extends menu {
    ActivityClasesBinding activityClasesBinding;

    DbHelper dbHelperMaestro,dbHelperAulas,dbHelper;
    Button btnguardarclase;
    Spinner SpAula,SpHORA,SpDocente,SpAction;

    private List<String> AulaValues;
    private List<String> DocenteValues;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityClasesBinding = ActivityClasesBinding.inflate(getLayoutInflater());
        setContentView(activityClasesBinding.getRoot());
        allocateActivityTitle("Clases");

        AulaValues = new ArrayList<>();
        DocenteValues = new ArrayList<>();
        btnguardarclase = findViewById(R.id.btnguardarclase);

        btnguardarclase = findViewById(R.id.btnguardarclase);
        SpAula = findViewById(R.id.SpAula);
        SpHORA = findViewById(R.id.SpHORA);
        SpDocente = findViewById(R.id.SpDocente);
        SpAction = findViewById(R.id.SpAction);

        ////////////////////////Maestros////////////////////////////////////////////////////////
        dbHelperMaestro = new DbHelper(this);
        dbHelperAulas = new DbHelper(this);


        Cursor cursorMaestro = dbHelperMaestro.DatosMaestro("holds");

        if(cursorMaestro.moveToFirst()){
            do{
                String nombre = cursorMaestro.getString(0); // Valor de la primera columna
                String apellido = cursorMaestro.getString(1); // Valor de la segunda columna
                String valorConcatenado = nombre + " " + apellido; // Concatenación de los valores

                DocenteValues.add(valorConcatenado);

            }while(cursorMaestro.moveToNext());
        }
        SpDocente.setAdapter(new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, DocenteValues));



////////////////////////Aula////////////////////////////////////////////////////////

        Cursor cursorAula = dbHelperAulas.DatasAula();
        if(cursorAula.moveToFirst()){
            do{
                AulaValues.add(cursorAula.getString(0));

            }while(cursorAula.moveToNext());
        }

        SpAula.setAdapter(new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, AulaValues));


//////////////////////////horas///////////////////////////////////////
        String[] horas = {"7AM-8AM","8AM-9AM","9AM-10AM","10AM-11AM","11AM-12AM","12AM-1PM","1PM-2PM","2PM-3PM"};
        ArrayAdapter<String> Adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, horas);
        SpHORA.setAdapter(Adapter);


        ///////////////////////////////Acciones//////////////////////////////////////////////////////////////////
        String[] crud = {"IMPARTIDA","NO IMPARTIDA","CLASE INCOMPLETA","SUSPENCION"};
        ArrayAdapter<String> AdapterCrud = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, crud);
        SpAction.setAdapter(AdapterCrud);



        btnguardarclase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Obtener los valores seleccionados de los spinners y el texto ingresado en el EditText
                String aula = "";
                String hora = SpHORA.getSelectedItem().toString();
                String docente = "";
                String accion = SpAction.getSelectedItem().toString();


                // Verificar si se ha seleccionado un valor en el spinner SpAula
                if (SpAula.getSelectedItem() != null) {
                    aula = SpAula.getSelectedItem().toString();
                } else {
                    // Mostrar mensaje de error si no se ha seleccionado un valor en SpAula
                    Toast.makeText(clases.this, "Por favor,  llene los campos en Aulas", Toast.LENGTH_SHORT).show();
                    return;
                }
                // Verificar si se ha seleccionado un valor en el spinner SpDocente
                if (SpDocente.getSelectedItem() != null) {
                    docente = SpDocente.getSelectedItem().toString();
                } else {
                    // Mostrar mensaje de error si no se ha seleccionado un valor en SpDocente
                    Toast.makeText(clases.this, "Por favor, llene los campos en Maestros", Toast.LENGTH_SHORT).show();
                    return;
                }
                // Verificar si los demás campos están vacíos
                if (TextUtils.isEmpty(aula) || TextUtils.isEmpty(docente) || TextUtils.isEmpty(hora) || TextUtils.isEmpty(accion)) {
                    // Mostrar mensaje de error
                    Toast.makeText(clases.this, "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show();
                    return;
                }else {
                    Toast.makeText(clases.this, "Se guardo Correcta Mente", Toast.LENGTH_SHORT).show();
                }

                // Insertar los datos en la tabla "ChequeoClases"
                dbHelperMaestro.insertarChequeoClases(aula, hora, docente, accion);

                // Limpiar los campos después de guardar
                SpAula.setSelection(0);
                SpHORA.setSelection(0);
                SpDocente.setSelection(0);
                SpAction.setSelection(0);
            }
        });


    }
    protected void onDestroy() {
        super.onDestroy();
        if (dbHelper != null) {
            dbHelper.close();
        }
    }
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Salir");
        builder.setMessage("¿Desea salir de la aplicación?");
        builder.setPositiveButton("Sí", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finishAffinity(); // Cerrar todas las actividades y salir de la aplicación
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

}