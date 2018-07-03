package com.nexwrfc.iris.iris.ListView;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nexwrfc.iris.iris.DTO.TareaDTO;
import com.nexwrfc.iris.iris.R;

import java.util.List;

public class ListadoTareasAdapter extends RecyclerView.Adapter<ListadoTareasAdapter.ListadoTareasViewHolder>
{
    private List<TareaDTO> items;

    public ListadoTareasAdapter(List<TareaDTO> tareas)
    {
        this.items=tareas;
    }


    public static class ListadoTareasViewHolder extends RecyclerView.ViewHolder
    {
        public TextView et_habitacion;

        public TextView et_tipoTarea;
        public View viewTareaTipo;
        public View viewTareaTipo2;
            public RelativeLayout relativeLayout;

            public ListadoTareasViewHolder(View v)
            {
                super(v);
                et_habitacion=(TextView) v.findViewById(R.id.tv_numero_habitacion);
                relativeLayout=(RelativeLayout) v.findViewById(R.id.rl_tareas);
                et_tipoTarea=(TextView) v.findViewById(R.id.tv_tipo_tarea);
                viewTareaTipo=(View) v.findViewById(R.id.indicator_tarea_tipo);
                viewTareaTipo2=(View) v.findViewById(R.id.indicator_tarea_tipo);
            }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }


    @Override
    public ListadoTareasViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.lv_habitacion_tareas, viewGroup, false);
        return new ListadoTareasViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ListadoTareasViewHolder viewHolder, int i) {

       viewHolder.et_habitacion.setText(items.get(i).getHabitacion().getNumero().toString());

        viewHolder.viewTareaTipo2.setBackgroundColor(Color.WHITE);
       if(items.get(i).getTipo().getId()==1)
       {
            viewHolder.viewTareaTipo.setBackgroundColor(Color.YELLOW);
           viewHolder.et_tipoTarea.setText("CHECK-IN");
       }
        if(items.get(i).getTipo().getId()==2)
        {
            viewHolder.viewTareaTipo.setBackgroundColor(Color.GREEN);
            viewHolder.et_tipoTarea.setText("OCUPACION");
        }
        if(items.get(i).getTipo().getId()==3)
        {
            viewHolder.viewTareaTipo.setBackgroundColor(Color.BLUE);
            viewHolder.et_tipoTarea.setText("CHECKOUT");
        }
    }



}
