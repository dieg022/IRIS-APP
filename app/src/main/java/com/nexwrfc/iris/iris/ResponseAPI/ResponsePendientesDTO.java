package com.nexwrfc.iris.iris.ResponseAPI;

import com.google.gson.annotations.SerializedName;
import com.nexwrfc.iris.iris.DTO.CarroDTO;
import com.nexwrfc.iris.iris.DTO.InfoReservaDTO;
import com.nexwrfc.iris.iris.DTO.NotaDTO;
import com.nexwrfc.iris.iris.DTO.TareaDTO;
import com.nexwrfc.iris.iris.DTO.TareaEstadoDTO;
import com.nexwrfc.iris.iris.DTO.Usuario;

/**
 * Created by diego on 15/03/2018.
 */

public class ResponsePendientesDTO {
    private TareaDTO tarea;
    private TareaEstadoDTO estado;
    private Usuario usuario;
    private CarroDTO carro;
    @SerializedName("pendientesEscuchar")
    private Integer pendientesEscuchar;
    @SerializedName("nota")
    private NotaDTO nota;

    @SerializedName("informacionReserva")
    private InfoReservaDTO informacionReserva;

    public ResponsePendientesDTO() {
    }


    public ResponsePendientesDTO(TareaDTO tarea, TareaEstadoDTO estado, Usuario usuario, CarroDTO carro, Integer pendientesEscuchar, NotaDTO nota, InfoReservaDTO informacionReserva) {
        this.tarea = tarea;
        this.estado = estado;
        this.usuario = usuario;
        this.carro = carro;
        this.pendientesEscuchar = pendientesEscuchar;
        this.nota = nota;
        this.informacionReserva = informacionReserva;
    }

    public ResponsePendientesDTO(TareaDTO tarea, TareaEstadoDTO estado, Usuario usuario, CarroDTO carro, Integer pendientesEscuchar, NotaDTO nota) {
        this.tarea = tarea;
        this.estado = estado;
        this.usuario = usuario;
        this.carro = carro;
        this.pendientesEscuchar = pendientesEscuchar;
        this.nota = nota;
    }

    public InfoReservaDTO getInformacionReserva() {
        return informacionReserva;
    }

    public void setInformacionReserva(InfoReservaDTO informacionReserva) {
        this.informacionReserva = informacionReserva;
    }

    public Integer getPendientesEscuchar() {
        return pendientesEscuchar;
    }

    public void setPendientesEscuchar(Integer pendientesEscuchar) {
        this.pendientesEscuchar = pendientesEscuchar;
    }

    public NotaDTO getNota() {
        return nota;
    }

    public void setNota(NotaDTO nota) {
        this.nota = nota;
    }

    public TareaDTO getTarea() {
        return tarea;
    }

    public void setTarea(TareaDTO tarea) {
        this.tarea = tarea;
    }

    public TareaEstadoDTO getEstado() {
        return estado;
    }

    public void setEstado(TareaEstadoDTO estado) {
        this.estado = estado;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public CarroDTO getCarro() {
        return carro;
    }

    public void setCarro(CarroDTO carro) {
        this.carro = carro;
    }
}
