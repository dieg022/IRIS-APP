package com.nexwrfc.iris.iris.Activity;


import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;

import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.messaging.FirebaseMessaging;
import com.nexwrfc.iris.iris.ClasesGenericas.Funciones;
import com.nexwrfc.iris.iris.DTO.CarroDTO;
import com.nexwrfc.iris.iris.DTO.HotelDTO;
import com.nexwrfc.iris.iris.DTO.LoginDTO;
import com.nexwrfc.iris.iris.DTO.TokenDTO;
import com.nexwrfc.iris.iris.ListView.CarroListadoAdapter;
import com.nexwrfc.iris.iris.ListView.HotelesListAdapter;
import com.nexwrfc.iris.iris.ListView.RecyclerTouchListener;
import com.nexwrfc.iris.iris.R;
import com.nexwrfc.iris.iris.Services.CarroRest;
import com.nexwrfc.iris.iris.Services.HotelRest;
import com.nexwrfc.iris.iris.Services.RestUsuario;
import com.nexwrfc.iris.iris.Services.RetroFitAdapter;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by diego on 11/04/2018.
 */

public class ConfigActivity extends AppCompatActivity {

    private SharedPreferences prefs;
    private static final String shared = "iris19.conf";

    //CONFIG IU
    private RecyclerView rvHoteles;

    private TextView tvNumeroCarro;
    private Switch swUsuario;
    private Button btAceptarConfig;
    private ConstraintLayout ctConfigCarro;
    List<HotelDTO> hoteles;

    private RecyclerView recycler;
    private RecyclerView recyclerCarros;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager lManager;

    HotelDTO hotelSeleccionado;
    CarroDTO carroSeleccionado;
    String nombreActivity;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        Funciones funciones=new Funciones();
        prefs = getSharedPreferences(getString(R.string.nombreFicheroConfiguracion), Context.MODE_PRIVATE);
        nombreActivity = getIntent().getStringExtra("Activity");
        Log.e("DEBUG","LLEGAMOS");
        //Si el tipo de usuario es 0 es una tablet de usuario, si es -1 no hay preferencias en esta tablet aún
        // o si proviene de otra actividad para volver a configurar la tablet
        if (prefs.getInt("tipoUsuario", -1) == -1 || nombreActivity != null)
        {
            comprobarPermisos();
            configuracion();
        }
        //Si el tipo de usuario es 0, es usuario normal si es 1 es carro
        else if (prefs.getInt("tipoUsuario", -1) == 0)//USUARIO LOGGIN
        {
            Intent intent = new Intent(ConfigActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        }
        else if (prefs.getInt("tipoUsuario", -1) == 1)//USUARIO CARRO
        {
            if(funciones.isNetDisponible(this,this))
            {
                Integer idHotel = prefs.getInt("hotel", -1);
                Integer idCarro = prefs.getInt("carro", -1);
                String nombreHotel = prefs.getString("nombrehotel", "");

                final SharedPreferences.Editor editor = prefs.edit();
                final String canalSuscrito = prefs.getString("notificacion", "");//OBTENEMOS EL CANAL DE LAS NOTIFICACIONES

                if (canalSuscrito != "")
                    FirebaseMessaging.getInstance().unsubscribeFromTopic(canalSuscrito);


                //COGER NOMBRE HOTEL Y CARRO PROPIETARIO DE LA TABLET
                String nombreCanal = nombreHotel + "-3-" + idCarro;

                editor.putString("notificacion", nombreCanal);

                FirebaseMessaging.getInstance().subscribeToTopic(nombreCanal);

                Log.d("FIREBASE", "NOS SUSCRIBIMOS" + nombreCanal);

                editor.commit();

                //Hacer loggin token

                Retrofit retrofit = new RetroFitAdapter().getAdaptador();
                RestUsuario restUsuario = retrofit.create(RestUsuario.class);
                Log.e("TABLET1",nombreCanal);
                LoginDTO loginToken=new LoginDTO("tomir","admin");
                Log.e("TABLET 2",nombreCanal);
                Call<TokenDTO> call2 = restUsuario.login(loginToken);
                Log.e("LOG TABLET","1");
                call2.enqueue(new Callback<TokenDTO>() {
                    @Override
                    public void onResponse(Call<TokenDTO> call, Response<TokenDTO> response) {
                        Log.e("LOG TABLET",""+response.code());

                        switch (response.code())
                        {

                            case 200:
                                final TokenDTO tokenDTO = response.body();
                                Log.e("TOKEN DEVUELTO",""+tokenDTO.getToken());

                                if(tokenDTO.getToken()!=null)
                                {
                                    final SharedPreferences prefs;
                                    prefs = getSharedPreferences(getString(R.string.nombreFicheroConfiguracion), Context.MODE_PRIVATE);
                                    final SharedPreferences.Editor editor = prefs.edit();
                                    editor.putString("token",tokenDTO.getToken());
                                    editor.commit();

                                    Intent intent = new Intent(ConfigActivity.this, EstadosTareasActivity.class);
                                    intent.putExtra("Activity", "Carro");
                                    startActivity(intent);
                                    finish();

                                }

                                break;
                            case 400:
                                Log.e("DATOS", call.request().toString());
                                Log.e("DATA", "400 primo" + response.body());
                                break;
                            case 500:
                                Log.e("DATOS 500", call.request().toString());
                                break;
                            default:
                                break;
                        }
                    }

                    @Override
                    public void onFailure(Call<TokenDTO> call, Throwable t) {
                        Log.e("Explota", t.toString());
                        Log.e("Explota", call.request().toString());

                    }
                });


            }
            else
                Toast.makeText(this,"Debe conectarse a una red!!!",Toast.LENGTH_LONG);
        }


    }

    private void comprobarPermisos()
    {
        int permisoMicrofono=ContextCompat.checkSelfPermission(ConfigActivity.this, android.Manifest.permission.RECORD_AUDIO);
        int permisoEscritura=ContextCompat.checkSelfPermission(ConfigActivity.this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (ContextCompat.checkSelfPermission(ConfigActivity.this,
                Manifest.permission.RECORD_AUDIO)
                != PackageManager.PERMISSION_GRANTED) {


            if (ActivityCompat.shouldShowRequestPermissionRationale(ConfigActivity.this,
                    Manifest.permission.RECORD_AUDIO)) {
                Toast.makeText(ConfigActivity.this, "Tienes que dar permisos de acceso al microfono!!!", Toast.LENGTH_LONG).show();


                ActivityCompat.requestPermissions(ConfigActivity.this,
                        new String[]{Manifest.permission.RECORD_AUDIO}, permisoMicrofono);

            } else {
                // Show user dialog to grant permission to record audio
                ActivityCompat.requestPermissions(ConfigActivity.this,
                        new String[]{Manifest.permission.RECORD_AUDIO}, permisoMicrofono);
            }
        }
        //PERMISOS DE ESCRITURA
        if (ContextCompat.checkSelfPermission(ConfigActivity.this,Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(ConfigActivity.this,Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                Toast.makeText(ConfigActivity.this, "Tienes que dar permisos de escritura!!!", Toast.LENGTH_LONG).show();


                ActivityCompat.requestPermissions(ConfigActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 225);

            } else {
                ActivityCompat.requestPermissions(ConfigActivity.this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, permisoEscritura);
            }
        }

    }
    //NOS LOGUEAMOS COMO CARRO PARA OBTENER LOS DATOS
    private void configInterface()
    {
        Retrofit retrofit = new RetroFitAdapter().getAdaptador();
        RestUsuario restUsuario = retrofit.create(RestUsuario.class);
        LoginDTO loginToken=new LoginDTO("tomir","Bearer $2a$10$e.9F/rbbtGK4WPikW/EYnOd4D.VSMfQsJadIv/P/srLsCRhMuyKZa");
        Call<TokenDTO> call = restUsuario.login(loginToken);

        call.enqueue(new Callback<TokenDTO>() {
            @Override
            public void onResponse(Call<TokenDTO> call, Response<TokenDTO> response) {

                switch (response.code()) {
                    case 200:
                        final TokenDTO tokenDTO = response.body();
                        Log.e("TOKEN DEVUELTO",""+tokenDTO.getToken());

                        if(tokenDTO.getToken()!=null)
                        {
                            final SharedPreferences prefs;
                            prefs = getSharedPreferences(getString(R.string.nombreFicheroConfiguracion), Context.MODE_PRIVATE);
                            final SharedPreferences.Editor editor = prefs.edit();
                            editor.putString("token",tokenDTO.getToken());
                            editor.commit();
                            configuracion();
                        }

                        break;
                    case 400:
                        Log.e("DATOS", call.request().toString());
                        Log.e("DATA", "400 primo" + response.body());
                        break;
                    case 500:

                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onFailure(Call<TokenDTO> call, Throwable t) {
                Log.e("Explota", t.toString());
                Log.e("Explota", call.request().toString());

            }
        });


    }
    private void configuracion()
    {
        setContentView(R.layout.instalador);
        Retrofit retrofit = new RetroFitAdapter().getAdaptador();

        HotelRest restHotel= retrofit.create(HotelRest.class);

        SharedPreferences prefs = getSharedPreferences(getString(R.string.nombreFicheroConfiguracion), Context.MODE_PRIVATE);
        String token = prefs.getString("token", "");

        Call<List<HotelDTO>> call = restHotel.getHoteles("Bearer "+token);

        call.enqueue(new Callback<List<HotelDTO>>() {
            @Override
            public void onResponse(Call<List<HotelDTO>> call, Response<List<HotelDTO>> response) {

                switch (response.code()) {
                    case 200:
                        hoteles = response.body();
                        cargarListaHoteles();
                        break;
                    case 400:
                        Log.e("DATOS", call.request().toString());
                        Log.e("DATA", "400 primo" + response.body());
                        break;
                    case 500:

                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onFailure(Call<List<HotelDTO>> call, Throwable t) {
                Log.e("Explota", t.toString());
                Log.e("Explota", call.request().toString());
                Log.e("Explota", call.request().body().toString());
            }
        });
    }

    private void cargarListaHoteles()
    {
        recycler = (RecyclerView) findViewById(R.id.rv_lista_hoteles);
        recycler.setHasFixedSize(true);
        lManager = new LinearLayoutManager(this);
        recycler.setLayoutManager(lManager);
        adapter = new HotelesListAdapter(hoteles);
        recycler.setAdapter(adapter);
        swUsuario=(Switch) findViewById(R.id.sw_usuario);
        ctConfigCarro=(ConstraintLayout) findViewById(R.id.ctl_configuracion_carro);
        btAceptarConfig=(Button) findViewById(R.id.bt_aceptar_configuracion);

        //CLICK DEL SWITCH
        swUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                AnimationSet set=new AnimationSet(true);
                Animation animation=null;
                if(swUsuario.isChecked()==Boolean.TRUE)
                    ctConfigCarro.setVisibility(View.VISIBLE);
                else
                    ctConfigCarro.setVisibility(View.INVISIBLE);
            }
        });

        recycler.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recycler, new RecyclerTouchListener.ClickListener()
        {
            @Override
            public void onClick(View view, int position)
            {
                hotelSeleccionado= hoteles.get(position);
                cargarCarrosHotel(hotelSeleccionado);


            }

            @Override
            public void onLongClick(View view, int position)
            {


            }

        }));

        //SALVAR CONFIGURACION BOTON
        btAceptarConfig.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v)
            {
                SharedPreferences.Editor editor = prefs.edit();

                if(swUsuario.isChecked()==Boolean.TRUE)
                {

                    if(hotelSeleccionado!=null && carroSeleccionado!=null) {
                        Integer idHotel = prefs.getInt("hotel", -1);
                        Integer idCarro = prefs.getInt("carro", -1);
                        String nombreHotel = prefs.getString("nombrehotel", "");

                        final String canalSuscrito = prefs.getString("notificacion", "");//OBTENEMOS EL CANAL DE LAS NOTIFICACIONES

                        if (canalSuscrito != "")
                            FirebaseMessaging.getInstance().unsubscribeFromTopic(canalSuscrito);

                        //GUARDAMOS LA CONFIGURACIÓN DE LA TABLET TIPO CARRO

                        String nombreCanal = nombreHotel + "-3-" + idCarro;
                        editor = prefs.edit();
                        editor.putInt("tipoUsuario", 1);
                        editor.putInt("hotel", hotelSeleccionado.getId());
                        editor.putInt("carro", carroSeleccionado.getNumero());
                        editor.putString("nombrehotel", hotelSeleccionado.getNombre());

                        Log.e("LOGIN", "CARRO" + carroSeleccionado.getId() + "");
                        editor.putString("notificacion", nombreCanal);
                        editor.commit();

                        FirebaseMessaging.getInstance().subscribeToTopic(nombreCanal);
                        Log.d("FIREBASE", "NOS SUSCRIBIMOS" + nombreCanal);

                        //Si la actividad es null, quiere decir que es la primera configuración
                        Log.e("CONFIG", nombreActivity + "");


                        if (nombreActivity == null) {
                            Intent intent = new Intent(ConfigActivity.this, EstadosTareasActivity.class);
                            intent.putExtra("Activity", "Carro");
                            startActivity(intent);
                            finish();
                        } else {

                            Toast.makeText(ConfigActivity.this, "Para ver los cambios debe reiniciar la aplicación", Toast.LENGTH_SHORT);
                            finish();
                        }
                    }
                    else
                        Toast.makeText(ConfigActivity.this, "Debe seleccionar el hotel y el carro para configurar correctamente la tablet!!!", Toast.LENGTH_SHORT);

                }
                else
                {

                    Log.e("CONFIG","Es tipo usuario");
                    editor = prefs.edit();
                    editor.putInt("tipoUsuario",0);
                    editor.commit();
                    Intent intent=new Intent(ConfigActivity.this,LoginActivity.class);
                    finish();
                }
            }
        });



    }
    private void cargarCarrosHotel(HotelDTO hotel)
    {

        Retrofit retrofit2=new RetroFitAdapter().getAdaptador();

        CarroRest carroRest2=retrofit2.create(CarroRest.class);

        SharedPreferences prefs = getSharedPreferences(getString(R.string.nombreFicheroConfiguracion), Context.MODE_PRIVATE);
        String token = prefs.getString("token", "");

        Call<List<CarroDTO>> call2=carroRest2.getCarrosHotel(hotel.getId(),"Bearer "+token);

        call2.enqueue(new Callback<List<CarroDTO>>()
        {
            @Override
            public void onResponse(Call<List<CarroDTO>> call2, Response<List<CarroDTO>> response2)
            {

                switch (response2.code())
                {

                    case 200:
                        List<CarroDTO>   carros = response2.body();
                        inicializarListaCarros(carros);

                        break;
                    case 400:
                        Log.e("DATOS",call2.request().toString());
                        Log.e("DATA","400 primo"+response2.body());
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onFailure(Call<List<CarroDTO>> call, Throwable t)
            {
                Log.e("Explota", t.toString());
                Log.e("Explota", call.request().toString());
                Log.e("Explota", call.request().body().toString());
            }
        });
    }
    private void inicializarListaCarros(final List<CarroDTO> carros)
    {

        recyclerCarros = (RecyclerView) findViewById(R.id.rv_lista_carros);
        recyclerCarros.setHasFixedSize(true);
        lManager = new LinearLayoutManager(this);
        recyclerCarros.setLayoutManager(lManager);
        adapter=new CarroListadoAdapter(carros);
        recyclerCarros.setAdapter(adapter);


        recyclerCarros.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recycler, new RecyclerTouchListener.ClickListener()
        {
            @Override
            public void onClick(View view, int position)
            {
                carroSeleccionado= carros.get(position);
                cargarCarrosHotel(hotelSeleccionado);

            }

            @Override
            public void onLongClick(View view, int position)
            {


            }

        }));

    }
}
