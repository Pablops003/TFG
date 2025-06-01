package com.example.tfg.API;

import com.example.tfg.model.Tarea;
import com.example.tfg.model.UserRegisterRequest;
import com.example.tfg.model.Usuario;
import com.example.tfg.model.UsuarioDTO;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ApiService {

    @GET("tareas/usuario/{id}")
    Call<List<Tarea>> getTareasPorUsuario(@Path("id") UUID usuarioId);

    @POST("tareas/usuario/{id}")
    Call<Tarea>crearTarea(@Path("id") UUID usuarioId, @Body Tarea tarea);

    @POST("usuarios/register/")
    Call<ResponseBody> registrarUsuario(@Body UserRegisterRequest request);

    @POST("usuarios/login")
    Call<Usuario> loginUsuario(@Body Map<String, String> datos);

//    @GET("usuarios/me")
//    Call<ResponseBody> validarLogin();

    @PUT("tareas/usuario/{id}/tarea/{tareaId}")
    Call<Tarea> actualizarTarea(
            @Path("id") UUID usuarioId,
            @Path("tareaId") UUID tareaId,
            @Body Tarea tarea
            );

    @DELETE("tareas/usuario/{id}/tarea/{tareaId}")
    Call<Void> borrarTarea(
            @Path("id") UUID usuarioId,
            @Path("tareaId") UUID tareaId
    );

    @GET("tareas/usuario/{id}/completadas")
    Call<List<Tarea>> getTareasCompletadas(@Path("id") String usuarioId);

    @GET("tareas/usuario/{id}/pendientes")
    Call<List<Tarea>> getTareasPendientes(@Path("id") UUID usuarioId);

    // Probar a ver si funciona

    @GET("tareas/usuario/{id}/tarea/{tareaId}")
    Call<Tarea> getTareaPorId(
            @Path("id") UUID usuarioId,
            @Path("tareaId") UUID tareaId
    );

    @GET("usuarios/me")
    Call<UsuarioDTO> validarLogin();

}
