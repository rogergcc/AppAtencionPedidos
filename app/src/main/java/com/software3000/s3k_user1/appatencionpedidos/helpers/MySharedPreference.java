package com.software3000.s3k_user1.appatencionpedidos.helpers;

import android.content.Context;
import android.content.SharedPreferences;

import com.software3000.s3k_user1.appatencionpedidos.model.MaquinaZona;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class MySharedPreference {
    private SharedPreferences prefs;
    private Context context;

    private GsonBuilder builder = new GsonBuilder();
    private Gson gson = builder.create();
    public MySharedPreference(Context context){
        this.context = context;
        prefs = context.getSharedPreferences(Constants.SHARED_PREF, Context.MODE_PRIVATE);
    }

    public String ObtenerIp(){

        String ip =prefs.getString("ip", "");
        return ip ;
    }

    public void GuardarReferenciaIp(String ip) {
        SharedPreferences.Editor edits = prefs.edit();
        edits.putString("ip", ip);
        edits.apply();

    }

    //Maquina
    public void guardarPreferenciaMaquinaZona(MaquinaZona maquinaZona){
        SharedPreferences.Editor edits = prefs.edit();
        String newMaquinaproducto = gson.toJson(maquinaZona);
        edits.putString(Constants.MAQUINA_ID, newMaquinaproducto);
        edits.apply();
    }


    public String recibirPreferenciaMaquinaGuardada(){

        //gson.fromJson(mShared.retrieveProductFromCart(), CortesiaProductos[].class);
        return prefs.getString(Constants.MAQUINA_ID, "");
    }
    //FIN Maquina

    public void addProductToTheCart(String product){
        SharedPreferences.Editor edits = prefs.edit();
        edits.putString(Constants.PRODUCT_ID, product);
        edits.apply();
    }
    public String retrieveProductFromCart(){
        return prefs.getString(Constants.PRODUCT_ID, "");
    }

    public void addComboToTheCart(String product){
        SharedPreferences.Editor edits = prefs.edit();
        edits.putString(Constants.COMBO_ID, product);
        edits.apply();
    }
    public String retrieveComboFromCart(){
        return prefs.getString(Constants.COMBO_ID, "");
    }
    //CANTIDADES
    public void addProductCount(int productCount){
        SharedPreferences.Editor edits = prefs.edit();
        edits.putInt(Constants.PRODUCT_COUNT, productCount);
        edits.apply();
    }


    public int retrieveProductCount(){
        return prefs.getInt(Constants.PRODUCT_COUNT, 0);
    }
    public void deleteAllProductCount( ){
        //prefs.edit().clear().apply();
        prefs.edit().remove(Constants.PRODUCT_COUNT).apply();
    }

    public void deleteAllProductsFromTheCart( ){
        //prefs.edit().clear().apply();
        prefs.edit().remove(Constants.PRODUCT_ID).apply();
    }

    public void deleteProductfromTheCart(String product){


        SharedPreferences.Editor editor = prefs.edit();
        editor.remove(Constants.PRODUCT_ID);
        editor.commit();
    }



}
