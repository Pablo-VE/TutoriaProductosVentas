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
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import pruebajavafx.dto.ProductosDto;

/**
 *
 * @author PabloVE
 */
@Entity
@Table(name = "PV_PRODUCTOS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PvProductos.findAll", query = "SELECT p FROM PvProductos p"),
    @NamedQuery(name = "PvProductos.findByProId", query = "SELECT p FROM PvProductos p WHERE p.proId = :proId"),
    @NamedQuery(name = "PvProductos.findByProNombre", query = "SELECT p FROM PvProductos p WHERE p.proNombre = :proNombre"),
    @NamedQuery(name = "PvProductos.findByProPrecio", query = "SELECT p FROM PvProductos p WHERE p.proPrecio = :proPrecio"),
    @NamedQuery(name = "PvProductos.findByProCantidad", query = "SELECT p FROM PvProductos p WHERE p.proCantidad = :proCantidad")})
public class PvProductos implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @SequenceGenerator(name = "ID_PV_PRODUCTOS_GENERATOR", sequenceName = "SEC_PV_PRODUCTOS", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ID_PV_PRODUCTOS_GENERATOR") 
    @Basic(optional = false)
    @Column(name = "PRO_ID")
    private BigDecimal proId;
    @Basic(optional = false)
    @Column(name = "PRO_NOMBRE")
    private String proNombre;
    @Basic(optional = false)
    @Column(name = "PRO_PRECIO")
    private BigInteger proPrecio;
    @Basic(optional = false)
    @Column(name = "PRO_CANTIDAD")
    private BigInteger proCantidad;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "detProducto")
    private Collection<PvDetalles> pvDetallesCollection;

    public PvProductos() {
    }

    public PvProductos(ProductosDto producto) {
        this.proId = BigDecimal.valueOf(producto.getProId());
        Modificar(producto);
    }
    
    public PvProductos(BigDecimal proId) {
        this.proId = proId;
    }

    public PvProductos(BigDecimal proId, String proNombre, BigInteger proPrecio, BigInteger proCantidad) {
        this.proId = proId;
        this.proNombre = proNombre;
        this.proPrecio = proPrecio;
        this.proCantidad = proCantidad;
    }

    public BigDecimal getProId() {
        return proId;
    }

    public void setProId(BigDecimal proId) {
        this.proId = proId;
    }

    public String getProNombre() {
        return proNombre;
    }

    public void setProNombre(String proNombre) {
        this.proNombre = proNombre;
    }

    public BigInteger getProPrecio() {
        return proPrecio;
    }

    public void setProPrecio(BigInteger proPrecio) {
        this.proPrecio = proPrecio;
    }

    public BigInteger getProCantidad() {
        return proCantidad;
    }

    public void setProCantidad(BigInteger proCantidad) {
        this.proCantidad = proCantidad;
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
        hash += (proId != null ? proId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PvProductos)) {
            return false;
        }
        PvProductos other = (PvProductos) object;
        if ((this.proId == null && other.proId != null) || (this.proId != null && !this.proId.equals(other.proId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "proId=" + proId + " - proNombre=" + proNombre + " - proPrecio=" + proPrecio + " - proCantidad=" + proCantidad;
    }
    
    public void Modificar(ProductosDto producto){
        proNombre = producto.getProNombre();
        proCantidad = BigInteger.valueOf(producto.getProCantidad());
        proPrecio = BigDecimal.valueOf(producto.getProPrecio()).toBigInteger();    
    }
    
}
