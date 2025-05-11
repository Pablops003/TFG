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

public class MainActivity extends AppCompatActivity {
    private TareaViewModel tareaViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        NotificacionHelper.mostrar(this, "Tarea de prueba", "Esta es una prueba de notificación.");

        NotificacionHelper.crearCanal(this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU){
            if (checkSelfPermission(android.Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{android.Manifest.permission.POST_NOTIFICATIONS}, 1);
            }
        }
        // Aqui van los botones para las estadicticas, para cxrear las tareas y las tareass completadas
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, CrearTareaActivity.class);
            startActivity(intent);
        });

        FloatingActionButton fabStats = findViewById(R.id.fabStats);
        fabStats.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, StatsActivity.class);
            startActivity(intent);
        });

        FloatingActionButton fabCompletada = findViewById(R.id.fabCompletadas);
        fabCompletada.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, TareasCompletasActivity.class);
            startActivity(intent);
        });

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        tareaViewModel = new ViewModelProvider(this).get(TareaViewModel.class);

        TareaAdapter adapter = new TareaAdapter(new TareaAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Tarea tarea) {
                Intent intent = new Intent(MainActivity.this, CrearTareaActivity.class);
                intent.putExtra(CrearTareaActivity.EXTRA_ID, tarea.getId());
                startActivity(intent);
            }

            @Override
            public void onDeleteClick(Tarea tarea) {
                new AlertDialog.Builder(MainActivity.this)
                        .setTitle("Eliminar tarea")
                        .setMessage("¿seguro que quieres eliminar esta tarea?")
                        .setPositiveButton("Sí", (dialogo, si) -> {
                            tareaViewModel.delete(tarea);
                            AlarmaManager.cancelarAlarma(MainActivity.this, tarea);
                            Toast.makeText(MainActivity.this, "Tarea eliminada y alarma cancelada", Toast.LENGTH_SHORT).show();
                        })
                        .setNegativeButton("Cancelar", null)
                        .show();
            }
        }, tareaViewModel);


        recyclerView.setAdapter(adapter);

        tareaViewModel = new ViewModelProvider(this).get(TareaViewModel.class);
      //  tareaViewModel.getAllTareas().observe(this, adapter::setTareas);
        tareaViewModel.getTareasPendientes().observe(this, adapter::setTareas);


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permiso de notificaciones concedido", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Permiso de notificaciones denegado", Toast.LENGTH_SHORT).show();
            }
        }
    }
}