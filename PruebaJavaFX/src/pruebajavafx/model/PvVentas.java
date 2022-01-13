/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pruebajavafx.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import pruebajavafx.dto.VentasDto;

/**
 *
 * @author PabloVE
 */
@Entity
@Table(name = "PV_VENTAS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PvVentas.findAll", query = "SELECT p FROM PvVentas p"),
    @NamedQuery(name = "PvVentas.findByVenId", query = "SELECT p FROM PvVentas p WHERE p.venId = :venId"),
    @NamedQuery(name = "PvVentas.findByVenFecha", query = "SELECT p FROM PvVentas p WHERE p.venFecha = :venFecha"),
    @NamedQuery(name = "PvVentas.findByVenPrecioTotal", query = "SELECT p FROM PvVentas p WHERE p.venPrecioTotal = :venPrecioTotal"),
    @NamedQuery(name = "PvVentas.findByVenCliente", query = "SELECT p FROM PvVentas p WHERE p.venCliente = :venCliente")})
public class PvVentas implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @Column(name = "VEN_ID")
    private BigDecimal venId;
    @Basic(optional = false)
    @Column(name = "VEN_FECHA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date venFecha;
    @Basic(optional = false)
    @Column(name = "VEN_PRECIO_TOTAL")
    private BigInteger venPrecioTotal;
    @Basic(optional = false)
    @Column(name = "VEN_CLIENTE")
    private String venCliente;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "detVenta")
    private Collection<PvDetalles> pvDetallesCollection;

    public PvVentas() {
    }

    public PvVentas(BigDecimal venId) {
        this.venId = venId;
    }
    
    public PvVentas(VentasDto venta) {
        if(venta.getVenId()>0){
            this.venId = BigDecimal.valueOf(venta.getVenId());
        }
        this.venCliente = venta.getVenCliente();
        this.venFecha = venta.getVenFecha();
        this.venPrecioTotal = BigDecimal.valueOf(venta.getVenPrecioTotal()).toBigInteger();
    }

    public PvVentas(BigDecimal venId, Date venFecha, BigInteger venPrecioTotal, String venCliente) {
        this.venId = venId;
        this.venFecha = venFecha;
        this.venPrecioTotal = venPrecioTotal;
        this.venCliente = venCliente;
    }

    public BigDecimal getVenId() {
        return venId;
    }

    public void setVenId(BigDecimal venId) {
        this.venId = venId;
    }

    public Date getVenFecha() {
        return venFecha;
    }

    public void setVenFecha(Date venFecha) {
        this.venFecha = venFecha;
    }

    public BigInteger getVenPrecioTotal() {
        return venPrecioTotal;
    }

    public void setVenPrecioTotal(BigInteger venPrecioTotal) {
        this.venPrecioTotal = venPrecioTotal;
    }

    public String getVenCliente() {
        return venCliente;
    }

    public void setVenCliente(String venCliente) {
        this.venCliente = venCliente;
    }

    @XmlTransient
    public Collection<PvDetalles> getPvDetallesCollection() {
        return pvDetallesCollection;
    }

    public void setPvDetallesCollection(Collection<PvDetalles> pvDetallesCollection) {
        this.pvDetallesCollection = pvDetallesCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (venId != null ? venId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PvVentas)) {
            return false;
        }
        PvVentas other = (PvVentas) object;
        if ((this.venId == null && other.venId != null) || (this.venId != null && !this.venId.equals(other.venId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "pruebajavafx.model.PvVentas[ venId=" + venId + " ]";
    }
    
}
