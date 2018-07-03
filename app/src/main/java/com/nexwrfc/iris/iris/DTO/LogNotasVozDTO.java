package com.nexwrfc.iris.iris.DTO;

import com.google.gson.annotations.SerializedName;

/**
 * Created by diego on 09/05/2018.
 */

public class LogNotasVozDTO
{
    private Integer id;
    private NotaDTO nota;
    private UsuarioDTO usuario;

    public LogNotasVozDTO() {
    }

    public LogNotasVozDTO(Integer id, NotaDTO nota, UsuarioDTO usuario) {
        this.id = id;
        this.nota = nota;
        this.usuario = usuario;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public NotaDTO getNota() {
        return nota;
    }

    public void setNota(NotaDTO nota) {
        this.nota = nota;
    }

    public UsuarioDTO getUsuario() {
        return usuario;
    }

    public void setUsuario(UsuarioDTO usuario) {
        this.usuario = usuario;
    }
}
