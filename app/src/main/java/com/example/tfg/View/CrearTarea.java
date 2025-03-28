package com.example.tfg.View;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tfg.R;
import com.example.tfg.model.Tarea;

public class CrearTarea extends AppCompatActivity {

    private EditText editTextTitulo;
    private EditText editTextDescripcion;
    private Button btnGuardar;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.crear_tarea);

        editTextTitulo = findViewById(R.id.editTextTitulo);
        editTextDescripcion = findViewById(R.id.editTextDescripcion);
        btnGuardar = findViewById(R.id.btnGuardar);

        btnGuardar.setOnClickListener(v -> {
            String titulo = editTextTitulo.getText().toString().trim();
            String descripcion = editTextDescripcion.getText().toString().trim();

            if (!titulo.isEmpty()) {
                Tarea nuevaTarea = new Tarea();
                nuevaTarea.setTitulo(titulo);
                nuevaTarea.setDescripcion(descripcion);

                // Guardar la tarea (necesitarás un ViewModel o DB)
                finish();
            } else {
                Toast.makeText(this, "Por favor ingresa un título", Toast.LENGTH_SHORT).show();
            }
        });
    }
}