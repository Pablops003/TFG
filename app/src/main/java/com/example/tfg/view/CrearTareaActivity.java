package com.example.tfg.view;

import static com.example.tfg.R.*;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import com.example.patareas.model.Tarea;
import com.example.tfg.R;
import com.example.tfg.viewModel.TareaViewModel;

public class CrearTareaActivity extends AppCompatActivity {
    public static final String EXTRA_ID = "com.example.patareas.EXTRA_ID";

    private EditText editTextTitulo;
    private EditText editTextDescripcion;
    private Spinner spinnerPrioridad;
    private Spinner spinnerCategoria;
    private TareaViewModel tareaViewModel;
    private int tareaId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_tarea);

        editTextTitulo = findViewById(R.id.edit_text_titulo);
        editTextDescripcion = findViewById(R.id.edit_text_descripcion);
        spinnerPrioridad = findViewById(R.id.spinner_prioridad);
        spinnerCategoria = findViewById(R.id.spinner_categoria);

        tareaViewModel = new ViewModelProvider(this).get(TareaViewModel.class);

        setupSpinners();

        if (getIntent().hasExtra(EXTRA_ID)) {
            setTitle("Editar Tarea");
            tareaId = getIntent().getIntExtra(EXTRA_ID, -1);

            tareaViewModel.getTareaById(tareaId).observe(this, tarea -> {
                if (tarea != null) {
                    editTextTitulo.setText(tarea.getTitulo());
                    editTextDescripcion.setText(tarea.getDescripcion());

                    int prioridadPosition = ((ArrayAdapter)spinnerPrioridad.getAdapter()).getPosition(tarea.getPrioridad());
                    spinnerPrioridad.setSelection(prioridadPosition);

                    int categoriaPosition = ((ArrayAdapter)spinnerCategoria.getAdapter()).getPosition(tarea.getCategoria());
                    spinnerCategoria.setSelection(categoriaPosition);
                }
            });
        } else {
            setTitle("Nueva Tarea");
        }
    }

    private void setupSpinners() {
        ArrayAdapter<CharSequence> prioridadAdapter = ArrayAdapter.createFromResource(this,
                R.array.prioridades_array, R.layout.spinner_item);
        prioridadAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerPrioridad.setAdapter(prioridadAdapter);

        ArrayAdapter<CharSequence> categoriaAdapter = ArrayAdapter.createFromResource(this,
                R.array.categorias_array, R.layout.spinner_item);
        categoriaAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCategoria.setAdapter(categoriaAdapter);
    }

    public void guardarTarea(View view) {
        String titulo = editTextTitulo.getText().toString().trim();
        String descripcion = editTextDescripcion.getText().toString().trim();
        String prioridad = spinnerPrioridad.getSelectedItem().toString();
        String categoria = spinnerCategoria.getSelectedItem().toString();

        if (TextUtils.isEmpty(titulo)) {
            editTextTitulo.setError("El título es obligatorio");
            editTextTitulo.requestFocus();
            return;
        }

        Tarea tarea = new Tarea(titulo, descripcion, categoria, prioridad);

        if (tareaId != -1) {
            tarea.setId(tareaId);
            tareaViewModel.update(tarea);
            Toast.makeText(this, "Tarea actualizada", Toast.LENGTH_SHORT).show();
        } else {
            tareaViewModel.insert(tarea);
            Toast.makeText(this, "Tarea creada", Toast.LENGTH_SHORT).show();
        }

        finish();
    }
}