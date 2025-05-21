package com.turismo.operadora.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Reserva {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

   private String cliente;
   private String tour;
   private String fecha;

   public Reserva() {}

    public Reserva(String cliente, String tour, String fecha) {
       this.cliente = cliente;
       this.tour = tour;
       this.fecha = fecha;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public String getTour() {
        return tour;
    }

    public void setTour(String tour) {
        this.tour = tour;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
}
