package com.nexwrfc.iris.iris.ListView;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nexwrfc.iris.iris.DTO.CarroDTO;
import com.nexwrfc.iris.iris.R;

import java.util.List;

/**
 * Created by diego on 09/03/2018.
 */

public class CarroListadoAdapter extends RecyclerView.Adapter<CarroListadoAdapter.MyViewHolder>
{
    private List<CarroDTO> items;

    public CarroListadoAdapter(List<CarroDTO> carros)
    {
        this.items=carros;
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder
    {
        public TextView et_numeroCarro;

        public MyViewHolder(View v)
        {
            super(v);
            et_numeroCarro=(TextView)  v.findViewById(R.id.tv_carro_num);

        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }


    @Override
    public CarroListadoAdapter.MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.lv_carros_tarea, viewGroup, false);
        return new CarroListadoAdapter.MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(CarroListadoAdapter.MyViewHolder viewHolder, int i) {

        viewHolder.et_numeroCarro.setText(items.get(i).getNumero().toString());


    }



}
