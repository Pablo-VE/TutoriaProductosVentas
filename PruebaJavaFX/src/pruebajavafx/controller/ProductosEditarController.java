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
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import pruebajavafx.utils.AppContext;

/**
 * FXML Controller class
 *
 * @author PabloVE
 */
public class ProductosEditarController implements Initializable {

    @FXML
    private TextField txtNombre;
    @FXML
    private TextField txtPrecio;
    @FXML
    private TextField txtCantidad;
    @FXML
    private Button btnCancelar;
    @FXML
    private Button btnGuardar;
    @FXML
    private Label lblTitulo;
    
    private String modalidad;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
//        modalidad = (String) AppContext.getInstance().get("ModalidadProductos");
//        
//        if(modalidad == "Agregar"){
//            lblTitulo.setText("Agregar Producto");
//        }else{
//            lblTitulo.setText("Editar Producto");
//        }
    }  
    
    public void EstablecerModalidad(String modalidad){
        this.modalidad = modalidad;
        
        if(this.modalidad == "Agregar"){
            lblTitulo.setText("Agregar Producto");
        }else{
            lblTitulo.setText("Editar Producto");
        }
    }

    @FXML
    private void actCancelar(ActionEvent event) {
    }

    @FXML
    private void actGuardar(ActionEvent event) {
        
        if(modalidad == "Agregar"){
            //llamar metodo bd crear
        }else{
            //llamar metodo bd editar
        }
    }
    
}
