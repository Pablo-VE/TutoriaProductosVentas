/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pruebajavafx.models;

/**
 *
 * @author Pablo-VE
 */
public class Usuario {
    private String usuario;
    private String nombre;
    private String password;
    private String rol;

    public Usuario() {
    }

    public Usuario(String usuario, String nombre, String password, String rol) {
        this.usuario = usuario;
        this.nombre = nombre;
        this.password = password;
        this.rol = rol;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }
    
    public boolean compareUser(String usuario, String password){
        if(this.usuario.equals(usuario)  && this.password.equals(password)){
            return true;
        }
        return false;
    }
}
