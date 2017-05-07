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
 * A Optico.
 */
@Entity
@Table(name = "optico")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Optico implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "velocidad_escritura")
    private String velocidadEscritura;

    @Column(name = "velocidad_lectura")
    private String velocidadLectura;

    @Column(name = "precio")
    private Double precio;

    @OneToMany(mappedBy = "optico")
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

    public Optico nombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getVelocidadEscritura() {
        return velocidadEscritura;
    }

    public Optico velocidadEscritura(String velocidadEscritura) {
        this.velocidadEscritura = velocidadEscritura;
        return this;
    }

    public void setVelocidadEscritura(String velocidadEscritura) {
        this.velocidadEscritura = velocidadEscritura;
    }

    public String getVelocidadLectura() {
        return velocidadLectura;
    }

    public Optico velocidadLectura(String velocidadLectura) {
        this.velocidadLectura = velocidadLectura;
        return this;
    }

    public void setVelocidadLectura(String velocidadLectura) {
        this.velocidadLectura = velocidadLectura;
    }

    public Double getPrecio() {
        return precio;
    }

    public Optico precio(Double precio) {
        this.precio = precio;
        return this;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public Set<Ordenador> getOrdenadors() {
        return ordenadors;
    }

    public Optico ordenadors(Set<Ordenador> ordenadors) {
        this.ordenadors = ordenadors;
        return this;
    }

    public Optico addOrdenador(Ordenador ordenador) {
        this.ordenadors.add(ordenador);
        ordenador.setOptico(this);
        return this;
    }

    public Optico removeOrdenador(Ordenador ordenador) {
        this.ordenadors.remove(ordenador);
        ordenador.setOptico(null);
        return this;
    }

    public void setOrdenadors(Set<Ordenador> ordenadors) {
        this.ordenadors = ordenadors;
    }

    public Fabricante getFabricante() {
        return fabricante;
    }

    public Optico fabricante(Fabricante fabricante) {
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
        Optico optico = (Optico) o;
        if (optico.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, optico.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Optico{" +
            "id=" + id +
            ", nombre='" + nombre + "'" +
            ", velocidadEscritura='" + velocidadEscritura + "'" +
            ", velocidadLectura='" + velocidadLectura + "'" +
            ", precio='" + precio + "'" +
            '}';
    }
}
