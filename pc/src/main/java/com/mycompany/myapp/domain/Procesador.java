package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import com.mycompany.myapp.domain.enumeration.Socket;

/**
 * A Procesador.
 */
@Entity
@Table(name = "procesador")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Procesador implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre_procesador")
    private String nombreProcesador;

    @Column(name = "memoria")
    private String memoria;

    @Column(name = "nucleos")
    private Integer nucleos;

    @Column(name = "ghz")
    private String ghz;

    @Column(name = "velocidad")
    private String velocidad;

    @Enumerated(EnumType.STRING)
    @Column(name = "socket")
    private Socket socket;

    @Column(name = "precio")
    private Double precio;

    @OneToMany(mappedBy = "procesador")
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

    public String getNombreProcesador() {
        return nombreProcesador;
    }

    public Procesador nombreProcesador(String nombreProcesador) {
        this.nombreProcesador = nombreProcesador;
        return this;
    }

    public void setNombreProcesador(String nombreProcesador) {
        this.nombreProcesador = nombreProcesador;
    }

    public String getMemoria() {
        return memoria;
    }

    public Procesador memoria(String memoria) {
        this.memoria = memoria;
        return this;
    }

    public void setMemoria(String memoria) {
        this.memoria = memoria;
    }

    public Integer getNucleos() {
        return nucleos;
    }

    public Procesador nucleos(Integer nucleos) {
        this.nucleos = nucleos;
        return this;
    }

    public void setNucleos(Integer nucleos) {
        this.nucleos = nucleos;
    }

    public String getGhz() {
        return ghz;
    }

    public Procesador ghz(String ghz) {
        this.ghz = ghz;
        return this;
    }

    public void setGhz(String ghz) {
        this.ghz = ghz;
    }

    public String getVelocidad() {
        return velocidad;
    }

    public Procesador velocidad(String velocidad) {
        this.velocidad = velocidad;
        return this;
    }

    public void setVelocidad(String velocidad) {
        this.velocidad = velocidad;
    }

    public Socket getSocket() {
        return socket;
    }

    public Procesador socket(Socket socket) {
        this.socket = socket;
        return this;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public Double getPrecio() {
        return precio;
    }

    public Procesador precio(Double precio) {
        this.precio = precio;
        return this;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public Set<Ordenador> getOrdenadors() {
        return ordenadors;
    }

    public Procesador ordenadors(Set<Ordenador> ordenadors) {
        this.ordenadors = ordenadors;
        return this;
    }

    public Procesador addOrdenador(Ordenador ordenador) {
        this.ordenadors.add(ordenador);
        ordenador.setProcesador(this);
        return this;
    }

    public Procesador removeOrdenador(Ordenador ordenador) {
        this.ordenadors.remove(ordenador);
        ordenador.setProcesador(null);
        return this;
    }

    public void setOrdenadors(Set<Ordenador> ordenadors) {
        this.ordenadors = ordenadors;
    }

    public Fabricante getFabricante() {
        return fabricante;
    }

    public Procesador fabricante(Fabricante fabricante) {
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
        Procesador procesador = (Procesador) o;
        if (procesador.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, procesador.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Procesador{" +
            "id=" + id +
            ", nombreProcesador='" + nombreProcesador + "'" +
            ", memoria='" + memoria + "'" +
            ", nucleos='" + nucleos + "'" +
            ", ghz='" + ghz + "'" +
            ", velocidad='" + velocidad + "'" +
            ", socket='" + socket + "'" +
            ", precio='" + precio + "'" +
            '}';
    }
}
