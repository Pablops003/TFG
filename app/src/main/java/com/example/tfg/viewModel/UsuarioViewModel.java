package com.example.tfg.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.tfg.data.UsuarioRepository;
import com.example.tfg.model.UsuarioDTO;

public class UsuarioViewModel extends AndroidViewModel {

    private UsuarioRepository usuarioRepository;

    public UsuarioViewModel(@NonNull Application application) {
        super(application);
        usuarioRepository = new UsuarioRepository();
    }

    public LiveData<String> registrarUsuario(String username, String password) {
        return usuarioRepository.registrarUsuario(username, password);
    }

    public LiveData<UsuarioDTO> loginUsuario(String username, String password) {
        return usuarioRepository.loginUsuario(username, password);
    }

}
