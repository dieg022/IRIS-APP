package com.nexwrfc.iris.iris.DTO;

/**
 * Created by diego on 09/05/2018.
 */

public class NotaUsuario {
    private UsuarioDTO usuario;
    private NotaDTO nota;

    public NotaUsuario() {
    }

    public NotaUsuario(UsuarioDTO usuario, NotaDTO nota) {
        this.usuario = usuario;
        this.nota = nota;
    }

    public UsuarioDTO getUsuario() {
        return usuario;
    }

    public void setUsuario(UsuarioDTO usuario) {
        this.usuario = usuario;
    }

    public NotaDTO getNota() {
        return nota;
    }

    public void setNota(NotaDTO nota) {
        this.nota = nota;
    }
}
