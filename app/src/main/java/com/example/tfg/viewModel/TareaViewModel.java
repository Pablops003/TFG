package com.example.tfg.viewModel;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.patareas.model.Tarea;
import com.example.tfg.data.AppDatabase;
import com.example.tfg.data.TareaDao;
import com.example.tfg.data.TareaRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executor;

public class TareaViewModel extends AndroidViewModel {
    private TareaRepository repository;
    private LiveData<List<Tarea>> allTareas;
    private TareaDao tareaDao;
    private AppDatabase AppDatabase;
    public TareaViewModel(Application application) {
        super(application);
        repository = new TareaRepository(application);
        allTareas = repository.getAllTareas();
    }

    public LiveData<List<Tarea>> getAllTareas() { return allTareas; }
    public LiveData<Tarea> getTareaById(int id) { return repository.getTareaById(id); }
    public void insert(Tarea tarea) { repository.insert(tarea); }
    public void update(Tarea tarea) { repository.update(tarea); }
    public void delete(Tarea tarea) { repository.delete(tarea); }
    public LiveData<Map<String, Integer>> getEstadisticas() {

        MutableLiveData<Map<String, Integer>> result = new MutableLiveData<>();

        AppDatabase.databaseWriteExecutor.execute(() -> {
            Map<String, Integer> stats = new HashMap<>();

            List<Tarea> tareas = tareaDao.getAllTareasSync(); // Necesitarás implementar este método

            int completadas = 0;
            int alta = 0, media = 0, baja = 0;

            for (Tarea t : tareas) {
                if (t.isCompletada()) completadas++;

                switch (t.getPrioridad()) {
                    case "Alta": alta++; break;
                    case "Media": media++; break;
                    case "Baja": baja++; break;
                }
            }

            stats.put("completadas", completadas);
            stats.put("pendientes", tareas.size() - completadas);
            stats.put("alta", alta);
            stats.put("media", media);
            stats.put("baja", baja);

            result.postValue(stats);
        });

        return result;
    }

}