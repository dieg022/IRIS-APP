package com.nexwrfc.iris.iris.ClasesGenericas;

/**
 * Created by diego on 11/05/2018.
 */

public class FiltroBusqueda
{
    private Integer tipoTarea;
    private Integer tipoEstado;

    public FiltroBusqueda() {
    }

    public FiltroBusqueda(Integer tipoTarea, Integer tipoEstado) {
        this.tipoTarea = tipoTarea;
        this.tipoEstado = tipoEstado;
    }

    public Integer getTipoTarea() {
        return tipoTarea;
    }

    public void setTipoTarea(Integer tipoTarea) {
        this.tipoTarea = tipoTarea;
    }

    public Integer getTipoEstado() {
        return tipoEstado;
    }

    public void setTipoEstado(Integer tipoEstado) {
        this.tipoEstado = tipoEstado;
    }
    public void setTareaEstado(Integer tipoTarea,Integer tipoEstado){this.tipoTarea=tipoTarea;this.tipoEstado=tipoEstado;}
}
