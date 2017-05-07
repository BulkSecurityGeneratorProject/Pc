package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import com.mycompany.myapp.domain.enumeration.Tipo;

import com.mycompany.myapp.domain.enumeration.Socket;

/**
 * A Placa.
 */
@Entity
@Table(name = "placa")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Placa implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre")
    private String nombre;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo")
    private Tipo tipo;

    @Column(name = "procesadores")
    private String procesadores;

    @Column(name = "memoria")
    private String memoria;

    @Column(name = "pci")
    private String pci;

    @Enumerated(EnumType.STRING)
    @Column(name = "socket")
    private Socket socket;

    @Column(name = "almacenamiento")
    private String almacenamiento;

    @Column(name = "bios")
    private String bios;

    @Column(name = "precio")
    private Double precio;

    @OneToMany(mappedBy = "disipador")
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

    public Placa nombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Tipo getTipo() {
        return tipo;
    }

    public Placa tipo(Tipo tipo) {
        this.tipo = tipo;
        return this;
    }

    public void setTipo(Tipo tipo) {
        this.tipo = tipo;
    }

    public String getProcesadores() {
        return procesadores;
    }

    public Placa procesadores(String procesadores) {
        this.procesadores = procesadores;
        return this;
    }

    public void setProcesadores(String procesadores) {
        this.procesadores = procesadores;
    }

    public String getMemoria() {
        return memoria;
    }

    public Placa memoria(String memoria) {
        this.memoria = memoria;
        return this;
    }

    public void setMemoria(String memoria) {
        this.memoria = memoria;
    }

    public String getPci() {
        return pci;
    }

    public Placa pci(String pci) {
        this.pci = pci;
        return this;
    }

    public void setPci(String pci) {
        this.pci = pci;
    }

    public Socket getSocket() {
        return socket;
    }

    public Placa socket(Socket socket) {
        this.socket = socket;
        return this;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public String getAlmacenamiento() {
        return almacenamiento;
    }

    public Placa almacenamiento(String almacenamiento) {
        this.almacenamiento = almacenamiento;
        return this;
    }

    public void setAlmacenamiento(String almacenamiento) {
        this.almacenamiento = almacenamiento;
    }

    public String getBios() {
        return bios;
    }

    public Placa bios(String bios) {
        this.bios = bios;
        return this;
    }

    public void setBios(String bios) {
        this.bios = bios;
    }

    public Double getPrecio() {
        return precio;
    }

    public Placa precio(Double precio) {
        this.precio = precio;
        return this;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public Set<Ordenador> getOrdenadors() {
        return ordenadors;
    }

    public Placa ordenadors(Set<Ordenador> ordenadors) {
        this.ordenadors = ordenadors;
        return this;
    }

    public Placa addOrdenador(Ordenador ordenador) {
        this.ordenadors.add(ordenador);
        ordenador.setDisipador(this);
        return this;
    }

    public Placa removeOrdenador(Ordenador ordenador) {
        this.ordenadors.remove(ordenador);
        ordenador.setDisipador(null);
        return this;
    }

    public void setOrdenadors(Set<Ordenador> ordenadors) {
        this.ordenadors = ordenadors;
    }

    public Fabricante getFabricante() {
        return fabricante;
    }

    public Placa fabricante(Fabricante fabricante) {
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
        Placa placa = (Placa) o;
        if (placa.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, placa.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Placa{" +
            "id=" + id +
            ", nombre='" + nombre + "'" +
            ", tipo='" + tipo + "'" +
            ", procesadores='" + procesadores + "'" +
            ", memoria='" + memoria + "'" +
            ", pci='" + pci + "'" +
            ", socket='" + socket + "'" +
            ", almacenamiento='" + almacenamiento + "'" +
            ", bios='" + bios + "'" +
            ", precio='" + precio + "'" +
            '}';
    }
}
