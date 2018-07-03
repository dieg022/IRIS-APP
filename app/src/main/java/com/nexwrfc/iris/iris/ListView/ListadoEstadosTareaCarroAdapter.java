package com.nexwrfc.iris.iris.ListView;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nexwrfc.iris.iris.R;
import com.nexwrfc.iris.iris.ResponseAPI.ResponsePendientesDTO;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class ListadoEstadosTareaCarroAdapter extends RecyclerView.Adapter<ListadoEstadosTareaCarroAdapter.MyViewHolder>
{
    private List<ResponsePendientesDTO> tareasEstados;

    public ListadoEstadosTareaCarroAdapter(List<ResponsePendientesDTO> tareas)

    {
        this.tareasEstados=tareas;
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder
    {
        public TextView tv_habitacion;
        private View indicador;
        private View indicador2;
        private TextView tv_carro;
        private TextView tv_fecha;
        private ProgressBar prioridad;
        private TextView tv_hora;
        private TextView tv_tipo_habitacion;
        private TextView tv_nombreCliente;

        private RelativeLayout rel;

        public MyViewHolder(View v)
        {
            super(v);
            tv_habitacion=(TextView)  v.findViewById(R.id.tv_habitacion_estado_tarea);
            indicador=(View) v.findViewById(R.id.indicador_estado_tarea);
            indicador2=(View) v.findViewById(R.id.indicador_estado_tarea2);
            tv_carro=(TextView) v.findViewById(R.id.tv_estados_carro);
            prioridad=(ProgressBar) v.findViewById(R.id.pb_prioridad);
            tv_fecha=(TextView) v.findViewById(R.id.tv_estado_fecha);
            rel=(RelativeLayout) v.findViewById(R.id.rl_estados_tarea);
            tv_hora=(TextView) v.findViewById(R.id.tv_estado_hora);


        }
    }

    public RecyclerView.ViewHolder getViewHolder()
    {
        return this.getViewHolder();
    }
    @Override
    public int getItemCount() {
        return tareasEstados.size();
    }


    @Override
    public ListadoEstadosTareaCarroAdapter.MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.adapter_lv_estados_tarea, viewGroup, false);
        return new ListadoEstadosTareaCarroAdapter.MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ListadoEstadosTareaCarroAdapter.MyViewHolder viewHolder, int i)
    {
        viewHolder.tv_carro.setText("Carro:"+tareasEstados.get(i).getCarro().getNumero().toString());
        viewHolder.tv_habitacion.setText(tareasEstados.get(i).getEstado().getTarea().getHabitacion().getNumero().toString());

       viewHolder.prioridad.setMax(5);
       Log.e("PRIORIDAD",tareasEstados.get(i).getTarea().getPrioridad()+"");
       viewHolder.prioridad.setProgress(tareasEstados.get(i).getEstado().getTarea().getPrioridad());


        Integer idEstado=tareasEstados.get(i).getEstado().getTipoEstado().getId();
        Integer idEstadoTarea=tareasEstados.get(i).getTarea().getTipo().getId();

        //PENDIENTE LIMPIAR
        //CHECKING SUCIO

        if(idEstado==1 && idEstadoTarea==1)
        {
            viewHolder.indicador.setBackgroundColor(Color.parseColor("#FEFEFE"));
            viewHolder.indicador2.setBackgroundColor(Color.parseColor("#F7F70C"));
        }
        //OCUPACION SUCIO
        if(idEstado==1 && idEstadoTarea==2)
        {
            viewHolder.indicador.setBackgroundColor(Color.parseColor("#FEFEFE"));//blanco
            viewHolder.indicador2.setBackgroundColor(Color.parseColor("#0CC400"));//verde
        }
        //CHECKOUT LIMPIO
        if(idEstado==1 && idEstadoTarea==3)
        {
            viewHolder.indicador.setBackgroundColor(Color.parseColor("#FEFEFE"));//blanco
            viewHolder.indicador2.setBackgroundColor(Color.parseColor("#FEFEFE"));//verde
        }
        if(idEstado==2 && idEstadoTarea==1)
        {
            viewHolder.indicador.setBackgroundColor(Color.parseColor("#F92E2E"));//amarillo
            viewHolder.indicador2.setBackgroundColor(Color.parseColor("#F92E2E"));//amarillo
        }
        //OCUPACION LIMPIO
        if(idEstado==2 && idEstadoTarea==2)
        {
            viewHolder.indicador.setBackgroundColor(Color.parseColor("#0CC400"));//VERDE
            viewHolder.indicador2.setBackgroundColor(Color.parseColor("#0CC400"));//verde
        }
        //CHECKOUT LIMPIO
        if(idEstado==2 && idEstadoTarea==3)
        {
            viewHolder.indicador.setBackgroundColor(Color.parseColor("#FEFEFE"));//blanco
            viewHolder.indicador2.setBackgroundColor(Color.parseColor("#FEFEFE"));//verde
        }
        if(idEstado==3)//MANTENIMIENTO
        {
            viewHolder.indicador.setBackgroundColor(Color.parseColor("#FEFEFE"));//
            viewHolder.indicador2.setBackgroundColor(Color.parseColor("#FF0F00"));
        }
        if(idEstado==4)//REELIMPIEZA
        {
            viewHolder.indicador.setBackgroundColor(Color.parseColor("#FFB900"));//
            viewHolder.indicador2.setBackgroundColor(Color.parseColor("#FFB900"));
        }
        if(idEstado==6)
        {//SINIESTRA
            viewHolder.indicador.setBackgroundColor(Color.parseColor("#DB0000"));
            viewHolder.indicador2.setBackgroundColor(Color.parseColor("#DB0000"));
        }


        String fecha=tareasEstados.get(i).getEstado().getTarea().getFecha();
        SimpleDateFormat sf = new SimpleDateFormat("dd/MM");
        Date date = new Date(Long.parseLong(fecha));
        viewHolder.tv_fecha.setText(sf.format(date));

        String hora=tareasEstados.get(i).getEstado().getFecha();
        SimpleDateFormat sf2 = new SimpleDateFormat("HH:mm");
        Date time = new Date(Long.parseLong(hora));

        viewHolder.tv_hora.setText(sf2.format(time));




    }
    public static Date sumarDiasAFecha(Date fecha, int dias){
        if (dias==0) return fecha;
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(fecha);
        calendar.add(Calendar.DAY_OF_YEAR, dias);
        return calendar.getTime();
    }
    public static Date sumarHora(Date hora, int horas){
        if (horas==0) return hora;
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(hora);
        calendar.add(Calendar.HOUR, horas);
        return calendar.getTime();
    }
}
