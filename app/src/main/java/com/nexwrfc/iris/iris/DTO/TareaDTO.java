package com.nexwrfc.iris.iris.DTO;

import java.util.Date;



public class TareaDTO {

	private Integer id;


	private String fecha;

	private HabitacionDTO habitacion;

	private TipoTareaDTO tipo;

	private HotelDTO hotel;

	private Integer prioridad;

	private Integer completada;


	public TareaDTO() {
	}

	public TareaDTO(Integer id, String fecha, HabitacionDTO habitacion, TipoTareaDTO tipo, HotelDTO hotel, Integer prioridad, Integer completada) {
		this.id = id;
		this.fecha = fecha;
		this.habitacion = habitacion;
		this.tipo = tipo;
		this.hotel = hotel;
		this.prioridad = prioridad;
		this.completada = completada;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public HabitacionDTO getHabitacion() {
		return habitacion;
	}

	public void setHabitacion(HabitacionDTO habitacion) {
		this.habitacion = habitacion;
	}

	public TipoTareaDTO getTipo() {
		return tipo;
	}

	public void setTipo(TipoTareaDTO tipo) {
		this.tipo = tipo;
	}

	public HotelDTO getHotel() {
		return hotel;
	}

	public void setHotel(HotelDTO hotel) {
		this.hotel = hotel;
	}

	public Integer getPrioridad() {
		return prioridad;
	}

	public void setPrioridad(Integer prioridad) {
		this.prioridad = prioridad;
	}

	public Integer getCompletada() {
		return completada;
	}

	public void setCompletada(Integer completada) {
		this.completada = completada;
	}
}
