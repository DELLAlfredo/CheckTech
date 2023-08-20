package com.example.checktech;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.checktech.db.DbHelper;
import com.google.android.material.textfield.TextInputEditText;

public class Login extends AppCompatActivity {
    EditText email,contraseña;
    Button loginButton,booton;
    String Paswword = "123";
    String Usuario = "root";
    private Object backgroundTint;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email = findViewById(R.id.email);
        contraseña = findViewById(R.id.contraseña);
        loginButton = findViewById(R.id.loginButton);
        booton=findViewById(R.id.button);
        recuperarPreferencias();


        booton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                eliminarBaseDeDatos();

            }
        });
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String frase = email.getText().toString();
                String pass = contraseña.getText().toString();
                if (email.getText().toString().equalsIgnoreCase(Usuario) && contraseña.getText().toString().equalsIgnoreCase(Paswword)) {
                    Toast.makeText(getApplicationContext(), "Ha iniciado sesión correctamente", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Login.this, clases.class);  //Creamos el intent y le indicamos desde donde vamos (this) y a donde vamos (OtraActividad.class)
                    startActivity(intent);  //Abrimos la otra actividad
                    guardarPreferencias();
                } else {
                    email.setError("Ingresa tu nombre de usuario correcto");
                    contraseña.setError("Ingresa la contraseña correcta");
                    Toast.makeText(getApplicationContext(), "User o contraseña incorrectos", Toast.LENGTH_SHORT).show();

                    camposVacios();
                }
            }
        });
    }
        private void camposVacios(){
            Toast.makeText(Login.this, "Campos vacios", Toast.LENGTH_SHORT).show();
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
    public void moveToRegistration(View view) {
        startActivity(new Intent(getApplicationContext(), Registro.class));
        finish();
    }


    private  void guardarPreferencias(){
        SharedPreferences preferences   = getSharedPreferences("PreferenciasLogin", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor= preferences.edit();
        editor.putString("email",Usuario);
        editor.putString("contraseña",Paswword);
        editor.putBoolean("sesion",true);
        editor.commit();
    }

    private void recuperarPreferencias(){
        SharedPreferences preferences=getSharedPreferences("PreferenciasLogin",Context.MODE_PRIVATE);
        email.setText(preferences.getString("email",""));
        contraseña.setText(preferences.getString("contraseña",""));
    }


    private void eliminarBaseDeDatos() {
        // Crea una instancia de la clase SQLiteOpenHelper
        DbHelper dbHelper = new DbHelper(this);

        // Elimina la base de datos utilizando deleteDatabase()
        boolean eliminada = this.deleteDatabase(dbHelper.getDatabaseName());

        // Verifica si se eliminó la base de datos correctamente
        if (eliminada) {
            Toast.makeText(this, "Base de datos eliminada correctamente", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "No se pudo eliminar la base de datos", Toast.LENGTH_SHORT).show();
        }
    }
    }
