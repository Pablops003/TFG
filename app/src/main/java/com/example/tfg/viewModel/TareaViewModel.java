package com.example.tfg.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.tfg.data.TareaRepository;
import com.example.tfg.model.Tarea;

import java.util.List;
import java.util.UUID;

public class TareaViewModel extends AndroidViewModel {

    private TareaRepository repository;

    private MutableLiveData<List<Tarea>> tareasLiveData = new MutableLiveData<>();
    private MutableLiveData<List<Tarea>> tareasCompletadasLiveData = new MutableLiveData<>();

    public TareaViewModel(@NonNull Application application) {
        super(application);
        repository = new TareaRepository();
    }

    public LiveData<List<Tarea>> getTareasUsuario(UUID usuarioId, String username, String password) {
        repository.getTareasPorUsuario(usuarioId, username, password).observeForever(tareasLiveData::postValue);
        return tareasLiveData;
    }

    public LiveData<List<Tarea>> getTareasCompletadas(UUID usuarioId, String username, String password) {
        repository.getTareasCompletadas(usuarioId, username, password).observeForever(tareasCompletadasLiveData::postValue);
        return tareasCompletadasLiveData;
    }

    public LiveData<Tarea> crearTarea(UUID usuarioId, Tarea tarea, String username, String password) {
        return repository.crearTarea(usuarioId, tarea, username, password);
    }

    public LiveData<Tarea> actualizarTarea(UUID usuarioId, UUID tareaId, Tarea tareaActualizada, String username, String password) {
        return repository.actualizarTarea(usuarioId, tareaId, tareaActualizada, username, password);
    }

    public LiveData<Boolean> borrarTarea(UUID usuarioId, UUID tareaId, String username, String password) {
        return repository.eliminarTarea(usuarioId, tareaId, username, password);
    }

    public LiveData<List<Tarea>> getTareasPendientes(UUID usuarioId, String username, String password) {
        return repository.getTareasPendientes(usuarioId, username, password);
    }

    public LiveData<Tarea> getTareaPorId(UUID usuarioId, UUID tareaId, String username, String password) {
        return repository.getTareaPorId(usuarioId, tareaId, username, password);
    }
}
