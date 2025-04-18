package com.example.tfg.receiver;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import androidx.core.app.NotificationCompat;

import com.example.tfg.R;
import com.example.tfg.view.MainActivity;

public class TareaNotificationReceiver //extends BroadcastReceiver
 {
//    @Override
//    public void onReceive(Context context, Intent intent) {
//        int tareaId = intent.getIntExtra("tarea_id", 0);
//        String titulo = intent.getStringExtra("tarea_titulo");
//
//        NotificationManager manager = (NotificationManager)
//                context.getSystemService(Context.NOTIFICATION_SERVICE);
//
//        // Crear canal (solo necesario en API 26+)
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            NotificationChannel channel = new NotificationChannel(
//                    "channel_tareas",
//                    "Recordatorios de tareas",
//                    NotificationManager.IMPORTANCE_HIGH
//            );
//            manager.createNotificationChannel(channel);
//        }
//
//        // Intent para abrir la app al hacer clic
//        Intent appIntent = new Intent(context, MainActivity.class);
//        PendingIntent pendingIntent = PendingIntent.getActivity(
//                context, 0, appIntent, PendingIntent.FLAG_IMMUTABLE);
//
//        // Construir notificación
//        Notification notification = new NotificationCompat.Builder(context, "channel_tareas")
//                .setSmallIcon(R.drawable.ic_notification)
//                .setContentTitle("¡Tarea pendiente!")
//                .setContentText(titulo)
//                .setPriority(NotificationCompat.PRIORITY_HIGH)
//                .setContentIntent(pendingIntent)
//                .setAutoCancel(true)
//                .build();
//
//        manager.notify(tareaId, notification);
//    }
}