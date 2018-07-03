package com.nexwrfc.iris.iris.ClasesGenericas;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import com.nexwrfc.iris.iris.DTO.Usuario;
import com.nexwrfc.iris.iris.R;

/**
 * Created by diego on 16/03/2018.
 */

public class SesionUsuario
{

    public Usuario getUsuarioLogin(Activity ac)
    {
        SharedPreferences sharedPref = ac.getPreferences(ac.MODE_PRIVATE);
        Usuario user=new Usuario();

        Integer idUsuario=sharedPref.getInt(String.valueOf(R.string.id_preferences),-1);
        Integer nivelUsuario=sharedPref.getInt(String.valueOf(R.string.nivel_preferences),-1);
        String perfilUsuario=sharedPref.getString(String.valueOf(R.string.perfil_preferences),"");
        String aliasUsuario=sharedPref.getString(String.valueOf(R.string.alias_preferences),"");

        return user;
    }
    public void setUsuarioLogin(Activity context)
    {
       /* SharedPreferences sharedPref = context.getSharedPreferences(getString(R.string.session_preferences), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();

        editor.putInt(getString(R.string.id_preferences), usuario.getId());
        editor.putString(getString(R.string.alias_preferences), usuario.getAlias().toString());
        editor.putInt(getString(R.string.nivel_preferences), usuario.getPerfil().getNivel().getId());
        editor.putString(getString(R.string.perfil_preferences), usuario.getPerfil().getNombre_perfil().toString());
        editor.putInt(getString(R.string.hotel_preferences),usuario.getHotel().getId());
        editor.putString(getString(R.string.hotel_nombre),usuario.getHotel().getNombre().toString());

        editor.commit();*/
    }
}
