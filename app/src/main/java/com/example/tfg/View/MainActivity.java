package com.example.tfg.View;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tfg.Adapter.TareaAdapter;
import com.example.tfg.R;
import com.example.tfg.ViewModel.TareaViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {

    private TareaViewModel tareaViewModel;
    private RecyclerView recyclerView;
    private TareaAdapter tareaAdapter;
    private FloatingActionButton btnAgregar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        btnAgregar = findViewById(R.id.btnAgregar);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        tareaAdapter = new TareaAdapter();
        recyclerView.setAdapter(tareaAdapter);

        tareaViewModel = new ViewModelProvider(this).get(TareaViewModel.class);
        tareaViewModel.getTodasLasTareas().observe(this, tareas -> {
            tareaAdapter.setTareas(tareas);
        });

        btnAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CrearTarea.class);
                startActivity(intent);
            }
        });
    }
}