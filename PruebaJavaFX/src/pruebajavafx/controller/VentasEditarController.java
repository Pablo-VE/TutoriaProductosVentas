/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pruebajavafx.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
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
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.Callback;
import pruebajavafx.dto.VentasDto;
import pruebajavafx.services.DetallesService;
import pruebajavafx.services.VentasService;
import pruebajavafx.utils.Helpers;
import pruebajavafx.utils.Respuesta;

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
    @FXML
    private Label lblTitulo;
    @FXML
    private Label lblDate;
    @FXML
    private Label lblPrecioTotal;
    
    
    private ProductosService productosService;
    private VentasService ventasService;
    private DetallesService detallesService;
    
    private String modalidad = "";

    ArrayList<DetallesDto> detalleProductos = new ArrayList<DetallesDto>();
    
    private VentasViewController ventasViewController;
    
    private VentasDto ventaAVer;
    @FXML
    private Label lblProducto;
    @FXML
    private Label lblCantidad;
    
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        productosService = new ProductosService();
        ventasService = new VentasService();
        detallesService = new DetallesService();
                
        lblPrecioTotal.setText("Total: ₡"+getPrecioTotalDeVenta());
        
        initCbxProductos();
        // TODO
    }   
    
    public void EstablecerModalidad(String modalidad){
        this.modalidad = modalidad;
        
        if(modalidad == "Agregar"){
            lblTitulo.setText("Agregar Venta");
        }else{
            if(modalidad == "Ver"){
                lblTitulo.setText("Ver Venta");
            }
            
        }
    }
    
    public void setVentaAVer(VentasDto venta){
        this.ventaAVer = venta;
        
        lblTitulo.setText("Venta #"+ventaAVer.getVenId());
        
        lblDate.setText(Helpers.dateToString(ventaAVer.getVenFecha()));
        
        txtCliente.setText(ventaAVer.getVenCliente());
        txtCliente.setDisable(true);
        
        lblProducto.setVisible(false);
        cbxProductos.setVisible(false);
        cbxProductos.setDisable(true);
        
        lblCantidad.setVisible(false);
        txtCantidad.setVisible(false);
        txtCantidad.setDisable(true);
        
        btnAgregar.setVisible(false);
        btnAgregar.setDisable(true);
        
        btnGuardar.setVisible(false);
        btnGuardar.setDisable(true);
        
        btnCancelar.setText("Salir");
        
        tvProductos.setLayoutY(133);
        tvProductos.setPrefHeight(337);
        
        lblPrecioTotal.setLayoutX(586);
        lblPrecioTotal.setLayoutY(506);
        
        detalleProductos = (ArrayList<DetallesDto>) detallesService.getDetallesByVenta(ventaAVer).getResultado("Detalles");
        cargarTabla();
        lblPrecioTotal.setText("Total: ₡"+getPrecioTotalDeVenta());
    }

    public void setVentasViewController(VentasViewController ventasViewController) {
        this.ventasViewController = ventasViewController;
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
            
            if(modalidad == "Agregar"){
                addButtonToTable();
            }
            
        }
    }
    
    private void addButtonToTable() {
        TableColumn<DetallesDto, Void> colBtn = new TableColumn("Acciones");

        Callback<TableColumn<DetallesDto, Void>, TableCell<DetallesDto, Void>> cellFactory = new Callback<TableColumn<DetallesDto, Void>, TableCell<DetallesDto, Void>>() {
            @Override
            public TableCell<DetallesDto, Void> call(final TableColumn<DetallesDto, Void> param) {
                final TableCell<DetallesDto, Void> cell = new TableCell<DetallesDto, Void>() {
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
        lblPrecioTotal.setText("Total: ₡"+getPrecioTotalDeVenta());
        cargarTabla();
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
                    lblPrecioTotal.setText("Total: ₡"+getPrecioTotalDeVenta());
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
    
    private boolean findDetalleByProduct(ProductosDto producto){
        for(int i=0; i<detalleProductos.size(); i++){
            if(detalleProductos.get(i).getDetProducto().getProNombre().equals(producto.getProNombre())){
                return true;
            }
        }
        return false;
    }

    @FXML
    private void actCancelar(ActionEvent event) {
        closeStage();
    }

    private void closeStage(){
        Stage stageActual =  (Stage) txtCantidad.getScene().getWindow();
        stageActual.close();
    }
    
    @FXML
    private void actGuardar(ActionEvent event) {
        if(!txtCliente.getText().isEmpty() && detalleProductos.size() > 0){
            if(modalidad.equals("Agregar")){
                VentasDto venta = new VentasDto();
                venta.setVenId(Long.valueOf(-1));
                venta.setVenCliente(txtCliente.getText());
                venta.setVenFecha(new Date());
                venta.setVenPrecioTotal(getPrecioTotalDeVenta());
                
                Respuesta res = ventasService.saveVenta(venta);
                if(res.getEstado()){
                    venta = (VentasDto) res.getResultado("Venta");
                    setVentaToDetalles(venta);
                    Respuesta resDetalles = detallesService.saveArrayDetalles(detalleProductos);
                    if(resDetalles.getEstado()){
                        closeStage();
                        Mensaje.showAndWait(Alert.AlertType.INFORMATION, "Registro de Venta", "La venta se realizo de manera exitosa");
                        ventasViewController.cargarTablaConTodosLosRegistros();
                    }else{
                        Mensaje.showAndWait(Alert.AlertType.INFORMATION, "Registro de Venta", "Algo salio mal al momento de realizar la venta, intenta luego");
                    }
                }
            }else{
                //editar la venta
            }
        }else{
            Mensaje.showAndWait(Alert.AlertType.WARNING, "Opps", "Hay campos vacíos");
        }
        
            
                
                
                
                
                
                
                
            
        
    }
    
    private void setVentaToDetalles(VentasDto venta){
        for(int i=0; i < detalleProductos.size(); i++){
            detalleProductos.get(i).setDetId(Long.valueOf(-1));
            detalleProductos.get(i).setDetVenta(venta);
        }
    }
    
    private float getPrecioTotalDeVenta(){
        float precioTotal = 0;
        for(int i=0; i < detalleProductos.size(); i++){
            precioTotal += (detalleProductos.get(i).getDetPrecio() * detalleProductos.get(i).getDetCantidad());
        }
        return precioTotal;
    }
    
}
