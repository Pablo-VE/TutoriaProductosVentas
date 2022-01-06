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
import pruebajavafx.dto.ProductosDto;
import pruebajavafx.model.PvProductos;
import pruebajavafx.utils.EntityManagerHelper;
import pruebajavafx.utils.Respuesta;

/**
 *
 * @author PabloVE
 */
public class ProductosService {
    EntityManager em = EntityManagerHelper.getInstance().getManager();
    private EntityTransaction et;
    
    public Respuesta getProductosByNombre(String nombre){
        Query query = em.createQuery("Select p from PvProductos p where upper(p.proNombre) like upper(:nombre)");
        query.setParameter("nombre", "%"+nombre+"%");
 
        ArrayList<ProductosDto> productos = new ArrayList<ProductosDto>();

        if(query.getResultList().size() > 0){
            for(int i=0; i<query.getResultList().size(); i++){
                productos.add(new ProductosDto((PvProductos) query.getResultList().get(i)));
            }
        }
     
        return new Respuesta(true, "", "", "Productos", productos);  
    }
    
}
