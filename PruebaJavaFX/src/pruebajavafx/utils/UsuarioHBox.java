/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pruebajavafx.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import pruebajavafx.dto.UsuariosDto;

/**
 *
 * @author PabloVE
 */
public class UsuarioHBox extends HBox{
    
    private UsuariosDto usuario;

    public UsuarioHBox(UsuariosDto usuario) {
        this.usuario = usuario;
        
        this.setStyle(HBoxStyles());
        this.setPrefHeight(80);
        
        agregarNombre();
        agregarCorreo();
        agregarBotones();
    }
    
    
    // Componentes
    
    private void agregarNombre(){
        
        try{
            HBox contenedorPerfil = new HBox();
            contenedorPerfil.setAlignment(Pos.CENTER_LEFT);
            contenedorPerfil.setStyle(HBoxSpacingStyles());
            
            FileInputStream input = new FileInputStream("./src/pruebajavafx/resources/nombre.png");
            Image image = new Image(input);
            ImageView imgNombre = new ImageView(image);

            VBox contenedorNombreRol = new VBox();
            Label lbNombre = new Label(usuario.getUsuNombre());
            lbNombre.setPrefWidth(250);
            lbNombre.setStyle(NombreStyles());
            Label lbRol = new Label();
            if(usuario.getUsuRol().equals("A")){
                lbRol.setText("Administrador");
            }else{
                lbRol.setText("Cajero");
            }
            lbRol.setPrefWidth(250);
            lbRol.setStyle(AdminStyles());
            contenedorNombreRol.setStyle(VBoxNombreRolStyles());
            contenedorNombreRol.getChildren().add(lbNombre);
            contenedorNombreRol.getChildren().add(lbRol);

            contenedorPerfil.getChildren().add(imgNombre);
            contenedorPerfil.getChildren().add(contenedorNombreRol);

            this.getChildren().add(contenedorPerfil);
        }catch(Exception ex){
            System.out.println(ex);
        }
                
            
    }
    
    private void agregarCorreo(){
        try{
            HBox contenedorCorreo = new HBox();
            contenedorCorreo.setAlignment(Pos.CENTER_LEFT);
            contenedorCorreo.setStyle(HBoxSpacingStyles());
            
            FileInputStream input = new FileInputStream("./src/pruebajavafx/resources/correo.png");
            Image image = new Image(input);
            ImageView imgCorreo = new ImageView(image);
            imgCorreo.setFitWidth(30);
            imgCorreo.setPreserveRatio(true);

            Label lbCorreo = new Label(usuario.getUsuUsuario());
            lbCorreo.setPrefWidth(300);
            lbCorreo.setStyle(CorreoStyles());

            contenedorCorreo.getChildren().add(imgCorreo);
            contenedorCorreo.getChildren().add(lbCorreo);

            this.getChildren().add(contenedorCorreo);
        }catch(Exception ex){
            System.out.println(ex);
        }
    }
    
    private void agregarBotones(){
        try{
            HBox contenedorBotones = new HBox();
            contenedorBotones.setSpacing(20);
            contenedorBotones.setAlignment(Pos.CENTER_RIGHT);
            
            FileInputStream input = new FileInputStream("./src/pruebajavafx/resources/editar.png");
            Image image = new Image(input);
            ImageView imgEditar = new ImageView(image);
            imgEditar.setFitWidth(35);
            imgEditar.setPreserveRatio(true);
            imgEditar.setCursor(Cursor.HAND);
            
            input = new FileInputStream("./src/pruebajavafx/resources/eliminar.png");
            image = new Image(input);
            ImageView imgEliminar = new ImageView(image);
            imgEliminar.setFitWidth(40);
            imgEliminar.setPreserveRatio(true);
            imgEliminar.setCursor(Cursor.HAND);
            
            contenedorBotones.getChildren().add(imgEditar);
            contenedorBotones.getChildren().add(imgEliminar);
            
            this.getChildren().add(contenedorBotones);
            
        }catch(Exception ex){
            System.out.println(ex);
        }
    }
    
    
    
    
    // Estilos de Componentes
    
    private String HBoxStyles(){
        return  "  -fx-background-color: #fffdde;" +
                "  -fx-padding: 15;" + 
                "  -fx-spacing: 20;" +
                "  -fx-border-radius: 20 0 0 20;" +
                "  -fx-background-radius: 20 0 0 20;";
    }
    
    private String HBoxSpacingStyles(){
        return   "  -fx-spacing: 10;";
    }
    
    
    private String VBoxNombreRolStyles(){
        return   "  -fx-spacing: 5;";
    }
    
    private String NombreStyles(){
        return  "   -fx-font-size: 15;" +
                "   -fx-font-weight: bold;";
    }
    
    private String AdminStyles(){
        return  "   -fx-font-size: 14;";
    }
    
    private String CorreoStyles(){
        return  "   -fx-font-size: 13;" +
                "   -fx-font-weight: bold;";
    }
    
    
    
}
