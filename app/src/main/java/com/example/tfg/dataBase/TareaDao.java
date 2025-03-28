package com.example.tfg.dataBase;

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
    void insertar(Tarea tarea);

    @Update
    void actualizar(Tarea tarea);

    @Delete
    void eliminar(Tarea tarea);

    @Query("SELECT * FROM tarea ORDER BY prioridad DESC")
    LiveData<List<Tarea>> obtenerTodasLasTareas();
}
