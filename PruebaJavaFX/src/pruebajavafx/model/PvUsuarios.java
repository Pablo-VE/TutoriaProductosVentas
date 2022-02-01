/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pruebajavafx.model;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import pruebajavafx.dto.UsuariosDto;

/**
 *
 * @author PabloVE
 */
@Entity
@Table(name = "PV_USUARIOS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PvUsuarios.findAll", query = "SELECT p FROM PvUsuarios p"),
    @NamedQuery(name = "PvUsuarios.findByUsuId", query = "SELECT p FROM PvUsuarios p WHERE p.usuId = :usuId"),
    @NamedQuery(name = "PvUsuarios.findByUsuNombre", query = "SELECT p FROM PvUsuarios p WHERE p.usuNombre = :usuNombre"),
    @NamedQuery(name = "PvUsuarios.findByUsuUsuario", query = "SELECT p FROM PvUsuarios p WHERE p.usuUsuario = :usuUsuario"),
    @NamedQuery(name = "PvUsuarios.findByUsuContrasena", query = "SELECT p FROM PvUsuarios p WHERE p.usuContrasena = :usuContrasena"),
    @NamedQuery(name = "PvUsuarios.findByUsuRol", query = "SELECT p FROM PvUsuarios p WHERE p.usuRol = :usuRol")})
public class PvUsuarios implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @SequenceGenerator(name = "ID_PV_USUARIOS_GENERATOR", sequenceName = "SEC_PV_USUARIOS", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ID_PV_USUARIOS_GENERATOR") 
    @Basic(optional = false)
    @Column(name = "USU_ID")
    private BigDecimal usuId;
    @Column(name = "USU_NOMBRE")
    private String usuNombre;
    @Basic(optional = false)
    @Column(name = "USU_USUARIO")
    private String usuUsuario;
    @Basic(optional = false)
    @Column(name = "USU_CONTRASENA")
    private String usuContrasena;
    @Basic(optional = false)
    @Column(name = "USU_ROL")
    private String usuRol;

    public PvUsuarios() {
    }

    public PvUsuarios(BigDecimal usuId) {
        this.usuId = usuId;
    }
    
    public PvUsuarios(UsuariosDto usuario) {
        if(usuario.getUsuId() > 0){
            this.usuId = BigDecimal.valueOf(usuario.getUsuId());
        }
        Modificar(usuario);
    }
    
    public void Modificar(UsuariosDto usuario) {
        usuUsuario = usuario.getUsuUsuario();
        usuContrasena = usuario.getUsuContrasena();
        usuNombre = usuario.getUsuNombre();
        usuRol = usuario.getUsuRol();
    }

    public PvUsuarios(BigDecimal usuId, String usuUsuario, String usuContrasena, String usuRol) {
        this.usuId = usuId;
        this.usuUsuario = usuUsuario;
        this.usuContrasena = usuContrasena;
        this.usuRol = usuRol;
    }

    public BigDecimal getUsuId() {
        return usuId;
    }

    public void setUsuId(BigDecimal usuId) {
        this.usuId = usuId;
    }

    public String getUsuNombre() {
        return usuNombre;
    }

    public void setUsuNombre(String usuNombre) {
        this.usuNombre = usuNombre;
    }

    public String getUsuUsuario() {
        return usuUsuario;
    }

    public void setUsuUsuario(String usuUsuario) {
        this.usuUsuario = usuUsuario;
    }

    public String getUsuContrasena() {
        return usuContrasena;
    }

    public void setUsuContrasena(String usuContrasena) {
        this.usuContrasena = usuContrasena;
    }

    public String getUsuRol() {
        return usuRol;
    }

    public void setUsuRol(String usuRol) {
        this.usuRol = usuRol;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (usuId != null ? usuId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PvUsuarios)) {
            return false;
        }
        PvUsuarios other = (PvUsuarios) object;
        if ((this.usuId == null && other.usuId != null) || (this.usuId != null && !this.usuId.equals(other.usuId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "pruebajavafx.model.PvUsuarios[ usuId=" + usuId + " ]";
    }
    
}
