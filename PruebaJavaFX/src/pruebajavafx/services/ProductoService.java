/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pruebajavafx.services;

import java.util.ArrayList;
import pruebajavafx.models.Producto;
import pruebajavafx.utils.AppContext;

/**
 *
 * @author PabloVE
 */
public class ProductoService {
    
    public void initializeProductos(){
        ArrayList<Producto> productos = new ArrayList<Producto>();
        productos.add(new Producto(1, "Coca Cola 355ml", 500, 16));
        productos.add(new Producto(2, "Tampico 350ml", 450, 23));
        productos.add(new Producto(3, "Nutella Grande", 4000, 6));
        productos.add(new Producto(4, "Galleta Maria", 830, 9));
        productos.add(new Producto(5, "Galleta Soda", 950, 11));
        AppContext.getInstance().set("productos", productos); 
    }
    public boolean create(String nombre, int cantidad, float precio){
        try{
            ArrayList<Producto> productos = (ArrayList<Producto>) AppContext.getInstance().get("productos");
            int lastId = productos.get(productos.size()-1).getId();
            Producto productoNuevo = new Producto(lastId+1, nombre, precio, cantidad);
            productos.add(productoNuevo);
            AppContext.getInstance().set("productos", productos); 
            System.out.println(productos);
        }catch(Exception ex){
            return false;
        }
        return true;
    }
    
    public ArrayList<Producto> getAll(){
        try{
            ArrayList<Producto> productos = (ArrayList<Producto>) AppContext.getInstance().get("productos");
            return productos;
        }catch(Exception ex){
            return null;
        }
    }
    
    public ArrayList<Producto> getByNombre(String nombre){
        try{
            ArrayList<Producto> productos = (ArrayList<Producto>) AppContext.getInstance().get("productos");
            
            if(nombre.isEmpty()){
                return productos;
            }else{
                nombre = nombre.toUpperCase();
                ArrayList<Producto> productosReturn = new ArrayList<Producto>();

                for(int i=0; i<productos.size(); i++){
                    if(productos.get(i).getNombre().toUpperCase().contains(nombre)){
                        productosReturn.add(productos.get(i));
                    }
                }
                return productosReturn;
            }
            
        }catch(Exception ex){
            return null;
        }
    }
    
    public boolean edit(int id, Producto productoEditar){
        ArrayList<Producto> productos = (ArrayList<Producto>) AppContext.getInstance().get("productos");
        for(int i=0; i<productos.size(); i++){
            if(productos.get(i).getId() == id){
                productos.set(i, productoEditar);
                AppContext.getInstance().set("productos", productos); 
                return true;
            }
        }
        return false;
    }
    
    public boolean delete(int id){
        try{
            ArrayList<Producto> productos = (ArrayList<Producto>) AppContext.getInstance().get("productos");
            for(int i=0; i<productos.size(); i++){
                if(productos.get(i).getId() == id){
                    productos.remove(i);
                    AppContext.getInstance().set("productos", productos); 
                    return true;
                }
            }
            return false;
        }catch(Exception ex){
            return false;
        }
    }
    
}
