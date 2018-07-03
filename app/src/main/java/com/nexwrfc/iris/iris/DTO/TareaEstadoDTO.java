package com.nexwrfc.iris.iris.DTO;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by diego on 13/03/2018.
 */

public class TareaEstadoDTO implements Serializable{

    private Integer id_estado;
    private TareaDTO tarea;

    @SerializedName("tipoEstado")
    private TipoEstadoDTO tipoEstado;
    @SerializedName("fecha")
    private String fecha;

    private Integer prioridad;
    @SerializedName("nota")
    private NotaDTO nota;

    @SerializedName("usuarioCreacion")
    private UsuarioDTO usuario;

    public TareaEstadoDTO() {
    }

    public TareaEstadoDTO(Integer id_estado, TareaDTO tarea, TipoEstadoDTO tipoEstado, String fecha, Integer prioridad, NotaDTO nota, UsuarioDTO usuario) {
        this.id_estado = id_estado;
        this.tarea = tarea;
        this.tipoEstado = tipoEstado;
        this.fecha = fecha;
        this.prioridad = prioridad;
        this.nota = nota;
        this.usuario = usuario;
    }

    public NotaDTO getNota() {
        return nota;
    }

    public void setNota(NotaDTO nota) {
        this.nota = nota;
    }

    public Integer getPrioridad() {
        return prioridad;
    }

    public void setPrioridad(Integer prioridad) {
        this.prioridad = prioridad;
    }

    public UsuarioDTO getUsuario() {
        return usuario;
    }

    public void setUsuario(UsuarioDTO usuario) {
        this.usuario = usuario;
    }

    public Integer getId_estado() {
        return id_estado;
    }

    public void setId_estado(Integer id_estado) {
        this.id_estado = id_estado;
    }

    public TareaDTO getTarea() {
        return tarea;
    }

    public void setTarea(TareaDTO tarea) {
        this.tarea = tarea;
    }

    public TipoEstadoDTO getTipoEstado() {
        return tipoEstado;
    }

    public void setTipoEstado(TipoEstadoDTO tipoEstado) {
        this.tipoEstado = tipoEstado;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public UsuarioDTO getUsuarioCreacion() {
        return usuario;
    }

    public void setUsuarioCreacion(UsuarioDTO usuarioCreacion) {
        this.usuario = usuarioCreacion;
    }
}
