package com.example.checktech;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.checktech.databinding.ActivityAbcDocentesBinding;
import com.example.checktech.databinding.ActivityClasesBinding;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.checktech.db.DbHelper;

public class abcDocentes extends menu {
    ActivityAbcDocentesBinding activityAbcDocentesBinding;
    EditText txtNombre,txtApellidos, txtId;
    Button btnGuardar;

    Spinner spAcademia, spCrud;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityAbcDocentesBinding = ActivityAbcDocentesBinding.inflate(getLayoutInflater());
        setContentView(activityAbcDocentesBinding.getRoot());
        txtNombre = findViewById(R.id.txtNombre);
        txtApellidos = findViewById(R.id.txtApellidos);
        txtId = findViewById(R.id.txtId);
        btnGuardar = findViewById(R.id.btnGuardar);
        spAcademia = findViewById(R.id.spAcademia);
        spCrud = findViewById(R.id.spCrud);


        String[] abc = {"ISIC","IIND","IGEM","IINA","IIA"};
        ArrayAdapter<String> Adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, abc);
        spAcademia.setAdapter(Adapter);

        String[] crud = {"Añadir","Actualizar","Buscar","Eliminar"};
        ArrayAdapter<String> AdapterCrud = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, crud);
        spCrud.setAdapter(AdapterCrud);


        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch(spCrud.getSelectedItem().toString()) {
                    case "Añadir":
                        if (!txtId.getText().toString().isEmpty() &&
                                !txtNombre.getText().toString().isEmpty() &&
                                !txtApellidos.getText().toString().isEmpty() &&
                                !spAcademia.getSelectedItem().toString().isEmpty()) {
                            add(Integer.parseInt(txtId.getText().toString()), txtNombre.getText().toString(), txtApellidos.getText().toString(), spAcademia.getSelectedItem().toString(),spCrud.getSelectedItem().toString());
                        }else{
                            Toast.makeText(abcDocentes.this, "Debes llenar los demas campos", Toast.LENGTH_SHORT).show();
                        }
                        break;
                    case "Actualizar":
                        if (!txtId.getText().toString().isEmpty() &&
                                !txtNombre.getText().toString().isEmpty() &&
                                !txtApellidos.getText().toString().isEmpty() &&
                                !spAcademia.getSelectedItem().toString().isEmpty()) {
                            edit(Integer.parseInt(txtId.getText().toString()), txtNombre.getText().toString(), txtApellidos.getText().toString(), spAcademia.getSelectedItem().toString(),spCrud.getSelectedItem().toString());
                        }else{
                            Toast.makeText(abcDocentes.this, "Los campos estan vacios", Toast.LENGTH_SHORT).show();
                        }
                        break;
                    case "Eliminar":
                        if(!txtId.getText().toString().isEmpty()){
                            delete(Integer.parseInt(txtId.getText().toString()), spCrud.getSelectedItem().toString());
                        }
                        break;
                    case "Buscar":
                        if(!txtId.getText().toString().isEmpty()){
                            search(Integer.parseInt(txtId.getText().toString()), spCrud.getSelectedItem().toString());
                        }
                        break;

                }
             /*       DbMaestros dbMaestros = new DbMaestros(abcMaestros.this);
                    long id = dbMaestros.InsertarMaestro(txtNombre.getText().toString(),txtApellidos.getText().toString(),spAcademia.getSelectedItem().toString());
                    if (id > 0){
                        limpiar();
                        Toast.makeText(abcMaestros.this, "REGISTRO GUARDADO", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(abcMaestros.this, "NO SE PUDO GUARDAR EL REGISTRO", Toast.LENGTH_SHORT).show();
                    }*/
            }
        });
    }
    public void add(int code, String nombre, String apellidos, String academia, String spCrud){
        if (spCrud.equals("Añadir")) {

            if (code != 0) {
                DbHelper admin = new DbHelper(abcDocentes.this, "POS", null, 2);
                SQLiteDatabase db = admin.getReadableDatabase();
                Cursor cursor = db.rawQuery("SELECT nombre,apellidos,academia from t_maestros where id_maestro=" + code, null);
                if (cursor.moveToFirst()) {
                    Toast.makeText(abcDocentes.this, "Codigo ya existente \n elija otro codigo", Toast.LENGTH_LONG).show();
                } else {
                    ContentValues registro = new ContentValues();
                    registro.put("id_maestro", code);
                    registro.put("nombre", nombre);
                    registro.put("apellidos", apellidos);
                    registro.put("academia", academia);

                    db.insert("t_maestros", null, registro);
                    db.close();
                    txtId.setText("");
                    txtApellidos.setText("");
                    txtNombre.setText("");
                    Toast.makeText(abcDocentes.this, "Se Guardo el registro", Toast.LENGTH_SHORT).show();

                }

            } else {
                Toast.makeText(abcDocentes.this, "No se pudo añadir, llena los campos", Toast.LENGTH_SHORT).show();
            }
        }
    }
    public void edit(int code, String nombre, String apellidos, String academia, String spCrud){
        if (spCrud.equals("Actualizar")) {
            DbHelper admin = new DbHelper(abcDocentes.this, "POS", null, 2);
            SQLiteDatabase db = admin.getWritableDatabase();

            ContentValues registry = new ContentValues();

            registry.put("id_maestro", code);
            registry.put("nombre", nombre);
            registry.put("apellidos", apellidos);
            registry.put("academia", academia);

            int result = db.update("t_maestros", registry,"id_maestro ="+code, null);
            if (result != 0){
                Toast.makeText(abcDocentes.this, "Registro actualizado", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(abcDocentes.this, "No se puedo actualizar el registro, verifique por favor", Toast.LENGTH_SHORT).show();
            }
        }
    }
    public  void delete(int code, String spCrud) {
        if (spCrud.equals("Eliminar")) {
            DbHelper admin = new DbHelper(abcDocentes.this, "POS", null, 2);
            SQLiteDatabase db = admin.getWritableDatabase();
            int result = db.delete("t_maestros", "id_maestro=" + code, null);

            admin.close();
            txtNombre.setText("");
            txtApellidos.setText("");
            spAcademia.getSelectedItem().toString().isEmpty();

            if (result >= 1) {
                Toast.makeText(abcDocentes.this, "Se elimino al usuario", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(abcDocentes.this, "El usuario que desea eliminar, no existe!", Toast.LENGTH_SHORT).show();
            }
        }
    }
    public void search(int code, String spCrud){
        if(spCrud.equals("Buscar")) {
            if (code != 0) {
                DbHelper admin = new DbHelper(abcDocentes.this, "POS", null, 2);
                SQLiteDatabase db = admin.getWritableDatabase();
                Cursor cursor = db.rawQuery("select nombre, apellidos from t_maestros where id_maestro=" + code, null);
                if (cursor.moveToFirst()) {
                    txtNombre.setText(cursor.getString(0));
                    txtApellidos.setText(cursor.getString(1));
                    spAcademia.setSelection(cursor.getPosition());
                    db.close();

                } else {
                    Toast.makeText(abcDocentes.this, "El codigo que desea buscar no existe! ", Toast.LENGTH_SHORT).show();

                }
            } else {
                Toast.makeText(abcDocentes.this, "Escribe un codigo valido, no puede ser zero", Toast.LENGTH_SHORT).show();
            }
        }
    }
    /*public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.opcions_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.spAcademia:
                Intent intent =  new Intent(abcMaestros.this, abcAcademia.class);
                startActivity(intent);

                return true;
            case R.id.edNOMBRE:
                Intent carrera_intent =  new Intent(abcMaestros.this, abcCarrera.class);
                startActivity(carrera_intent);

                return true;
            case R.id.reporte_semanal:
                Intent reporte_intent =  new Intent(abcMaestros.this, ReporteSemanal.class);
                startActivity(reporte_intent);

                return true;
            case R.id.campoaula:
                Intent aula_intent =  new Intent(abcMaestros.this, abcAulas.class);
                startActivity(aula_intent);

                return true;
            case R.id.Salir:
                Intent salir =  new Intent(abcMaestros.this, Inicio_de_sesion.class);
                startActivity(salir);

                return true;
            case R.id.chequeoClases:
                Intent clases =  new Intent(abcMaestros.this, chequeoClases.class);
                startActivity(clases);
            default:
                return super.onOptionsItemSelected(item);
        }

    }*/
}