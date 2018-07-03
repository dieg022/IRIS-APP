package com.nexwrfc.iris.iris.Services;

import com.nexwrfc.iris.iris.DTO.CarroDTO;
import com.nexwrfc.iris.iris.DTO.TareaDTO;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

/**
 * Created by diego on 09/03/2018.
 */

public interface CarroRest
{

    @GET("carro")
    Call<List<CarroDTO>> getCarrosHotel(@Query("hotel") Integer idHotel,@Header("Authorization") String authHeader);
}
