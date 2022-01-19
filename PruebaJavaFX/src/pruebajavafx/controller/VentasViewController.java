/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pruebajavafx.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.Callback;
import pruebajavafx.dto.ProductosDto;
import pruebajavafx.dto.VentasDto;
import pruebajavafx.services.ProductosService;
import pruebajavafx.services.VentasService;
import pruebajavafx.utils.Helpers;

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
    @FXML
    private Label lbLimpiar;
    @FXML
    private Button btnAmbosFiltros;

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
            
            TableColumn <VentasDto, String> colFecha = new TableColumn("Fecha");
            colFecha.setCellValueFactory(obj -> {
                return new ReadOnlyStringWrapper(Helpers.dateToString(obj.getValue().getVenFecha())); 
            });
            
            
            tvVentas.getColumns().addAll(colId);
            tvVentas.getColumns().addAll(colNombre);
            tvVentas.getColumns().addAll(colCantidad);
            tvVentas.getColumns().addAll(colFecha);
            
            addButtonToTable();
            
            tvVentas.setItems(items);
        }
    }
    
    private void addButtonToTable() {
        TableColumn<VentasDto, Void> colBtn = new TableColumn("Acciones");

        Callback<TableColumn<VentasDto, Void>, TableCell<VentasDto, Void>> cellFactory = new Callback<TableColumn<VentasDto, Void>, TableCell<VentasDto, Void>>() {
            @Override
            public TableCell<VentasDto, Void> call(final TableColumn<VentasDto, Void> param) {
                final TableCell<VentasDto, Void> cell = new TableCell<VentasDto, Void>() {
                    private final Button btn = new Button("Ver");
                    {
                        btn.setOnAction((ActionEvent event) -> {
                            try{
                                abrirVentasEditar("Ver", getTableView().getItems().get(getIndex()));
                            }catch(Exception ex){}
                        });
                    }
                    HBox pane = new HBox(btn);
                    {
                        pane.setAlignment(Pos.CENTER);
                    }
                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(pane);
                            
                        }
                    }
                };
                return cell;
            }
        };

        colBtn.setCellFactory(cellFactory);

        tvVentas.getColumns().add(colBtn);
    }


    @FXML
    private void actAgregar(ActionEvent event) {
        abrirVentasEditar("Agregar", null);
    }

    
    
    private void abrirVentasEditar(String modalidad, VentasDto venta){
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../views/VentasEditar.fxml"));
            Parent root = loader.load();
            VentasEditarController ventasEditarController = loader.getController();
            
            ventasEditarController.EstablecerModalidad(modalidad);
            ventasEditarController.setVentasViewController(this);
            if(modalidad.equals("Ver")){
                ventasEditarController.setVentaAVer(venta);
            }
                    
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
    
    @FXML
    private void actBuscarProductos(ActionEvent event) {
//        cargarTabla((ArrayList<VentasDto>) ventasService.getVentasByProducto(cbxProductos.getValue()).getResultado("Ventas"));
    }
    
    @FXML
    private void btnBuscarAmbosFiltros(ActionEvent event) {
        if(cbxProductos.getValue() != null){
            cargarTabla((ArrayList<VentasDto>) ventasService.getVentasByClienteAndProducto(txtFilter.getText(), cbxProductos.getValue()).getResultado("Ventas"));
        }else{
            cargarTabla((ArrayList<VentasDto>) ventasService.getVentasByCliente(txtFilter.getText()).getResultado("Ventas"));
        }
        
    }

    @FXML
    private void actLimpiarCbx(MouseEvent event) {
        cbxProductos.setValue(null);
        cargarTablaConTodosLosRegistros();
    }

    
    
    
}
