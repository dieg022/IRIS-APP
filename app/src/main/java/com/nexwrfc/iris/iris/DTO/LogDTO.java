package com.nexwrfc.iris.iris.DTO;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;



public class LogDTO {

	

	private Integer idHotel;

	private Integer idUsuario;
	private List<DatosFicheroDTO> datosJson=new ArrayList<DatosFicheroDTO>();

	
	private Date fechaHora;

	public LogDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public LogDTO(Integer idHotel, Integer idUsuario, List<DatosFicheroDTO> datosJson, Date fechaHora) {
		super();
		this.idHotel = idHotel;
		this.idUsuario = idUsuario;
		this.datosJson = datosJson;

	}

	/**
	 * @return the idHotel
	 */
	public Integer getIdHotel() {
		return idHotel;
	}

	/**
	 * @param idHotel the idHotel to set
	 */
	public void setIdHotel(Integer idHotel) {
		this.idHotel = idHotel;
	}

	/**
	 * @return the idUsuario
	 */
	public Integer getIdUsuario() {
		return idUsuario;
	}

	/**
	 * @param idUsuario the idUsuario to set
	 */
	public void setIdUsuario(Integer idUsuario) {
		this.idUsuario = idUsuario;
	}

	/**
	 * @return the datosJson
	 */
	public List<DatosFicheroDTO> getDatos() {
		return datosJson;
	}

	/**
	 * @param datosJson the datosJson to set
	 */
	public void setDatos(List<DatosFicheroDTO> datosJson) {
		this.datosJson = datosJson;
	}



	/**
	 * @return the fechaHora
	 */
	public Date getFechaHora() {
		return fechaHora;
	}

	/**
	 * @param fechaHora the fechaHora to set
	 */
	public void setFechaHora(Date fechaHora) {
		this.fechaHora = fechaHora;
	}

	
	
	
	
	
}
