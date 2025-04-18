package com.example.tfg.receiver;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import com.example.tfg.model.Tarea;

public class AlarmaManager {
//    private static final int REQUEST_CODE_PREFIX = 1000;
//
//    public static void programarAlarma(Context context, Tarea tarea) {
//        if (tarea.getFechaVencimiento() == null) return;
//
//        AlarmManager alarmManager = (AlarmManager)
//                context.getSystemService(Context.ALARM_SERVICE);
//
//        Intent intent = new Intent(context, TareaNotificationReceiver.class);
//        intent.putExtra("tarea_id", tarea.getId());
//        intent.putExtra("tarea_titulo", tarea.getTitulo());
//
//        PendingIntent pendingIntent = PendingIntent.getBroadcast(
//                context,
//                REQUEST_CODE_PREFIX + tarea.getId(),
//                intent,
//                PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE
//        );
//
//        // Programar alarmas:
//        // 1. 1 hora antes
//        programarAlarmaIndividual(
//                alarmManager,
//                tarea.getFechaVencimiento() - (60 * 60 * 1000),
//                pendingIntent
//        );
//
//        // 2. En el momento exacto
//        programarAlarmaIndividual(
//                alarmManager,
//                tarea.getFechaVencimiento(),
//                pendingIntent
//        );
//    }
//
//    private static void programarAlarmaIndividual(AlarmManager alarmManager,
//                                                  long triggerTime,
//                                                  PendingIntent pendingIntent) {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            alarmManager.setExactAndAllowWhileIdle(
//                    AlarmManager.RTC_WAKEUP,
//                    triggerTime,
//                    pendingIntent
//            );
//        } else {
//            alarmManager.setExact(
//                    AlarmManager.RTC_WAKEUP,
//                    triggerTime,
//                    pendingIntent
//            );
//        }
//    }
//
//    public static void cancelarAlarma(Context context, int tareaId) {
//        AlarmManager alarmManager = (AlarmManager)
//                context.getSystemService(Context.ALARM_SERVICE);
//
//        Intent intent = new Intent(context, TareaNotificationReceiver.class);
//        PendingIntent pendingIntent = PendingIntent.getBroadcast(
//                context,
//                REQUEST_CODE_PREFIX + tareaId,
//                intent,
//                PendingIntent.FLAG_NO_CREATE | PendingIntent.FLAG_IMMUTABLE
//        );
//
//        if (pendingIntent != null) {
//            alarmManager.cancel(pendingIntent);
//            pendingIntent.cancel();
//        }
//    }
}
