package com.nexwrfc.iris.iris.DTO;



public class ClienteHotelDTO {


	private HotelDTO hotel;
	private ClienteDTO cliente;
	
	public ClienteHotelDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ClienteHotelDTO(HotelDTO hotel, ClienteDTO cliente) {
		super();
		this.hotel = hotel;
		this.cliente = cliente;
	}


	public HotelDTO getHotel() {
		return hotel;
	}

	public void setHotel(HotelDTO hotel) {
		this.hotel = hotel;
	}

	public ClienteDTO getCliente() {
		return cliente;
	}

	public void setCliente(ClienteDTO cliente) {
		this.cliente = cliente;
	}
	
	
	
	
}
