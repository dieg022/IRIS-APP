package com.nexwrfc.iris.iris.DTO;

import java.util.ArrayList;
import java.util.List;



public class ClienteDTO {

	private Integer id;
	private String nombre;
	private List<HotelDTO> hotel=new ArrayList<>();
	
	
	public ClienteDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	public ClienteDTO(Integer id, String nombre, List<HotelDTO> hotel) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.hotel = hotel;
	}
	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public List<HotelDTO> getHotel() {
		return hotel;
	}
	public void setHotel(List<HotelDTO> hotel) {
		this.hotel = hotel;
	}
	

	
	
	
}
