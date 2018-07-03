package com.nexwrfc.iris.iris.ListView;

import android.graphics.Color;
import android.support.annotation.IntegerRes;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nexwrfc.iris.iris.DTO.CarroDTO;
import com.nexwrfc.iris.iris.DTO.ReservaDTO;
import com.nexwrfc.iris.iris.ResponseAPI.ResponsePendientesDTO;
import com.nexwrfc.iris.iris.R;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class ListadoEstadosTareaAdapter  extends RecyclerView.Adapter<ListadoEstadosTareaAdapter.MyViewHolder>
{
    private List<ResponsePendientesDTO> tareasEstados;
    private ReservaDTO reserva;
    private Integer perfil;
    private SparseBooleanArray selectedItems;
    private Integer select;
    private RecyclerView recycler;

    public ListadoEstadosTareaAdapter(List<ResponsePendientesDTO> tareas,Integer perfilPresentacion,RecyclerView recyclerView)

    {
        this.tareasEstados=tareas;
        this.perfil=perfilPresentacion;
        this.recycler=recyclerView;

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

        private Button btMantenimiento;

        private ImageView iv_nota;

        private TextView tv_tipo_habitacion;
        private TextView tv_nombreCliente;
        private TextView tv_ocupacion;

        private RelativeLayout rel;

        public MyViewHolder(final View v, final RecyclerView recyclerView)
        {
            super(v);

            tv_habitacion=(TextView)  v.findViewById(R.id.tv_habitacion_estado_tarea);
            iv_nota=(ImageView) v.findViewById(R.id.iv_nota_audio);
            indicador=(View) v.findViewById(R.id.indicador_estado_tarea);
            indicador2=(View) v.findViewById(R.id.indicador_estado_tarea2);
            tv_carro=(TextView) v.findViewById(R.id.tv_estados_carro);
            prioridad=(ProgressBar) v.findViewById(R.id.pb_prioridad);
            tv_fecha=(TextView) v.findViewById(R.id.tv_estado_fecha);
            rel=(RelativeLayout) v.findViewById(R.id.rl_estados_tarea);
            tv_hora=(TextView) v.findViewById(R.id.tv_estado_hora);
            //FICHA LIMPIEZA
            tv_nombreCliente=(TextView) v.findViewById(R.id.tv_nombre_cliente);
            tv_tipo_habitacion=(TextView) v.findViewById(R.id.tv_tipo_habi);
            tv_ocupacion=(TextView) v.findViewById(R.id.tv_ocupacion);

            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view)
                {
                    Integer tam=recyclerView.getAdapter().getItemCount();
                    for(Integer i=0;i<tam;i++)
                    {
                        if(getAdapterPosition()==i)

                        {

                            recyclerView.getAdapter().notifyItemChanged(i);
                            /*ListadoEstadosTareaAdapter ls=(ListadoEstadosTareaAdapter) recyclerView.getAdapter();
                            RecyclerView.ViewHolder vh=ls.getViewHolder();
                            View item=vh.itemView;3*/


                        }

                    }
                    Log.e("CLICK",""+getAdapterPosition());



                }
            });

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
    public ListadoEstadosTareaAdapter.MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.adapter_lv_estados_tarea, viewGroup, false);
        return new ListadoEstadosTareaAdapter.MyViewHolder(v,recycler);
    }

    @Override
    public void onBindViewHolder(ListadoEstadosTareaAdapter.MyViewHolder viewHolder, final int i)
    {





        //Si el perfil no es un carro;
        if(this.perfil!=4)
        {
            viewHolder.tv_carro.setText("Carro:" + tareasEstados.get(i).getCarro().getNumero().toString());
            if(tareasEstados.get(i).getPendientesEscuchar()!=0)
            {
                Log.e("NOTAS DE VOZ","HAY"+tareasEstados.get(i).getTarea().getId());
                viewHolder.iv_nota.setVisibility(View.VISIBLE);
            }
            else
            {
                Log.e("NOTAS DE VOZ","NO HAY"+tareasEstados.get(i).getTarea().getId());
                viewHolder.iv_nota.setVisibility(View.INVISIBLE);
            }
        }
        else
        {
            viewHolder.iv_nota.setVisibility(View.INVISIBLE);
            if(tareasEstados.get(i).getInformacionReserva()!=null) {
                viewHolder.tv_nombreCliente.setText(tareasEstados.get(i).getInformacionReserva().getNombreCliente());
                viewHolder.tv_tipo_habitacion.setText(tareasEstados.get(i).getInformacionReserva().getTipoHabitacion());
                viewHolder.tv_ocupacion.setText("Per:" + tareasEstados.get(i).getInformacionReserva().getPersonas());
            }


        }





        viewHolder.tv_habitacion.setText(tareasEstados.get(i).getEstado().getTarea().getHabitacion().getNumero().toString());
        viewHolder.prioridad.setMax(5);
        viewHolder.prioridad.setProgress(tareasEstados.get(i).getEstado().getTarea().getPrioridad());
        String fecha = tareasEstados.get(i).getEstado().getTarea().getFecha();
        SimpleDateFormat sf = new SimpleDateFormat("dd/MM");
        Date date = new Date(Long.parseLong(fecha));
        viewHolder.tv_fecha.setText(sf.format(date));

        String hora = tareasEstados.get(i).getEstado().getFecha();
        SimpleDateFormat sf2 = new SimpleDateFormat("HH:mm");
        Date time = new Date(Long.parseLong(hora));
        viewHolder.tv_hora.setText(sf2.format(time));


        Integer idEstado = tareasEstados.get(i).getEstado().getTipoEstado().getId();
        Integer idEstadoTarea = tareasEstados.get(i).getTarea().getTipo().getId();
        //PENDIENTE LIMPIAR


        //CHECKING SUCIO
        if(idEstado==1 && idEstadoTarea==1)
        {
            viewHolder.indicador2.setBackgroundColor(Color.parseColor("#Fefefe"));//    blanco
            viewHolder.indicador.setBackgroundColor(Color.parseColor("#F0FF00"));//amarillo
        }
        //OCUPACION SUCIO
        if(idEstado==1 && idEstadoTarea==2)
        {
            viewHolder.indicador2.setBackgroundColor(Color.parseColor("#Fefefe"));//    blanco
            viewHolder.indicador.setBackgroundColor(Color.parseColor("#08FF00"));//verde
        }
        //CHECKOUT SUCIO
        if(idEstado==1 && idEstadoTarea==3)
        {
            viewHolder.indicador.setBackgroundColor(Color.parseColor("#Fefefe"));//    blanco
            viewHolder.indicador2.setBackgroundColor(Color.parseColor("#Fefefe"));//    blanco
        }


        //LIMPIA CHECKIN
        if(idEstado==2 && idEstadoTarea==1)
        {
            viewHolder.indicador.setBackgroundColor(Color.parseColor("#F0FF00"));//amarillo
            viewHolder.indicador2.setBackgroundColor(Color.parseColor("#F0FF00"));//amarillo
        }
        //LIMPIA OCUPADA
        if(idEstado==2 && idEstadoTarea==2)
        {
            viewHolder.indicador.setBackgroundColor(Color.parseColor("#08FF00"));//verde
            viewHolder.indicador2.setBackgroundColor(Color.parseColor("#08FF00"));//verde
        }
        //LIMPIA CHECKOUT
        if(idEstado==2 && idEstadoTarea==3)
        {
            viewHolder.indicador.setBackgroundColor(Color.parseColor("#0668FF"));//azul
            viewHolder.indicador2.setBackgroundColor(Color.parseColor("#0668FF"));//azul
        }

        if(idEstado==3)//MANTENIMIENTO
        {
            viewHolder.indicador2.setBackgroundColor(Color.parseColor("#Fefefe"));//    blanco
            viewHolder.indicador.setBackgroundColor(Color.parseColor("#FF1313"));  //rojo
        }
        if(idEstado==4)//REELIMPIEZA CHECKING
        {
            viewHolder.indicador.setBackgroundColor(Color.parseColor("#FFB900"));//celeste
            viewHolder.indicador2.setBackgroundColor(Color.parseColor("#FFB900"));//celeste
        }
        if(idEstado==6)
        {//SINIESTRA
            viewHolder.indicador.setBackgroundColor(Color.parseColor("#FF1313"));  //rojo
            viewHolder.indicador2.setBackgroundColor(Color.parseColor("#FF1313"));  //rojo
        }






    }
    //FUNCIONES PINTAR SELECCION
    public void toggleSelection(int pos) {
        if (selectedItems.get(pos, false)) {
            selectedItems.delete(pos);
        }
        else {
            selectedItems.put(pos, true);
        }
        notifyItemChanged(pos);
    }

    public void clearSelections() {
        selectedItems.clear();
        notifyDataSetChanged();
    }

    public int getSelectedItemCount() {
        return selectedItems.size();
    }

    public List<Integer> getSelectedItems() {
        List<Integer> items =
                new ArrayList<Integer>(selectedItems.size());
        for (int i = 0; i < selectedItems.size(); i++) {
            items.add(selectedItems.keyAt(i));
        }
        return items;
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
