package com.example.tfg.view;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.tfg.R;
import com.example.tfg.model.Tarea;
import com.example.tfg.util.AlarmaManager;
import com.example.tfg.viewModel.TareaViewModel;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import java.util.UUID;

public class CrearTareaActivity extends AppCompatActivity {
    public static final String EXTRA_ID = "com.example.tfg.EXTRA_ID";

    private EditText editTextTitulo;
    private EditText editTextDescripcion;
    private Spinner spinnerPrioridad;
    private Spinner spinnerCategoria;
    private EditText editTextFecha;
    private Long fechaSeleccionada;

    private UUID usuarioId;
    private String username;
    private String password;

    private Tarea tareaEditando = null;

    private TareaViewModel tareaViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_tarea);

        editTextTitulo = findViewById(R.id.edit_text_titulo);
        editTextDescripcion = findViewById(R.id.edit_text_descripcion);
        spinnerPrioridad = findViewById(R.id.spinner_prioridad);
        spinnerCategoria = findViewById(R.id.spinner_categoria);
        editTextFecha = findViewById(R.id.edit_text_fecha);

        tareaViewModel = new ViewModelProvider(this).get(TareaViewModel.class);

        setupSpinners();

        editTextFecha.setFocusable(false);
        editTextFecha.setOnClickListener(v -> mostrarSelectorFechaHora());

        usuarioId = (UUID) getIntent().getSerializableExtra("id");
        username = getIntent().getStringExtra("username");
        password = getIntent().getStringExtra("password");

        if (usuarioId == null || username == null || password == null) {
            Toast.makeText(this, "Error: usuario no identificado", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        UUID tareaId = (UUID) getIntent().getSerializableExtra(EXTRA_ID);
        if (tareaId != null) {
            cargarTareaRemota(tareaId);
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

    private void mostrarSelectorFechaHora() {
        final Calendar calendario = Calendar.getInstance(TimeZone.getTimeZone("Europe/Madrid"));

        DatePickerDialog datePicker = new DatePickerDialog(this,
                (view, year, month, day) -> {
                    calendario.set(year, month, day);
                    mostrarSelectorHora(calendario);
                },
                calendario.get(Calendar.YEAR),
                calendario.get(Calendar.MONTH),
                calendario.get(Calendar.DAY_OF_MONTH)
        );
        datePicker.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
        datePicker.show();
    }

    private void mostrarSelectorHora(Calendar calendario) {
        TimePickerDialog timePicker = new TimePickerDialog(this,
                (view, hour, minute) -> {
                    calendario.set(Calendar.HOUR_OF_DAY, hour);
                    calendario.set(Calendar.MINUTE, minute);

                    fechaSeleccionada = calendario.getTimeInMillis();

                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault());
                    sdf.setTimeZone(    TimeZone.getTimeZone("Europe/Madrid"));

                    editTextFecha.setText(sdf.format(calendario.getTime()));
                },
                calendario.get(Calendar.HOUR_OF_DAY),
                calendario.get(Calendar.MINUTE),
                true
        );
        timePicker.show();
    }


    private void cargarTareaRemota(UUID tareaId) {
        tareaViewModel.getTareaPorId(usuarioId, tareaId, username, password).observe(this, tarea -> {
            if (tarea != null) {
                tareaEditando = tarea;
                rellenarCampos(tarea);
            } else {
                Toast.makeText(this, "Error cargando la tarea", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }

    private void rellenarCampos(Tarea tarea) {
        editTextTitulo.setText(tarea.getTitulo());
        editTextDescripcion.setText(tarea.getDescripcion());
        fechaSeleccionada = tarea.getFecha().getTime();
        editTextFecha.setText(new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
                .format(tarea.getFecha()));

        spinnerPrioridad.setSelection(((ArrayAdapter<String>) spinnerPrioridad.getAdapter()).getPosition(tarea.getPrioridad()));
        spinnerCategoria.setSelection(((ArrayAdapter<String>) spinnerCategoria.getAdapter()).getPosition(tarea.getCategoria()));
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

        if (TextUtils.isEmpty(descripcion)) {
            editTextDescripcion.setError("La descripción es obligatoria");
            editTextDescripcion.requestFocus();
            return;
        }

        if (fechaSeleccionada == null) {
            editTextFecha.setError("La fecha es obligatoria");
            editTextFecha.requestFocus();
            return;
        }

        Date fechaFinal = new Date(fechaSeleccionada);

        if (tareaEditando == null) {
            Tarea nuevaTarea = new Tarea();
            nuevaTarea.setTitulo(titulo);
            nuevaTarea.setDescripcion(descripcion);
            nuevaTarea.setCategoria(categoria);
            nuevaTarea.setPrioridad(prioridad);
            nuevaTarea.setFecha(fechaFinal);
            nuevaTarea.setCompletada(false);
            nuevaTarea.setUsuarioId(usuarioId);

            tareaViewModel.crearTarea(usuarioId, nuevaTarea, username, password).observe(this, tareaCreada -> {
                if (tareaCreada != null) {
                    AlarmaManager.programarAlarma(this, tareaCreada);
                    Toast.makeText(this, "Tarea creada correctamente", Toast.LENGTH_LONG).show();
                    setResult(RESULT_OK);
                    finish();
                } else {
                    Toast.makeText(this, "Error creando la tarea", Toast.LENGTH_LONG).show();
                }
            });

        } else {
            AlarmaManager.cancelarAlarma(this, tareaEditando);
            tareaEditando.setTitulo(titulo);
            tareaEditando.setDescripcion(descripcion);
            tareaEditando.setCategoria(categoria);
            tareaEditando.setPrioridad(prioridad);
            tareaEditando.setFecha(fechaFinal);

            tareaViewModel.actualizarTarea(usuarioId, tareaEditando.getId(), tareaEditando, username, password).observe(this, tareaActualizada -> {
                if (tareaActualizada != null) {
                    AlarmaManager.programarAlarma(this, tareaActualizada);
                    Toast.makeText(this, "Tarea actualizada correctamente", Toast.LENGTH_LONG).show();
                    setResult(RESULT_OK);
                    finish();
                } else {
                    Toast.makeText(this, "Error actualizando la tarea", Toast.LENGTH_LONG).show();
                }
            });
        }
    }
}
