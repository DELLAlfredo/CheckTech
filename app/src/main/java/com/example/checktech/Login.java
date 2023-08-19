package com.example.checktech;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

public class Login extends AppCompatActivity {
    EditText email,contraseña;
    Button loginButton;
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

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String frase = email.getText().toString();
                String pass = contraseña.getText().toString();
                if (email.getText().toString().equalsIgnoreCase(Usuario) && contraseña.getText().toString().equalsIgnoreCase(Paswword)) {
                    Toast.makeText(getApplicationContext(), "Ha iniciado sesión correctamente", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Login.this, abcAula.class);  //Creamos el intent y le indicamos desde donde vamos (this) y a donde vamos (OtraActividad.class)
                    startActivity(intent);  //Abrimos la otra actividad
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
    }
