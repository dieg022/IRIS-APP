package com.nexwrfc.iris.iris.Services;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.nexwrfc.iris.iris.Activity.ConfigActivity;

/**
 * Created by diego on 28/06/2018.
 */

public class Monitor extends BroadcastReceiver
{
    public void onReceive(Context context, Intent intent) {
        Intent i = new Intent(context, ConfigActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(i);
    }
}
