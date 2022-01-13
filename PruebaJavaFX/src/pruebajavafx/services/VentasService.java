/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pruebajavafx.services;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import pruebajavafx.dto.VentasDto;
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
    
    public Respuesta getVenta(String nombre, BigInteger precio, Date fecha){
        Query query = em.createQuery("Select v from PvVentas v where v.venCliente = :nombre and v.venPrecioTotal = :precio and v.venFecha = :fecha");
        query.setParameter("nombre", nombre);
        query.setParameter("precio", precio);
        query.setParameter("fecha", fecha);

        return new Respuesta(true, "", "", "Venta", new VentasDto((PvVentas) query.getSingleResult()));  
    }
    
    public Respuesta saveVenta(VentasDto ventaDto){
        try{
            et = em.getTransaction();
            et.begin();
            PvVentas ventaBd;
            if(ventaDto.getVenId() != null && ventaDto.getVenId() > 0){ //si el id es diferente de nulo
//                //editarlo
                ventaBd = em.find(PvVentas.class, BigDecimal.valueOf(ventaDto.getVenId()));
                if(ventaBd == null){
                    return new Respuesta(false, "No se pudo modificar el registro", "Id no existe en la base de datos");
                }
//                ventaBd.Modificar(ventaDto);
//                ventaBd = em.merge(ventaBd);
            }else{
                //agregarlo
                ventaBd = new PvVentas(ventaDto);
                em.persist(ventaBd);
            }
            et.commit();
            return new Respuesta(true, "", "", "Venta", new VentasDto(ventaBd));
        }catch(Exception ex){
            et.rollback();
            System.out.println(ex);
            return new Respuesta(false, "No se pudo guardar el registro", "");
        }
    }
}
