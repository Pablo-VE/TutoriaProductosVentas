/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pruebajavafx.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.Callback;
import pruebajavafx.dto.Producto;
import pruebajavafx.dto.Usuario;
import pruebajavafx.dto.UsuariosDto;
import pruebajavafx.services.ProductoService;
import pruebajavafx.utils.AppContext;
import pruebajavafx.utils.Mensaje;

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
    @FXML
    private TableView<Producto> tvProductos;
    
    private ProductoService productoService;
    
    private String rolUsuario;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        rolUsuario = ((UsuariosDto) AppContext.getInstance().get("usuarioLogeado")).getUsuRol();
                
        if(!rolUsuario.equals("A")){
            btnAgregar.setDisable(true);
            btnAgregar.setVisible(false);
        }
                
        productoService = new ProductoService();
        cargarTabla(productoService.getAll());
        // TODO
    }    

    @FXML
    private void actBuscar(ActionEvent event) {
        cargarTabla(productoService.getByNombre(txtFilter.getText()));
    }
    
    public void cargarTabla(ArrayList<Producto> productos){
        tvProductos.getColumns().clear();
        if(!productos.isEmpty()){
            
            ObservableList items = FXCollections.observableArrayList(productos);
            
            TableColumn <Producto, Integer> colId = new TableColumn("ID");
            colId.setCellValueFactory(new PropertyValueFactory("id"));
            
            TableColumn <Producto, String> colNombre = new TableColumn("Nombre");
            colNombre.setCellValueFactory(new PropertyValueFactory("nombre"));
            
            TableColumn <Producto, Integer> colCantidad = new TableColumn("Cantidad");
            colCantidad.setCellValueFactory(new PropertyValueFactory("cantidad"));
            
            TableColumn <Producto, Float> colPrecio = new TableColumn("Precio");
            colPrecio.setCellValueFactory(new PropertyValueFactory("precio"));
            
            tvProductos.getColumns().addAll(colId);
            tvProductos.getColumns().addAll(colNombre);
            tvProductos.getColumns().addAll(colCantidad);
            tvProductos.getColumns().addAll(colPrecio);
            
            if(rolUsuario.equals("admin")){
                addButtonToTable();
            }
            
            tvProductos.setItems(items);
        }
    }
    
    private void addButtonToTable() {
        TableColumn<Producto, Void> colBtn = new TableColumn("Acciones");

        Callback<TableColumn<Producto, Void>, TableCell<Producto, Void>> cellFactory = new Callback<TableColumn<Producto, Void>, TableCell<Producto, Void>>() {
            @Override
            public TableCell<Producto, Void> call(final TableColumn<Producto, Void> param) {
                final TableCell<Producto, Void> cell = new TableCell<Producto, Void>() {
                    private final Button btn = new Button("Editar");
                    {
                        btn.setOnAction((ActionEvent event) -> {
                            try{
                                abrirProductosEditar("Editar", getTableView().getItems().get(getIndex()));
                            }catch(Exception ex){}
                        });
                    }
                    private final Button btn2 = new Button("Eliminar");

                    {
                        btn2.setOnAction((ActionEvent event) -> {
                            try{
                                eliminarProducto(getTableView().getItems().get(getIndex()));
                            }catch(Exception ex){}
                        });
                    }
                    HBox pane = new HBox(btn, btn2);
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

    @FXML
    private void actAgregar(ActionEvent event) {
        abrirProductosEditar("Agregar", null);
        
    }
    
    private void abrirProductosEditar(String modalidad, Producto productoEditar){
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../views/ProductosEditar.fxml"));
            Parent root = loader.load();
            ProductosEditarController productosEditarController = loader.getController();
            
            productosEditarController.EstablecerModalidad(modalidad);
            productosEditarController.setProductosViewController(this);
            if(modalidad.equals("Editar")){
                productosEditarController.setProductoAEditar(productoEditar);
            }
                    
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle(modalidad + " Producto");
            stage.setResizable(false);
            stage.show();
        }catch(Exception e){
            System.out.println("Error: Controller - ProductosViewController - actAgregar");
        }
    }
    
    public void eliminarProducto(Producto producto){
        
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText(null);
        alert.setTitle("Eliminar Producto");
        alert.setContentText("¿Esta seguro de ELIMINAR el producto: "+producto.getNombre()+"?");
        Optional<ButtonType> action = alert.showAndWait();
        if (action.get() == ButtonType.OK) {
            boolean isRemoved = productoService.delete(producto.getId());
            if(isRemoved){
                cargarTabla(productoService.getAll());
                Mensaje.show(Alert.AlertType.INFORMATION, "Producto eliminado", "Producto ha sido eliminado de manera exitosa");
            }else{
                Mensaje.show(Alert.AlertType.ERROR, "Opps", "Error al eliminar producto");
            }
        }
    }
    
}
