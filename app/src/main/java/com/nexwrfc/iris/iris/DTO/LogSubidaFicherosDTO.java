package com.nexwrfc.iris.iris.DTO;

import java.util.Date;


public class LogSubidaFicherosDTO {
	
	
	UsuarioDTO usuario;
	Date fechaHora;
	String fichero;
	
	public LogSubidaFicherosDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	public LogSubidaFicherosDTO(UsuarioDTO usuario, Date fechaHora, String fichero) {
		super();
		this.usuario = usuario;
		this.fechaHora = fechaHora;
		this.fichero = fichero;
	}
	public UsuarioDTO getUsuario() {
		return usuario;
	}
	public void setUsuario(UsuarioDTO usuario) {
		this.usuario = usuario;
	}
	public Date getFechaHora() {
		return fechaHora;
	}
	public void setFechaHora(Date fechaHora) {
		this.fechaHora = fechaHora;
	}
	public String getFichero() {
		return fichero;
	}
	public void setFichero(String fichero) {
		this.fichero = fichero;
	}
	
	
}
