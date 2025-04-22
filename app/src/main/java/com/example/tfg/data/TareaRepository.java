package com.example.tfg.data;

import android.app.Application;
import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.room.Room;

import com.example.tfg.model.Tarea;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TareaRepository {
    private TareaDao tareaDao;
    private LiveData<List<Tarea>> allTareas;
    private ExecutorService executorService;

    public TareaRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        tareaDao = db.tareaDao();
        allTareas = tareaDao.getAllTareas();
        executorService = Executors.newSingleThreadExecutor();
    }
//
//    public TareaRepository(Context context) {
//        AppDatabase db = Room.databaseBuilder(context.getApplicationContext(),
//                        AppDatabase.class, "tareas.db")
//                .fallbackToDestructiveMigration()
//                .build();
//        tareaDao = db.tareaDao();
//        allTareas = tareaDao.getAllTareas();
//        executorService = Executors.newSingleThreadExecutor();
//
//    }

    public LiveData<List<Tarea>> getAllTareas() {
        return allTareas;
    }

    public LiveData<Tarea> getTareaById(int id) {
        return tareaDao.getTareaById(id);
    }

    public void insert(Tarea tarea) {
        executorService.execute(() -> tareaDao.insert(tarea));
    }

    public void update(Tarea tarea) {
        executorService.execute(() -> tareaDao.update(tarea));
    }

    public void delete(Tarea tarea) {
        executorService.execute(() -> tareaDao.delete(tarea));
    }


}