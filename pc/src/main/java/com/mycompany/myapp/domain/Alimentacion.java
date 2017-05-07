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
 * A Alimentacion.
 */
@Entity
@Table(name = "alimentacion")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Alimentacion implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "potencia")
    private String potencia;

    @Column(name = "flujo_aire")
    private String flujoAire;

    @Column(name = "conectores")
    private String conectores;

    @Column(name = "precio")
    private Double precio;

    @OneToMany(mappedBy = "alimentacion")
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

    public Alimentacion nombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPotencia() {
        return potencia;
    }

    public Alimentacion potencia(String potencia) {
        this.potencia = potencia;
        return this;
    }

    public void setPotencia(String potencia) {
        this.potencia = potencia;
    }

    public String getFlujoAire() {
        return flujoAire;
    }

    public Alimentacion flujoAire(String flujoAire) {
        this.flujoAire = flujoAire;
        return this;
    }

    public void setFlujoAire(String flujoAire) {
        this.flujoAire = flujoAire;
    }

    public String getConectores() {
        return conectores;
    }

    public Alimentacion conectores(String conectores) {
        this.conectores = conectores;
        return this;
    }

    public void setConectores(String conectores) {
        this.conectores = conectores;
    }

    public Double getPrecio() {
        return precio;
    }

    public Alimentacion precio(Double precio) {
        this.precio = precio;
        return this;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public Set<Ordenador> getOrdenadors() {
        return ordenadors;
    }

    public Alimentacion ordenadors(Set<Ordenador> ordenadors) {
        this.ordenadors = ordenadors;
        return this;
    }

    public Alimentacion addOrdenador(Ordenador ordenador) {
        this.ordenadors.add(ordenador);
        ordenador.setAlimentacion(this);
        return this;
    }

    public Alimentacion removeOrdenador(Ordenador ordenador) {
        this.ordenadors.remove(ordenador);
        ordenador.setAlimentacion(null);
        return this;
    }

    public void setOrdenadors(Set<Ordenador> ordenadors) {
        this.ordenadors = ordenadors;
    }

    public Fabricante getFabricante() {
        return fabricante;
    }

    public Alimentacion fabricante(Fabricante fabricante) {
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
        Alimentacion alimentacion = (Alimentacion) o;
        if (alimentacion.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, alimentacion.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Alimentacion{" +
            "id=" + id +
            ", nombre='" + nombre + "'" +
            ", potencia='" + potencia + "'" +
            ", flujoAire='" + flujoAire + "'" +
            ", conectores='" + conectores + "'" +
            ", precio='" + precio + "'" +
            '}';
    }
}
