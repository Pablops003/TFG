package com.example.tfg.view;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.tfg.API.RetrofitCliente;
import com.example.tfg.R;
import com.example.tfg.util.SesionManager;
import com.example.tfg.viewModel.UsuarioViewModel;

import java.util.UUID;

public class Login extends AppCompatActivity {

    private EditText usernameEditText, passwordEditText;
    private Button loginButton, registerButton;
    private UsuarioViewModel usuarioViewModel;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        usernameEditText = findViewById(R.id.etUsername);
        passwordEditText = findViewById(R.id.etPassword);
        loginButton = findViewById(R.id.btnLogin);
        registerButton = findViewById(R.id.btnRegister);

        usuarioViewModel = new ViewModelProvider(this).get(UsuarioViewModel.class);

        loginButton.setOnClickListener(v -> {
            String username = usernameEditText.getText().toString().trim();
            String password = passwordEditText.getText().toString().trim();

            usuarioViewModel.loginUsuario(username, password).observe(this, usuarioDTO -> {
                if (usuarioDTO != null) {
                    new SesionManager(this).saveLogin(username, password);

                    RetrofitCliente.username = username;
                    RetrofitCliente.password = password;
                    Toast.makeText(this, "Login correcto", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(this, MainActivity.class);
                    intent.putExtra("id", UUID.fromString(String.valueOf(usuarioDTO.getId())));
                    intent.putExtra("username", usuarioDTO.getUsername());
                    intent.putExtra("password", password);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(this, "Credenciales incorrectas", Toast.LENGTH_SHORT).show();
                }
            });

        });

        registerButton.setOnClickListener(v -> startActivity(new Intent(this, Registrar.class)));
    }
}
