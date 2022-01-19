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
import pruebajavafx.dto.ProductosDto;
import pruebajavafx.dto.VentasDto;
import pruebajavafx.model.PvProductos;
import pruebajavafx.model.PvVentas;
import pruebajavafx.utils.EntityManagerHelper;
import pruebajavafx.utils.Respuesta;

/**
 *
 * @author PabloVE
 */
public class VentasService {
    
    EntityManager em = EntityManagerHelper.getInstance().getManager();
    private EntityTransaction et;
    
    public Respuesta getVentasByCliente(String cliente){
        Query query = em.createQuery("Select v from PvVentas v where upper(v.venCliente) like upper(:cliente)");
        query.setParameter("cliente", "%"+cliente+"%");
 
        ArrayList<VentasDto> ventas = new ArrayList<VentasDto>();

        if(query.getResultList().size() > 0){
            for(int i=0; i<query.getResultList().size(); i++){
                ventas.add(new VentasDto((PvVentas) query.getResultList().get(i)));
            }
        }
     
        return new Respuesta(true, "", "", "Ventas", ventas);
    }
    
    public Respuesta getVentasByProducto(ProductosDto producto){
        Query query = em.createQuery("select d.detVenta from PvDetalles d where d.detProducto = :producto");
        query.setParameter("producto", new PvProductos(producto));
 
        ArrayList<VentasDto> ventas = new ArrayList<VentasDto>();

        if(query.getResultList().size() > 0){
            for(int i=0; i<query.getResultList().size(); i++){
                ventas.add(new VentasDto((PvVentas) query.getResultList().get(i)));
            }
        }
     
        return new Respuesta(true, "", "", "Ventas", ventas);
    }
    
    public Respuesta getVentasByClienteAndProducto(String cliente, ProductosDto producto){
        
        Query query = em.createQuery("Select v from PvVentas v where upper(v.venCliente) like upper(:cliente) and v in (select d.detVenta from PvDetalles d where d.detProducto = :producto)");
        query.setParameter("cliente", "%"+cliente+"%");
        query.setParameter("producto", new PvProductos(producto));
        
        ArrayList<VentasDto> ventas = new ArrayList<VentasDto>();

        if(query.getResultList().size() > 0){
            for(int i=0; i<query.getResultList().size(); i++){
                ventas.add(new VentasDto((PvVentas) query.getResultList().get(i)));
            }
        }
     
        return new Respuesta(true, "", "", "Ventas", ventas);
    }
    
    public Respuesta saveVenta(VentasDto ventaDto){
        try{
            et = em.getTransaction();
            et.begin();
            PvVentas ventaBd;
            if(ventaDto.getVenId() != null && ventaDto.getVenId() > 0){ 
                ventaBd = em.find(PvVentas.class, BigDecimal.valueOf(ventaDto.getVenId()));
                if(ventaBd == null){
                    return new Respuesta(false, "No se pudo modificar el registro", "Id no existe en la base de datos");
                }
//                ventaBd.Modificar(ventaDto);
//                ventaBd = em.merge(ventaBd);
            }else{
                ventaBd = new PvVentas(ventaDto);
                em.persist(ventaBd);
                
            }
            et.commit();
            return new Respuesta(true, "", "", "Venta", new VentasDto(ventaBd));
        }catch(Exception ex){
            et.rollback();
            return new Respuesta(false, "No se pudo guardar el registro", "");
        }
    }
}
