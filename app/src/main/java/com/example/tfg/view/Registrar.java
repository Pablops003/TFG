package com.example.tfg.view;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.tfg.R;
import com.example.tfg.viewModel.UsuarioViewModel;

public class Registrar extends AppCompatActivity {

    private EditText usernameEditText, passwordEditText;
    private Button registerButton;
    private UsuarioViewModel usuarioViewModel;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activiry_register);  // Asegúrate que el nombre del layout esté bien escrito

        usernameEditText = findViewById(R.id.Nombre);
        passwordEditText = findViewById(R.id.Password);
        registerButton = findViewById(R.id.btnRegister);

        usuarioViewModel = new ViewModelProvider(this).get(UsuarioViewModel.class);

        registerButton.setOnClickListener(v -> {
            String username = usernameEditText.getText().toString().trim();
            String password = passwordEditText.getText().toString().trim();

            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Rellena todos los campos", Toast.LENGTH_SHORT).show();
                return;
            }

            usuarioViewModel.registrarUsuario(username, password).observe(this, resultado -> {
                if (resultado == null) {
                    Toast.makeText(this, "Error inesperado", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (resultado.equals("OK")) {
                    Toast.makeText(this, "Registro exitoso", Toast.LENGTH_SHORT).show();
                    finish(); // O redirigir
                } else if (resultado.contains("ERROR")) {
                    Toast.makeText(this, "El usuario ya existe", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Error desconocido en el registro", Toast.LENGTH_SHORT).show();
                }
            });


        });
    }
}
