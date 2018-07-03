package com.nexwrfc.iris.iris.DTO;

import java.io.Serializable;
import java.util.Date;


public class EstadosTareasDTO implements Serializable  {



	private Usuario usuario;
	private CarroDTO carro;
	private TareaEstadoDTO estado;


	public EstadosTareasDTO() {
	}

	public EstadosTareasDTO(Usuario usuario, CarroDTO carro, TareaEstadoDTO estado) {
		this.usuario = usuario;
		this.carro = carro;
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

	public TareaEstadoDTO getEstado() {
		return estado;
	}

	public void setEstado(TareaEstadoDTO estado) {
		this.estado = estado;
	}
}
