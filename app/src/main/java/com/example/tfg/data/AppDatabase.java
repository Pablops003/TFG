package com.example.tfg.data;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.tfg.util.Converters;


@Database(entities = {com.example.tfg.model.Tarea.class}, version = 4)// Lo he cambiado a 3 estaba en 2 lo he vuelto a cambiar a 2
@TypeConverters({Converters.class})

public abstract class AppDatabase extends RoomDatabase {

    public abstract TareaDao tareaDao();
    public abstract UsuarioDao usuarioDao();
    private static volatile AppDatabase INSTANCE;

    public static AppDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    AppDatabase.class, "tareas.db")
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}


