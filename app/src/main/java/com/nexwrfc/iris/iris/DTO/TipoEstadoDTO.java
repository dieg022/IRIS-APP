package com.nexwrfc.iris.iris.DTO;




public class TipoEstadoDTO {

	private Integer id;
	private String nombre;
	
	
	public TipoEstadoDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	public TipoEstadoDTO(Integer id, String nombre) {
		super();
		this.id = id;
		this.nombre = nombre;
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
	 * @return the nombre
	 */
	public String getNombre() {
		return nombre;
	}
	/**
	 * @param nombre the nombre to set
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	
}
