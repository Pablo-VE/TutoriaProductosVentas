/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pruebajavafx.dto;

import java.util.Date;
import javafx.beans.property.SimpleStringProperty;
import pruebajavafx.model.PvVentas;

/**
 *
 * @author PabloVE
 */
public class VentasDto {
    
    private SimpleStringProperty venId;
    private Date venFecha;
    private SimpleStringProperty venPrecioTotal;
    private SimpleStringProperty venCliente;

    public VentasDto() {
        venId = new SimpleStringProperty();
        venFecha = new Date();
        venPrecioTotal = new SimpleStringProperty();
        venCliente = new SimpleStringProperty();
    }

    public VentasDto(PvVentas venta) {
        this();
        this.venId.set(String.valueOf(venta.getVenId()));
        this.venPrecioTotal.set(String.valueOf(venta.getVenPrecioTotal()));
        this.venCliente.set(String.valueOf(venta.getVenCliente()));
        this.venFecha = venta.getVenFecha();
    }
    
    public Long getVenId() {
        return Long.valueOf(venId.get());
    }

    public void setVenId(Long venId) {
        this.venId.set(String.valueOf(venId));
    }

    public Date getVenFecha() {
        return this.venFecha;
    }
    
    public void setVenFecha(Date venFecha) {
        this.venFecha = venFecha;
    }

    public float getVenPrecioTotal() {
        return Float.valueOf(venPrecioTotal.get());
    }

    public void setVenPrecioTotal(float venPrecioTotal) {
        this.venPrecioTotal.set(String.valueOf(venPrecioTotal));
    }

    public String getVenCliente() {
        return venCliente.get();
    }

    public void setVenCliente(String venCliente) {
        this.venCliente.set(venCliente);
    }
    
    
    
    
}
