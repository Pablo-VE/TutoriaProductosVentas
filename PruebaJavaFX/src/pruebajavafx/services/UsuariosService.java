/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pruebajavafx.services;

import java.util.ArrayList;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import pruebajavafx.dto.UsuariosDto;
import pruebajavafx.model.PvUsuarios;
import pruebajavafx.utils.EntityManagerHelper;
import pruebajavafx.utils.Respuesta;

/**
 *
 * @author PabloVE
 */
public class UsuariosService {
    EntityManager em = EntityManagerHelper.getInstance().getManager();
    private EntityTransaction et;
    
    public Respuesta getUsuarioByNombreUsuario(String usuario, String contrasena){
        Query query = em.createQuery("Select u from PvUsuarios u where u.usuUsuario = :usuario and u.usuContrasena = :contrasena");
        query.setParameter("usuario", usuario);
        query.setParameter("contrasena", contrasena);
        try{
            return new Respuesta(true, "Inicio de sesion exitoso", "Bienvenid@", "Usuario", new UsuariosDto((PvUsuarios) query.getSingleResult()));
        }catch(Exception ex){
            return new Respuesta(false, "Opps", "Usuario o contraseña incorrecto");
        }
        
    }
    
    public Respuesta getUsuarioByNombreOrCorreo(String filtro){
        Query query = em.createQuery("Select u from PvUsuarios u where upper(u.usuNombre) like upper(:filtro) or upper(u.usuUsuario) like upper(:filtro)");
        query.setParameter("filtro", "%"+filtro+"%");
 
        ArrayList<UsuariosDto> usuarios = new ArrayList<UsuariosDto>();

        if(query.getResultList().size() > 0){
            for(int i=0; i<query.getResultList().size(); i++){
                usuarios.add(new UsuariosDto((PvUsuarios) query.getResultList().get(i)));
            }
        }
     
        return new Respuesta(true, "", "", "Usuarios", usuarios); 
    }
}
