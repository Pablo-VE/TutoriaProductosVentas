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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import pruebajavafx.dto.Producto;
import pruebajavafx.services.ProductoService;
import pruebajavafx.utils.AppContext;
import pruebajavafx.utils.Mensaje;

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

    private ProductoService productoService;
    
    private ProductosViewController productosViewController;
    
    private Producto productoAEditar;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        productoService = new ProductoService();
    }  
    
    public void EstablecerModalidad(String modalidad){
        this.modalidad = modalidad;
        
        if(this.modalidad == "Agregar"){
            lblTitulo.setText("Agregar Producto");
        }else{
            lblTitulo.setText("Editar Producto");
        }
    }
    
    public void setProductosViewController(ProductosViewController controller){
        productosViewController = controller;
    }

    public void setProductoAEditar(Producto productoAEditar) {
        this.productoAEditar = productoAEditar;
        txtNombre.setText(productoAEditar.getNombre());
        txtPrecio.setText(String.valueOf(productoAEditar.getPrecio()));
        txtCantidad.setText(String.valueOf(productoAEditar.getCantidad()));
    }

    @FXML
    private void actCancelar(ActionEvent event) {
        closeStage();
    }

    @FXML
    private void actGuardar(ActionEvent event) {
        
        if(modalidad == "Agregar"){
            if(txtNombre.getText().isEmpty() || txtCantidad.getText().isEmpty() || txtPrecio.getText().isEmpty()){
                Mensaje.showAndWait(Alert.AlertType.WARNING, "Opps", "Hay campos vacíos");
            }else{
                boolean isCreated = productoService.create(txtNombre.getText(), Integer.parseInt(txtCantidad.getText()) , Float.parseFloat(txtPrecio.getText()));
                if(isCreated){
                    productosViewController.cargarTabla(productoService.getAll());
                    closeStage();
                    Mensaje.show(Alert.AlertType.INFORMATION, "Producto creado", "Producto "+txtNombre.getText()+" ha sido creado de manera exitosa");
                }else{
                    Mensaje.show(Alert.AlertType.ERROR, "Opps", "Error al crear producto");
                }
            }
        }else{
            if(txtNombre.getText().isEmpty() || txtCantidad.getText().isEmpty() || txtPrecio.getText().isEmpty()){
                Mensaje.showAndWait(Alert.AlertType.WARNING, "Opps", "Hay campos vacíos");
            }else{
                productoAEditar.setNombre(txtNombre.getText());
                productoAEditar.setCantidad(Integer.parseInt(txtCantidad.getText()));
                productoAEditar.setPrecio(Float.parseFloat(txtPrecio.getText()));
                boolean isEdited = productoService.edit(productoAEditar.getId(), productoAEditar);
                if(isEdited){
                    productosViewController.cargarTabla(productoService.getAll());
                    closeStage();
                    Mensaje.show(Alert.AlertType.INFORMATION, "Producto editado", "Producto "+txtNombre.getText()+" ha sido actualizado de manera exitosa");
                }else{
                    Mensaje.show(Alert.AlertType.ERROR, "Opps", "Error al editar producto");
                }
            }
        }
    }
    
    private void closeStage(){
        Stage stageActual =  (Stage) txtNombre.getScene().getWindow();
        stageActual.close();
    }
    
}
