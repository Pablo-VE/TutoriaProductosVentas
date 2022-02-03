/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pruebajavafx.services;

import java.math.BigDecimal;
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
    
    public Respuesta saveUsuario(UsuariosDto usuarioDto){
        try{
            et = em.getTransaction();
            et.begin();
            PvUsuarios usuarioBd;
            if(usuarioDto.getUsuId() != null && usuarioDto.getUsuId() > 0){
                usuarioBd = em.find(PvUsuarios.class, BigDecimal.valueOf(usuarioDto.getUsuId()));
                if(usuarioBd == null){
                    return new Respuesta(false, "No se pudo modificar el registro", "Id no existe en la base de datos");
                }
                usuarioBd.Modificar(usuarioDto);
                usuarioBd = em.merge(usuarioBd);
            }else{
                usuarioBd = new PvUsuarios(usuarioDto);
                em.persist(usuarioBd);
            }
            et.commit();
            return new Respuesta(true, "", "", "Usuario", new UsuariosDto(usuarioBd));
        }catch(Exception ex){
            et.rollback();
            System.out.println(ex);
            return new Respuesta(false, "No se pudo guardar el registro", "");
        }
    }
    
    public Respuesta deleteUsuario(Long id){
        try{
            et = em.getTransaction();
            et.begin();
            PvUsuarios usuarioBd;
            if(id != null && id > 0){ 
                usuarioBd = em.find(PvUsuarios.class, BigDecimal.valueOf(id));
                if(usuarioBd == null){
                    et.rollback();
                    return new Respuesta(false, "Eliminar registro", "No se encontro el producto a eliminar");
                }
                em.remove(usuarioBd);
            }else{
                et.rollback();
                return new Respuesta(false, "Eliminar registro", "Debe cargar el producto a eliminar");
            }
            et.commit();
            return new Respuesta(true, "", "");
        }catch(Exception ex){
            et.rollback();
            System.out.println(ex);
            return new Respuesta(false, "No se pudo eliminar el registro", "");
        }
    }
}
