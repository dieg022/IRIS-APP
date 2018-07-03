package com.nexwrfc.iris.iris.ListView;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Environment;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nexwrfc.iris.iris.DTO.AsignadasDTO;
import com.nexwrfc.iris.iris.DTO.LogNotasVozDTO;
import com.nexwrfc.iris.iris.DTO.NotaDTO;
import com.nexwrfc.iris.iris.DTO.NotaUsuario;
import com.nexwrfc.iris.iris.DTO.UsuarioDTO;
import com.nexwrfc.iris.iris.R;
import com.nexwrfc.iris.iris.ResponseAPI.ResponsePendientesDTO;
import com.nexwrfc.iris.iris.Services.RetroFitAdapter;
import com.nexwrfc.iris.iris.Services.TareasRest;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by diego on 20/03/2018.
 */

public class NotasVozAdapter extends RecyclerView.Adapter<NotasVozAdapter.MyViewHolder> {

    private List<NotaDTO> notasVoz;
    private Context context;
    MediaPlayer mediaPlayer;
    UsuarioDTO usuario;
    String token;
    Retrofit retrofit;
    TareasRest tareasRest;

    public NotasVozAdapter(List<NotaDTO> notasVoz,Context context,UsuarioDTO usuarioLogueado,String token)

    {
        this.notasVoz = notasVoz;
        this.context=context;
        this.usuario=usuarioLogueado;
        this.token=token;
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView tv_usuario;
        public TextView tv_fecha;
        public TextView tv_hora;
        public Button bt_play;
        public Button bt_stop;

        public MyViewHolder(View v) {
            super(v);
            tv_usuario = (TextView) v.findViewById(R.id.tv_usuario_nota);
            tv_fecha = (TextView) v.findViewById(R.id.tv_nota_fecha);
            tv_hora = (TextView) v.findViewById(R.id.tv_hora_nota);
            bt_play = (Button) v.findViewById(R.id.bt_play_nota);
            bt_stop = (Button) v.findViewById(R.id.bt_stop_nota);


        }
    }

    @Override
    public int getItemCount() {
        return notasVoz.size();
    }


    @Override
    public NotasVozAdapter.MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.lv_notas_voz_tareas, viewGroup, false);
        return new NotasVozAdapter.MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final NotasVozAdapter.MyViewHolder viewHolder, final int i) {

        final Integer pos=i;

        viewHolder.bt_play.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View v)
            {


                //String url =Base64.decode(notasVoz.get(pos).getMensaje(),Base64.DEFAULT);
                String nota64 = notasVoz.get(i).getMensajeAPP();

                String nombre="audio"+i+".mp4";

                try
                {
                    FileOutputStream fos = context.openFileOutput(nombre, Context.MODE_PRIVATE);
                    fos.write(Base64.decode(nota64, Base64.DEFAULT));
                    retrofit = new RetroFitAdapter().getAdaptador();
                    tareasRest = retrofit.create(TareasRest.class);
                    NotaUsuario n=new NotaUsuario();
                    n.setNota(notasVoz.get(i));
                    n.setUsuario(usuario);


                    Call<LogNotasVozDTO> call = tareasRest.setNotaLog(n,token);
                    call.enqueue(new Callback<LogNotasVozDTO>() {
                        @Override
                        public void onResponse(Call<LogNotasVozDTO> call, Response<LogNotasVozDTO> response) {
                            switch (response.code()) {
                                case 200:
                                    LogNotasVozDTO log = response.body();

                                    Intent local = new Intent();

                                    local.setAction("actualizarEstados");

                                    context.sendBroadcast(local);
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
                        public void onFailure(Call<LogNotasVozDTO> call, Throwable t) {
                            Log.e("Explota", t.toString());
                            Log.e("Explota", call.request().toString());
                            Log.e("Explota", call.request().body().toString());
                        }
                    });
                    fos.close();

                }
                catch(IOException ex)
                {
                    Log.e("AUDIO","ERROR PRIMO"+ex.toString());
                }
               cargarAudio(nombre);

            }
        });

        viewHolder.bt_stop.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View v)
            {
                try {
                    mediaPlayer.pause();
                }catch (Exception ex)
                {
                    Log.e("AUDIO",ex.toString());
                }

            }
        });

        viewHolder.tv_usuario.setText(notasVoz.get(i).getUsuario().getNombre_usuario());
        viewHolder.tv_fecha.setText(notasVoz.get(i).getFecha());

        String fecha=notasVoz.get(i).getFecha();
        SimpleDateFormat sf = new SimpleDateFormat("dd/MM");
        Date date = new Date(Long.parseLong(fecha));
        viewHolder.tv_fecha.setText(sf.format(date));
        String hora=notasVoz.get(i).getFecha();
        SimpleDateFormat sf2 = new SimpleDateFormat("HH:mm");
        Date time = new Date(Long.parseLong(hora));

        viewHolder.tv_hora.setText(sf2.format(time));

    }
    private void cargarAudio(String nombreFichero)
    {

        try
        {
            FileInputStream fos = context.openFileInput(nombreFichero);

            MediaPlayer mediaPlayer = new MediaPlayer();
            mediaPlayer.setDataSource(fos.getFD());
            mediaPlayer.prepare();
            mediaPlayer.start();

        }catch (ExceptionInInitializerError e)
        {
            Log.e("AUDIO",e.toString()+"");
        } catch (FileNotFoundException e) {
            Log.e("AUDIO",e.toString()+"");
        } catch (IOException e) {
            Log.e("AUDIO",e.toString()+"");
        }
    }

}