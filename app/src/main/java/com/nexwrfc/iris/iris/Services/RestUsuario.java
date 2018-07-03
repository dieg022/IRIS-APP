package com.nexwrfc.iris.iris.Services;

import com.nexwrfc.iris.iris.DTO.LoginDTO;
import com.nexwrfc.iris.iris.DTO.TokenDTO;
import com.nexwrfc.iris.iris.DTO.Usuario;
import com.nexwrfc.iris.iris.ResponseAPI.ResponseLogUsuario;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by diego on 02/03/2018.
 */

public interface RestUsuario {

    @POST("login")
    Call<TokenDTO> login(@Body LoginDTO login);

    @GET("usuarios")
    Call<Usuario> getUsuario(@Header("Authorization") String authHeader);
    @GET("usuarios/login2")
    Call<ResponseLogUsuario> loginUsuario(@Query("usuario")String usuario, @Query("pass")String pass,@Header("Authorization") String authHeader);

    @GET("usuarios/login2")
    Call<ResponseLogUsuario> loginUsuario(@Query("pin")Integer pin,@Header("Authorization") String authHeader);

}
