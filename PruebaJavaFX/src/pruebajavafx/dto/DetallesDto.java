/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pruebajavafx.dto;

import javafx.beans.property.SimpleStringProperty;
import pruebajavafx.model.PvDetalles;

/**
 *
 * @author PabloVE
 */
public class DetallesDto {
    private SimpleStringProperty detId;
    private SimpleStringProperty detCantidad;
    private SimpleStringProperty detPrecio;
    private ProductosDto detProducto;
    private VentasDto detVenta;

    public DetallesDto() {
        detId = new SimpleStringProperty();
        detCantidad = new SimpleStringProperty();
        detPrecio = new SimpleStringProperty();
    }
    
    public DetallesDto(PvDetalles detalle){
        this();
        this.detId.set(String.valueOf(detalle.getDetId()));
        this.detCantidad.set(String.valueOf(detalle.getDetCantidad()));
        this.detPrecio.set(String.valueOf(detalle.getDetPrecio()));
//        this.detProducto = new ProductosDto(detalle.getDetProducto());
//        this.detVenta = new VentasDto(detalle.getDetVenta());
    }

    public Long getDetId() {
        return Long.valueOf(this.detId.get());
    }

    public void setDetId(Long detId) {
        this.detId.set(String.valueOf(detId));
    }

    public int getDetCantidad() {
        return Integer.valueOf(detCantidad.get());
    }

    public void setDetCantidad(int detCantidad) {
        this.detCantidad.set(String.valueOf(detCantidad));
    }

    public float getDetPrecio() {
        return Float.valueOf(detPrecio.get());
    }

    public void setDetPrecio(float detPrecio) {
        this.detPrecio.set(String.valueOf(detPrecio));
    }

    public ProductosDto getDetProducto() {
        return detProducto;
    }

    public void setDetProducto(ProductosDto detProducto) {
        this.detProducto = detProducto;
    }

    public VentasDto getDetVenta() {
        return detVenta;
    }

    public void setDetVenta(VentasDto detVenta) {
        this.detVenta = detVenta;
    }
    
    
    
    
}
