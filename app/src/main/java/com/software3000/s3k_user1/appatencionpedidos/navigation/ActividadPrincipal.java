package com.software3000.s3k_user1.appatencionpedidos.navigation;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.software3000.s3k_user1.appatencionpedidos.CheckoutActivity;
import com.software3000.s3k_user1.appatencionpedidos.R;
import com.software3000.s3k_user1.appatencionpedidos.helpers.MySharedPreference;
import com.software3000.s3k_user1.appatencionpedidos.helpers.SessionManager;
import com.software3000.s3k_user1.appatencionpedidos.loginSistema.LoginActivity;
import com.software3000.s3k_user1.appatencionpedidos.model.CortesiaAtencion;
import com.software3000.s3k_user1.appatencionpedidos.services.VolleySingleton;
import com.software3000.s3k_user1.appatencionpedidos.ui.navegacionlateral.FragmentoCategorias;
import com.software3000.s3k_user1.appatencionpedidos.ui.navegacionlateral.FragmentoInicio;
import com.software3000.s3k_user1.appatencionpedidos.ui.navegacionlateral.FragmentoPedidos;
import com.software3000.s3k_user1.appatencionpedidos.ui.navegacionlateral.FragmentoProductos;
import com.software3000.s3k_user1.appatencionpedidos.utils.CountDrawable;
import com.software3000.s3k_user1.appatencionpedidos.utils.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class ActividadPrincipal extends AppCompatActivity {

    private DrawerLayout drawerLayout;
private NavigationView navigationView;
    public int item;
    public static Menu menuclasprincipal;
    public static int cantidadCarrito;

    private MySharedPreference sharedPreference;
    public static Context contextoAcPrincipal;
    public static Activity activitydelAcPrincipal;
    SessionManager session;
    String sesion_usuario = "";

    // id
    String sesion_id = "";

    String sesion_empleado = "";
    String sesion_empresa_razonsocial = "";
    String sesion_salanombre = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actividad_principal);

        agregarToolbar();

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
         navigationView = (NavigationView) findViewById(R.id.nav_view);

        if (navigationView != null) {
            prepararDrawer(navigationView);
            // Seleccionar item por defecto
            seleccionarItem(navigationView.getMenu().getItem(0));
        }
        session = new SessionManager(getApplicationContext());
        session.checkLogin();

        HashMap<String, String> user = session.getUserDetails();
        sesion_usuario = user.get(SessionManager.KEY_USUARIO_NOMBRE);
        sesion_id = user.get(SessionManager.KEY_USUARIO_ID);
        sesion_empleado = user.get(SessionManager.KEY_USUARIO_EMPLEADO);

        sesion_empresa_razonsocial = user.get(SessionManager.KEY_EMPRESA_RAZONSOCIAL);
        sesion_salanombre = user.get(SessionManager.KEY_SALA_NOMBRE);

        View header = ((NavigationView)findViewById(R.id.nav_view)).getHeaderView(0);
        ((TextView) header.findViewById(R.id.usuario_sesion_navegacion)).setText(sesion_empresa_razonsocial + " \n"+ sesion_salanombre);
        ((TextView) header.findViewById(R.id.nombreusuario_sesion_navegacion)).setText(sesion_usuario);
        sharedPreference = new MySharedPreference(this.getApplicationContext());
        contextoAcPrincipal = this.getApplicationContext();

        seguirPeticiciones();
    }
    public void seguirPeticiciones() {

        final Handler handler = new Handler();
        final Timer timer = new Timer(false);
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        //estado 2 por recoger
                        if (session.isLoggedIn()){
                            peticiconesServicioPedidosPorRecoger("2");
                        }else{
                            timer.cancel();
                        }


                    }
                });
            }
        };

        timer.scheduleAtFixedRate(timerTask, 5000, 5000); // every 5 seconds.
        //timer.cancel();

    }
    public String ObtenerIp(){

        SharedPreferences sharedPreferences =getSharedPreferences("Protocol", Context.MODE_PRIVATE);
        String ip =sharedPreferences.getString("ip","");
        return ip ;
    }

    public void peticiconesServicioPedidosPorRecoger(final String estado){

        //la lista  tambien  eenvio la sessionid del usuario para encuentre si tiene
        //pedidos para recoger solo  por ese usuario
        String URls =  ObtenerIp()+"/Cortesias/ListarCortesiaPedidoPendientesPorEstado";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URls, new com.android.volley.Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                JSONArray jsondata=null;
                try {
                    //converting response to json object
                    JSONObject jsonObject = new JSONObject(response);


//                    JSONArray datosArray = new JSONArray(response);
                    jsondata = jsonObject.getJSONArray("data");
                    List<CortesiaAtencion> cortesiaAtencions = new ArrayList<>();
                    
                    if (jsondata.length()!=0){
                        Toast.makeText(getApplicationContext(), sesion_usuario+" Tiene Pedidos por Recoger", Toast.LENGTH_SHORT).show();
                        Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
                        //TODO Comentario Vibrator
                        if (Build.VERSION.SDK_INT >= 26) {
                            vibrator.vibrate(VibrationEffect.createOneShot(200, VibrationEffect.DEFAULT_AMPLITUDE));
                        } else {
                            vibrator.vibrate(200);
                        }
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("estado", estado);
                params.put("usuarioRegistroId", sesion_id);
                return params;
            }
        };

        //VolleySingleton.getInstance(this).addToRequestQueue(stringRequest); //en un activity

        //AppSin
        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
    }
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("Frag", item);
    }
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        item = savedInstanceState.getInt("Frag");
        seleccionarItem(navigationView.getMenu().getItem(item));
    }

    private void agregarToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        final ActionBar ab = getSupportActionBar();
        if (ab != null) {
            // Poner ícono del drawer toggle
            ab.setHomeAsUpIndicator(R.drawable.drawer_toggle);
            ab.setDisplayHomeAsUpEnabled(true);
        }

    }

    private void prepararDrawer(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        menuItem.setChecked(true);
                        seleccionarItem(menuItem);
                        drawerLayout.closeDrawers();
                        return true;
                    }
                });

    }

    private void seleccionarItem(MenuItem itemDrawer) {
        Fragment fragmentoGenerico = null;
        FragmentManager fragmentManager = getSupportFragmentManager();

        switch (itemDrawer.getItemId()) {
            case R.id.item_inicio:
                fragmentoGenerico = new FragmentoInicio();
                item=0;
                break;
//            case R.id.item_cuenta:
//                fragmentoGenerico = new FragmentoCuenta();
//                item=1;
//                break;
//            case R.id.item_categorias:
//                fragmentoGenerico = new FragmentoCategorias();
//                item=1;
//                break;
            case R.id.item_pedidos:
                fragmentoGenerico = new FragmentoPedidos();
                item=1;
                break;
//            case R.id.item_productos:
//
//                fragmentoGenerico = new FragmentoProductos();
//                item=3;
//                break;
            case R.id.item_cerrarsesion:
                session.logoutUser();
                Intent intent1 = new Intent(this,LoginActivity.class);
                startActivity(intent1);
                item=2;
                break;

            case R.id.item_configuracion:
                startActivity(new Intent(this, ActividadConfiguracion.class));
                item=3;
                break;

        }
        if (fragmentoGenerico != null) {
            fragmentManager
                    .beginTransaction()
                    .replace(R.id.contenedor_principal, fragmentoGenerico)
                    .commit();
        }

        // Setear título actual
        setTitle(itemDrawer.getTitle());
    }

    public void setCount(Context context, String count) {
//        MenuItem menuItem = defaultMenu.findItem(R.id.ic_group);
//        LayerDrawable icon = (LayerDrawable) menuItem.getIcon();

//        CountDrawable badge;
//
//        // Reuse drawable if possible
//        Drawable reuse = icon.findDrawableByLayerId(R.id.ic_group_count);
//        if (reuse != null && reuse instanceof CountDrawable) {
//            badge = (CountDrawable) reuse;
//        } else {
//            badge = new CountDrawable(context);
//        }
//
//        badge.setCount(count);
//        icon.mutate();
//        icon.setDrawableByLayerId(R.id.ic_group_count, badge);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menuclasprincipal = menu;
        getMenuInflater().inflate(R.menu.menu_actividad_principal, menu);

//        MenuItem item = menu.findItem(R.id.action_carrito);
//
//        // Obtener drawable del item
//        LayerDrawable icon = (LayerDrawable) item.getIcon();
//
//        // Actualizar el contador
//        Utils.setBadgeCount(this, icon, 0);

        CountDrawable badge;

        MenuItem menuItem = menu.findItem(R.id.action_carrito);

        LayerDrawable icon = (LayerDrawable) menuItem.getIcon();
        // Reuse drawable if possible

        int mCount = sharedPreference.retrieveProductCount();

//        Utils.setBadgeCount(this, icon, mCount);
        Utils.setCount(this, icon, mCount);
        return true;
    }
    //Agregar carrito nuevo

    //
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
        }

        if (item.getItemId() == R.id.action_carrito) {
            Intent checkoutIntent = new Intent(ActividadPrincipal.this, CheckoutActivity.class);
            startActivity(checkoutIntent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
