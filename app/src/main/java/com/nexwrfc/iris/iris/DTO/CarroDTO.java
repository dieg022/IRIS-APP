package com.nexwrfc.iris.iris.DTO;

public class CarroDTO {

	
	
	private Integer id;
	
	private HotelDTO hotel;
	
	private Integer numero;

	private Integer planta;
	
	public CarroDTO() {
		super();
	}

	public CarroDTO(Integer id, HotelDTO hotel, Integer numero) {
		this.id = id;
		this.hotel = hotel;
		this.numero = numero;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public HotelDTO getHotel() {
		return hotel;
	}

	public void setHotel(HotelDTO hotel) {
		this.hotel = hotel;
	}

	public Integer getNumero() {
		return numero;
	}

	public void setNumero(Integer numero) {
		this.numero = numero;
	}

	public Integer getPlanta() {
		return planta;
	}

	public void setPlanta(Integer planta) {
		this.planta = planta;
	}
}
