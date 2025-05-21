package com.turismo.operadora.dtos;

public class ReservaEvent {

    private String accion;
    private ReservaRequest datos;

    public ReservaEvent(){}

    public ReservaEvent(String accion, ReservaRequest datos) {
        this.accion = accion;
        this.datos = datos;
    }

    public String getAccion() {
        return accion;
    }

    public void setAccion(String accion) {
        this.accion = accion;
    }

    public ReservaRequest getDatos() {
        return datos;
    }

    public void setDatos(ReservaRequest datos) {
        this.datos = datos;
    }
}
