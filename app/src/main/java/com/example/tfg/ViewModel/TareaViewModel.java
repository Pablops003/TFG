package com.example.tfg.ViewModel;

import android.app.Application;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.tfg.dataBase.TareaDao;
import com.example.tfg.dataBase.TareaDatabase;
import com.example.tfg.model.Tarea;

import java.util.List;

public class TareaViewModel extends AndroidViewModel {
    private TareaDao tareaDao;
    private LiveData<List<Tarea>> todasLasTareas;

    public TareaViewModel(@NonNull Application application) {
        super(application);
        TareaDatabase db = TareaDatabase.getInstancia(application);
        tareaDao = db.tareaDao();
        todasLasTareas = tareaDao.obtenerTodasLasTareas();
    }

    public void insertar(Tarea tarea) {
        new InsertarTareaAsyncTask(tareaDao).execute(tarea);
    }

    public LiveData<List<Tarea>> getTodasLasTareas() {
        return todasLasTareas;
    }

    private static class InsertarTareaAsyncTask extends AsyncTask<Tarea, Void, Void> {
        private TareaDao tareaDao;

        private InsertarTareaAsyncTask(TareaDao dao) {
            this.tareaDao = dao;
        }

        @Override
        protected Void doInBackground(Tarea... tareas) {
            tareaDao.insertar(tareas[0]);
            return null;
        }
    }
}
