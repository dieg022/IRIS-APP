package com.nexwrfc.iris.iris.DTO;


import java.io.Serializable;

public class HotelDTO implements Serializable {

	
	private Integer id;
	private EmpresaDTO empresa;
	private String nombre;
	
	
	public HotelDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	public HotelDTO(Integer id, EmpresaDTO empresa, String nombre) {
		super();
		this.id = id;
		this.empresa = empresa;
		this.nombre = nombre;
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
	
	
	
}
