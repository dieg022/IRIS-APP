package com.nexwrfc.iris.iris.DTO;

public class TipoTareaDTO {

	
	private Integer id;
	private String nombre;
	public TipoTareaDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	public TipoTareaDTO(Integer id, String nombre) {
		super();
		this.id = id;
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
