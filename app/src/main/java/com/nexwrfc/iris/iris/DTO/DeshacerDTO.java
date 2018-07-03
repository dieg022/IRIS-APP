package com.nexwrfc.iris.iris.DTO;

/**
 * Created by diego on 11/06/2018.
 */

public class DeshacerDTO
{
    private String deshacer;
    private Integer id;

    public DeshacerDTO() {
    }

    public DeshacerDTO(String deshacer) {
        this.deshacer = deshacer;
    }

    public DeshacerDTO(String deshacer, Integer id) {
        this.deshacer = deshacer;
        this.id = id;
    }

    public String getDeshacer() {
        return deshacer;
    }

    public void setDeshacer(String deshacer) {
        this.deshacer = deshacer;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
