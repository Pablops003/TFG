package com.example.tfg.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.os.Bundle;

import com.example.patareas.model.Tarea;
import com.example.tfg.R;
import com.example.tfg.adapter.TareaAdapter;
import com.example.tfg.view.CrearTareaActivity;
import com.example.tfg.viewModel.TareaViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {
    private TareaViewModel tareaViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, CrearTareaActivity.class);
            startActivity(intent);
        });

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        TareaAdapter adapter = new TareaAdapter(new TareaAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Tarea tarea) {
                Intent intent = new Intent(MainActivity.this, CrearTareaActivity.class);
                intent.putExtra(CrearTareaActivity.EXTRA_ID, tarea.getId());
                startActivity(intent);
            }

            @Override
            public void onDeleteClick(Tarea tarea) {
                 // tareaViewModel.delete(tarea);
                new androidx.appcompat.app.AlertDialog.Builder(MainActivity.this)
                        .setTitle("Eliminar tarea")
                        .setMessage("¿seguro que quieres eliminar esta tarea?")
                        .setPositiveButton("Sí", (dialogo, si) -> {
                            tareaViewModel.delete(tarea);
                        })
                        .setNegativeButton("Cancelar", null)
                        .show();
            }
        });


        recyclerView.setAdapter(adapter);

        tareaViewModel = new ViewModelProvider(this).get(TareaViewModel.class);
        tareaViewModel.getAllTareas().observe(this, adapter::setTareas);


    }
}