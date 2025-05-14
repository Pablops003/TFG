package com.example.tfg.receiver;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.example.tfg.util.NotificacionHelper;

public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String titulo = intent.getStringExtra("titulo");
        String descripcion = intent.getStringExtra("descripcion");

        Log.d("AlarmReceiver", "¡Alarma recibida! Mostrando notificación para: " + titulo);
        NotificacionHelper.mostrar(context, titulo, descripcion);
    }
}