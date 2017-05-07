package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DiscoDuro.
 */
@Entity
@Table(name = "disco_duro")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class DiscoDuro implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "capacidad")
    private String capacidad;

    @Column(name = "tamano")
    private String tamano;

    @Column(name = "tiempo_acceso")
    private String tiempoAcceso;

    @Column(name = "tasa_transferencia")
    private String tasaTransferencia;

    @Column(name = "precio")
    private Double precio;

    @OneToMany(mappedBy = "discoDuro")
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

    public DiscoDuro nombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCapacidad() {
        return capacidad;
    }

    public DiscoDuro capacidad(String capacidad) {
        this.capacidad = capacidad;
        return this;
    }

    public void setCapacidad(String capacidad) {
        this.capacidad = capacidad;
    }

    public String getTamano() {
        return tamano;
    }

    public DiscoDuro tamano(String tamano) {
        this.tamano = tamano;
        return this;
    }

    public void setTamano(String tamano) {
        this.tamano = tamano;
    }

    public String getTiempoAcceso() {
        return tiempoAcceso;
    }

    public DiscoDuro tiempoAcceso(String tiempoAcceso) {
        this.tiempoAcceso = tiempoAcceso;
        return this;
    }

    public void setTiempoAcceso(String tiempoAcceso) {
        this.tiempoAcceso = tiempoAcceso;
    }

    public String getTasaTransferencia() {
        return tasaTransferencia;
    }

    public DiscoDuro tasaTransferencia(String tasaTransferencia) {
        this.tasaTransferencia = tasaTransferencia;
        return this;
    }

    public void setTasaTransferencia(String tasaTransferencia) {
        this.tasaTransferencia = tasaTransferencia;
    }

    public Double getPrecio() {
        return precio;
    }

    public DiscoDuro precio(Double precio) {
        this.precio = precio;
        return this;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public Set<Ordenador> getOrdenadors() {
        return ordenadors;
    }

    public DiscoDuro ordenadors(Set<Ordenador> ordenadors) {
        this.ordenadors = ordenadors;
        return this;
    }

    public DiscoDuro addOrdenador(Ordenador ordenador) {
        this.ordenadors.add(ordenador);
        ordenador.setDiscoDuro(this);
        return this;
    }

    public DiscoDuro removeOrdenador(Ordenador ordenador) {
        this.ordenadors.remove(ordenador);
        ordenador.setDiscoDuro(null);
        return this;
    }

    public void setOrdenadors(Set<Ordenador> ordenadors) {
        this.ordenadors = ordenadors;
    }

    public Fabricante getFabricante() {
        return fabricante;
    }

    public DiscoDuro fabricante(Fabricante fabricante) {
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
        DiscoDuro discoDuro = (DiscoDuro) o;
        if (discoDuro.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, discoDuro.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "DiscoDuro{" +
            "id=" + id +
            ", nombre='" + nombre + "'" +
            ", capacidad='" + capacidad + "'" +
            ", tamano='" + tamano + "'" +
            ", tiempoAcceso='" + tiempoAcceso + "'" +
            ", tasaTransferencia='" + tasaTransferencia + "'" +
            ", precio='" + precio + "'" +
            '}';
    }
}
