package com.nexwrfc.iris.iris.Activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.nexwrfc.iris.iris.DTO.TareaDTO;
import com.nexwrfc.iris.iris.DTO.Usuario;
import com.nexwrfc.iris.iris.R;
import com.nexwrfc.iris.iris.ResponseAPI.ResponseLogUsuario;
import com.nexwrfc.iris.iris.Services.RetroFitAdapter;
import com.nexwrfc.iris.iris.Services.TareasRest;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by diego on 06/03/2018.
 */

public class Nivel2PanelActivity extends AppCompatActivity {

   private Button btAsignar;
   private Button btEstados;
   private Integer idUsuario,nivelUsuario,idHotel;
   private String perfilUsuario,aliasUsuario,nombreHotel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ResponseLogUsuario usuario=(ResponseLogUsuario) getIntent().getExtras().get("usuario");
        cargarInterface(usuario);

    }

    public void cargarInterface(final ResponseLogUsuario usuario)
    {

        setContentView(R.layout.activity_inicial_nivel2);

        comprobarTareas(usuario.getHotel().getId());
        btAsignar=(Button) findViewById(R.id.bt_asignar_tareas);
        btEstados=(Button) findViewById(R.id.bt_estados_tareas);

        btAsignar.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent=new Intent(Nivel2PanelActivity.this,AsignarTareasActivity.class);
                intent.putExtra("usuario",usuario);
                startActivity(intent);
            }
        });

        btEstados.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent=new Intent(Nivel2PanelActivity.this,EstadosTareasActivity.class);
                intent.putExtra("usuario",usuario);
                intent.putExtra("Activity", "Usuario");
                startActivity(intent);
            }
        });
    }

    //Comprueba si hay tareas pendientes
    private void comprobarTareas(Integer idHotel)
    {
        Retrofit retrofit=new RetroFitAdapter().getAdaptador();

        TareasRest tareasRest=retrofit.create(TareasRest.class);

        SharedPreferences prefs = getSharedPreferences(getString(R.string.nombreFicheroConfiguracion), Context.MODE_PRIVATE);
        String token = prefs.getString("token", "");
        Call<List<TareaDTO>> call=tareasRest.getTareasHoy(idHotel,0,token);

        call.enqueue(new Callback<List<TareaDTO>>()
        {
            @Override
            public void onResponse(Call<List<TareaDTO>> call, Response<List<TareaDTO>> response)
            {
                switch (response.code())
                {
                    case 200:
                        if(response.body().toString()=="[]")
                            btAsignar.setVisibility(View.INVISIBLE);
                        else
                            btAsignar.setVisibility(View.VISIBLE);


                        break;
                    case 400:
                        Log.e("DATOS",call.request().toString());
                        Log.e("DATA","400 primo"+response.body());
                    break;
                    case 500:

                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onFailure(Call<List<TareaDTO>> call, Throwable t)
            {
                Log.e("Explota", t.toString());
                Log.e("Explota", call.request().toString());
                Log.e("Explota", call.request().body().toString());
            }
        });
    }
}
