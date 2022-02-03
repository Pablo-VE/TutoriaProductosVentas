/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pruebajavafx.utils;

import java.io.FileInputStream;
import java.util.Optional;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import pruebajavafx.controller.UsuariosEditarController;
import pruebajavafx.controller.UsuariosViewController;
import pruebajavafx.dto.UsuariosDto;
import pruebajavafx.services.UsuariosService;

/**
 *
 * @author PabloVE
 */
public class UsuarioHBox extends HBox{
    
    private UsuariosDto usuario;
    private UsuariosViewController usuariosViewController;

    public UsuarioHBox(UsuariosDto usuario, UsuariosViewController usuariosViewController) {
        this.usuario = usuario;
        this.usuariosViewController = usuariosViewController;
        
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
            imgEditar.setOnMouseClicked( event -> {
                try{
                    EditarUsuario();
                }catch(Exception ex){
                }
            });
                    
            //-------------------------------------------------------------------------
            
            input = new FileInputStream("./src/pruebajavafx/resources/eliminar.png");
            image = new Image(input);
            ImageView imgEliminar = new ImageView(image);
            imgEliminar.setFitWidth(40);
            imgEliminar.setPreserveRatio(true);
            imgEliminar.setCursor(Cursor.HAND);
            
            imgEliminar.setOnMouseClicked( event -> {
                try{
                    EliminarUsuario();
                }catch(Exception ex){
                }
            });
            
            //-------------------------------------------------------------------------
            
            contenedorBotones.getChildren().add(imgEditar);
            contenedorBotones.getChildren().add(imgEliminar);
            
            this.getChildren().add(contenedorBotones);
            
        }catch(Exception ex){
            System.out.println(ex);
        }
    }
    
    private void EditarUsuario(){
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../views/UsuariosEditar.fxml"));
            Parent root = loader.load();
            UsuariosEditarController usuariosEditarController = loader.getController();
            
            usuariosEditarController.EstablecerModalidad("Editar");
            usuariosEditarController.setUsuarioEditar(usuario);
            usuariosEditarController.setUsuariosViewController(usuariosViewController);
                    
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("Editar" + " Usuario");
            stage.setResizable(false);
            stage.show();
        }catch(Exception e){
            System.out.println("Error: Utils - UsuarioHBox - EditarUsuario");
        }
    }
    
    private void EliminarUsuario(){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText(null);
        alert.setTitle("Eliminar Usuario");
        alert.setContentText("¿Esta seguro de ELIMINAR el usuario: "+usuario.getUsuNombre()+"?");
        Optional<ButtonType> action = alert.showAndWait();
        if (action.get() == ButtonType.OK) {
            UsuariosService usuarioService = new UsuariosService();
            Respuesta res = usuarioService.deleteUsuario(usuario.getUsuId());
            if(res.getEstado()){
                Mensaje.showAndWait(Alert.AlertType.INFORMATION, "Eliminar Usuario", "El usuario ha sido eliminado de manera exitosa");
                usuariosViewController.cargarDatos("");
            }else{
                Mensaje.showAndWait(Alert.AlertType.ERROR, "Eliminar Usuario", "Surgio un error al eliminar el usuario, intenta luego");
            }
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
