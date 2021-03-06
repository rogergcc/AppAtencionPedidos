package com.software3000.s3k_user1.appatencionpedidos.helpers;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.software3000.s3k_user1.appatencionpedidos.loginSistema.LoginActivity;

import java.util.HashMap;

public class SessionManager {
    // Shared Preferences
    SharedPreferences pref;

    // Editor for Shared preferences
    SharedPreferences.Editor editor;

    // Context
    Context _context;

    // Shared pref mode
    int PRIVATE_MODE = 0;

    // Sharedpref file usuarioNombre
    private static final String PREF_NAME = "AndroidHivePref";

    // All Shared Preferences Keys
    private static final String IS_LOGIN = "IsLoggedIn";

    // User usuarioNombre (make variable public to access from outside)
    public static final String KEY_USUARIO_NOMBRE = "usuarioNombre";

    // Email address (make variable public to access from outside)
    public static final String KEY_USUARIO_ID = "usuarioId";

    public static final String KEY_EMPLEADO_ID = "empleadoId";

    public static final String KEY_USUARIO_EMPLEADO = "usuarioEmpleado";

    public static final String KEY_USUARIO_CORREO = "usuarioCorreo";

    public static final String KEY_USUARIO_ROL = "usuarioRol";

    public static final String KEY_SALA= "salaSesion";
    public static final String KEY_SALA_NOMBRE= "salaSesionNombre";

    public static final String KEY_EMPRESA = "empresaSesion";
    public static final String KEY_EMPRESA_RAZONSOCIAL = "empresaSesionRazonSocial";

    // Constructor
    public SessionManager(Context context){
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    /**
     * Create login session
     * */
    public void createLoginSession(String razonSocial,String nombreEmpresa,String usuarioNombre,String empleadoId, String usuarioId,String empleado, String correo, String rol,String sala, String empresa){
        // Storing login value as TRUE
        editor.putBoolean(IS_LOGIN, true);

        editor.putString(KEY_EMPRESA_RAZONSOCIAL, razonSocial);
        editor.putString(KEY_SALA_NOMBRE, nombreEmpresa);

        // Storing usuarioNombre in pref
        editor.putString(KEY_USUARIO_NOMBRE, usuarioNombre);

        editor.putString(KEY_EMPLEADO_ID, empleadoId);
        // Storing usuarioId in pref
        editor.putString(KEY_USUARIO_ID, usuarioId);

        editor.putString(KEY_USUARIO_EMPLEADO, empleado);

        editor.putString(KEY_USUARIO_CORREO, correo);

        editor.putString(KEY_USUARIO_ROL, rol);
        editor.putString(KEY_SALA, sala);
        editor.putString(KEY_EMPRESA, empresa);
        // commit changes
        editor.commit();
    }

    /**
     * Check login method wil check user login status
     * If false it will redirect user to login page
     * Else won't do anything
     * */
    public void checkLogin(){
        // Check login status
        if(!this.isLoggedIn()){
            // user is not logged in redirect him to LoginActivity Activity
            Intent i = new Intent(_context, LoginActivity.class);
            // Closing all the Activities
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            // Add new Flag to start new Activity
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            // Staring LoginActivity Activity
            _context.startActivity(i);
        }

    }



    /**
     * Get stored session data
     * */
    public HashMap<String, String> getUserDetails(){
        HashMap<String, String> user = new HashMap<String, String>();
        // user usuarioNombre



        user.put(KEY_EMPRESA_RAZONSOCIAL, pref.getString(KEY_EMPRESA_RAZONSOCIAL, null));
        user.put(KEY_SALA_NOMBRE, pref.getString(KEY_SALA_NOMBRE, null));
        user.put(KEY_USUARIO_NOMBRE, pref.getString(KEY_USUARIO_NOMBRE, null));

        // user usuarioId id
        user.put(KEY_USUARIO_ID, pref.getString(KEY_USUARIO_ID, null));

        user.put(KEY_EMPLEADO_ID, pref.getString(KEY_EMPLEADO_ID, null));

        user.put(KEY_USUARIO_EMPLEADO, pref.getString(KEY_USUARIO_EMPLEADO, null));

        user.put(KEY_USUARIO_CORREO, pref.getString(KEY_USUARIO_CORREO, null));

        user.put(KEY_USUARIO_ROL, pref.getString(KEY_USUARIO_ROL, null));
        user.put(KEY_SALA, pref.getString(KEY_SALA, null));
        user.put(KEY_EMPRESA, pref.getString(KEY_EMPRESA, null));

        // return user
        return user;
    }

    /**
     * Clear session details
     * */
    public void logoutUser(){
        // Clearing all data from Shared Preferences
        editor.clear(); //comente esto
//        prefs.edit().remove(Constants.PRODUCT_ID).apply();

        editor.remove(IS_LOGIN).apply();


        editor.remove(KEY_EMPRESA_RAZONSOCIAL).apply();
        editor.remove(KEY_SALA_NOMBRE).apply();

        editor.remove(KEY_USUARIO_NOMBRE).apply();
        editor.remove(KEY_EMPLEADO_ID).apply();
        editor.remove(KEY_USUARIO_ID).apply();

        editor.remove(KEY_USUARIO_EMPLEADO).apply();

        editor.remove(KEY_USUARIO_CORREO).apply();

        editor.remove(KEY_USUARIO_ROL).apply();
        editor.remove(KEY_SALA).apply();
        editor.remove(KEY_EMPRESA).apply();


        //editor.commit(); //comente esto
        // After logout redirect user to Loing Activity
        Intent i = new Intent(_context, LoginActivity.class);
        // Closing all the Activities
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        // Add new Flag to start new Activity
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        // Staring LoginActivity Activity
        _context.startActivity(i);
    }

    /**
     * Quick check for login
     * **/
    // Get LoginActivity State
    public boolean isLoggedIn(){
        return pref.getBoolean(IS_LOGIN, false);
    }

    //sesion IP


}

