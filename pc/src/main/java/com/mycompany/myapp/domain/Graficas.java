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
 * A Graficas.
 */
@Entity
@Table(name = "graficas")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Graficas implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "bus")
    private String bus;

    @Column(name = "salidas")
    private String salidas;

    @Column(name = "refrigeracion")
    private String refrigeracion;

    @Column(name = "compatibilidad")
    private String compatibilidad;

    @Column(name = "chipset")
    private String chipset;

    @Column(name = "valocidad")
    private String valocidad;

    @Column(name = "memoria")
    private String memoria;

    @Column(name = "ramdac")
    private String ramdac;

    @Column(name = "stream_procesor")
    private String streamProcesor;

    @Column(name = "shader_clock")
    private String shaderClock;

    @Column(name = "pixel_rate")
    private String pixelRate;

    @Column(name = "shader_model")
    private String shaderModel;

    @Column(name = "precio")
    private Double precio;

    @OneToMany(mappedBy = "graficas")
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

    public Graficas nombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getBus() {
        return bus;
    }

    public Graficas bus(String bus) {
        this.bus = bus;
        return this;
    }

    public void setBus(String bus) {
        this.bus = bus;
    }

    public String getSalidas() {
        return salidas;
    }

    public Graficas salidas(String salidas) {
        this.salidas = salidas;
        return this;
    }

    public void setSalidas(String salidas) {
        this.salidas = salidas;
    }

    public String getRefrigeracion() {
        return refrigeracion;
    }

    public Graficas refrigeracion(String refrigeracion) {
        this.refrigeracion = refrigeracion;
        return this;
    }

    public void setRefrigeracion(String refrigeracion) {
        this.refrigeracion = refrigeracion;
    }

    public String getCompatibilidad() {
        return compatibilidad;
    }

    public Graficas compatibilidad(String compatibilidad) {
        this.compatibilidad = compatibilidad;
        return this;
    }

    public void setCompatibilidad(String compatibilidad) {
        this.compatibilidad = compatibilidad;
    }

    public String getChipset() {
        return chipset;
    }

    public Graficas chipset(String chipset) {
        this.chipset = chipset;
        return this;
    }

    public void setChipset(String chipset) {
        this.chipset = chipset;
    }

    public String getValocidad() {
        return valocidad;
    }

    public Graficas valocidad(String valocidad) {
        this.valocidad = valocidad;
        return this;
    }

    public void setValocidad(String valocidad) {
        this.valocidad = valocidad;
    }

    public String getMemoria() {
        return memoria;
    }

    public Graficas memoria(String memoria) {
        this.memoria = memoria;
        return this;
    }

    public void setMemoria(String memoria) {
        this.memoria = memoria;
    }

    public String getRamdac() {
        return ramdac;
    }

    public Graficas ramdac(String ramdac) {
        this.ramdac = ramdac;
        return this;
    }

    public void setRamdac(String ramdac) {
        this.ramdac = ramdac;
    }

    public String getStreamProcesor() {
        return streamProcesor;
    }

    public Graficas streamProcesor(String streamProcesor) {
        this.streamProcesor = streamProcesor;
        return this;
    }

    public void setStreamProcesor(String streamProcesor) {
        this.streamProcesor = streamProcesor;
    }

    public String getShaderClock() {
        return shaderClock;
    }

    public Graficas shaderClock(String shaderClock) {
        this.shaderClock = shaderClock;
        return this;
    }

    public void setShaderClock(String shaderClock) {
        this.shaderClock = shaderClock;
    }

    public String getPixelRate() {
        return pixelRate;
    }

    public Graficas pixelRate(String pixelRate) {
        this.pixelRate = pixelRate;
        return this;
    }

    public void setPixelRate(String pixelRate) {
        this.pixelRate = pixelRate;
    }

    public String getShaderModel() {
        return shaderModel;
    }

    public Graficas shaderModel(String shaderModel) {
        this.shaderModel = shaderModel;
        return this;
    }

    public void setShaderModel(String shaderModel) {
        this.shaderModel = shaderModel;
    }

    public Double getPrecio() {
        return precio;
    }

    public Graficas precio(Double precio) {
        this.precio = precio;
        return this;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public Set<Ordenador> getOrdenadors() {
        return ordenadors;
    }

    public Graficas ordenadors(Set<Ordenador> ordenadors) {
        this.ordenadors = ordenadors;
        return this;
    }

    public Graficas addOrdenador(Ordenador ordenador) {
        this.ordenadors.add(ordenador);
        ordenador.setGraficas(this);
        return this;
    }

    public Graficas removeOrdenador(Ordenador ordenador) {
        this.ordenadors.remove(ordenador);
        ordenador.setGraficas(null);
        return this;
    }

    public void setOrdenadors(Set<Ordenador> ordenadors) {
        this.ordenadors = ordenadors;
    }

    public Fabricante getFabricante() {
        return fabricante;
    }

    public Graficas fabricante(Fabricante fabricante) {
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
        Graficas graficas = (Graficas) o;
        if (graficas.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, graficas.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Graficas{" +
            "id=" + id +
            ", nombre='" + nombre + "'" +
            ", bus='" + bus + "'" +
            ", salidas='" + salidas + "'" +
            ", refrigeracion='" + refrigeracion + "'" +
            ", compatibilidad='" + compatibilidad + "'" +
            ", chipset='" + chipset + "'" +
            ", valocidad='" + valocidad + "'" +
            ", memoria='" + memoria + "'" +
            ", ramdac='" + ramdac + "'" +
            ", streamProcesor='" + streamProcesor + "'" +
            ", shaderClock='" + shaderClock + "'" +
            ", pixelRate='" + pixelRate + "'" +
            ", shaderModel='" + shaderModel + "'" +
            ", precio='" + precio + "'" +
            '}';
    }
}
