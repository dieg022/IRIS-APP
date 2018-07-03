package com.nexwrfc.iris.iris.ClasesGenericas;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

/**
 * Created by diego on 16/04/2018.
 */

public class Funciones
{

    public Funciones(){}
    public boolean isNetDisponible(Activity activity,Context context) {

        ConnectivityManager connectivityManager = (ConnectivityManager) activity.getSystemService(context.CONNECTIVITY_SERVICE);

        NetworkInfo actNetInfo = connectivityManager.getActiveNetworkInfo();
        return (actNetInfo != null && actNetInfo.isConnected());
    }

    public Boolean isOnlineNet(String url) {

        Boolean flag;
        try {
            Process p = java.lang.Runtime.getRuntime().exec("ping  "+url);
            int val           = p.waitFor();
            boolean reachable = (val == 0);
            Log.e("FUNCIONES","HACE PIN");
            flag=true;

        } catch (Exception e) {
            Log.e("FUNCIONES",e.toString());
            // TODO Auto-generated catch block
            e.printStackTrace();
            flag=false;
        }
        return flag;
    }
}
