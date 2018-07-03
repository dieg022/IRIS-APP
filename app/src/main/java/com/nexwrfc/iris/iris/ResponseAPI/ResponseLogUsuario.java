package com.nexwrfc.iris.iris.ResponseAPI;

import com.nexwrfc.iris.iris.DTO.HotelDTO;
import com.nexwrfc.iris.iris.DTO.PerfilDTO;
import com.nexwrfc.iris.iris.DTO.UsuarioDTO;

import java.io.Serializable;

/**
 * Created by diego on 16/03/2018.
 */

public class ResponseLogUsuario implements Serializable {
    private UsuarioDTO usuario;
    private HotelDTO hotel;

    public ResponseLogUsuario()  {
    }

    public ResponseLogUsuario(UsuarioDTO usuario,HotelDTO hotel) {
        this.usuario = usuario;
        this.hotel = hotel;
    }

    public UsuarioDTO getUsuario() {
        return usuario;
    }

    public void setUsuario(UsuarioDTO usuario) {
        this.usuario = usuario;
    }



    public HotelDTO getHotel() {
        return hotel;
    }

    public void setHotel(HotelDTO hotel) {
        this.hotel = hotel;
    }
}
