package com.nexwrfc.iris.iris.ListView;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nexwrfc.iris.iris.DTO.AsignadasDTO;
import com.nexwrfc.iris.iris.DTO.CarroDTO;
import com.nexwrfc.iris.iris.DTO.TareaDTO;
import com.nexwrfc.iris.iris.R;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by diego on 12/03/2018.
 */

public class AsignadasTareasAdapter  extends RecyclerView.Adapter<AsignadasTareasAdapter.MyViewHolder>
{

    private List<AsignadasDTO> asignadas;

    public AsignadasTareasAdapter(List<AsignadasDTO> asignadas)

    {
        this.asignadas=asignadas;
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder
    {
        public TextView et_tarea_asignada;
        public TextView et_carro_asignado;
        public RelativeLayout viewForeground;

        public MyViewHolder(View v)
        {
            super(v);
            et_tarea_asignada=(TextView)  v.findViewById(R.id.tv_habitacion_asignada);
            et_carro_asignado=(TextView) v.findViewById(R.id.tv_carro_asignado);
            viewForeground =(RelativeLayout) v.findViewById(R.id.rl_estados_tarea);

        }
    }

    @Override
    public int getItemCount() {
        return asignadas.size();
    }


    @Override
    public AsignadasTareasAdapter.MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.lv_habitaciones_asignadas, viewGroup, false);
        return new AsignadasTareasAdapter.MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(AsignadasTareasAdapter.MyViewHolder viewHolder, int i)
    {

        viewHolder.et_tarea_asignada.setText(asignadas.get(i).getTarea().getHabitacion().getNumero().toString());
        viewHolder.et_carro_asignado.setText(asignadas.get(i).getCarro().getNumero().toString());
    }
}
