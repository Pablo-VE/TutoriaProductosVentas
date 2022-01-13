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
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import pruebajavafx.dto.ProductosDto;

/**
 * FXML Controller class
 *
 * @author PabloVE
 */
public class VentasViewController implements Initializable {

    @FXML
    private TextField txtFilter;
    @FXML
    private Button btnAgregar;
    @FXML
    private TableView<?> tvVentas;
    @FXML
    private ComboBox<?> cbxProductos;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void actBuscar2(KeyEvent event) {
    }

    @FXML
    private void actAgregar(ActionEvent event) {
        abrirVentasEditar("Agregar");
    }

    @FXML
    private void actBuscarProductos(ActionEvent event) {
    }
    
    private void abrirVentasEditar(String modalidad/*, ProductosDto productoEditar*/){
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../views/VentasEditar.fxml"));
            Parent root = loader.load();
            VentasEditarController ventasEditarController = loader.getController();
            
            ventasEditarController.EstablecerModalidad(modalidad);
//            ventasEditarController.setVentasViewController(this);
//            if(modalidad.equals("Editar")){
//                productosEditarController.setProductoAEditar(productoEditar);
//            }
                    
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle(modalidad + " Venta");
            stage.setResizable(false);
            stage.show();
        }catch(Exception e){
            System.out.println("Error: Controller - VentasViewController - abrirVentasEditar");
        }
    }
    
}
