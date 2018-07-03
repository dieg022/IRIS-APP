package com.nexwrfc.iris.iris.DTO;

/**
 * Created by diego on 12/03/2018.
 */

public class AsignadasDTO {

    private TareaDTO tarea;
    private CarroDTO carro;
    private UsuarioDTO usuario;

    public AsignadasDTO() {
    }

    public AsignadasDTO(TareaDTO tarea, CarroDTO carro, UsuarioDTO usuario) {
        this.tarea = tarea;
        this.carro = carro;
        this.usuario = usuario;
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

    public UsuarioDTO getUsuario() {
        return usuario;
    }

    public void setUsuario(UsuarioDTO usuario) {
        this.usuario = usuario;
    }
}
