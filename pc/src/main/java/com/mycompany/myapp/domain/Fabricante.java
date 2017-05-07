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
 * A Fabricante.
 */
@Entity
@Table(name = "fabricante")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Fabricante implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre")
    private String nombre;

    @OneToMany(mappedBy = "fabricante")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<DiscoDuro> discoDuros = new HashSet<>();

    @OneToMany(mappedBy = "fabricante")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Ram> rams = new HashSet<>();

    @OneToMany(mappedBy = "fabricante")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Graficas> graficas = new HashSet<>();

    @OneToMany(mappedBy = "fabricante")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Alimentacion> alimentacions = new HashSet<>();

    @OneToMany(mappedBy = "fabricante")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Ssd> ssds = new HashSet<>();

    @OneToMany(mappedBy = "fabricante")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Optico> opticos = new HashSet<>();

    @OneToMany(mappedBy = "fabricante")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Placa> placas = new HashSet<>();

    @OneToMany(mappedBy = "fabricante")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Procesador> procesadors = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public Fabricante nombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Set<DiscoDuro> getDiscoDuros() {
        return discoDuros;
    }

    public Fabricante discoDuros(Set<DiscoDuro> discoDuros) {
        this.discoDuros = discoDuros;
        return this;
    }

    public Fabricante addDiscoDuro(DiscoDuro discoDuro) {
        this.discoDuros.add(discoDuro);
        discoDuro.setFabricante(this);
        return this;
    }

    public Fabricante removeDiscoDuro(DiscoDuro discoDuro) {
        this.discoDuros.remove(discoDuro);
        discoDuro.setFabricante(null);
        return this;
    }

    public void setDiscoDuros(Set<DiscoDuro> discoDuros) {
        this.discoDuros = discoDuros;
    }

    public Set<Ram> getRams() {
        return rams;
    }

    public Fabricante rams(Set<Ram> rams) {
        this.rams = rams;
        return this;
    }

    public Fabricante addRam(Ram ram) {
        this.rams.add(ram);
        ram.setFabricante(this);
        return this;
    }

    public Fabricante removeRam(Ram ram) {
        this.rams.remove(ram);
        ram.setFabricante(null);
        return this;
    }

    public void setRams(Set<Ram> rams) {
        this.rams = rams;
    }

    public Set<Graficas> getGraficas() {
        return graficas;
    }

    public Fabricante graficas(Set<Graficas> graficas) {
        this.graficas = graficas;
        return this;
    }

    public Fabricante addGraficas(Graficas graficas) {
        this.graficas.add(graficas);
        graficas.setFabricante(this);
        return this;
    }

    public Fabricante removeGraficas(Graficas graficas) {
        this.graficas.remove(graficas);
        graficas.setFabricante(null);
        return this;
    }

    public void setGraficas(Set<Graficas> graficas) {
        this.graficas = graficas;
    }

    public Set<Alimentacion> getAlimentacions() {
        return alimentacions;
    }

    public Fabricante alimentacions(Set<Alimentacion> alimentacions) {
        this.alimentacions = alimentacions;
        return this;
    }

    public Fabricante addAlimentacion(Alimentacion alimentacion) {
        this.alimentacions.add(alimentacion);
        alimentacion.setFabricante(this);
        return this;
    }

    public Fabricante removeAlimentacion(Alimentacion alimentacion) {
        this.alimentacions.remove(alimentacion);
        alimentacion.setFabricante(null);
        return this;
    }

    public void setAlimentacions(Set<Alimentacion> alimentacions) {
        this.alimentacions = alimentacions;
    }

    public Set<Ssd> getSsds() {
        return ssds;
    }

    public Fabricante ssds(Set<Ssd> ssds) {
        this.ssds = ssds;
        return this;
    }

    public Fabricante addSsd(Ssd ssd) {
        this.ssds.add(ssd);
        ssd.setFabricante(this);
        return this;
    }

    public Fabricante removeSsd(Ssd ssd) {
        this.ssds.remove(ssd);
        ssd.setFabricante(null);
        return this;
    }

    public void setSsds(Set<Ssd> ssds) {
        this.ssds = ssds;
    }

    public Set<Optico> getOpticos() {
        return opticos;
    }

    public Fabricante opticos(Set<Optico> opticos) {
        this.opticos = opticos;
        return this;
    }

    public Fabricante addOptico(Optico optico) {
        this.opticos.add(optico);
        optico.setFabricante(this);
        return this;
    }

    public Fabricante removeOptico(Optico optico) {
        this.opticos.remove(optico);
        optico.setFabricante(null);
        return this;
    }

    public void setOpticos(Set<Optico> opticos) {
        this.opticos = opticos;
    }

    public Set<Placa> getPlacas() {
        return placas;
    }

    public Fabricante placas(Set<Placa> placas) {
        this.placas = placas;
        return this;
    }

    public Fabricante addPlaca(Placa placa) {
        this.placas.add(placa);
        placa.setFabricante(this);
        return this;
    }

    public Fabricante removePlaca(Placa placa) {
        this.placas.remove(placa);
        placa.setFabricante(null);
        return this;
    }

    public void setPlacas(Set<Placa> placas) {
        this.placas = placas;
    }

    public Set<Procesador> getProcesadors() {
        return procesadors;
    }

    public Fabricante procesadors(Set<Procesador> procesadors) {
        this.procesadors = procesadors;
        return this;
    }

    public Fabricante addProcesador(Procesador procesador) {
        this.procesadors.add(procesador);
        procesador.setFabricante(this);
        return this;
    }

    public Fabricante removeProcesador(Procesador procesador) {
        this.procesadors.remove(procesador);
        procesador.setFabricante(null);
        return this;
    }

    public void setProcesadors(Set<Procesador> procesadors) {
        this.procesadors = procesadors;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Fabricante fabricante = (Fabricante) o;
        if (fabricante.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, fabricante.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Fabricante{" +
            "id=" + id +
            ", nombre='" + nombre + "'" +
            '}';
    }
}
