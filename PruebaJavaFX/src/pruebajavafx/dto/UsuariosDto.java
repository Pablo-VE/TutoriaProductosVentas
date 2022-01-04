/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pruebajavafx.dto;

import javafx.beans.property.SimpleStringProperty;
import pruebajavafx.model.PvUsuarios;

/**
 *
 * @author PabloVE
 */
public class UsuariosDto {
    public SimpleStringProperty usuId;
    public SimpleStringProperty usuNombre;
    public SimpleStringProperty usuUsuario;
    public SimpleStringProperty usuContrasena;
    public SimpleStringProperty usuRol;

    public UsuariosDto() {
        this.usuId = new SimpleStringProperty();
        this.usuNombre = new SimpleStringProperty();
        this.usuUsuario = new SimpleStringProperty();
        this.usuContrasena = new SimpleStringProperty();
        this.usuRol = new SimpleStringProperty();
    }

    public UsuariosDto(Long usuId, String usuNombre, String usuUsuario, String usuContrasena, String usuRol) {
        this();
        this.usuId.set(usuId.toString());
        this.usuNombre.set(usuNombre);
        this.usuUsuario.set(usuUsuario);
        this.usuContrasena.set(usuContrasena);
        this.usuRol.set(usuRol);
    }
    
    public UsuariosDto(PvUsuarios usuario){
        this();
        usuId.set(usuario.getUsuId().toString());
        usuNombre.set(usuario.getUsuNombre());
        usuUsuario.set(usuario.getUsuUsuario());
        usuContrasena.set(usuario.getUsuContrasena());
        usuRol.set(usuario.getUsuRol());
    }

    public Long getUsuId() {
        return Long.valueOf(usuId.get());
    }

    public void setUsuId(Long usuId) {
        this.usuId.set(usuId.toString());
    }

    public String getUsuNombre() {
        return usuNombre.get();
    }

    public void setUsuNombre(String usuNombre) {
        this.usuNombre.set(usuNombre);
    }

    public String getUsuUsuario() {
        return usuUsuario.get();
    }

    public void setUsuUsuario(String usuUsuario) {
        this.usuUsuario.set(usuUsuario);
    }

    public String getUsuContrasena() {
        return usuContrasena.get();
    }

    public void setUsuContrasena(String usuContrasena) {
        this.usuContrasena.set(usuContrasena);
    }

    public String getUsuRol() {
        return usuRol.get();
    }

    public void setUsuRol(String usuRol) {
        this.usuRol.set(usuRol);
    }
    
    public String toString(){
        return "Usuario: " + this.usuUsuario.get() + " - Nombre: " + this.usuNombre.get();
    }
    
}
