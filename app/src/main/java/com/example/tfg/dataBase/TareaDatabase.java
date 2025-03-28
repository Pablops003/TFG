package com.example.tfg.dataBase;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.tfg.model.Tarea;

@Database(entities = {Tarea.class}, version = 1)
public abstract class TareaDatabase extends RoomDatabase {
    private static TareaDatabase instancia;

    public abstract TareaDao tareaDao();

    public static synchronized TareaDatabase getInstancia(Context context) {
        if (instancia == null) {
            instancia = Room.databaseBuilder(context.getApplicationContext(),
                            TareaDatabase.class, "tarea_db")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instancia;
    }
}
