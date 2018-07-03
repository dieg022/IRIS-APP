package com.nexwrfc.iris.iris.ClasesGenericas;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;

import com.nexwrfc.iris.iris.Activity.EstadosTareasActivity;

/**
 * Created by diego on 19/03/2018.
 */

public class DialogoError {

   public DialogoError (Context context,String titulo,String descripcion)
    {
        super();
        new AlertDialog.Builder(context)
                .setIcon(android.R.drawable.alert_light_frame)
                .setTitle(titulo)
                .setMessage(descripcion)
                .show();

    }
}

