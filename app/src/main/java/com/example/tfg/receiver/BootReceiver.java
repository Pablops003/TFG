//package com.example.tfg.receiver;
//
//import android.content.BroadcastReceiver;
//import android.content.Context;
//import android.content.Intent;
//import android.content.SharedPreferences;
//import android.util.Log;
//
//import com.example.tfg.API.ApiService;
//import com.example.tfg.API.RetrofitCliente;
//import com.example.tfg.model.Tarea;
//import com.example.tfg.util.AlarmaManager;
//
//import java.util.List;
//import java.util.UUID;
//
//import retrofit2.Call;
//import retrofit2.Callback;
//import retrofit2.Response;
//
//public class BootReceiver extends BroadcastReceiver {
//    @Override
//    public void onReceive(Context context, Intent intent) {
//        if (!Intent.ACTION_BOOT_COMPLETED.equals(intent.getAction())) return;
//
//        SharedPreferences prefs = context.getSharedPreferences("auth", Context.MODE_PRIVATE);
//        String username = prefs.getString("username", null);
//        String password = prefs.getString("password", null);
//        String usuarioIdStr = prefs.getString("usuarioId", null);
//
//        if (username == null || password == null || usuarioIdStr == null) {
//            Log.w("BootReceiver", "Credenciales o ID de usuario no encontrados");
//            return;
//        }
//
//        // Establecer credenciales en Retrofit
//        RetrofitCliente.username = username;
//        RetrofitCliente.password = password;
//
//        UUID usuarioId = UUID.fromString(usuarioIdStr);
//        ApiService apiService = RetrofitCliente.getRetrofitInstance().create(ApiService.class);
//
//        apiService.getTareasPendientes(usuarioId).enqueue(new Callback<List<Tarea>>() {
//            @Override
//            public void onResponse(Call<List<Tarea>> call, Response<List<Tarea>> response) {
//                if (response.isSuccessful() && response.body() != null) {
//                    for (Tarea tarea : response.body()) {
//                        AlarmaManager.programarAlarma(context, tarea);
//                    }
//                    Log.d("BootReceiver", "Tareas reprogramadas tras reinicio");
//                } else {
//                    Log.e("BootReceiver", "Respuesta no exitosa: " + response.code());
//                }
//            }
//
//            @Override
//            public void onFailure(Call<List<Tarea>> call, Throwable t) {
//                Log.e("BootReceiver", "Error al obtener tareas pendientes", t);
//            }
//        });
//    }
//}
