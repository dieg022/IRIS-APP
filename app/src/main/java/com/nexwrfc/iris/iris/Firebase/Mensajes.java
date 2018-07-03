package com.nexwrfc.iris.iris.Firebase;

import android.app.AlertDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.nexwrfc.iris.iris.Activity.EstadosTareasActivity;
import com.nexwrfc.iris.iris.R;

import java.util.Random;

/**
 * Created by diego on 09/04/2018.
 */

public class Mensajes extends FirebaseMessagingService
{
    public NotificationManager notificationManager;
    private static final String NOTIFICATION_ID_EXTRA = "notificationId";
    private static final String IMAGE_URL_EXTRA = "imageUrl";
    private static final String ADMIN_CHANNEL_ID ="admin_channel";

    @Override public void onMessageReceived(RemoteMessage remoteMessage)
    {
          //Obtiene el mensaje enviado desde otra aplicación
          Log.e("FIREBASEMENSSAGE",remoteMessage.getData()+"");

          notificationManager=(NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            Log.e("FIREBASEACTIVITY",getApplicationContext().getClass()+"");
        Log.e("FIREBASEACTIVITY",getApplication().getApplicationContext().getClass()+"");

          int idNotificacion=new Random().nextInt(60000);

          Uri sonidoURI=RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

          NotificationCompat.Builder notificationBuilder=new NotificationCompat.Builder(this,ADMIN_CHANNEL_ID)
                  .setSmallIcon(R.drawable.ico)
                  .setContentTitle(remoteMessage.getData().get("title"))
                  .setContentText(remoteMessage.getData().get("message"))
                  .setAutoCancel(true)
                  .setSound(sonidoURI);
          notificationManager.notify(idNotificacion,notificationBuilder.build());

        Intent local = new Intent();

        local.setAction("actualizarEstados");

        this.sendBroadcast(local);

    }



}
