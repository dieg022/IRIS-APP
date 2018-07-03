package com.nexwrfc.iris.iris.Activity;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableStringBuilder;
import android.text.style.RelativeSizeSpan;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.nexwrfc.iris.iris.DTO.AsignadasDTO;
import com.nexwrfc.iris.iris.DTO.CarroDTO;
import com.nexwrfc.iris.iris.DTO.HabitacionDTO;
import com.nexwrfc.iris.iris.DTO.TareaDTO;
import com.nexwrfc.iris.iris.ListView.AsignadasTareasAdapter;
import com.nexwrfc.iris.iris.ListView.CarroListadoAdapter;
import com.nexwrfc.iris.iris.ListView.ListadoTareasAdapter;
import com.nexwrfc.iris.iris.ListView.RecyclerTouchListener;
import com.nexwrfc.iris.iris.R;
import com.nexwrfc.iris.iris.ResponseAPI.ResponseLogUsuario;
import com.nexwrfc.iris.iris.Services.CarroRest;
import com.nexwrfc.iris.iris.Services.RetroFitAdapter;
import com.nexwrfc.iris.iris.Services.TareasRest;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
/**
 * Created by diego on 09/03/2018.
 */

public class AsignarTareasActivity extends AppCompatActivity
{

    private RecyclerView recyclerTareas;
    private RecyclerView recyclerCarros;

    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager lManager;
    List<TareaDTO> tareas;

    private RecyclerView recyclerAsignadas;
    private RecyclerView.Adapter adapterAsignadas;
    private RecyclerView.LayoutManager lManagerAsignadas;

    private TareaDTO tareaPulsada;
    private CarroDTO carroPulsado;
    private ResponseLogUsuario usuario;

    private List<AsignadasDTO> asignadas;
    private Button bt_asignar;
    ProgressBar pbAsignar;

    TextView tvHabitacion;


    /*
    ACTIVIDAD PARA LA ASIGNACIÓN DE TAREAS A LOS CARROS DE UN HOTEL
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.asignar_tareas_activity);
        bt_asignar=(Button) findViewById(R.id.bt_asignar_tareas);
        tvHabitacion=(TextView) findViewById(R.id.tvHabitacionSel);
        usuario=(ResponseLogUsuario) getIntent().getExtras().get("usuario");
        asignadas=new ArrayList<>();
        cargarTareasDia();
        cargarCarrosHotel();

        pbAsignar=(ProgressBar) findViewById(R.id.pb_asignar);
        bt_asignar.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                pbAsignar.setVisibility(View.VISIBLE);
                Retrofit retrofit=new RetroFitAdapter().getAdaptador();

                SharedPreferences prefs = getSharedPreferences(getString(R.string.nombreFicheroConfiguracion), Context.MODE_PRIVATE);
                String token = prefs.getString("token", "");
                TareasRest tareasRest = retrofit.create(TareasRest.class);
                Call<List<AsignadasDTO>> call = tareasRest.setTareasEstados(asignadas,"Bearer "+token);

                call.enqueue(new Callback<List<AsignadasDTO>>()
                {


                    @Override
                    public void onResponse(Call<List<AsignadasDTO>> call, Response<List<AsignadasDTO>> response) {

                        switch (response.code()) {

                            case 200:
                                Log.e("RES",response.body().toString());
                                pbAsignar.setVisibility(View.INVISIBLE);
                                finish();

                                break;
                            case 400:
                                Log.e("DATOS", call.request().toString());
                                Log.e("DATA", "400 primo" + response.body());
                                pbAsignar.setVisibility(View.INVISIBLE);
                                break;
                            default:
                                break;
                        }
                    }

                    @Override
                    public void onFailure(Call<List<AsignadasDTO>> call, Throwable t) {
                        Log.e("Explota", t.toString());
                        Log.e("Explota", call.request().toString());
                        Log.e("Explota", call.request().body().toString());
                    }
                });

            }
        });

    }

    /*
    Carga las tareas del día que aún no están asignadas con una llamada RETROFIT2
     */
    private void cargarTareasDia()
    {
        Retrofit retrofit=new RetroFitAdapter().getAdaptador();

        SharedPreferences prefs = getSharedPreferences(getString(R.string.nombreFicheroConfiguracion), Context.MODE_PRIVATE);
        final String token = prefs.getString("token", "");
        TareasRest tareasRest=retrofit.create(TareasRest.class);
        Call<List<TareaDTO>> call=tareasRest.getTareasHoy(1,0,"Bearer "+token);

        call.enqueue(new Callback<List<TareaDTO>>()
        {
            @Override
            public void onResponse(Call<List<TareaDTO>> call, Response<List<TareaDTO>> response)
            {

                switch (response.code())
                {
                    case 200:
                        tareas = response.body();
                        inicializarListaTareas(tareas);

                        break;
                    case 400:
                        Log.e("DATOS",call.request().toString());
                        Log.e("DATA","400 primo"+response.body());
                        break;
                    case 500:
                        bt_asignar.setVisibility(View.INVISIBLE);
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

    /*
   PARAMS LIST<CARROS> INICIALIZA EL RECYCLER VIEW A PARTIR DE UNA LISTA DE TAREAS
    */
    private void inicializarListaTareas(final List<TareaDTO> tareas)
    {
        recyclerTareas = (RecyclerView) findViewById(R.id.rv_listaTareas);
        recyclerTareas.setHasFixedSize(true);
        lManager = new LinearLayoutManager(this);
        recyclerTareas.setLayoutManager(lManager);
        adapter=new ListadoTareasAdapter(tareas);
        recyclerTareas.setAdapter(adapter);
        recyclerTareas.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recyclerTareas, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {

                tareaPulsada= tareas.get(position);
                tvHabitacion.setText("HABITACION PULSADA:"+tareas.get(position).getHabitacion().getNumero());
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
    }

    /*
   Carga los carros del hotel con una llamada ReTROFIT2
    */
    private void cargarCarrosHotel()
    {

        Retrofit retrofit2=new RetroFitAdapter().getAdaptador();

        CarroRest carroRest2=retrofit2.create(CarroRest.class);

        SharedPreferences prefs = getSharedPreferences(getString(R.string.nombreFicheroConfiguracion), Context.MODE_PRIVATE);
        String token = prefs.getString("token", "");
        Call<List<CarroDTO>> call2=carroRest2.getCarrosHotel(1,"Bearer "+token);

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
                        Log.e("CARROS",""+carros.size());
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

    /*
    PARAMS LIST<CARROS> INICIALIZA EL RECYCLER VIEW A PARTIR DE UNA LISTA DE CARROS
     */
    private void inicializarListaCarros(final List<CarroDTO> carros)
    {

        recyclerCarros = (RecyclerView) findViewById(R.id.rv_listaCarros);
        recyclerCarros.setHasFixedSize(true);
        lManager = new LinearLayoutManager(this);
        recyclerCarros.setLayoutManager(lManager);
        adapter=new CarroListadoAdapter(carros);
        recyclerCarros.setAdapter(adapter);


        recyclerCarros.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recyclerCarros, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                carroPulsado = carros.get(position);
                Integer plantaAsignada=-1;

                if(tareaPulsada!=null)
                {
                    carroPulsado.setPlanta(Integer.parseInt(tareaPulsada.getHabitacion().getNumero().toString().substring(0,1)));

                    recyclerAsignadas = (RecyclerView) findViewById(R.id.rv_tareas_asignadas);
                    lManagerAsignadas= new LinearLayoutManager(view.getContext());
                    recyclerAsignadas.setLayoutManager(lManagerAsignadas);

                    final AsignadasDTO asig=new AsignadasDTO();

                    asig.setCarro(carroPulsado);
                    asig.setTarea(tareaPulsada);
                    asig.setUsuario(usuario.getUsuario());

                    asignadas.add(asig);

                    adapterAsignadas=new AsignadasTareasAdapter(asignadas);
                    recyclerAsignadas.setAdapter(adapterAsignadas);

                    tareas.remove(tareaPulsada);
                    inicializarListaTareas(tareas);

                    tareaPulsada=null;
                    recyclerAsignadas.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recyclerAsignadas, new RecyclerTouchListener.ClickListener() {
                        @Override
                        public void onClick(View view, int position)
                        {
                            tareas.add(asignadas.get(position).getTarea());
                            asignadas.remove(position);

                            adapterAsignadas=new AsignadasTareasAdapter(asignadas);
                            recyclerAsignadas.setAdapter(adapterAsignadas);
                            inicializarListaCarros(carros);
                            inicializarListaTareas(tareas);

                        }

                        @Override
                        public void onLongClick(View view, int position) {

                        }
                    }));

                }
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

    }


}
