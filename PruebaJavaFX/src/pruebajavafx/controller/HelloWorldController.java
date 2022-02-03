/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pruebajavafx.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import pruebajavafx.dto.UsuariosDto;
import pruebajavafx.utils.AppContext;

/**
 * FXML Controller class
 *
 * @author Pablo-VE
 */
public class HelloWorldController implements Initializable {

    @FXML
    private StackPane spContenedor;
    @FXML
    private Button btnInicio;
    @FXML
    private Button btnProductos;
    @FXML
    private Button btnVentas;
    @FXML
    private AnchorPane apInicio;
    @FXML
    private Button btnUsuarios;
    @FXML
    private MenuButton btnUsuarioLogeado;
    @FXML
    private MenuItem btnVerPerfil;
    @FXML
    private MenuItem btnCerrarSesion;
    
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        UsuariosDto usuario = (UsuariosDto) AppContext.getInstance().get("usuarioLogeado");
        btnUsuarioLogeado.setText(usuario.getUsuNombre());
        
        if(!usuario.getUsuRol().equals("A")){
            btnUsuarios.setDisable(true);
            btnUsuarios.setVisible(false);
        }
    }    

    @FXML
    private void actGoInicio(ActionEvent event) {
        spContenedor.getChildren().clear();
        spContenedor.getChildren().add(apInicio);
    }

    @FXML
    private void actGoProductos(ActionEvent event){
        try{
            Parent root = FXMLLoader.load(getClass().getResource("../views/ProductosView.fxml"));
            spContenedor.getChildren().clear();
            spContenedor.getChildren().add(root);
            spContenedor.getChildren().remove(apInicio);
        }catch(Exception e){
            System.out.println("Error: Controller - HelloWorldController - actGoProductos");
        }
    }

    @FXML
    private void actGoVentas(ActionEvent event) {
        try{
            Parent root = FXMLLoader.load(getClass().getResource("../views/VentasView.fxml"));
            spContenedor.getChildren().clear();
            spContenedor.getChildren().add(root);
            spContenedor.getChildren().remove(apInicio);
        }catch(Exception e){
            System.out.println("Error: Controller - HelloWorldController - actGoVentas");
        }
    }

    @FXML
    private void actGoUsuarios(ActionEvent event) {
        try{
            Parent root = FXMLLoader.load(getClass().getResource("../views/UsuariosView.fxml"));
            spContenedor.getChildren().clear();
            spContenedor.getChildren().add(root);
            spContenedor.getChildren().remove(apInicio);
        }catch(Exception e){
            System.out.println("Error: Controller - HelloWorldController - actGoUsuarios");
        }
    }

    @FXML
    private void actVerPerfil(ActionEvent event) {
        try{
            Parent root = FXMLLoader.load(getClass().getResource("../views/PerfilView.fxml"));
            spContenedor.getChildren().clear();
            spContenedor.getChildren().add(root);
            spContenedor.getChildren().remove(apInicio);
        }catch(Exception e){
            System.out.println("Error: Controller - HelloWorldController - actVerPerfil");
        }
    }

    @FXML
    private void actCerrarSesion(ActionEvent event) {
        try{
            
            Stage stageActual =  (Stage) btnUsuarioLogeado.getScene().getWindow();
            stageActual.close();
            
            Parent root = FXMLLoader.load(getClass().getResource("../views/LoginView.fxml"));
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("Supermecados Pali - Inicio");
            stage.setResizable(false);
            stage.show();
        }catch(Exception e){
            System.out.println("Error: Controller - HelloWorldController - actGoVentas");
        }
    }
    
}
