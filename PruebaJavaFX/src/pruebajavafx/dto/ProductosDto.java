/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pruebajavafx.dto;

import javafx.beans.property.SimpleStringProperty;
import pruebajavafx.model.PvProductos;

/**
 *
 * @author PabloVE
 */
public class ProductosDto {
    private SimpleStringProperty proId;
    private SimpleStringProperty proNombre;
    private SimpleStringProperty proPrecio;
    private SimpleStringProperty proCantidad;

    public ProductosDto() {
        this.proId = new SimpleStringProperty();
        this.proNombre = new SimpleStringProperty();
        this.proPrecio = new SimpleStringProperty();
        this.proCantidad = new SimpleStringProperty();
    }

    public ProductosDto(Long proId, String proNombre, Float proPrecio, int proCantidad) {
        this.proId.set(String.valueOf(proId));
        this.proNombre.set(proNombre);
        this.proPrecio.set(String.valueOf(proPrecio));
        this.proCantidad.set(String.valueOf(proCantidad));
    }
    
    public ProductosDto(PvProductos producto) {
        this();
        this.proId.set(String.valueOf(producto.getProId()));
        this.proNombre.set(producto.getProNombre());
        this.proPrecio.set(String.valueOf(producto.getProPrecio()));
        this.proCantidad.set(String.valueOf(producto.getProCantidad()));
    }

    public Long getProId() {
        return Long.valueOf(this.proId.get());
    }

    public void setProId(Long proId) {
        this.proId.set(String.valueOf(proId));
    }

    public String getProNombre() {
        return proNombre.get();
    }

    public void setProNombre(String proNombre) {
        this.proNombre.set(proNombre);
    }

    public Float getProPrecio() {
        return Float.valueOf(proPrecio.get());
    }

    public void setProPrecio(Float proPrecio) {
        this.proPrecio.set(String.valueOf(proPrecio));
    }

    public int getProCantidad() {
        return Integer.valueOf(proCantidad.get());
    }

    public void setProCantidad(int proCantidad) {
        this.proCantidad.set(String.valueOf(proCantidad));
    }
    
    @Override
    public String toString() {
        return "proId=" + proId + " - proNombre=" + proNombre + " - proPrecio=" + proPrecio + " - proCantidad=" + proCantidad;
    }
    
    
}
