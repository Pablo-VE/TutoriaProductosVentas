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
import pruebajavafx.dto.ProductosDto;
import pruebajavafx.services.ProductosService;
import pruebajavafx.utils.AppContext;
import pruebajavafx.utils.Mensaje;
import pruebajavafx.utils.Respuesta;

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

    private ProductosService productosService;
    
    private ProductosViewController productosViewController;
    
    private ProductosDto productoAEditar;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        productosService = new ProductosService();
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

    public void setProductoAEditar(ProductosDto productoAEditar) {
        this.productoAEditar = productoAEditar;
        txtNombre.setText(productoAEditar.getProNombre());
        txtPrecio.setText(String.valueOf(productoAEditar.getProPrecio()));
        txtCantidad.setText(String.valueOf(productoAEditar.getProCantidad()));
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
                ProductosDto pro = new ProductosDto();
                pro.setProId(Long.valueOf(-1));
                pro.setProNombre(txtNombre.getText());
                pro.setProCantidad(Integer.valueOf(txtCantidad.getText()));
                pro.setProPrecio(Float.valueOf(txtPrecio.getText()));
                Respuesta res = productosService.saveProducto(pro);
                if(res.getEstado()){
                    closeStage();
                    Mensaje.showAndWait(Alert.AlertType.INFORMATION, "Registro de Producto", "Se ha registrado el producto exitosamente");
                    productosViewController.cargarTablaConTodosLosRegistros();
                }else{
                    Mensaje.showAndWait(Alert.AlertType.INFORMATION, "Registro de Producto", "Ocurrio un error al registrar el producto");
                }
            }
        }else{
            if(txtNombre.getText().isEmpty() || txtCantidad.getText().isEmpty() || txtPrecio.getText().isEmpty()){
                Mensaje.showAndWait(Alert.AlertType.WARNING, "Opps", "Hay campos vacíos");
            }else{
                productoAEditar.setProNombre(txtNombre.getText());
                productoAEditar.setProCantidad(Integer.parseInt(txtCantidad.getText()));
                productoAEditar.setProPrecio(Float.parseFloat(txtPrecio.getText()));
                
                Respuesta res = productosService.saveProducto(productoAEditar);
                if(res.getEstado()){
                    productosViewController.cargarTablaConTodosLosRegistros();
                    closeStage();
                    Mensaje.showAndWait(Alert.AlertType.INFORMATION, "Registro de Producto", "Se ha editado el producto exitosamente"); 
                }else{
                    Mensaje.showAndWait(Alert.AlertType.INFORMATION, "Registro de Producto", "Ocurrio un error al registrar el producto");
                }
            }
        }
    }
    
    private void closeStage(){
        Stage stageActual =  (Stage) txtNombre.getScene().getWindow();
        stageActual.close();
    }
    
}
