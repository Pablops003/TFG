package com.example.tfg.data;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.tfg.API.ApiService;
import com.example.tfg.API.RetrofitCliente;
import com.example.tfg.model.UserRegisterRequest;
import com.example.tfg.model.UsuarioDTO;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UsuarioRepository {

    private ApiService apiService;

    public UsuarioRepository() {
        apiService = RetrofitCliente.getRetrofitInstance().create(ApiService.class);
    }

    // Registro SIN autenticación
    // Registro usuario
    public LiveData<String> registrarUsuario(String username, String password) {
        MutableLiveData<String> usuarioLiveData = new MutableLiveData<>();

        RetrofitCliente.username = "";  // Limpiar auth para registro
        RetrofitCliente.password = "";

        UserRegisterRequest request = new UserRegisterRequest(username, password);

        apiService.registrarUsuario(request).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful() && response.body() != null) {
                    try {
                        String resp = response.body().string();  // Leer texto plano
                        usuarioLiveData.postValue(resp);
                    } catch (IOException e) {
                        e.printStackTrace();
                        usuarioLiveData.postValue(null);
                    }
                } else {
                    Log.e("Registro", "Error: " + response.code() + " " + response.message());
                    usuarioLiveData.postValue(null);
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("Registro", "Error de red o conexión: " + t.getMessage());
                usuarioLiveData.postValue(null);
            }
        });

        return usuarioLiveData;
    }




    public LiveData<UsuarioDTO> loginUsuario(String username, String password) {
        MutableLiveData<UsuarioDTO> loginLiveData = new MutableLiveData<>();

        RetrofitCliente.username = username;
        RetrofitCliente.password = password;

        apiService.validarLogin().enqueue(new Callback<UsuarioDTO>() {
            @Override
            public void onResponse(Call<UsuarioDTO> call, Response<UsuarioDTO> response) {
                if (response.isSuccessful() && response.body() != null) {
                    loginLiveData.postValue(response.body());
                } else {
                    loginLiveData.postValue(null);
                }
            }

            @Override
            public void onFailure(Call<UsuarioDTO> call, Throwable t) {
                loginLiveData.postValue(null);
            }
        });

        return loginLiveData;
    }


}
