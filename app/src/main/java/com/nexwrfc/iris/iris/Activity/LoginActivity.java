package com.nexwrfc.iris.iris.Activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;

import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;
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
import com.nexwrfc.iris.iris.ResponseAPI.ResponseLogUsuario;

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
 * Created by diego on 06/03/2018.
 */

public class LoginActivity extends AppCompatActivity
{
    //LOGGIN IU
    private EditText etAlias;
    private EditText etPass;

    private RecyclerView recycler;
    private RecyclerView recyclerCarros;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager lManager;

    //CONFIG IU
    private RecyclerView rvHoteles;

    private TextView tvNumeroCarro;
    private Switch swUsuario;
    private Button btAceptarConfig;
    private ConstraintLayout ctConfigCarro;

    HotelDTO hotelSeleccionado;
    CarroDTO carroSeleccionado;

    SharedPreferences prefs;
    List<HotelDTO> hoteles;



    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        String refreshedToken = FirebaseInstanceId.getInstance().getToken();

        prefs = getSharedPreferences(getString(R.string.nombreFicheroConfiguracion),Context.MODE_PRIVATE);

        //Si el tipo de usuario es 0 es una tablet de usuario, si es -1 no hay preferencias en esta tablet aún
        if(prefs.getInt("tipoUsuario",-1)==-1 )
        {
            configInterface();
        }
        //Si el tipo de usuario es 0, es usuario normal si es 1 es carro
        else if(prefs.getInt("tipoUsuario",-1)==0)//USUARIO LOGGIN
        {
            logginInterface();
        }


    }

    private void configInterface()
    {
        Retrofit retrofit = new RetroFitAdapter().getAdaptador();
        RestUsuario restUsuario = retrofit.create(RestUsuario.class);
        LoginDTO loginToken=new LoginDTO("tomir","$2a$10$e.9F/rbbtGK4WPikW/EYnOd4D.VSMfQsJadIv/P/srLsCRhMuyKZa");
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
                            setContentView(R.layout.instalador);
                            Retrofit retrofit = new RetroFitAdapter().getAdaptador();

                            HotelRest restHotel= retrofit.create(HotelRest.class);

                            Call<List<HotelDTO>> call2 = restHotel.getHoteles(tokenDTO.getToken());


                            call2.enqueue(new Callback<List<HotelDTO>>() {
                                @Override
                                public void onResponse(Call<List<HotelDTO>> call2, Response<List<HotelDTO>> response) {

                                    switch (response.code()) {
                                        case 200:
                                            hoteles = response.body();
                                            cargarListaHoteles(tokenDTO.getToken());
                                            break;
                                        case 400:
                                            Log.e("DATOS", call2.request().toString());
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

    private void cargarListaHoteles(final String token)
    {
        recycler = (RecyclerView) findViewById(R.id.rv_lista_hoteles);
        recycler.setHasFixedSize(true);
        lManager = new LinearLayoutManager(this);
        recycler.setLayoutManager(lManager);
        adapter = new HotelesListAdapter(hoteles);
        recycler.setAdapter(adapter);


        recycler.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recycler, new RecyclerTouchListener.ClickListener()
        {
            @Override
            public void onClick(View view, int position)
            {
                hotelSeleccionado= hoteles.get(position);
                cargarCarrosHotel(hotelSeleccionado,token);


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
                    editor.putInt("tipoUsuario",1);
                    editor.putInt("hotel",hotelSeleccionado.getId());
                    editor.putInt("carro",carroSeleccionado.getNumero());
                    editor.commit();
                    Intent intent=new Intent(LoginActivity.this,EstadosTareasActivity.class);

                    startActivity(intent);
                    finish();

                }
                else
                {
                    editor.putInt("tipoUsuario",0);
                    editor.commit();
                    logginInterface();
                }
            }
        });



    }

    private void cargarCarrosHotel(HotelDTO hotel,final String token)
    {

        Retrofit retrofit2=new RetroFitAdapter().getAdaptador();

        CarroRest carroRest2=retrofit2.create(CarroRest.class);

        Call<List<CarroDTO>> call2=carroRest2.getCarrosHotel(hotel.getId(),token);

        call2.enqueue(new Callback<List<CarroDTO>>()
        {
            @Override
            public void onResponse(Call<List<CarroDTO>> call2, Response<List<CarroDTO>> response2)
            {

                switch (response2.code())
                {

                    case 200:
                        List<CarroDTO>   carros = response2.body();
                        inicializarListaCarros(carros,token);

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
    private void inicializarListaCarros(final List<CarroDTO> carros,final String token)
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
                cargarCarrosHotel(hotelSeleccionado,token);


            }

            @Override
            public void onLongClick(View view, int position)
            {


            }

        }));

    }
    private void logginInterface()
    {
        setContentView(R.layout.loggin_activity);
        Button buttonLogin=(Button) findViewById(R.id.bt_login);
        etAlias=(EditText) findViewById(R.id.et_alias);
        etPass=(EditText) findViewById(R.id.et_pass);

        buttonLogin.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {

                //OBTENEMOS EL TOKEN PARA LAS PETICIONES AL SERVIDOR*/
                Retrofit retrofit = new RetroFitAdapter().getAdaptador();
                RestUsuario restUsuario = retrofit.create(RestUsuario.class);
                LoginDTO loginToken=new LoginDTO(etAlias.getText().toString(), etPass.getText().toString());
                Call<TokenDTO> call = restUsuario.login(loginToken);

                final SharedPreferences prefs;
                prefs = getSharedPreferences(getString(R.string.nombreFicheroConfiguracion), Context.MODE_PRIVATE);
                final SharedPreferences.Editor editor = prefs.edit();
                final String canalSuscrito = prefs.getString("notificacion", "");//OBTENEMOS EL CANAL DE LAS NOTIFICACIONES

                if (canalSuscrito != "")
                    FirebaseMessaging.getInstance().unsubscribeFromTopic(canalSuscrito);

                call.enqueue(new Callback<TokenDTO>() {
                    @Override
                    public void onResponse(Call<TokenDTO> call, Response<TokenDTO> response) {

                        switch (response.code()) {
                            case 200:
                                TokenDTO tokenDTO = response.body();
                                Log.e("TOKEN DEVUELTO",""+tokenDTO.getToken());

                                if(tokenDTO.getToken()!=null)
                                    hacerLoginUsuario(tokenDTO.getToken());

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
        });
    }
    private void hacerLoginUsuario(String token)
    {

          Retrofit retrofit = new RetroFitAdapter().getAdaptador();
                    RestUsuario restUsuario = retrofit.create(RestUsuario.class);
                    Call<ResponseLogUsuario> call = restUsuario.loginUsuario(etAlias.getText().toString(), etPass.getText().toString(),"Bearer "+token);

                    final SharedPreferences prefs;
                    prefs = getSharedPreferences(getString(R.string.nombreFicheroConfiguracion), Context.MODE_PRIVATE);
                    final SharedPreferences.Editor editor = prefs.edit();
                    final String canalSuscrito = prefs.getString("notificacion", "");//OBTENEMOS EL CANAL DE LAS NOTIFICACIONES
                    //GUARDAMOS EL TOKEN DEL USUARIO
                    editor.putString("token", token);
                    editor.commit();

        if (canalSuscrito != "")
                        FirebaseMessaging.getInstance().unsubscribeFromTopic(canalSuscrito);

                    call.enqueue(new Callback<ResponseLogUsuario>() {
                        @Override
                        public void onResponse(Call<ResponseLogUsuario> call, Response<ResponseLogUsuario> response) {
                            switch (response.code())
                            {
                                case 200:

                                    ResponseLogUsuario usuario = response.body();

                                    Log.e("LOGIN",""+response.body());

                                    if (usuario.getUsuario() != null)
                                    {
                                        if (usuario.getUsuario().getId() != null) {
                                            if (usuario.getUsuario().getPerfil().getNivel().getId() == 2) {
                                                Intent intent = new Intent(LoginActivity.this, Nivel2PanelActivity.class);
                                                intent.putExtra("Activity", "Usuario");
                                                intent.putExtra("usuario", usuario);

                                                //SUSCRIBIMOS ESTE USUARIO AL NIVEL 2 DE SU HOTEL, PRIMERO MIRAMOS SI NO TIENE CANAL SUSCRITO.

                                                String nombreCanal = usuario.getHotel().getNombre() + "-" + usuario.getUsuario().getPerfil().getNivel().getId() + "-" + usuario.getUsuario().getPerfil().getNombre_perfil();
                                                editor.putString("notificacion", nombreCanal);
                                                FirebaseMessaging.getInstance().subscribeToTopic(nombreCanal);
                                                Log.d("FIREBASE", "NOS SUSCRIBIMOS" + nombreCanal);
                                                editor.commit();


                                                Log.e("FIREBASE", "SUSCRITO TEMA 2");
                                                startActivity(intent);
                                            } else if (usuario.getUsuario().getPerfil().getNivel().getId() == 3) {
                                                Intent intent = new Intent(LoginActivity.this, EstadosTareasActivity.class);
                                                intent.putExtra("Activity", "Usuario");
                                                intent.putExtra("usuario", usuario);

                                                //SUSCRIBIMOS ESTE USUARIO AL NIVEL 3 DE SU HOTEL, PRIMERO MIRAMOS SI NO TIENE CANAL SUSCRITO.

                                                String nombreCanal = usuario.getHotel().getNombre() + "-" + usuario.getUsuario().getPerfil().getNivel().getId() + "-" + usuario.getUsuario().getPerfil().getNombre_perfil();
                                                editor.putString("notificacion", nombreCanal);
                                                FirebaseMessaging.getInstance().subscribeToTopic(nombreCanal);
                                                Log.d("FIREBASE", "NOS SUSCRIBIMOS" + nombreCanal);
                                                editor.commit();

                                                startActivity(intent);
                                            }
                                        }
                                    } else
                                        Toast.makeText(getApplicationContext(), "Login incorrecto", Toast.LENGTH_LONG).show();

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
                        public void onFailure(Call<ResponseLogUsuario> call, Throwable t) {
                            Log.e("Explota", t.toString());
                            Log.e("Explota", call.request().toString());

                        }
                    });
    }

}
