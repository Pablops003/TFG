package com.example.tfg.API;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.Base64;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitCliente {

    public static String username = "nologin";
    public static String password = "nopassword";
    private static Retrofit retrofit;
   // private static final String BASE_URL = "https://demo-8lbj.onrender.com/api/";
    private static final String BASE_URL = "http://192.168.1.38:8080/api/";

    public static Retrofit getRetrofitInstance() {
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd")
                .create();

        if (retrofit == null) {
            OkHttpClient client = new OkHttpClient.Builder()
                    .addInterceptor(chain -> {
                        Request originalRequest = chain.request();
                        String url = originalRequest.url().toString();

                        // Evita añadir autenticación para registro
                        if (url.contains("/usuarios/register/")) {
                            return chain.proceed(originalRequest);
                        }

                        // Añadir Authorization para el resto
                        Request authenticatedRequest = originalRequest.newBuilder()
                                .header("Authorization", getBasicAuth())
                                .build();

                        return chain.proceed(authenticatedRequest);
                    })
                    .build();

            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
        }
        return retrofit;
    }

    static String getBasicAuth() {
        return "Basic " + Base64.getEncoder().encodeToString((username + ":" + password).getBytes());
    }
}
