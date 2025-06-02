package com.example.tfg.util;

import android.content.Context;
import android.content.SharedPreferences;

public class SesionManager {
    private static final String PREF_NAME = "user_session";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_PASSWORD = "password";
   // private static final String KEY_USER_ID = "usuarioId";  // NUEVO

    private SharedPreferences prefs;
    private SharedPreferences.Editor editor;

    public SesionManager(Context context) {
        prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = prefs.edit();
    }

    public void saveLogin(String username, String password) {
        editor.putString(KEY_USERNAME, username);
        editor.putString(KEY_PASSWORD, password);
        editor.apply();
    }

//    public void saveUserId(String userId) {
//        editor.putString(KEY_USER_ID, userId);
//        editor.apply();
//    }

    public String getUsername() {
        return prefs.getString(KEY_USERNAME, null);
    }

    public String getPassword() {
        return prefs.getString(KEY_PASSWORD, null);
    }

//    public String getUserId() {
//        return prefs.getString(KEY_USER_ID, null);
//    }

    public boolean isLoggedIn() {
        return getUsername() != null && getPassword() != null;
    }

    public void clearSession() {
        editor.clear();
        editor.apply();
    }
}
