package com.nexwrfc.iris.iris.DTO;

import java.sql.Date;

public class DatosFicheroDTO {


	private Integer habitacion;

	private String cliente;

	private Date fechaSalida;
	

	private String tipoHabitacion;

	private Integer numeroPersonas;
	
	
	public DatosFicheroDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public DatosFicheroDTO(Integer habitacion, String cliente, Date fechaSalida, String tipoHabitacion,
			Integer numeroPersonas) {
		super();
		this.habitacion = habitacion;
		this.cliente = cliente;
		this.fechaSalida = fechaSalida;
		this.tipoHabitacion = tipoHabitacion;
		this.numeroPersonas = numeroPersonas;
	}
	
	/**
	 * @return the habitacion
	 */
	public Integer getHabitacion() {
		return habitacion;
	}
	/**
	 * @param habitacion the habitacion to set
	 */
	public void setHabitacion(Integer habitacion) {
		this.habitacion = habitacion;
	}
	/**
	 * @return the cliente
	 */
	public String getCliente() {
		return cliente;
	}
	/**
	 * @param cliente the cliente to set
	 */
	public void setCliente(String cliente) {
		this.cliente = cliente;
	}
	/**
	 * @return the fechaSalida
	 */
	public Date getFechaSalida() {
		return fechaSalida;
	}
	/**
	 * @param fechaSalida the fechaSalida to set
	 */
	public void setFechaSalida(Date fechaSalida) {
		this.fechaSalida = fechaSalida;
	}

	/**
	 * @return the tipoHabitacion
	 */
	public String getTipoHabitacion() {
		return tipoHabitacion;
	}

	/**
	 * @param tipoHabitacion the tipoHabitacion to set
	 */
	public void setTipoHabitacion(String tipoHabitacion) {
		this.tipoHabitacion = tipoHabitacion;
	}

	/**
	 * @return the numeroPersonas
	 */
	public Integer getNumeroPersonas() {
		return numeroPersonas;
	}

	/**
	 * @param numeroPersonas the numeroPersonas to set
	 */
	public void setNumeroPersonas(Integer numeroPersonas) {
		this.numeroPersonas = numeroPersonas;
	}
	
	

	

	
	
	
}
