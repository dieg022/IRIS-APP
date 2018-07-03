package com.nexwrfc.iris.iris.Activity;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.os.SystemClock;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.nexwrfc.iris.iris.AdapterSpinner.AdapterCategory;
import com.nexwrfc.iris.iris.ClasesGenericas.FiltroBusqueda;
import com.nexwrfc.iris.iris.DTO.CarroDTO;
import com.nexwrfc.iris.iris.DTO.DeshacerDTO;
import com.nexwrfc.iris.iris.DTO.EstadosTareasDTO;
import com.nexwrfc.iris.iris.DTO.HotelDTO;
import com.nexwrfc.iris.iris.DTO.NotaDTO;
import com.nexwrfc.iris.iris.DTO.TareaCarroDTO;
import com.nexwrfc.iris.iris.DTO.TareaDTO;
import com.nexwrfc.iris.iris.DTO.Usuario;
import com.nexwrfc.iris.iris.ListView.CarroListadoAdapter;
import com.nexwrfc.iris.iris.ListView.NotasVozAdapter;
import com.nexwrfc.iris.iris.ResponseAPI.ResponseLogUsuario;
import com.nexwrfc.iris.iris.ResponseAPI.ResponsePendientesDTO;
import com.nexwrfc.iris.iris.DTO.TareaEstadoDTO;
import com.nexwrfc.iris.iris.ListView.ListadoEstadosTareaAdapter;
import com.nexwrfc.iris.iris.ListView.RecyclerTouchListener;
import com.nexwrfc.iris.iris.ListView.SwipeController;
import com.nexwrfc.iris.iris.ListView.SwipeControllerActions;
import com.nexwrfc.iris.iris.R;
import com.nexwrfc.iris.iris.Services.CarroRest;
import com.nexwrfc.iris.iris.Services.RestUsuario;
import com.nexwrfc.iris.iris.Services.RetroFitAdapter;
import com.nexwrfc.iris.iris.Services.TareasRest;

import org.w3c.dom.Text;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


public class EstadosTareasActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener
{

    private List<ResponsePendientesDTO> estadosTareas;
    List<NotaDTO> notasVoz;
    private ResponseLogUsuario usuario;
    private Integer nivel,perfil;
    Integer tipoActivity;//Para saber si estamos buscando por usuarios o por carros

    private RecyclerView recyclerTareas;
    private RecyclerView.Adapter adapterTareas;
    private RecyclerView.LayoutManager lManagerTareas;

    private RecyclerView recyclerNota;
    private RecyclerView.Adapter adapterNota;
    private RecyclerView.LayoutManager lManagerNota,lManagerCarro;

    private  RecyclerView recyclerCarros;
    private  RecyclerView recyclerListCarros;
    private RecyclerView.Adapter adapterCarros;
    private RecyclerView.LayoutManager lManagerCarros;
    List<String> ficherosAudio;
    SwipeController swipeController = null;
    private  Dialog dialogAudios;
    private MediaRecorder miGrabacion;
    private String outputFile = null;
    private String nombreFichero;
    private TareaDTO tareaClick;
    private Button btRelimpiar,btMantenimiento,btVerificar,btConfirmarPin,btGrabar,btStop,btReproducir,btPrioridad,btCambiarCarro,btMantenimientoDialog,btDeshacer;
    private RelativeLayout rlListaNotas;
    private EditText et_pin;
    private ProgressBar pb;
    private TextView pbText;
    private Canvas canvas;
    List<HotelDTO> hoteles;
    ResponsePendientesDTO estadoTareaClick;
    private TextView tvNombreUsuario,tvFiltroString;
    private Integer positionClick;
    private FiltroBusqueda filtros;

    private ProgressBar pbInicial;

    List<String> listaFiltros;

    Spinner spinnerFiltros;

    ArrayAdapter<String> comboAdapter;

    int carga=0;

    Dialog dialogCambioCarro;

    private long mLastClickTime = 0;

    private Integer idHotel,idCarro;

    private

    Retrofit retrofit;
    TareasRest tareasRest;
    BroadcastReceiver updateUIReciver;

    TextView tvInstrucciones;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);




        pbInicial=findViewById(R.id.pb_estados);

        //Actualizar en segundo plano
        IntentFilter filter = new IntentFilter();

        filtros=new FiltroBusqueda(0,0);

        filter.addAction("actualizarEstados");

        updateUIReciver = new BroadcastReceiver()
        {

            @Override
            public void onReceive(Context context, Intent intent)
            {

                if(usuario!=null)
                {

                    cargarTareasSinCompletar(usuario);
                }
                else //SI ES UN CARRO
                {

                    SharedPreferences prefs = getSharedPreferences(getString(R.string.nombreFicheroConfiguracion), Context.MODE_PRIVATE);
                    Integer idHotel = prefs.getInt("hotel", -1);
                    Integer idCarro = prefs.getInt("carro", -1);

                    cargarTareasSinCompletar(idHotel, idCarro);

                }

            }
        };

            registerReceiver(updateUIReciver,filter);
        setContentView(R.layout.activity_estados_tareas);

        String activity = getIntent().getStringExtra("Activity");

        retrofit = new RetroFitAdapter().getAdaptador();
        tareasRest = retrofit.create(TareasRest.class);
        btPrioridad=(Button) findViewById(R.id.bt_dar_prioridad);
        btCambiarCarro=(Button) findViewById(R.id.bt_cambiar_carro);
        tvNombreUsuario=(TextView) findViewById(R.id.tv_usuario);
        btMantenimientoDialog=(Button) findViewById(R.id.btMantenimientoDialog);

        setClickDialogoMantenimiento();
        setClickDeshacer();

        tvFiltroString=(TextView) findViewById(R.id.tv_filtro_st);
        if(activity.equals("Carro"))
        {
//            tvFiltroString.setVisibility(View.INVISIBLE);
            Log.e("DEBUG","ES UN CARRO");
            SharedPreferences prefs = getSharedPreferences(getString(R.string.nombreFicheroConfiguracion), Context.MODE_PRIVATE);
            Integer idHotel = prefs.getInt("hotel", -1);
            Integer idCarro = prefs.getInt("carro", -1);
            String nombreHotel = prefs.getString("nombrehotel", "");
            tvInstrucciones=(TextView) findViewById(R.id.tv_instrucciones_estado);
            tvInstrucciones.setText("PARA PASAR UNA HABITACIÓN A REVISIÓN, ARRASTRE CON EL DEDO LA HABITACIÓN DESDE LA DERECHA HACIA IZQUIERDA <- Y PULSE OK. A CONTINUACIÓN SE LE SOLICITARÁ SU PIN PERSONAL.\n" +
                    "PARA PASAR UNA HABITACION A MANTENIMIENTO PULSE LA TAREA Y A CONTINUACIÓN PULSE EN EL BOTÓN CENTRAL INFERIOR 'MANTENIMIENTO'.");
            tvNombreUsuario.setText("CARRO "+idCarro);

            spinnerFiltros=findViewById(R.id.sp_filtros);
            spinnerFiltros.setVisibility(View.INVISIBLE);

            btPrioridad.setVisibility(View.INVISIBLE);
            btCambiarCarro.setVisibility(View.INVISIBLE);
            tipoActivity=1;

            Log.e("LOGIN","CARRO:"+idCarro+"HOTEL:"+idHotel);
            Log.e("DEBUG","ES UN CARRO");

            cargarTareasSinCompletar(idHotel,idCarro);

        }
        else if(activity.equals("Usuario"))
        {
            tipoActivity=0;

            setClickPrioridad();
            setClickCambiarCarro();

            usuario = (ResponseLogUsuario) getIntent().getExtras().get("usuario");


            tvNombreUsuario.setText("BIENVENIDO, "+usuario.getUsuario().getNombre_usuario());

            //OCULTAR Y MOSTRAR BOTONES DEPENDIENDO DEL PERFIL
            if(usuario.getUsuario().getPerfil().getNivel().getId()<=2)
            {
                tvInstrucciones=(TextView) findViewById(R.id.tv_instrucciones_estado);
                btPrioridad.setVisibility(View.VISIBLE);
                btCambiarCarro.setVisibility(View.VISIBLE);
                tvInstrucciones.setText("PARA PASAR UNA HABITACIÓN A REELIMPIEZA, DESPLACE LA HABITACIÓN A SU DERECHA -> Y PULSE REELIMPIEZA.\n" +
                        "SI DESEA GENERAR UNA INCIDENCIA EN LA HABITACIÓN PULSE SOBRE LA HABITACIÓN Y A CONTINUACIÓN EL BOTÓN INFERIOR CENTRAL 'MANTENIMIENTO'.\n" +
                        "PARA VALIDAR LA HABITACIÓN Y PASAR A DISPONIBLE, DESPLACE LA HABITACIÓN DESDE LA DERECHA HACIA IZQUIERDA Y PULSE OK.\n" +
                        "PARA DAR MAS PRIORIDAD A UNA TAREA PULSE SOBRE LA HABITACIÓN Y A CONTINUACIÓN PULSE EN DAR PRIORIDAD\n" +
                        "PARA CAMBIAR DE CARRO PULSE SOBRE LA HABITACIÓN Y A CONTINUACIÓN PULSE CAMBIO DE CARRO Y SELECCIONE UN CARRO DE LA LISTA EMERGENTE.");
                cargarFiltros();

            }
            else
            {
//                tvFiltroString.setVisibility(View.INVISIBLE);
                tvInstrucciones=(TextView) findViewById(R.id.tv_instrucciones_estado);
                btPrioridad.setVisibility(View.INVISIBLE);
                btCambiarCarro.setVisibility(View.INVISIBLE);
                tvInstrucciones.setText("PARA PASAR UNA HABITACIÓN A REVISIÓN DE GOBERNANTA,DESPLACE LA HABITACIÓN HACIA SU IZQUIERDA <- Y PULSE EL BOTÓN OK.\n" +
                        "SI DESEA GENERAR UNA NUEVA INCIDENCIA PULSE SOBRE LA HABITACIÓN Y A CONTINUACIÓN SOBRE EL BOTÓN CENTRAL INFERIOR 'INCIDENCIA'.");
                spinnerFiltros=findViewById(R.id.sp_filtros);
                spinnerFiltros.setVisibility(View.INVISIBLE);
            }
            cargarTareasSinCompletar(usuario);
        }
    }

    //CARGA LAS TAREAS SIN COMPLETAR DEPENDIENDO DEL NIVEL DE USUARIO QUE ESTÉ LOGUEADO*/
    public void cargarTareasSinCompletar(final ResponseLogUsuario usuario) {

        Call <List<ResponsePendientesDTO>> call;

        SharedPreferences prefs = getSharedPreferences(getString(R.string.nombreFicheroConfiguracion), Context.MODE_PRIVATE);
        String token = prefs.getString("token", "");

        if(filtros.getTipoTarea()==0 && filtros.getTipoEstado()==0)
            call =tareasRest.getEstadoTarea(usuario.getHotel().getId(),usuario.getUsuario(),"Bearer "+token);
        else
            call = tareasRest.getEstadoTarea(usuario.getHotel().getId(),filtros.getTipoTarea(), filtros.getTipoEstado(),usuario.getUsuario(),"Bearer "+token);



        call.enqueue(new Callback<List<ResponsePendientesDTO>>()
        {
            @Override
            public void onResponse(Call<List<ResponsePendientesDTO>> call, Response<List<ResponsePendientesDTO>> response) {
                switch (response.code())
                {
                    case 200:

                        estadosTareas = response.body();
                        Log.e("LISTA DEVUELTA",estadosTareas.size()+"");
                        nivel=usuario.getUsuario().getPerfil().getNivel().getId();
                        perfil=usuario.getUsuario().getPerfil().getId();
                        inicializarEstadoTarea(estadosTareas);

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
            public void onFailure(Call<List<ResponsePendientesDTO>> call, Throwable t) {
                Log.e("Explota", t.toString());
                Log.e("Explota", call.request().toString());
                Log.e("Explota", call.request().body().toString());
            }
        });
    }

    //INICIALIZA EL RECYCLERVIEW DE LAS TAREAS CARGADAS Y GENERA EL SWIPE CONTROLLER
    private void inicializarEstadoTarea(final List<ResponsePendientesDTO> estados)
    {

        final RecyclerView recyclerT = (RecyclerView) findViewById(R.id.rv_listaTareas);

        lManagerTareas = new LinearLayoutManager(this);
        recyclerT.setLayoutManager(lManagerTareas);
        adapterTareas = new ListadoEstadosTareaAdapter(estados,perfil,recyclerT);
        recyclerT.setAdapter(adapterTareas);

        recyclerT.setItemAnimator(new DefaultItemAnimator());
        pbInicial=findViewById(R.id.pb_estados);pbInicial.setVisibility(View.INVISIBLE);

        if(carga==0)
        {
            carga = 1;
            swipeController = new SwipeController(perfil, nivel, new SwipeControllerActions() {
                @Override
                public void onRightClicked(int position) {

                    if (nivel == 2) //SI ES DE NIVEL 2
                    {
                        Log.e("VALIDAR",""+estados.get(position).getEstado().getTipoEstado().getId());


                            dialogoConfirmar(position, "VALIDAR", "¿Desea pasar la habitación " + estadosTareas.get(position).getTarea().getHabitacion().getNumero() + " a disponible?", 5,1);

                    }
                    else if (perfil == 4)
                        dialogoConfirmarPin(position,0);
                    else if (perfil == 5)
                        dialogoConfirmar(position, "VERIFICACIÓN", "¿Desea pasar la habitación " + estadosTareas.get(position).getTarea().getHabitacion().getNumero() + " a revisión de la gobernanta?", 2,1);

                }

                @Override
                public void onLeftClicked(final int position)
                {
                    if (nivel == 2)
                    {
                        estadoTareaClick = estadosTareas.get(position);
                        dialogoNotaVoz("RE-LIMPIEZA HABITACIÓN:",position,4);
                        // dialogoConfirmar(position, "RELIMPIEZA", "¿Desea volver a limpiar la habitación " + estados.get(position).getTarea().getHabitacion().getNumero() + "?", 4);
                    }
                    if (perfil == 5)
                        dialogoConfirmar(position, "SINIESTRA", "¿Desea inhabiltar la habitación " + estados.get(position).getTarea().getHabitacion().getNumero() + " ?", 6,1);
                }
            });
            recyclerT.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recyclerT, new RecyclerTouchListener.ClickListener() {
                @Override
                public void onClick(View view, final int position)
                {

                    tareaClick = estadosTareas.get(position).getTarea();

                    estadoTareaClick = estadosTareas.get(position);
                    positionClick=position;
                    btMantenimiento=(Button) findViewById(R.id.btMantenimientoDialog);
                    btPrioridad=(Button) findViewById(R.id.bt_dar_prioridad);
                    btCambiarCarro=(Button) findViewById(R.id.bt_cambiar_carro);

                    btMantenimiento.setText("MANT.HAB:"+estadoTareaClick.getTarea().getHabitacion().getNumero());

                    btPrioridad.setText("DAR PRIORIDAD HAB:"+estadoTareaClick.getTarea().getHabitacion().getNumero());
                    btCambiarCarro.setText("CAMBIAR CARRO  HAB:"+estadoTareaClick.getTarea().getHabitacion().getNumero());




                }

                @Override
                public void onLongClick(View view, int position) {


                }
            }));
        }

        ItemTouchHelper itemTouchhelper = new ItemTouchHelper(swipeController);
        itemTouchhelper.attachToRecyclerView(recyclerT);

        recyclerT.addItemDecoration(new RecyclerView.ItemDecoration()
        {
            @Override
            public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
                swipeController.onDraw(c);

            }

        });
    }

    private void dialogoNotaVoz(final String titulo, final Integer position, final Integer idEstado)
    {
        dialogAudios = new Dialog(EstadosTareasActivity.this);

        dialogAudios.setContentView(R.layout.dialog_mantenimiento);
        TextView tituloDialog= (TextView) dialogAudios.findViewById(R.id.tv_titulo_nota_voz);
        btGrabar = (Button) dialogAudios.findViewById(R.id.bt_grabar_voz);
        btStop = (Button) dialogAudios.findViewById(R.id.bt_stop_grabacion);
        btReproducir = (Button) dialogAudios.findViewById(R.id.bt_reproducir_voz);
        btMantenimiento = (Button) dialogAudios.findViewById(R.id.bt_generar_mantenimiento);
        pb = (ProgressBar) dialogAudios.findViewById(R.id.pb_grabacion);
        pbText=(TextView) dialogAudios.findViewById(R.id.tv_progres);

        rlListaNotas = (RelativeLayout) dialogAudios.findViewById(R.id.rl_lista_notas);
        rlListaNotas.setVisibility(View.VISIBLE);
        tituloDialog.setText(titulo+" ");
        cargarNotasVozTarea(estadoTareaClick, dialogAudios);

        pb.setVisibility(View.INVISIBLE);
        pbText.setVisibility(View.INVISIBLE);

        dialogAudios.setTitle(titulo+""+ estadoTareaClick.getEstado().getTarea().getHabitacion().getNumero().toString());

        nombreFichero = inicializarGrabacion();

        dialogAudios.setCancelable(true);
        dialogAudios.show();

        btMantenimiento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //SI EL USUARIO ES NULL TENEMOS QUE MOSTRAR EL DIALOGO DEL PIN
                if (usuario == null)
                {
                    dialogoConfirmarPin(position,1);
                    Log.e("PINUSER","SALIMOS");
                }
                else
                {
                    byte[] encodeValue = Base64.encode(nombreFichero.getBytes(), Base64.DEFAULT);

                    try {

                        String enco = codificarBase64(nombreFichero);
                        NotaDTO nota = new NotaDTO();
                        nota.setMensaje(enco);

                        estadoTareaClick.setNota(nota);

                        actualizarTarea(estadoTareaClick, idEstado);


                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    dialogAudios.dismiss();
                }
            }

        });
    }
    //Inicializa los Estados de un carro
    private void inicializarEstadoTareaCarro(final List<ResponsePendientesDTO> estados) {

        RecyclerView recyclerT = (RecyclerView) findViewById(R.id.rv_listaTareas);


        lManagerTareas = new LinearLayoutManager(this);
        recyclerT.setLayoutManager(lManagerTareas);

        adapterTareas = new ListadoEstadosTareaAdapter(estados,perfil,recyclerT);


        recyclerT.setAdapter(adapterTareas);

        if(carga==0)
        {
            carga = 1;
            swipeController = new SwipeController(perfil, nivel, new SwipeControllerActions() {
                @Override
                public void onRightClicked(int position) {

                    if (nivel == 2)//SI ES DE NIVEL 2
                        dialogoConfirmar(position, "VALIDAR", "¿Desea pasar la habitación " + estadosTareas.get(position).getTarea().getHabitacion().getNumero() + " a disponible?", 5,1);
                    else if (perfil == 4)
                        dialogoConfirmarPin(position,0);
                    else if (perfil == 5)
                        dialogoConfirmar(position, "VERIFICACIÓN", "¿Desea pasar la habitación " + estadosTareas.get(position).getTarea().getHabitacion().getNumero() + " a revisión de la gobernanta?", 2,1);

                }

                @Override
                public void onLeftClicked(final int position) {
                    if (nivel == 2)
                        dialogoConfirmar(position, "RELIMPIEZA", "¿Desea volver a limpiar la habitación " + estados.get(position).getTarea().getHabitacion().getNumero() + "?", 4,1);
                    if (perfil == 5)
                        dialogoConfirmar(position, "SINIESTRA", "¿Desea inhabiltar la habitación " + estados.get(position).getTarea().getHabitacion().getNumero() + " ?", 6,1);
                }
            });
            recyclerT.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recyclerT, new RecyclerTouchListener.ClickListener() {
                @Override
                public void onClick(View view, final int position) {

                    tareaClick = estadosTareas.get(position).getTarea();
                    estadoTareaClick = estadosTareas.get(position);
                    positionClick=position;



                }

                @Override
                public void onLongClick(View view, int position) {


                }
            }));
        }


        ItemTouchHelper itemTouchhelper = new ItemTouchHelper(swipeController);
        itemTouchhelper.attachToRecyclerView(recyclerT);

        recyclerT.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
                swipeController.onDraw(c);

            }

        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();

        inflater.inflate(R.menu.loginmenu, menu);


        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.bt_config:
                Intent intent=new Intent(EstadosTareasActivity.this,ConfigActivity.class);
                intent.putExtra("Activity", "Estados");
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    //Establecer funcionalidad al boton prioridad
    public void setClickPrioridad()
    {

        btPrioridad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tareaClick != null)
                {

                    SharedPreferences prefs = getSharedPreferences(getString(R.string.nombreFicheroConfiguracion), Context.MODE_PRIVATE);
                    String token = prefs.getString("token", "");
                    Call<Integer> call = tareasRest.setPrioridadTarea(tareaClick.getId(),"Bearer "+token);
                    call.enqueue(new Callback<Integer>() {
                        @Override
                        public void onResponse(Call<Integer> call, Response<Integer> response) {
                            switch (response.code())
                            {
                                case 200:

                                    cargarTareasSinCompletar(usuario);


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
                        public void onFailure(Call<Integer> call, Throwable t) {
                            Log.e("Explota", t.toString());
                            Log.e("Explota", call.request().toString());
                            Log.e("Explota", call.request().body().toString());
                        }
                    });
                }
                else {
                    Toast.makeText(getApplicationContext(), "Debe seleccionar la tarea para dar prioridad primero", Toast.LENGTH_LONG).show();
                }
            }

        });
    }

    public void setClickCambiarCarro()
    {
        btCambiarCarro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tareaClick != null)
                {
                    dialogCambioCarro = new Dialog(EstadosTareasActivity.this);
                    dialogCambioCarro.setContentView(R.layout.dialog_seleccionar_carro);
                    dialogCambioCarro.show();
                    Button btAceptar= (Button)  dialogCambioCarro.findViewById(R.id.bt_cambiar_carro);

                    cargarCarrosHotel();
                    recyclerCarros = (RecyclerView)  dialogCambioCarro.findViewById(R.id.rv_carros_reasignar);

                    dialogCambioCarro.setCancelable(true);
                    dialogCambioCarro.setTitle("SELECCIONE EL CARRO PARA REASIGNAR LA HABITACIÓN"+tareaClick.getHabitacion().getNumero());
                    dialogCambioCarro.show();

                }
                else {
                    Toast.makeText(getApplicationContext(), "Debe seleccionar la tarea para reasignar el carro", Toast.LENGTH_LONG).show();
                }
            }

        });
    }
    public void setClickDeshacer()
    {
        btDeshacer=(Button) findViewById(R.id.btDeshacer);


        btDeshacer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                if (usuario != null)
                {
                    deshacerTareaUsuario(usuario.getUsuario().getId());
                    Toast.makeText(getApplicationContext(), "Deshacer ultima accion de usuario", Toast.LENGTH_LONG).show();

                }
                else
                {

                   dialogoConfirmarPin(1,-1);
                }
            }

        });
    }
    public void setClickDialogoMantenimiento()
    {
        btMantenimientoDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tareaClick != null)
                {
                    mostrarMantenimiento();

                }

            }

        });
    }

    public void deshacerTareaUsuario(Integer idUsuario)
    {

        SharedPreferences prefs = getSharedPreferences(getString(R.string.nombreFicheroConfiguracion), Context.MODE_PRIVATE);
        String token = prefs.getString("token", "");
        Call <DeshacerDTO> call;
            call =tareasRest.borrarTareaUsuario(idUsuario,"Bearer "+token);

        call.enqueue(new Callback<DeshacerDTO>()
        {
            @Override
            public void onResponse(Call<DeshacerDTO> call, Response<DeshacerDTO> response) {
                switch (response.code())
                {

                    case 200:
                        //Si el usuario pasado no es una limpiadora (4) cargamos las tareas con el usuario sino por el hotel y carro
                        if(usuario.getUsuario().getPerfil().getId()!=4)
                            cargarTareasSinCompletar(usuario);
                        else
                        {

                            SharedPreferences prefs = getSharedPreferences(getString(R.string.nombreFicheroConfiguracion), Context.MODE_PRIVATE);
                            Integer idH = prefs.getInt("hotel", -1);
                            Integer idC = prefs.getInt("carro", -1);
                            usuario=null;
                            cargarTareasSinCompletar(idH, idC);


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
            public void onFailure(Call<DeshacerDTO> call, Throwable t) {
                Log.e("Explota", t.toString());
                Log.e("Explota", call.request().toString());
                Log.e("Explota", call.request().body().toString());
            }
        });
    }
    public void mostrarMantenimiento()
    {
        dialogAudios = new Dialog(EstadosTareasActivity.this);


        dialogAudios.setContentView(R.layout.dialog_mantenimiento);

        TextView        tv_titulo=(TextView) dialogAudios.findViewById(R.id.tv_titulo_nota_voz);
        btGrabar = (Button) dialogAudios.findViewById(R.id.bt_grabar_voz);
        btStop = (Button) dialogAudios.findViewById(R.id.bt_stop_grabacion);
        btReproducir = (Button) dialogAudios.findViewById(R.id.bt_reproducir_voz);
        btMantenimiento = (Button) dialogAudios.findViewById(R.id.bt_generar_mantenimiento);
        pb = (ProgressBar) dialogAudios.findViewById(R.id.pb_grabacion);
        pbText=(TextView) dialogAudios.findViewById(R.id.tv_progres);

        tv_titulo.setText("INCIDENCIA HABITACIÓN: "+estadoTareaClick.getTarea().getHabitacion().getNumero());

        if (perfil == 5 || nivel <= 2) {
            rlListaNotas = (RelativeLayout) dialogAudios.findViewById(R.id.rl_lista_notas);
            rlListaNotas.setVisibility(View.VISIBLE);
            cargarNotasVozTarea(estadoTareaClick, dialogAudios);
            Log.e("NOTA", "CARGARMOS");
        }
        pb.setVisibility(View.INVISIBLE);
        pbText.setVisibility(View.INVISIBLE);

        dialogAudios.setTitle("INCIDENCIA HABITACION:" + estadoTareaClick.getEstado().getTarea().getHabitacion().getNumero().toString());

        nombreFichero = inicializarGrabacion();

        dialogAudios.setCancelable(true);
        dialogAudios.show();

        btMantenimiento.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {

                //SI EL USUARIO ES NULL TENEMOS QUE MOSTRAR EL DIALOGO DEL PIN
                if (usuario == null) {
                    dialogoConfirmarPin(positionClick,1);
                    Log.e("PINUSER","SALIMOS");
                } else
                {
                    byte[] encodeValue = Base64.encode(nombreFichero.getBytes(), Base64.DEFAULT);
                    try {

                        String enco = codificarBase64(nombreFichero);
                        NotaDTO nota = new NotaDTO();
                        nota.setMensaje(enco);
                        Log.e("AUDI", nota + "");
                        estadoTareaClick.setNota(nota);

                        actualizarTarea(estadoTareaClick, 3);


                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    dialogAudios.dismiss();
                }
            }

        });
    }

    //Carga la lista de carros para hacer el cambio de carro.
    private void cargarCarrosHotel()
    {

        SharedPreferences prefs = getSharedPreferences(getString(R.string.nombreFicheroConfiguracion), Context.MODE_PRIVATE);
        String token = prefs.getString("token", "");

        CarroRest carroRest2=retrofit.create(CarroRest.class);
        Call<List<CarroDTO>> call2=carroRest2.getCarrosHotel(usuario.getHotel().getId(),"Bearer "+token);

        call2.enqueue(new Callback<List<CarroDTO>>()
        {
            @Override
            public void onResponse(Call<List<CarroDTO>> call2, Response<List<CarroDTO>> response2)
            {

                switch (response2.code())
                {

                    case 200:
                        List<CarroDTO>   carros = response2.body();
                        setDialogoCarros(carros);

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

    public void setDialogoCarros(final List<CarroDTO> carros)
    {

        try
        {
            lManagerCarro = new LinearLayoutManager(this);

            recyclerCarros.setLayoutManager(lManagerCarro);
            adapterCarros = new CarroListadoAdapter(carros);
            recyclerCarros.setAdapter(adapterCarros);

            recyclerCarros.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recyclerCarros, new RecyclerTouchListener.ClickListener() {
                @Override
                public void onClick(View view, int position)
                {
                    CarroDTO carro=carros.get(position);

                    TareaCarroDTO tc=new TareaCarroDTO();

                    tc.setCarro(carro);
                    tc.setTarea(tareaClick);
                    tc.setUsuario(usuario.getUsuario());

                    SharedPreferences prefs = getSharedPreferences(getString(R.string.nombreFicheroConfiguracion), Context.MODE_PRIVATE);
                    String token = prefs.getString("token", "");
                    Call<TareaCarroDTO> call=tareasRest.setCarroTarea(tc,"Bearer "+token);


                    call.enqueue(new Callback<TareaCarroDTO>() {

                        @Override
                        public void onResponse(Call<TareaCarroDTO> call, Response<TareaCarroDTO> response) {
                            switch (response.code())
                            {
                                case 200:

                                    cargarTareasSinCompletar(usuario);
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
                            dialogCambioCarro.dismiss();
                        }

                        @Override
                        public void onFailure(Call<TareaCarroDTO> call, Throwable t) {
                            Log.e("Explota", t.toString());
                            Log.e("Explota", call.request().toString());
                            Log.e("Explota", call.request().body().toString());
                        }
                    });




                }

                @Override
                public void onLongClick(View view, int position)
                {


                }
            }));


        }catch(RuntimeException ex)
        {
            Log.e("DIALOG",ex.toString()+"");
            Log.e("DIALOG","RW"+recyclerCarros+"");
        }

    }

    //CARGA LAS TAREAS SIN COMPLETAR PARA EL CARRO QUE ESTE ASIGNADA EL DISPOSITIVO
    public void cargarTareasSinCompletar(Integer idH,Integer idC)
    {

        SharedPreferences prefs = getSharedPreferences(getString(R.string.nombreFicheroConfiguracion), Context.MODE_PRIVATE);
        String token = prefs.getString("token", "");
        Call <List<ResponsePendientesDTO>> call = tareasRest.getEstadoTarea(idH,idC,"Bearer "+token);

        call.enqueue(new Callback<List<ResponsePendientesDTO>>()
        {
            @Override
            public void onResponse(Call<List<ResponsePendientesDTO>> call, Response<List<ResponsePendientesDTO>> response) {
                switch (response.code()) {
                    case 200:

                        estadosTareas = response.body();

                        nivel = 3;
                        perfil = 4;
                        inicializarEstadoTarea(estadosTareas);

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
            public void onFailure(Call<List<ResponsePendientesDTO>> call, Throwable t) {
                Log.e("Explota", t.toString());
                Log.e("Explota", call.request().toString());
                Log.e("Explota", call.request().body().toString());
            }
        });
    }


    private void cargarNotasVozTarea(final ResponsePendientesDTO tarea, final Dialog dialog)
    {

        SharedPreferences prefs = getSharedPreferences(getString(R.string.nombreFicheroConfiguracion), Context.MODE_PRIVATE);
        String token = prefs.getString("token", "");
        Call <List<NotaDTO>> call = tareasRest.getNotasVozTarea(tarea.getTarea().getId(),"Bearer "+token);
        Log.e("NOTA","BUSCANDO NOTA"+tarea.getTarea().getId());


        call.enqueue(new Callback<List<NotaDTO>>() {
            @Override
            public void onResponse(Call<List<NotaDTO>> call, Response<List<NotaDTO>> response) {
                switch (response.code()) {
                    case 200:

                        notasVoz=response.body();

                        if(notasVoz!=null) {
                            notasVoz = response.body();

                            inicializarNotasVoz(dialog);
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
            public void onFailure(Call<List<NotaDTO>> call, Throwable t) {
                Log.e("Explota", t.toString());
                Log.e("Explota", call.request().toString());
                Log.e("Explota", call.request().body().toString());
            }
        });
    }

    private String codificarBase64(String nombreFichero) throws IOException {
        byte[] audioBytes;
        String audioBase64="";
        try {

            // Just to check file size.. Its is correct i-e; Not Zero
            File audioFile = new File(nombreFichero);
            long fileSize = audioFile.length();

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            FileInputStream fis = new FileInputStream(new File(nombreFichero));
            byte[] buf = new byte[1024];
            int n;
            while (-1 != (n = fis.read(buf)))
                baos.write(buf, 0, n);
            audioBytes = baos.toByteArray();

            // Here goes the Base64 string
            audioBase64 = Base64.encodeToString(audioBytes, Base64.DEFAULT);

        } catch (Exception e) {

        }
        return audioBase64;
    }

    private static byte[] leerFichero(File file) throws IOException {
        InputStream is = new FileInputStream(file);

        long length = file.length();
        if (length > Integer.MAX_VALUE) {
            // File is too large
        }
        byte[] bytes = new byte[(int)length];

        int offset = 0;
        int numRead = 0;
        while (offset < bytes.length
                && (numRead=is.read(bytes, offset, bytes.length-offset)) >= 0) {
            offset += numRead;
        }

        if (offset < bytes.length) {
            throw new IOException("Could not completely read file "+file.getName());
        }

        is.close();
        return bytes;
    }

    private void inicializarNotasVoz(Dialog d)
    {
        recyclerNota = (RecyclerView) d.findViewById(R.id.rv_notas_voz);
        lManagerNota = new LinearLayoutManager(this);
        recyclerNota.setLayoutManager(lManagerNota);
        SharedPreferences prefs = getSharedPreferences(getString(R.string.nombreFicheroConfiguracion), Context.MODE_PRIVATE);
        String token = prefs.getString("token", "");
        adapterNota = new NotasVozAdapter(notasVoz,this,usuario.getUsuario(),"Bearer "+token);
        recyclerNota.setAdapter(adapterNota);


        //Se pone a null para no tener los audios anteriores
        ficherosAudio=null;

    }

    private String inicializarGrabacion()
    {
        btStop.setEnabled(false);
        btReproducir.setEnabled(false);
        outputFile = Environment.getExternalStorageDirectory().getAbsolutePath() + "/pruebas.3gp";


        miGrabacion = new MediaRecorder();
        miGrabacion.setAudioSource(MediaRecorder.AudioSource.MIC);
        miGrabacion.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
        miGrabacion.setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB);
        miGrabacion.setOutputFile(outputFile);

        btGrabar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    pb.setVisibility(View.VISIBLE);
                    pbText.setVisibility(View.VISIBLE);
                    miGrabacion.prepare();
                    miGrabacion.start();
                }

                catch (IllegalStateException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

                catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

                btGrabar.setEnabled(false);
                btStop.setEnabled(true);

                Toast.makeText(getApplicationContext(), "La grabación comenzó", Toast.LENGTH_LONG).show();
            }
        });

        btStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                miGrabacion.stop();
                pb.setVisibility(View.INVISIBLE);
                pbText.setVisibility(View.INVISIBLE);
                miGrabacion.release();
                miGrabacion = null;

                btStop.setEnabled(false);
                btReproducir.setEnabled(true);

                Toast.makeText(getApplicationContext(), "El audio grabado con éxito",Toast.LENGTH_LONG).show();
            }
        });

        btReproducir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) throws IllegalArgumentException,SecurityException,IllegalStateException {
                MediaPlayer m = new MediaPlayer();

                try {
                    m.setDataSource(outputFile);
                }

                catch (IOException e) {
                    e.printStackTrace();
                }

                try {
                    m.prepare();
                }

                catch (IOException e) {
                    e.printStackTrace();
                }

                m.start();

            }
        });
        return outputFile;

    }

    //ACTUALIZA LOS ESTADOS DE LAS TAREAS
    private void actualizarTarea(ResponsePendientesDTO es,Integer idEstado)
    {
        TareaEstadoDTO tarea=new TareaEstadoDTO();

        tarea.setTarea(es.getEstado().getTarea());

        tarea.setTipoEstado(es.getEstado().getTipoEstado());

        tarea.setUsuarioCreacion(usuario.getUsuario());

        tarea.setNota(es.getNota());

        SharedPreferences prefs = getSharedPreferences(getString(R.string.nombreFicheroConfiguracion), Context.MODE_PRIVATE);
        String token = prefs.getString("token", "");

        Call<TareaEstadoDTO> call = tareasRest.setEstadoTarea(tarea,idEstado,"Bearer "+token);

        call.enqueue(new Callback<TareaEstadoDTO>() {
            @Override
            public void onResponse(Call<TareaEstadoDTO> call, Response<TareaEstadoDTO> response) {

                SharedPreferences prefs = getSharedPreferences(getString(R.string.nombreFicheroConfiguracion), Context.MODE_PRIVATE);
                Integer idH = prefs.getInt("hotel", -1);
                Integer idC = prefs.getInt("carro", -1);
                switch (response.code()) {


                    case 200:

                        if(tipoActivity==0)//Si es actividad de usuario
                            cargarTareasSinCompletar(usuario);
                        else    //Si es app de carro
                        {
                            usuario=null;
                            cargarTareasSinCompletar(idH, idC);
                        }

                        break;
                    case 400:
                        Log.e("DATOS", call.request().toString());
                        Log.e("DATA", "400 primo" + response.body());
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onFailure(Call<TareaEstadoDTO> call, Throwable t) {
                Log.e("Explota", t.toString());
                Log.e("Explota", call.request().toString());
                Log.e("Explota", call.request().body().toString());
            }
        });
    }
    //DIALOGOS DE CONFIRMACIONES
    private void dialogoConfirmar(final Integer position, String titulo, String descripcion, final Integer idEstado,final Integer permitir)
    {
        new AlertDialog.Builder(EstadosTareasActivity.this)
                .setTitle(titulo)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id)
                    {
                        if(permitir==1)
                            actualizarTarea(estadosTareas.get(position),idEstado);
                        else
                            dialog.cancel();

                    }})
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id)
                    {
                        dialog.cancel();
                    }})
                .setMessage(descripcion)
                .show();
    }

    //DIALOGO DE CONFIRMACIÓN DEL PIN DE USUARIO
    //se le pasa un integer del tipo de tarea:
    //0-->ES EL TIPO DE REELIMPIEZA
    //1-->ES EL TIPO DE MANTENIMIENTO
    //3-->Tipo
    private void dialogoConfirmarPin(Integer position, final Integer tipoTarea)
    {
        final Dialog dialogPin = new Dialog(EstadosTareasActivity.this);
        final Integer p = position;

        dialogPin.setContentView(R.layout.dialog_teclado_pin);
        et_pin = (EditText) dialogPin.findViewById(R.id.et_pin);
        et_pin.requestFocus();

        //getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        final InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        dialogPin.setCancelable(true);
        dialogPin.setTitle("INTRODUCIR PIN PARA CONFIRMAR LIMPIEZA");
        dialogPin.show();

        btConfirmarPin = (Button) dialogPin.findViewById(R.id.bt_confirmar_pin);

        btConfirmarPin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!et_pin.getText().toString().equals(" "))
                {
                    if(tipoTarea!=-1)
                    {
                        buscarPorPin(estadosTareas.get(p), Integer.parseInt(et_pin.getText().toString()), tipoTarea);
                    }
                    else
                    {
                        Log.e("DESHACER","debe buscar el pin");
                        buscarPorPin(null, Integer.parseInt(et_pin.getText().toString()), tipoTarea);

                    }
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);

                    dialogPin.dismiss();

                } else
                {

                    TextView tv_error = (TextView) dialogPin.findViewById(R.id.tv_error_pin);
                    tv_error.setText("EL PIN INTRODUCIDO ES INCORRECTO");
                    et_pin.setText("");
                    et_pin.requestFocus();

                }



            }

        });

    }

    private void buscarPorPin(final ResponsePendientesDTO pendientes, Integer pin, final Integer tipoTarea)
    {

        RestUsuario rest = retrofit.create(RestUsuario.class);

        SharedPreferences prefs = getSharedPreferences(getString(R.string.nombreFicheroConfiguracion), Context.MODE_PRIVATE);
        String token = prefs.getString("token", "");

        Call <ResponseLogUsuario> call = rest.loginUsuario(pin,"Bearer "+token);

        call.enqueue(new Callback<ResponseLogUsuario>() {
            @Override
            public void onResponse(Call<ResponseLogUsuario> call, Response<ResponseLogUsuario> response) {
                switch (response.code()) {
                    case 200:
                        SharedPreferences prefs = getSharedPreferences(getString(R.string.nombreFicheroConfiguracion), Context.MODE_PRIVATE);
                        Integer idH = prefs.getInt("hotel", -1);
                        Integer idC = prefs.getInt("carro", -1);
                        usuario=null;

                        usuario=response.body();

                        if(tipoTarea!=-1)
                        {
                            Log.e("DESHACER","AQUi no debe entrar primo"+tipoTarea);
                            if(usuario.getUsuario().getId()!=null) {
                                if (tipoTarea == 0) {
                                    actualizarTarea(pendientes, 2);
                                    cargarTareasSinCompletar(idH, idC);
                                } else if (tipoTarea == 1) {
                                    byte[] encodeValue = Base64.encode(nombreFichero.getBytes(), Base64.DEFAULT);
                                    try {

                                        String enco = codificarBase64(nombreFichero);
                                        NotaDTO nota = new NotaDTO();
                                        nota.setMensaje(enco);
                                        Log.e("AUDI", nota + "");
                                        estadoTareaClick.setNota(nota);
                                        dialogAudios.dismiss();
                                        actualizarTarea(estadoTareaClick, 3);
                                        usuario = null;

                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }

                        }
                        else
                        {

                            deshacerTareaUsuario(usuario.getUsuario().getId());

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
            public void onFailure(Call<ResponseLogUsuario> call, Throwable t) {
                Log.e("Explota", t.toString());
                Log.e("Explota", call.request().toString());
                Log.e("Explota", call.request().body().toString());
            }
        });
    }

    //CARGAMOS LOS ELEMENTOS DEL SPINNER DE LOS FILTROS
    public void cargarFiltros()
    {
        spinnerFiltros=(Spinner) findViewById(R.id.sp_filtros);
        spinnerFiltros.setOnItemSelectedListener(this);
        //Convierto la variable List<> en un ArrayList<>()
        listaFiltros= new ArrayList<>();



        //Arreglo con nombre de frutas
        String[] strFiltros = new String[] {"TODOS","CHECK-IN SUCIO", "CHECK-IN LIMPIO", "OCUPACION SUCIA","OCUPACION LIMPIA", "CHECK-OUT SUCIO", "CHECK-OUT LIMPIO","AVERIA","SINIESTRA","RE-LIMPIEZA"};
        //Agrego las frutas del arreglo `strFrutas` a la listaFrutas
        Collections.addAll(listaFiltros,strFiltros);
        AdapterCategory adapter = new AdapterCategory(this, listaFiltros);

        //Cargo el spinner con los datos
        spinnerFiltros.setAdapter(adapter);
    }
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Toast.makeText(this,listaFiltros.get(position).toString(), Toast.LENGTH_SHORT).show();
        switch(position)
        {
            case 0:
                filtros.setTareaEstado(0,0);
                cargarTareasSinCompletar(usuario);
                break;
            case 1://CHEKING-SUCIO
                filtros.setTareaEstado(1,1);
                cargarTareasPorFiltro(1,1);
                break;
            case 2://CHECKING-LIMPIO
                filtros.setTareaEstado(1,2);
                cargarTareasPorFiltro(1,2);
                break;
            case 3://OCUPACION SUCIA
                filtros.setTareaEstado(2,1);
                cargarTareasPorFiltro(2,1);
                break;
            case 4://OCUPACION SUCIA
                filtros.setTareaEstado(2,2);
                cargarTareasPorFiltro(2,2);
                break;
            case 5://CHECK-OUT-SUCIO
                filtros.setTareaEstado(3,1);
                cargarTareasPorFiltro(3,1);
                break;
            case 6://CHECK-OUT-LIMPIO
                filtros.setTareaEstado(3,2);
                cargarTareasPorFiltro(3,2);
                break;

            case 7://AVERIA
                filtros.setTareaEstado(0,3);
                cargarTareasPorFiltro(0,3);

                break;
            case 8://SINIESTRA
                filtros.setTareaEstado(0,6);
                cargarTareasPorFiltro(0,6);
                break;
            case 9://RELIMPIEZA
                filtros.setTareaEstado(0,4);
                cargarTareasPorFiltro(0,4);
                //SINIESTRO
                break;
        }

    }

    public void cargarTareasPorFiltro(Integer tipoTarea,Integer tipoEstado)
    {

        SharedPreferences prefs = getSharedPreferences(getString(R.string.nombreFicheroConfiguracion), Context.MODE_PRIVATE);
        String token = prefs.getString("token", "");
        Call <List<ResponsePendientesDTO>> call = tareasRest.getEstadoTarea(usuario.getHotel().getId(),tipoTarea,tipoEstado,usuario.getUsuario(),"Bearer "+token);
        call.enqueue(new Callback<List<ResponsePendientesDTO>>() {
            @Override
            public void onResponse(Call<List<ResponsePendientesDTO>> call, Response<List<ResponsePendientesDTO>> response) {
                switch (response.code()) {
                    case 200:

                        estadosTareas = response.body();
                        inicializarEstadoTarea(estadosTareas);

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
            public void onFailure(Call<List<ResponsePendientesDTO>> call, Throwable t) {
                Log.e("Explota", t.toString());
                Log.e("Explota", call.request().toString());
                Log.e("Explota", call.request().body().toString());
            }
        });
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
