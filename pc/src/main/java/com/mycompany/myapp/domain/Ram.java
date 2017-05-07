package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import com.mycompany.myapp.domain.enumeration.Zocalo;

/**
 * A Ram.
 */
@Entity
@Table(name = "ram")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Ram implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "capacidad")
    private String capacidad;

    @Column(name = "ancho_danda")
    private String anchoDanda;

    @Column(name = "frecuencia")
    private String frecuencia;

    @Column(name = "tiempo_acceso")
    private String tiempoAcceso;

    @Column(name = "latencia")
    private String latencia;

    @Column(name = "conectores")
    private String conectores;

    @Enumerated(EnumType.STRING)
    @Column(name = "zocalo")
    private Zocalo zocalo;

    @Column(name = "precio")
    private Double precio;

    @OneToMany(mappedBy = "memoria")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Ordenador> ordenadors = new HashSet<>();

    @ManyToOne
    private Fabricante fabricante;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public Ram nombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCapacidad() {
        return capacidad;
    }

    public Ram capacidad(String capacidad) {
        this.capacidad = capacidad;
        return this;
    }

    public void setCapacidad(String capacidad) {
        this.capacidad = capacidad;
    }

    public String getAnchoDanda() {
        return anchoDanda;
    }

    public Ram anchoDanda(String anchoDanda) {
        this.anchoDanda = anchoDanda;
        return this;
    }

    public void setAnchoDanda(String anchoDanda) {
        this.anchoDanda = anchoDanda;
    }

    public String getFrecuencia() {
        return frecuencia;
    }

    public Ram frecuencia(String frecuencia) {
        this.frecuencia = frecuencia;
        return this;
    }

    public void setFrecuencia(String frecuencia) {
        this.frecuencia = frecuencia;
    }

    public String getTiempoAcceso() {
        return tiempoAcceso;
    }

    public Ram tiempoAcceso(String tiempoAcceso) {
        this.tiempoAcceso = tiempoAcceso;
        return this;
    }

    public void setTiempoAcceso(String tiempoAcceso) {
        this.tiempoAcceso = tiempoAcceso;
    }

    public String getLatencia() {
        return latencia;
    }

    public Ram latencia(String latencia) {
        this.latencia = latencia;
        return this;
    }

    public void setLatencia(String latencia) {
        this.latencia = latencia;
    }

    public String getConectores() {
        return conectores;
    }

    public Ram conectores(String conectores) {
        this.conectores = conectores;
        return this;
    }

    public void setConectores(String conectores) {
        this.conectores = conectores;
    }

    public Zocalo getZocalo() {
        return zocalo;
    }

    public Ram zocalo(Zocalo zocalo) {
        this.zocalo = zocalo;
        return this;
    }

    public void setZocalo(Zocalo zocalo) {
        this.zocalo = zocalo;
    }

    public Double getPrecio() {
        return precio;
    }

    public Ram precio(Double precio) {
        this.precio = precio;
        return this;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public Set<Ordenador> getOrdenadors() {
        return ordenadors;
    }

    public Ram ordenadors(Set<Ordenador> ordenadors) {
        this.ordenadors = ordenadors;
        return this;
    }

    public Ram addOrdenador(Ordenador ordenador) {
        this.ordenadors.add(ordenador);
        ordenador.setMemoria(this);
        return this;
    }

    public Ram removeOrdenador(Ordenador ordenador) {
        this.ordenadors.remove(ordenador);
        ordenador.setMemoria(null);
        return this;
    }

    public void setOrdenadors(Set<Ordenador> ordenadors) {
        this.ordenadors = ordenadors;
    }

    public Fabricante getFabricante() {
        return fabricante;
    }

    public Ram fabricante(Fabricante fabricante) {
        this.fabricante = fabricante;
        return this;
    }

    public void setFabricante(Fabricante fabricante) {
        this.fabricante = fabricante;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Ram ram = (Ram) o;
        if (ram.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, ram.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Ram{" +
            "id=" + id +
            ", nombre='" + nombre + "'" +
            ", capacidad='" + capacidad + "'" +
            ", anchoDanda='" + anchoDanda + "'" +
            ", frecuencia='" + frecuencia + "'" +
            ", tiempoAcceso='" + tiempoAcceso + "'" +
            ", latencia='" + latencia + "'" +
            ", conectores='" + conectores + "'" +
            ", zocalo='" + zocalo + "'" +
            ", precio='" + precio + "'" +
            '}';
    }
}
