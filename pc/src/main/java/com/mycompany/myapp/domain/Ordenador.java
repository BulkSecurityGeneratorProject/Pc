package com.mycompany.myapp.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Ordenador.
 */
@Entity
@Table(name = "ordenador")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Ordenador implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "observaciones")
    private String observaciones;

    @Column(name = "precio")
    private Double precio;

    @ManyToOne
    private Procesador procesador;

    @ManyToOne
    private DiscoDuro discoDuro;

    @ManyToOne
    private Ram memoria;

    @ManyToOne
    private Ssd ssd;

    @ManyToOne
    private Optico optico;

    @ManyToOne
    private Graficas graficas;

    @ManyToOne
    private Alimentacion alimentacion;

    @ManyToOne
    private Placa disipador;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public Ordenador observaciones(String observaciones) {
        this.observaciones = observaciones;
        return this;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public Double getPrecio() {
        return precio;
    }

    public Ordenador precio(Double precio) {
        this.precio = precio;
        return this;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public Procesador getProcesador() {
        return procesador;
    }

    public Ordenador procesador(Procesador procesador) {
        this.procesador = procesador;
        return this;
    }

    public void setProcesador(Procesador procesador) {
        this.procesador = procesador;
    }

    public DiscoDuro getDiscoDuro() {
        return discoDuro;
    }

    public Ordenador discoDuro(DiscoDuro discoDuro) {
        this.discoDuro = discoDuro;
        return this;
    }

    public void setDiscoDuro(DiscoDuro discoDuro) {
        this.discoDuro = discoDuro;
    }

    public Ram getMemoria() {
        return memoria;
    }

    public Ordenador memoria(Ram ram) {
        this.memoria = ram;
        return this;
    }

    public void setMemoria(Ram ram) {
        this.memoria = ram;
    }

    public Ssd getSsd() {
        return ssd;
    }

    public Ordenador ssd(Ssd ssd) {
        this.ssd = ssd;
        return this;
    }

    public void setSsd(Ssd ssd) {
        this.ssd = ssd;
    }

    public Optico getOptico() {
        return optico;
    }

    public Ordenador optico(Optico optico) {
        this.optico = optico;
        return this;
    }

    public void setOptico(Optico optico) {
        this.optico = optico;
    }

    public Graficas getGraficas() {
        return graficas;
    }

    public Ordenador graficas(Graficas graficas) {
        this.graficas = graficas;
        return this;
    }

    public void setGraficas(Graficas graficas) {
        this.graficas = graficas;
    }

    public Alimentacion getAlimentacion() {
        return alimentacion;
    }

    public Ordenador alimentacion(Alimentacion alimentacion) {
        this.alimentacion = alimentacion;
        return this;
    }

    public void setAlimentacion(Alimentacion alimentacion) {
        this.alimentacion = alimentacion;
    }

    public Placa getDisipador() {
        return disipador;
    }

    public Ordenador disipador(Placa placa) {
        this.disipador = placa;
        return this;
    }

    public void setDisipador(Placa placa) {
        this.disipador = placa;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Ordenador ordenador = (Ordenador) o;
        if (ordenador.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, ordenador.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Ordenador{" +
            "id=" + id +
            ", observaciones='" + observaciones + "'" +
            ", precio='" + precio + "'" +
            '}';
    }
}
