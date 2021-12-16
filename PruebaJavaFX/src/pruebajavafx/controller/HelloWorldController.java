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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import pruebajavafx.models.Usuario;
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
    private Label lblNombreUsuario;
    @FXML
    private AnchorPane apInicio;
    
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Usuario usuario = (Usuario) AppContext.getInstance().get("usuarioLogeado");
        lblNombreUsuario.setText(usuario.getNombre());
        // TODO
    }    

    @FXML
    private void actGoInicio(ActionEvent event) {
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
    }
    
}
