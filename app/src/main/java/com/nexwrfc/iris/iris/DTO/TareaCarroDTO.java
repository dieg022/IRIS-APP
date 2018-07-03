package com.nexwrfc.iris.iris.DTO;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by diego on 22/03/2018.
 */

public class TareaCarroDTO implements Serializable
{
    private Integer id;

    private TareaDTO tarea;

    private CarroDTO carro;

    private Date fechaHoraAsignacion;

    private UsuarioDTO usuario;

    public TareaCarroDTO() {
    }

    public TareaCarroDTO(Integer id, TareaDTO tarea, CarroDTO carro, Date fechaHoraAsignacion, UsuarioDTO usuario) {
        this.id = id;
        this.tarea = tarea;
        this.carro = carro;
        this.fechaHoraAsignacion = fechaHoraAsignacion;
        this.usuario = usuario;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public TareaDTO getTarea() {
        return tarea;
    }

    public void setTarea(TareaDTO tarea) {
        this.tarea = tarea;
    }

    public CarroDTO getCarro() {
        return carro;
    }

    public void setCarro(CarroDTO carro) {
        this.carro = carro;
    }

    public Date getFechaHoraAsignacion() {
        return fechaHoraAsignacion;
    }

    public void setFechaHoraAsignacion(Date fechaHoraAsignacion) {
        this.fechaHoraAsignacion = fechaHoraAsignacion;
    }

    public UsuarioDTO getUsuario() {
        return usuario;
    }

    public void setUsuario(UsuarioDTO usuario) {
        this.usuario = usuario;
    }
}
