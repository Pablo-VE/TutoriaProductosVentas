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
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import pruebajavafx.dto.DetallesDto;
import pruebajavafx.dto.ProductosDto;
import pruebajavafx.services.ProductosService;
import pruebajavafx.utils.Mensaje;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.geometry.Pos;
import javafx.scene.control.TableCell;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.Callback;

/**
 * FXML Controller class
 *
 * @author PabloVE
 */
public class VentasEditarController implements Initializable {

    @FXML
    private TextField txtCliente;
    @FXML
    private ComboBox<ProductosDto> cbxProductos;
    @FXML
    private TextField txtCantidad;
    @FXML
    private Button btnAgregar;
    @FXML
    private TableView<DetallesDto> tvProductos;
    @FXML
    private Button btnCancelar;
    @FXML
    private Button btnGuardar;
    
    private ProductosService productosService;

    ArrayList<DetallesDto> detalleProductos = new ArrayList<DetallesDto>();
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        productosService = new ProductosService();
        initCbxProductos();
        // TODO
    }   
    
    public void initCbxProductos(){
        ArrayList<ProductosDto> productos = new ArrayList<ProductosDto>();
        productos = (ArrayList<ProductosDto>) (productosService.getProductosByNombre("")).getResultado("Productos");
        ObservableList items = FXCollections.observableArrayList(productos);
        cbxProductos.setItems(items);
    }
    
    public void cargarTabla(){
        tvProductos.getColumns().clear();
        if(!detalleProductos.isEmpty()){
            
            ObservableList items = FXCollections.observableArrayList(detalleProductos);
            
            TableColumn <DetallesDto, ProductosDto> colProducto = new TableColumn("Producto");
            colProducto.setCellValueFactory(new PropertyValueFactory("detProducto"));
            
            TableColumn <DetallesDto, SimpleStringProperty> colCantidad = new TableColumn("Cantidad");
            colCantidad.setCellValueFactory(new PropertyValueFactory("detCantidad"));
            
            TableColumn <DetallesDto, SimpleStringProperty> colPrecio = new TableColumn("Precio Unitario");
            colPrecio.setCellValueFactory(new PropertyValueFactory("detPrecio"));
            
            TableColumn <DetallesDto, String> colSubtotal = new TableColumn("Sub total");
            colSubtotal.setCellValueFactory(obj -> {
                float subtotal  = obj.getValue().getDetPrecio() * obj.getValue().getDetCantidad();
                return new ReadOnlyStringWrapper(String.valueOf(subtotal)); 
            });
            
            
            tvProductos.getColumns().addAll(colProducto);
            tvProductos.getColumns().addAll(colCantidad);
            tvProductos.getColumns().addAll(colPrecio);
            tvProductos.getColumns().addAll(colSubtotal);
            
            tvProductos.setItems(items);
            
            addButtonToTable();
        }
    }
    
    private void addButtonToTable() {
        TableColumn<DetallesDto, Void> colBtn = new TableColumn("Acciones");

        Callback<TableColumn<DetallesDto, Void>, TableCell<DetallesDto, Void>> cellFactory = new Callback<TableColumn<DetallesDto, Void>, TableCell<DetallesDto, Void>>() {
            @Override
            public TableCell<DetallesDto, Void> call(final TableColumn<DetallesDto, Void> param) {
                final TableCell<DetallesDto, Void> cell = new TableCell<DetallesDto, Void>() {
                    private final Button btn = new Button("Editar");
                    {
                        btn.setOnAction((ActionEvent event) -> {
                            try{
                                
                            }catch(Exception ex){}
                        });
                    }
                    private final Button btn2 = new Button("Eliminar");

                    {
                        btn2.setOnAction((ActionEvent event) -> {
                            try{
                                eliminarDetalleDeLaLista(getIndex());
                            }catch(Exception ex){}
                        });
                    }
                    HBox pane = new HBox(btn2);
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

        tvProductos.getColumns().add(colBtn);
    }
    
    private void eliminarDetalleDeLaLista(int index){
        detalleProductos.remove(index);
        cargarTabla();
    }

    @FXML
    private void actSelProducto(ActionEvent event) {
    }

    @FXML
    private void actAgregar(ActionEvent event) {
        if(!txtCantidad.getText().isEmpty() && cbxProductos.getValue() != null){
            if(!findDetalleByProduct(cbxProductos.getValue())){
                if(Integer.valueOf(txtCantidad.getText())<=cbxProductos.getValue().getProCantidad()){
                    DetallesDto detalle = new DetallesDto();
                    detalle.setDetCantidad(Integer.valueOf(txtCantidad.getText()));
                    detalle.setDetPrecio(cbxProductos.getValue().getProPrecio());
                    detalle.setDetProducto(cbxProductos.getValue());
                    detalleProductos.add(detalle);
                    cargarTabla();
                }else{
                    Mensaje.show(Alert.AlertType.WARNING, "Opps", "No hay unidades suficientes de "+cbxProductos.getValue().getProNombre()+". Cantidad disponible: "+cbxProductos.getValue().getProCantidad());
                }
            }else{
                Mensaje.show(Alert.AlertType.WARNING, "Opps", "Ese producto ya se encuentra en la venta, puede eliminarlo y volver a agregarlo");
            }
        }else{
            Mensaje.show(Alert.AlertType.WARNING, "Campos vacios", "Por favor ingrese todos los datos del producto");
        }
    }
    
    public boolean findDetalleByProduct(ProductosDto producto){
        for(int i=0; i<detalleProductos.size(); i++){
            if(detalleProductos.get(i).getDetProducto().getProNombre().equals(producto.getProNombre())){
                return true;
            }
        }
        return false;
    }

    @FXML
    private void actCancelar(ActionEvent event) {
        Stage stageActual =  (Stage) txtCantidad.getScene().getWindow();
        stageActual.close();
    }

    @FXML
    private void actGuardar(ActionEvent event) {
    }
    
}
