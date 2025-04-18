package com.example.tfg.viewModel;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.tfg.model.Tarea;
import com.example.tfg.data.AppDatabase;
import com.example.tfg.data.TareaDao;
import com.example.tfg.data.TareaRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TareaViewModel extends AndroidViewModel {
    private TareaRepository repository;
    private LiveData<List<Tarea>> allTareas;
    private TareaDao tareaDao;
    private AppDatabase AppDatabase;

    public TareaViewModel(Application application) {
        super(application);
        repository = new TareaRepository(application);
        allTareas = repository.getAllTareas();

//        AppDatabase = AppDatabase.getInstance(application);
//        tareaDao = AppDatabase.tareaDao();

    }

    public LiveData<List<Tarea>> getAllTareas() { return allTareas; }
    public LiveData<Tarea> getTareaById(int id) { return repository.getTareaById(id); }
    public void insert(Tarea tarea) { repository.insert(tarea); }
    public void update(Tarea tarea) { repository.update(tarea); }
    public void delete(Tarea tarea) { repository.delete(tarea); }


}