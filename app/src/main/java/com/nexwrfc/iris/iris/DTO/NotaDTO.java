package com.nexwrfc.iris.iris.DTO;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by diego on 19/03/2018.
 */

public class NotaDTO implements Serializable {
    @SerializedName("id")
    private Integer id;
    @SerializedName("url")
    private String url;
    @SerializedName("mensaje")
    private String mensaje;
    @SerializedName("mensajeAPP")
    private String mensajeAPP;
    @SerializedName("usuario")
    private UsuarioDTO usuario;

    @SerializedName("fecha")
    private String fecha;

    public NotaDTO() {
    }

    public NotaDTO(Integer id, String url, String mensaje, String mensajeAPP, UsuarioDTO usuario, String fecha) {
        this.id = id;
        this.url = url;
        this.mensaje = mensaje;
        this.mensajeAPP = mensajeAPP;
        this.usuario = usuario;
        this.fecha = fecha;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }


    public UsuarioDTO getUsuario() {
        return usuario;
    }

    public void setUsuario(UsuarioDTO usuario) {
        this.usuario = usuario;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getMensajeAPP() {
        return mensajeAPP;
    }

    public void setMensajeAPP(String mensajeAPP) {
        this.mensajeAPP = mensajeAPP;
    }
}
