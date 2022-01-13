/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pruebajavafx.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import pruebajavafx.dto.ProductosDto;
import pruebajavafx.dto.VentasDto;
import pruebajavafx.services.ProductosService;
import pruebajavafx.services.VentasService;

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
    private TableView<VentasDto> tvVentas;
    @FXML
    private ComboBox<ProductosDto> cbxProductos;
    
    private ProductosService productosService;
    private VentasService ventasService;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ventasService = new VentasService();
        productosService = new ProductosService();
        
        initCbxProductos();
        
        cargarTablaConTodosLosRegistros();
    }  
    
    public void initCbxProductos(){
        ArrayList<ProductosDto> productos = new ArrayList<ProductosDto>();
        productos = (ArrayList<ProductosDto>) (productosService.getProductosByNombre("")).getResultado("Productos");
        ObservableList items = FXCollections.observableArrayList(productos);
        cbxProductos.setItems(items);
    }
    
    public void cargarTablaConTodosLosRegistros(){
        ArrayList<VentasDto> ventas = new ArrayList<VentasDto>();
        ventas = (ArrayList<VentasDto>) (ventasService.getVentasByCliente("").getResultado("Ventas"));
        cargarTabla(ventas);
    }
    
    public void cargarTabla(ArrayList<VentasDto> ventas){
        tvVentas.getColumns().clear();
        if(!ventas.isEmpty()){
            
            ObservableList items = FXCollections.observableArrayList(ventas);
            
            TableColumn <VentasDto, SimpleStringProperty> colId = new TableColumn("Codigo");
            colId.setCellValueFactory(new PropertyValueFactory("venId"));
            
            TableColumn <VentasDto, SimpleStringProperty> colNombre = new TableColumn("Nombre del Cliente");
            colNombre.setCellValueFactory(new PropertyValueFactory("venCliente"));
            
            TableColumn <VentasDto, SimpleStringProperty> colCantidad = new TableColumn("Precio Total");
            colCantidad.setCellValueFactory(new PropertyValueFactory("venPrecioTotal"));
            
            TableColumn <VentasDto, SimpleStringProperty> colPrecio = new TableColumn("Fecha");
            colPrecio.setCellValueFactory(new PropertyValueFactory("venFecha"));
            
            tvVentas.getColumns().addAll(colId);
            tvVentas.getColumns().addAll(colNombre);
            tvVentas.getColumns().addAll(colCantidad);
            tvVentas.getColumns().addAll(colPrecio);
            
            tvVentas.setItems(items);
        }
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

    @FXML
    private void actBuscarCliente(KeyEvent event) {
        if(event.getCode() == KeyCode.ENTER){
            cargarTabla((ArrayList<VentasDto>) ventasService.getVentasByCliente(txtFilter.getText()).getResultado("Ventas"));
        }
    }
    
}
