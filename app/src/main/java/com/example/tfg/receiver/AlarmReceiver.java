package com.example.tfg.receiver;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.example.tfg.util.NotificacionHelper;

public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("AlarmReceiver", "¡Alarma recibida! Mostrando notificación...");
        String titulo = intent.getStringExtra("titulo");
        NotificacionHelper.mostrar(context, titulo);
    }
}

