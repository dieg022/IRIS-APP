package com.nexwrfc.iris.iris.DTO;

import com.google.gson.annotations.SerializedName;

public class InfoReservaDTO {
	@SerializedName("nombreCliente")
	private String nombreCliente;
	@SerializedName("tipoHabitacion")
	private String tipoHabitacion;
	@SerializedName("personas")
	private Integer personas;
	
	
	public InfoReservaDTO() {
		super();
		// TODO Auto-generated constructor stub
	}


	public InfoReservaDTO(String nombreCliente, String tipoHabitacion, Integer personas) {
		this.nombreCliente = nombreCliente;
		this.tipoHabitacion = tipoHabitacion;
		this.personas = personas;
	}


	public String getNombreCliente() {
		return nombreCliente;
	}


	public void setNombreCliente(String nombreCliente) {
		this.nombreCliente = nombreCliente;
	}


	public String getTipoHabitacion() {
		return tipoHabitacion;
	}


	public void setTipoHabitacion(String tipoHabitacion) {
		this.tipoHabitacion = tipoHabitacion;
	}


	public Integer getPersonas() {
		return personas;
	}


	public void setPersonas(Integer personas) {
		this.personas = personas;
	}
	
	
	
	
}
