package com.example.tfg.view;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tfg.R;
import com.example.tfg.adapter.TareaAdapter;
import com.example.tfg.model.Tarea;
import com.example.tfg.util.AlarmaManager;
import com.example.tfg.util.NotificacionHelper;
import com.example.tfg.viewModel.TareaViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.UUID;

public class MainActivity extends AppCompatActivity {

    private TareaViewModel tareaViewModel;
    private UUID usuarioId;
    private String username;
    private String password;

    private TareaAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        NotificacionHelper.crearCanal(this);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (checkSelfPermission(android.Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{android.Manifest.permission.POST_NOTIFICATIONS}, 1);
            }
        }

        usuarioId = (UUID) getIntent().getSerializableExtra("id");
        username = getIntent().getStringExtra("username");
        password = getIntent().getStringExtra("password");

        if (usuarioId == null || username == null || password == null) {
            Toast.makeText(this, "Error: usuario no identificado", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, CrearTareaActivity.class);
            intent.putExtra("id", usuarioId);
            intent.putExtra("username", username);
            intent.putExtra("password", password);
            startActivityForResult(intent, 1);
        });

        FloatingActionButton fabStats = findViewById(R.id.fabStats);
        fabStats.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, StatsActivity.class);
            intent.putExtra("id", usuarioId);
            intent.putExtra("username", username);
            intent.putExtra("password", password);
            startActivity(intent);
        });

        FloatingActionButton fabCompletada = findViewById(R.id.fabCompletadas);
        fabCompletada.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, TareasCompletasActivity.class);
            intent.putExtra("id", usuarioId);
            intent.putExtra("username", username);
            intent.putExtra("password", password);
            startActivity(intent);
        });

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        tareaViewModel = new ViewModelProvider(this).get(TareaViewModel.class);

        adapter = new TareaAdapter(usuarioId, new TareaAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Tarea tarea) {
                Intent intent = new Intent(MainActivity.this, CrearTareaActivity.class);
                intent.putExtra(CrearTareaActivity.EXTRA_ID, tarea.getId());
                intent.putExtra("id", usuarioId);
                intent.putExtra("username", username);
                intent.putExtra("password", password);
                startActivity(intent);
            }

            @Override
            public void onDeleteClick(Tarea tarea) {
                new AlertDialog.Builder(MainActivity.this)
                        .setTitle("Eliminar tarea")
                        .setMessage("¿Seguro que quieres eliminar esta tarea?")
                        .setPositiveButton("Sí", (dialog, which) -> {
                            tareaViewModel.borrarTarea(usuarioId, tarea.getId(), username, password)
                                    .observe(MainActivity.this, success -> {
                                        if (success != null && success) {
                                            AlarmaManager.cancelarAlarma(MainActivity.this, tarea);
                                            Toast.makeText(MainActivity.this, "Tarea eliminada", Toast.LENGTH_SHORT).show();
                                            recargarTareas();
                                        } else {
                                            Toast.makeText(MainActivity.this, "Error eliminando tarea", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                        })
                        .setNegativeButton("Cancelar", null)
                        .show();
            }

            @Override
            public void onCompleteClick(Tarea tarea) {
                tarea.setCompletada(true);
                tareaViewModel.actualizarTarea(usuarioId, tarea.getId(), tarea, username, password);

                AlarmaManager.cancelarAlarma(MainActivity.this, tarea);
                Toast.makeText(MainActivity.this, "¡Tarea marcada como completada!", Toast.LENGTH_SHORT).show();
                recargarTareas();

                Intent intent = new Intent(MainActivity.this, TareasCompletasActivity.class);
                intent.putExtra("id", usuarioId);
                intent.putExtra("username", username);
                intent.putExtra("password", password);
                startActivity(intent);
            }
        }, tareaViewModel);

        recyclerView.setAdapter(adapter);

        recargarTareas();
    }

    private void recargarTareas() {
        tareaViewModel.getTareasPendientes(usuarioId, username, password).observe(this, adapter::setTareas);
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            recargarTareas();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permiso concedido", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Permiso denegado", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
