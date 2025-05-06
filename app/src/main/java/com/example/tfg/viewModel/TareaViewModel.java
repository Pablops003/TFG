package com.example.tfg.viewModel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.tfg.data.TareaRepository;
import com.example.tfg.model.Tarea;

import java.util.List;

public class TareaViewModel extends AndroidViewModel {
    private TareaRepository repository;
    private LiveData<List<Tarea>> allTareas;
    private LiveData<List<Tarea>> tareasPendientes;
    private LiveData<List<Tarea>> tareasCompletadas;

    public TareaViewModel(Application application) {
        super(application);
        repository = new TareaRepository(application);
        allTareas = repository.getAllTareas();
        tareasPendientes = repository.getTareasPendientes();
        tareasCompletadas = repository.getTareasCompletadas();
    }

    // Métodos
    public LiveData<List<Tarea>> getAllTareas() { return allTareas; }
    public LiveData<Tarea> getTareaById(int id) { return repository.getTareaById(id); }
    public void insert(Tarea tarea) { repository.insert(tarea); }
    public void update(Tarea tarea) { repository.update(tarea); }
    public void delete(Tarea tarea) { repository.delete(tarea); }

    public LiveData<List<Tarea>> getTareasPendientes() {
        return tareasPendientes;
    }

    public LiveData<List<Tarea>> getTareasCompletadas() {
        return tareasCompletadas;
    }
}