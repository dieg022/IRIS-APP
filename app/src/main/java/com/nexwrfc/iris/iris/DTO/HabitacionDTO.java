package com.nexwrfc.iris.iris.DTO;



public class HabitacionDTO {


	private Integer id;
	private HotelDTO hotel;
	private Integer numero;
	private TipoHabitacionDTO tipo;
	private String nombre;
	public HabitacionDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	public HabitacionDTO(Integer id, HotelDTO hotel, Integer numero, TipoHabitacionDTO tipo, String nombre) {
		super();
		this.id = id;
		this.hotel = hotel;
		this.numero = numero;
		this.tipo = tipo;
		this.nombre = nombre;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id_habitacion) {
		this.id = id_habitacion;
	}
	public HotelDTO getHotel() {
		return hotel;
	}
	public void setHotel(HotelDTO hotel2) {
		this.hotel = hotel2;
	}
	public Integer getNumero() {
		return numero;
	}
	public void setNumero(Integer numero) {
		this.numero = numero;
	}
	public TipoHabitacionDTO getTipo() {
		return tipo;
	}
	public void setTipo(TipoHabitacionDTO tipo) {
		this.tipo = tipo;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	
	
	
}
