package com.example.tfg.util;

import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.tfg.R;

public class NotificacionHelper {

 public static final String CHANNEL_ID = "tarea_channel";

 public static void crearCanal(Context context) {
  if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
   NotificationChannel canal = new NotificationChannel(
           CHANNEL_ID,
           "Recordatorios de tareas",
           NotificationManager.IMPORTANCE_HIGH
   );
   canal.setDescription("Notifica cuando una tarea está a punto de vencer");

   NotificationManager manager = context.getSystemService(NotificationManager.class);
   if (manager != null) {
    manager.createNotificationChannel(canal);
   }
  }
 }

 @SuppressLint("MissingPermission")// Lo he tenido que poner por que si no me da error
 public static void mostrar(Context context, String tituloTarea) {
  NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
          .setSmallIcon(R.drawable.ic_notification)
          .setContentTitle("Tarea por vencer")
          .setContentText("La tarea \"" + tituloTarea + "\" vence en 1 hora.")
          .setPriority(NotificationCompat.PRIORITY_HIGH)
          .setAutoCancel(true);

  NotificationManagerCompat managerCompat = NotificationManagerCompat.from(context);
  managerCompat.notify((int) System.currentTimeMillis(), builder.build()); // Aqui
 }
}
