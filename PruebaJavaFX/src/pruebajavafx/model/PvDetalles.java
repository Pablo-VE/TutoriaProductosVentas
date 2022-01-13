/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pruebajavafx.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import pruebajavafx.dto.DetallesDto;

/**
 *
 * @author PabloVE
 */
@Entity
@Table(name = "PV_DETALLES")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PvDetalles.findAll", query = "SELECT p FROM PvDetalles p"),
    @NamedQuery(name = "PvDetalles.findByDetId", query = "SELECT p FROM PvDetalles p WHERE p.detId = :detId"),
    @NamedQuery(name = "PvDetalles.findByDetCantidad", query = "SELECT p FROM PvDetalles p WHERE p.detCantidad = :detCantidad"),
    @NamedQuery(name = "PvDetalles.findByDetPrecio", query = "SELECT p FROM PvDetalles p WHERE p.detPrecio = :detPrecio")})
public class PvDetalles implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @SequenceGenerator(name = "ID_PV_DETALLES_GENERATOR", sequenceName = "SEC_PV_DETALLES", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ID_PV_DETALLES_GENERATOR") 
    @Basic(optional = false)
    @Column(name = "DET_ID")
    private BigDecimal detId;
    @Basic(optional = false)
    @Column(name = "DET_CANTIDAD")
    private BigInteger detCantidad;
    @Basic(optional = false)
    @Column(name = "DET_PRECIO")
    private BigInteger detPrecio;
    @JoinColumn(name = "DET_PRODUCTO", referencedColumnName = "PRO_ID")
    @ManyToOne(optional = false)
    private PvProductos detProducto;
    @JoinColumn(name = "DET_VENTA", referencedColumnName = "VEN_ID")
    @ManyToOne(optional = false)
    private PvVentas detVenta;

    public PvDetalles() {
    }

    public PvDetalles(BigDecimal detId) {
        this.detId = detId;
    }

    public PvDetalles(BigDecimal detId, BigInteger detCantidad, BigInteger detPrecio) {
        this.detId = detId;
        this.detCantidad = detCantidad;
        this.detPrecio = detPrecio;
    }
    
    public PvDetalles(DetallesDto detalle) {
        if(detalle.getDetId() > 0){
            this.detId = BigDecimal.valueOf(detalle.getDetId());
        }
        Modificar(detalle);
    }

    public BigDecimal getDetId() {
        return detId;
    }

    public void setDetId(BigDecimal detId) {
        this.detId = detId;
    }

    public BigInteger getDetCantidad() {
        return detCantidad;
    }

    public void setDetCantidad(BigInteger detCantidad) {
        this.detCantidad = detCantidad;
    }

    public BigInteger getDetPrecio() {
        return detPrecio;
    }

    public void setDetPrecio(BigInteger detPrecio) {
        this.detPrecio = detPrecio;
    }

    public PvProductos getDetProducto() {
        return detProducto;
    }

    public void setDetProducto(PvProductos detProducto) {
        this.detProducto = detProducto;
    }

    public PvVentas getDetVenta() {
        return detVenta;
    }

    public void setDetVenta(PvVentas detVenta) {
        this.detVenta = detVenta;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (detId != null ? detId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PvDetalles)) {
            return false;
        }
        PvDetalles other = (PvDetalles) object;
        if ((this.detId == null && other.detId != null) || (this.detId != null && !this.detId.equals(other.detId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "pruebajavafx.model.PvDetalles[ detId=" + detId + " ]";
    }
    
    public void Modificar(DetallesDto detalle){
        this.detCantidad = BigInteger.valueOf(detalle.getDetCantidad());
        this.detPrecio = BigDecimal.valueOf(detalle.getDetPrecio()).toBigInteger();
        this.detProducto = new PvProductos(detalle.getDetProducto());
        this.detVenta = new PvVentas(detalle.getDetVenta());
    }
    
}
