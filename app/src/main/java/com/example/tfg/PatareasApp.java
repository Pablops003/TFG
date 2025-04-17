package com.example.tfg;

import android.app.Application;
import androidx.room.Room;

import com.example.tfg.data.AppDatabase;

public class PatareasApp extends Application {
    private static AppDatabase database;

    @Override
    public void onCreate() {
        super.onCreate();

        // Inicializa la base de datos Room
        database = Room.databaseBuilder(this, AppDatabase.class, "tareas-db")
                .allowMainThreadQueries() // Solo para pruebas - eliminar en producción
                .build();
    }

    public static AppDatabase getDatabase() {
        return database;
    }
}
