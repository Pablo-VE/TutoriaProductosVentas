/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pruebajavafx.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import pruebajavafx.dto.UsuariosDto;
import pruebajavafx.services.UsuariosService;
import pruebajavafx.utils.UsuarioHBox;

/**
 * FXML Controller class
 *
 * @author PabloVE
 */
public class UsuariosViewController implements Initializable {

    @FXML
    private TextField txtFilter;
    @FXML
    private Button btnBuscar;
    @FXML
    private Button btnAgregar;
    @FXML
    private ScrollPane scPane;
    @FXML
    private VBox vbContenedor;
    
    private UsuariosService usuariosService;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        usuariosService = new UsuariosService();
        cargarDatos("");
        
        scPane.setStyle("-fx-background-color: transparent;");
        vbContenedor.setStyle(ContenedorStyle());
    }   
    
    public void cargarDatos(String filtro){
        ArrayList<UsuariosDto> usuarios = (ArrayList<UsuariosDto>) usuariosService.getUsuarioByNombreOrCorreo(filtro).getResultado("Usuarios");
        if(usuarios.size() > 0 ){
            scPane.setVisible(true);
            vbContenedor.getChildren().clear();
            
            for(int i=0; i<usuarios.size(); i++){
                UsuarioHBox usuHbox = new UsuarioHBox(usuarios.get(i));
                vbContenedor.getChildren().add(usuHbox);
            }
            
        }else{
            scPane.setVisible(false);
        }
    }

    @FXML
    private void actBuscar2(KeyEvent event) {
        if(event.getCode() == KeyCode.ENTER){
            cargarDatos(txtFilter.getText());
        }
    }

    @FXML
    private void actBuscar(ActionEvent event) {
        cargarDatos(txtFilter.getText());
    }

    @FXML
    private void actAgregar(ActionEvent event) {
    }
    
    private String ContenedorStyle(){
        return "-fx-spacing: 10;";
    }
}
