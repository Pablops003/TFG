package com.example.tfg.view;

import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tfg.R;
import com.example.tfg.adapter.TareaAdapter;
import com.example.tfg.model.Tarea;
import com.example.tfg.viewModel.TareaViewModel;

public class TareasCompletasActivity extends AppCompatActivity {

    private TareaViewModel tareaViewModel;
    private TareaAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tareas_completas);

        RecyclerView recyclerView = findViewById(R.id.recyclerViewCompletas);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        adapter = new TareaAdapter(new TareaAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Tarea tarea) {
            }

            @Override
            public void onDeleteClick(Tarea tarea) {
            }
        },new ViewModelProvider(this).get(TareaViewModel.class));

        recyclerView.setAdapter(adapter);

        tareaViewModel = new ViewModelProvider(this).get(TareaViewModel.class);
        tareaViewModel.getTareasCompletadas().observe(this, adapter::setTareas);

        Button btnBack = findViewById(R.id.btn_back);
        btnBack.setOnClickListener(v -> finish());
    }
}