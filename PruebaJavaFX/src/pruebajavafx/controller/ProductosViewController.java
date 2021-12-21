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
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import pruebajavafx.utils.AppContext;

/**
 * FXML Controller class
 *
 * @author Pablo-VE
 */
public class ProductosViewController implements Initializable {

    @FXML
    private AnchorPane apProductos;
    @FXML
    private TextField txtFilter;
    @FXML
    private Button btnBuscar;
    @FXML
    private Button btnAgregar;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void actBuscar(ActionEvent event) {
    }

    @FXML
    private void actAgregar(ActionEvent event) {
        try{
//            AppContext.getInstance().set("ModalidadProductos", "Agregar");
            
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../views/ProductosEditar.fxml"));
            Parent root = loader.load();
            ProductosEditarController productosController = loader.getController();
            productosController.EstablecerModalidad("Agregar");
                    
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("Producto");
            stage.setResizable(false);
            stage.show();
        }catch(Exception e){
            System.out.println("Error: Controller - ProductosViewController - actAgregar");
        }
        
    }
    
}
