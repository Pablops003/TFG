package com.example.tfg.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import com.example.tfg.model.Tarea;

import java.util.List;

@Dao
public interface TareaDao {
    @Insert
    long insert(Tarea tarea);

    @Update
    void update(Tarea tarea);

    @Delete
    void delete(Tarea tarea);

    @Query("SELECT * FROM tareas WHERE completada = false ORDER BY CASE prioridad WHEN 'Alta' THEN 1 WHEN 'Media' THEN 2 ELSE 3 END")
    LiveData<List<Tarea>> getTareasPendientes();

    @Query("SELECT * FROM tareas WHERE id = :id")
    LiveData<Tarea> getTareaById(int id);

    @Query("SELECT * FROM tareas WHERE completada = 1 ORDER BY fecha DESC")
    LiveData<List<Tarea>> getTareasCompletadas();


    @Query("SELECT * FROM tareas ORDER BY CASE prioridad WHEN 'Alta' THEN 1 WHEN 'Media' THEN 2 ELSE 3 END")
    LiveData<List<Tarea>> getAllTareas();
}