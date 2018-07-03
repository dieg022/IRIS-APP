package com.nexwrfc.iris.iris.Services;

import com.nexwrfc.iris.iris.DTO.AsignadasDTO;
import com.nexwrfc.iris.iris.DTO.DeshacerDTO;
import com.nexwrfc.iris.iris.DTO.LogNotasVozDTO;
import com.nexwrfc.iris.iris.DTO.NotaDTO;
import com.nexwrfc.iris.iris.DTO.NotaUsuario;
import com.nexwrfc.iris.iris.DTO.TareaCarroDTO;
import com.nexwrfc.iris.iris.DTO.UsuarioDTO;
import com.nexwrfc.iris.iris.ResponseAPI.ResponsePendientesDTO;
import com.nexwrfc.iris.iris.DTO.TareaDTO;
import com.nexwrfc.iris.iris.DTO.TareaEstadoDTO;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by diego on 06/03/2018.
 */

public interface TareasRest {


    @GET("tareas")
    Call<List<TareaDTO>> getTareasHoy(
            @Query("hotel") Integer idHotel,@Query("asignadas")Integer asignadas,@Header("Authorization") String authHeader);

    @POST("tareas/crear/")
    Call<List<AsignadasDTO>> setTareasEstados(@Body List<AsignadasDTO> lista,@Header("Authorization") String authHeader);

    @POST("tareas/pendientes")
    Call<List<ResponsePendientesDTO>> getEstadoTarea(@Query("hotel") Integer idHotel, @Body UsuarioDTO usuario,@Header("Authorization") String authHeader);

    @POST("tareas/pendientes")
    Call<List<ResponsePendientesDTO>> getEstadoTarea(@Query("hotel") Integer idHotel,@Query("tipoTarea")Integer idTipoTarea,@Query("tipoEstado")Integer tipoEstado, @Body UsuarioDTO usuario,@Header("Authorization") String authHeader);

    @GET("tareas/pendientes")
    Call<List<ResponsePendientesDTO>> getEstadoTarea(@Query("hotel") Integer idHotel, @Query("carro")Integer idCarro,@Header("Authorization") String authHeader);

    @POST("tareas/actualizar/")
    Call<TareaEstadoDTO> setEstadoTarea(@Body TareaEstadoDTO tarea,@Query("idEstado") Integer idEstado,@Header("Authorization") String authHeader);

    @GET("tareas/nota")
    Call<List<NotaDTO>> getNotasVozTarea(@Query("tarea")Integer idTarea,@Header("Authorization") String authHeader);

    @POST("log/notas/")
    Call<LogNotasVozDTO> setNotaLog(@Body NotaUsuario notaUsuario,@Header("Authorization") String authHeader);

    @GET("tareas/prioridad")
    Call<Integer> setPrioridadTarea(@Query("tarea")Integer idTarea,@Header("Authorization") String authHeader);

    @POST("tareaCarro/")
    Call<TareaCarroDTO> setCarroTarea(@Body TareaCarroDTO tareaCarro,@Header("Authorization") String authHeader);

    @GET("estadoTarea/deshacer") Call<DeshacerDTO> borrarTareaUsuario(@Query("idUsuario") Integer idUsuario,@Header("Authorization") String authHeader);

}
