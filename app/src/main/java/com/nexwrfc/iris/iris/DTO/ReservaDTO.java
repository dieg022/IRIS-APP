package com.nexwrfc.iris.iris.DTO;

import java.util.Date;


public class ReservaDTO {

	

	private Integer id;
	
	private HabitacionDTO habitacion;
	
	private Date fechaEntrada;
	
	private Date fechaSalida;
	
	private ClienteDTO cliente;
	
	private HotelDTO hotel;
	
	private Integer personas;
	
	public ReservaDTO() {
		super();
		// TODO Auto-generated constructor stub
	}



	public ReservaDTO(Integer id, HabitacionDTO habitacion, Date fechaEntrada, Date fechaSalida, ClienteDTO cliente,
			HotelDTO hotel, Integer personas) {
		super();
		this.id = id;
		this.habitacion = habitacion;
		this.fechaEntrada = fechaEntrada;
		this.fechaSalida = fechaSalida;
		this.cliente = cliente;
		this.hotel = hotel;
		this.personas = personas;
	}



	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}


	/**
	 * @param id the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
	}


	/**
	 * @return the habitacion
	 */
	public HabitacionDTO getHabitacion() {
		return habitacion;
	}


	/**
	 * @param habitacion the habitacion to set
	 */
	public void setHabitacion(HabitacionDTO habitacion) {
		this.habitacion = habitacion;
	}


	/**
	 * @return the fechaEntrada
	 */
	public Date getFechaEntrada() {
		return fechaEntrada;
	}


	/**
	 * @param fechaEntrada the fechaEntrada to set
	 */
	public void setFechaEntrada(Date fechaEntrada) {
		this.fechaEntrada = fechaEntrada;
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
	 * @return the cliente
	 */
	public ClienteDTO getCliente() {
		return cliente;
	}


	/**
	 * @param cliente the cliente to set
	 */
	public void setCliente(ClienteDTO cliente) {
		this.cliente = cliente;
	}

	public HotelDTO getHotel() {
		return hotel;
	}

	public void setHotel(HotelDTO hotel) {
		this.hotel = hotel;
	}



	public Integer getPersonas() {
		return personas;
	}



	public void setPersonas(Integer personas) {
		this.personas = personas;
	}
	
	
	
	
	
	
}
