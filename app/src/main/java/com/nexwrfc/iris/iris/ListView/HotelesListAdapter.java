package com.nexwrfc.iris.iris.ListView;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nexwrfc.iris.iris.DTO.CarroDTO;
import com.nexwrfc.iris.iris.DTO.HotelDTO;
import com.nexwrfc.iris.iris.R;

import java.util.List;

/**
 * Created by diego on 16/03/2018.
 */

public class HotelesListAdapter extends RecyclerView.Adapter<HotelesListAdapter.MyViewHolder>
{

    private List<HotelDTO> items;

    public  HotelesListAdapter(List<HotelDTO> hoteles)
    {
        this.items=hoteles;
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder
    {
        public TextView tv_nombreHotel;

        public MyViewHolder(View v)
        {
            super(v);
            tv_nombreHotel=(TextView)  v.findViewById(R.id.tv_nombreHotel);

        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }


    @Override
    public HotelesListAdapter.MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.lv_hoteles_configuracion, viewGroup, false);
        return new HotelesListAdapter.MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(HotelesListAdapter.MyViewHolder viewHolder, int i) {

        viewHolder.tv_nombreHotel.setText(items.get(i).getNombre().toString());


    }
}
