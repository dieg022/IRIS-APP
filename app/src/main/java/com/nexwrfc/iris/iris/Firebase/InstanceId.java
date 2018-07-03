package com.nexwrfc.iris.iris.Firebase;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.provider.SyncStateContract;
import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

/**
 * Created by diego on 09/04/2018.
 */

public class InstanceId extends FirebaseInstanceIdService
{

    @Override public void onTokenRefresh() {


        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        //Log.d("FIREBASE", "Refreshed token: " + refreshedToken);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        preferences.edit().putString("TOKEN_FIREBASE", refreshedToken).apply();
    }
}
