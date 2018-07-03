package com.nexwrfc.iris.iris.DTO;

import java.io.Serializable;

public class NivelDTO implements Serializable{

	
	private Integer id;
	private String nombre_nivel;
	
	public NivelDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	public NivelDTO(Integer id, String nombre_nivel) {
		super();
		this.id = id;
		this.nombre_nivel = nombre_nivel;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getNombre_nivel() {
		return nombre_nivel;
	}
	public void setNombre_nivel(String nombre_nivel) {
		this.nombre_nivel = nombre_nivel;
	}
	
	
}
