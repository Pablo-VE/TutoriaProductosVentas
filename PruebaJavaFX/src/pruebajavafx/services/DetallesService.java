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
import pruebajavafx.dto.DetallesDto;
import pruebajavafx.dto.ProductosDto;
import pruebajavafx.dto.VentasDto;
import pruebajavafx.model.PvDetalles;
import pruebajavafx.model.PvProductos;
import pruebajavafx.model.PvVentas;
import pruebajavafx.utils.EntityManagerHelper;
import pruebajavafx.utils.Respuesta;

/**
 *
 * @author PabloVE
 */
public class DetallesService {
    EntityManager em = EntityManagerHelper.getInstance().getManager();
    private EntityTransaction et;
    
    public Respuesta getDetallesByVenta(VentasDto venta){
        Query query = em.createQuery("select d from PvDetalles d where d.detVenta = :venta");
        query.setParameter("venta", new PvVentas(venta));
 
        ArrayList<DetallesDto> detalles = new ArrayList<DetallesDto>();

        if(query.getResultList().size() > 0){
            for(int i=0; i<query.getResultList().size(); i++){
                detalles.add(new DetallesDto((PvDetalles) query.getResultList().get(i)));
            }
        }
     
        return new Respuesta(true, "", "", "Detalles", detalles);
    }
    
    public Respuesta saveDetalle(DetallesDto detalleDto){
        try{
            et = em.getTransaction();
            et.begin();
            PvDetalles detalleBd;
            if(detalleDto.getDetId()!= null && detalleDto.getDetId() > 0){ //si el id es diferente de nulo
                //editarlo
                detalleBd = em.find(PvDetalles.class, BigDecimal.valueOf(detalleDto.getDetId()));
                if(detalleBd == null){
                    return new Respuesta(false, "No se pudo modificar el registro", "Id no existe en la base de datos");
                }
//                detalleBd.Modificar(detalleDto);
//                detalleBd = em.merge(detalleBd);
            }else{
                //agregarlo
                detalleBd = new PvDetalles(detalleDto);
                em.persist(detalleBd);
            }
            et.commit();
            return new Respuesta(true, "", "", "Detalle", new DetallesDto(detalleBd));
        }catch(Exception ex){
            et.rollback();
            System.out.println(ex);
            return new Respuesta(false, "No se pudo guardar el registro", "");
        }
    }
    
    public Respuesta saveArrayDetalles(ArrayList<DetallesDto> detalles){
        try{
            et = em.getTransaction();
            et.begin();
            
            for(int i=0; i<detalles.size(); i++){
                PvDetalles detalleBd;
                detalleBd = new PvDetalles(detalles.get(i));
                em.persist(detalleBd);
            }
            
            et.commit();
            return new Respuesta(true, "", "");
        }catch(Exception ex){
            et.rollback();
            System.out.println(ex);
            return new Respuesta(false, "No se pudo guardar el registro", "");
        }
    }
}
