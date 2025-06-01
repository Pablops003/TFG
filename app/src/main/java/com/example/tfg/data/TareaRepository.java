package com.example.tfg.data;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.tfg.API.ApiService;
import com.example.tfg.API.RetrofitCliente;
import com.example.tfg.model.Tarea;
import com.example.tfg.model.UsuarioDTO;

import java.util.List;
import java.util.UUID;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TareaRepository {

    private final ApiService apiService;

    public TareaRepository() {
        apiService = RetrofitCliente.getRetrofitInstance().create(ApiService.class);
    }

    private void setBasicAuth(String username, String password) {
        RetrofitCliente.username = username;
        RetrofitCliente.password = password;
    }

    public LiveData<List<Tarea>> getTareasPorUsuario(UUID usuarioId, String username, String password) {
        setBasicAuth(username, password);
        MutableLiveData<List<Tarea>> tareasLiveData = new MutableLiveData<>();

        apiService.getTareasPorUsuario(usuarioId).enqueue(new Callback<List<Tarea>>() {
            @Override
            public void onResponse(Call<List<Tarea>> call, Response<List<Tarea>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    tareasLiveData.postValue(response.body());
                } else {
                    tareasLiveData.postValue(null);
                }
            }

            @Override
            public void onFailure(Call<List<Tarea>> call, Throwable t) {
                tareasLiveData.postValue(null);
            }
        });

        return tareasLiveData;
    }

    public LiveData<Tarea> crearTarea(UUID usuarioId, Tarea tarea, String username, String password) {
        setBasicAuth(username, password);
        MutableLiveData<Tarea> tareaLiveData = new MutableLiveData<>();

        apiService.crearTarea(usuarioId, tarea).enqueue(new Callback<Tarea>() {
            @Override
            public void onResponse(Call<Tarea> call, Response<Tarea> response) {
                if (response.isSuccessful() && response.body() != null) {
                    tareaLiveData.postValue(response.body());
                } else {
                    Log.e("RETROFIT", "Error en la respuesta: " + response.code() + " - " + response.message());
                    try {
                        if (response.errorBody() != null) {
                            Log.e("RETROFIT", "Error body: " + response.errorBody().string());
                        }
                    } catch (Exception e) {
                        Log.e("RETROFIT", "Excepción leyendo errorBody", e);
                    }
                    tareaLiveData.postValue(null);
                }
            }

            @Override
            public void onFailure(Call<Tarea> call, Throwable t) {
                Log.e("RETROFIT", "Fallo en la llamada: ", t);
                tareaLiveData.postValue(null);
            }
        });

        return tareaLiveData;
    }

    public LiveData<Tarea> actualizarTarea(UUID usuarioId, UUID tareaId, Tarea tareaActualizada, String username, String password) {
        setBasicAuth(username, password);
        MutableLiveData<Tarea> resultado = new MutableLiveData<>();

        apiService.actualizarTarea(usuarioId, tareaId, tareaActualizada)
                .enqueue(new Callback<Tarea>() {
                    @Override
                    public void onResponse(Call<Tarea> call, Response<Tarea> response) {
                        if (response.isSuccessful()) {
                            resultado.postValue(response.body());
                        } else {
                            resultado.postValue(null);
                        }
                    }

                    @Override
                    public void onFailure(Call<Tarea> call, Throwable t) {
                        resultado.postValue(null);
                    }
                });

        return resultado;
    }

    public LiveData<Boolean> eliminarTarea(UUID usuarioId, UUID tareaId, String username, String password) {
        setBasicAuth(username, password);
        MutableLiveData<Boolean> resultado = new MutableLiveData<>();

        apiService.borrarTarea(usuarioId, tareaId).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                resultado.postValue(response.isSuccessful());
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                resultado.postValue(false);
            }
        });

        return resultado;
    }

    public LiveData<List<Tarea>> getTareasCompletadas(UUID usuarioId, String username, String password) {
        setBasicAuth(username, password);
        MutableLiveData<List<Tarea>> tareasLiveData = new MutableLiveData<>();

        apiService.getTareasCompletadas(usuarioId.toString()).enqueue(new Callback<List<Tarea>>() {
            @Override
            public void onResponse(Call<List<Tarea>> call, Response<List<Tarea>> response) {
                if (response.isSuccessful()) {
                    tareasLiveData.postValue(response.body());
                } else {
                    tareasLiveData.postValue(null);
                }
            }

            @Override
            public void onFailure(Call<List<Tarea>> call, Throwable t) {
                tareasLiveData.postValue(null);
            }
        });

        return tareasLiveData;
    }

    private final MutableLiveData<List<Tarea>> tareasPendientes = new MutableLiveData<>();

    public LiveData<List<Tarea>> getTareasPendientes(UUID usuarioId, String username, String password) {
        setBasicAuth(username, password);
        apiService.getTareasPendientes(usuarioId).enqueue(new Callback<List<Tarea>>() {
            @Override
            public void onResponse(Call<List<Tarea>> call, Response<List<Tarea>> response) {
                if (response.isSuccessful()) {
                    tareasPendientes.postValue(response.body());
                } else {
                    tareasPendientes.postValue(null);
                }
            }

            @Override
            public void onFailure(Call<List<Tarea>> call, Throwable t) {
                tareasPendientes.postValue(null);
            }
        });

        return tareasPendientes;
    }

    public LiveData<Tarea> getTareaPorId(UUID usuarioId, UUID tareaId, String username, String password) {
        setBasicAuth(username, password);
        MutableLiveData<Tarea> tareaLiveData = new MutableLiveData<>();

        apiService.getTareaPorId(usuarioId, tareaId).enqueue(new Callback<Tarea>() {
            @Override
            public void onResponse(Call<Tarea> call, Response<Tarea> response) {
                if (response.isSuccessful()) {
                    tareaLiveData.postValue(response.body());
                } else {
                    tareaLiveData.postValue(null);
                }
            }

            @Override
            public void onFailure(Call<Tarea> call, Throwable t) {
                tareaLiveData.postValue(null);
            }
        });

        return tareaLiveData;
    }

    public LiveData<UsuarioDTO> validarLogin(String username, String password) {
        setBasicAuth(username, password);
        MutableLiveData<UsuarioDTO> liveData = new MutableLiveData<>();

        apiService.validarLogin().enqueue(new Callback<UsuarioDTO>() {
            @Override
            public void onResponse(Call<UsuarioDTO> call, Response<UsuarioDTO> response) {
                if (response.isSuccessful() && response.body() != null) {
                    liveData.postValue(response.body());
                } else {
                    Log.e("Login", "Código: " + response.code());
                    liveData.postValue(null);
                }
            }

            @Override
            public void onFailure(Call<UsuarioDTO> call, Throwable t) {
                Log.e("Login", "Fallo en llamada: " + t.getMessage());
                liveData.postValue(null);
            }
        });

        return liveData;
    }


}
