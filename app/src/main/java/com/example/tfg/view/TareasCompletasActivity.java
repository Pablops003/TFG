package com.example.tfg.view;

import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tfg.R;
import com.example.tfg.adapter.TareaAdapter;
import com.example.tfg.model.Tarea;
import com.example.tfg.util.AlarmaManager;
import com.example.tfg.viewModel.TareaViewModel;

import java.util.UUID;

public class TareasCompletasActivity extends AppCompatActivity {

    private TareaViewModel tareaViewModel;
    private TareaAdapter adapter;
    private UUID usuarioId;
    private String username;
    private String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tareas_completas);

        usuarioId = (UUID) getIntent().getSerializableExtra("id");
        username = getIntent().getStringExtra("username");
        password = getIntent().getStringExtra("password");

        if (usuarioId == null || username == null || password == null) {
            Toast.makeText(this, "Error: usuario no identificado", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        RecyclerView recyclerView = findViewById(R.id.recyclerViewCompletas);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        tareaViewModel = new ViewModelProvider(this).get(TareaViewModel.class);

        adapter = new TareaAdapter(
                usuarioId,
                new TareaAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(Tarea tarea) {
                        // No hacemos nada con tareas completadas
                    }

                    @Override
                    public void onDeleteClick(Tarea tarea) {
                        tareaViewModel.borrarTarea(usuarioId, tarea.getId(), username, password)
                                .observe(TareasCompletasActivity.this, success -> {
                                    if (success != null && success) {
                                        AlarmaManager.cancelarAlarma(TareasCompletasActivity.this, tarea);
                                        Toast.makeText(TareasCompletasActivity.this, "Tarea completada eliminada", Toast.LENGTH_SHORT).show();
                                        recargarTareasCompletadas();
                                    } else {
                                        Toast.makeText(TareasCompletasActivity.this, "Error eliminando tarea", Toast.LENGTH_SHORT).show();
                                    }
                                });
                    }

                    @Override
                    public void onCompleteClick(Tarea tarea) {
                        // No se puede marcar como completada
                    }
                },
                tareaViewModel
        );

        recyclerView.setAdapter(adapter);

        recargarTareasCompletadas();

        Button btnBack = findViewById(R.id.btn_back);
        btnBack.setOnClickListener(v -> finish());
    }

    private void recargarTareasCompletadas() {
        tareaViewModel.getTareasCompletadas(usuarioId, username, password)
                .observe(this, tareas -> {
                    if (tareas != null) adapter.setTareas(tareas);
                });
    }
}
