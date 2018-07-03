package com.nexwrfc.iris.iris.DTO;

import java.io.Serializable;

public class PerfilDTO implements Serializable {


	private Integer id;
	private String nombre_perfil;
	private NivelDTO nivel;
	
	public PerfilDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	public PerfilDTO(Integer id, String nombre_perfil,NivelDTO nivel) {
		super();
		this.id = id;
		this.nombre_perfil = nombre_perfil;
		this.nivel=nivel;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getNombre_perfil() {
		return nombre_perfil;
	}
	public void setNombre_perfil(String nombre_perfil) {
		this.nombre_perfil = nombre_perfil;
	}
	
	public NivelDTO getNivel() {
		return nivel;
	}
	public void setNivel(NivelDTO nivel) {
		this.nivel = nivel;
	}

	
	
}
