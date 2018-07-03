package com.nexwrfc.iris.iris.AdapterSpinner;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.nexwrfc.iris.iris.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by diego on 10/05/2018.
 */

public class AdapterCategory extends BaseAdapter
{
    protected Activity activity;
    protected List<String> items;


    public AdapterCategory (Activity activity, List<String> items) {
        this.activity = activity;
        this.items = items;

    }

    @Override
    public int getCount() {
        return items.size();
    }

    public void clear() {
        items.clear();
    }

    public void addAll(ArrayList<String> category) {
        for (int i = 0; i < category.size(); i++) {
            items.add(category.get(i));
        }
    }

    @Override
    public Object getItem(int arg0) {
        return items.get(arg0);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;

        if (convertView == null) {
            LayoutInflater inf = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inf.inflate(R.layout.spinner_adapter_filtros, null);
        }
        View  indicador1=(View) v.findViewById(R.id.indicador_color1_filtro);
        View  indicador2=(View) v.findViewById(R.id.indicador_color2_filtro);

        TextView title = (TextView) v.findViewById(R.id.tv_titulo_filtro);

        title.setText(items.get(position));

       switch(position)
        {
            case 0:
                indicador2.setVisibility(View.INVISIBLE);
                indicador1.setVisibility(View.INVISIBLE);
                break;
            case 1://CHECK-IN SUCIO
                indicador2.setBackgroundColor(Color.parseColor("#FEFEFE"));
                indicador1.setBackgroundColor(Color.parseColor("#F7F70C"));
                break;
            case 2://CHECK-IN LIMPIO

                indicador2.setBackgroundColor(Color.parseColor("#F7F70C"));
                indicador1.setBackgroundColor(Color.parseColor("#F7F70C"));
                break;
            case 3://RE-LIMPIEZA OCUPACION SUCIA
                indicador1.setBackgroundColor(Color.parseColor("#0CC400"));//VERDE
                indicador2.setBackgroundColor(Color.parseColor("#FEFEFE"));//verde
                break;
            case 4://RE-LIMPIEZA OCUPACION limpia
                indicador1.setBackgroundColor(Color.parseColor("#0CC400"));//VERDE
                indicador2.setBackgroundColor(Color.parseColor("#0CC400"));//verde
                break;
            case 5://CHECK-OUT SUCIO
                indicador1.setBackgroundColor(Color.parseColor("#FEFEFE"));//blanco
                indicador2.setBackgroundColor(Color.parseColor("#FEFEFE"));
                break;
            case 6://CHECK-OUT LIMPIO
                indicador1.setBackgroundColor(Color.parseColor("#068AFF"));//blanco
                indicador2.setBackgroundColor(Color.parseColor("#068AFF"));
                break;
            case 7://AVERIA
                indicador1.setBackgroundColor(Color.parseColor("#FE1300"));//blanco
                indicador2.setBackgroundColor(Color.parseColor("#FEFEFE"));
                break;
            case 8://SINIESTRA
                indicador1.setBackgroundColor(Color.parseColor("#FE1300"));//blanco
                indicador2.setBackgroundColor(Color.parseColor("#FE1300"));
                break;
            case 9://RELIMPIEZA
                indicador1.setBackgroundColor(Color.parseColor("#FFB900"));//blanco
                indicador2.setBackgroundColor(Color.parseColor("#FFB900"));
                break;


        }



        return v;
    }
}
