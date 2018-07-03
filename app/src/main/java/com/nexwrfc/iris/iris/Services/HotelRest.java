package com.nexwrfc.iris.iris.Services;

import com.nexwrfc.iris.iris.DTO.HotelDTO;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

/**
 * Created by diego on 16/03/2018.
 */

public interface HotelRest
{

    @GET("hotel")
    Call<List<HotelDTO>> getHoteles(@Header("Authorization") String authHeader);
}
