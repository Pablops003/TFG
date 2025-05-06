package com.example.tfg.util;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import com.example.tfg.model.Tarea;
import com.example.tfg.receiver.AlarmReceiver;

import java.util.Calendar;

public class AlarmaManager {

    public static void programarAlarma(Context context, Tarea tarea) {
        if (tarea.getFecha() == null) return;

        // Restar 1 hora a la fecha de fin
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(tarea.getFecha());
        calendar.add(Calendar.HOUR_OF_DAY, -1); // Una hora antes

        // No programar si ya pasó
        if (calendar.getTimeInMillis() <= System.currentTimeMillis()) return;

        Intent intent = new Intent(context, AlarmReceiver.class);
        intent.putExtra("titulo", tarea.getTitulo());

        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                context,
                tarea.getId(), // Usa el ID como requestCode unico
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE
        );

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        if (alarmManager != null) {
            alarmManager.setExact(
                    AlarmManager.RTC_WAKEUP,
                    calendar.getTimeInMillis(),
                    pendingIntent
            );
        }

    }
    public static void cancelarAlarma(Context context, Tarea tarea) {
        PendingIntent pendingIntent = crearPendingIntent(context, tarea);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        if (alarmManager != null) {
            alarmManager.cancel(pendingIntent);
        }
    }

    // Reutilizar PendingIntent
    private static PendingIntent crearPendingIntent(Context context, Tarea tarea) {
        Intent intent = new Intent(context, AlarmReceiver.class);
        intent.putExtra("titulo", tarea.getTitulo());

        return PendingIntent.getBroadcast(
                context,
                tarea.getId(), // unico para cada tarea
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE
        );
    }
}

