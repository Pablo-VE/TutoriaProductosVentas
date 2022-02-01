/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pruebajavafx.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import pruebajavafx.dto.UsuariosDto;
import pruebajavafx.services.UsuariosService;
import pruebajavafx.utils.Helpers;
import pruebajavafx.utils.MailManager;
import pruebajavafx.utils.Mensaje;
import pruebajavafx.utils.Respuesta;

/**
 * FXML Controller class
 *
 * @author PabloVE
 */
public class UsuariosEditarController implements Initializable {

    @FXML
    private TextField txtNombre;
    @FXML
    private Button btnCancelar;
    @FXML
    private Button btnGuardar;
    @FXML
    private Label lblTitulo;
    @FXML
    private TextField txtCorreo;
    @FXML
    private ComboBox<String> cbxRol;
    
    private String modalidad;

    private UsuariosViewController usuariosViewController;
    
    private String rol;
    
    private UsuariosService usuarioService;
    
    private MailManager mailManager;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initCbxRoles();
        
        usuarioService = new UsuariosService();
        mailManager = new MailManager();
        
        rol = "";
    } 
    
    public void initCbxRoles(){
        ArrayList<String> roles = new ArrayList<String>();
        roles.add("Administrador");
        roles.add("Cajero");
        ObservableList items = FXCollections.observableArrayList(roles);
        cbxRol.setItems(items);
    }
    
    public void EstablecerModalidad(String modalidad){
        this.modalidad = modalidad;
        
        if(this.modalidad == "Agregar"){
            lblTitulo.setText("Agregar Usuario");
        }else{
            lblTitulo.setText("Editar Usuario");
        }
    }
    
    public void setUsuariosViewController(UsuariosViewController controller){
        usuariosViewController = controller;
    }

    @FXML
    private void actCancelar(ActionEvent event) {
        closeStage();
    }

    private void closeStage(){
        Stage stageActual =  (Stage) txtNombre.getScene().getWindow();
        stageActual.close();
    }

    @FXML
    private void actGuardar(ActionEvent event) {
        if(!txtNombre.getText().isEmpty() && !txtCorreo.getText().isEmpty() && !rol.isEmpty()){
            String nombre = txtNombre.getText();
            String correo = txtCorreo.getText();
            if(modalidad.equals("Agregar")){
                //Agregar
                UsuariosDto usuario = new UsuariosDto();
                usuario.setUsuId(Long.valueOf(-1));
                usuario.setUsuNombre(nombre);
                usuario.setUsuUsuario(correo);
                usuario.setUsuRol(rol);
                
                String password = Helpers.generateRandomAlphanumericString();
                
                usuario.setUsuContrasena(password);
                
                Respuesta res = usuarioService.saveUsuario(usuario);
                
                if(res.getEstado()){
//                    mailManager.SendMailOnlyText(correo, "Bienvenido a Pali", "Bienvenido "+nombre+" a nuestra empresa, se te ha creado una cuenta con el correo: "+correo+" y la contraseña "+password+" . Por favor, cuando ingreses cambiar la contraseña por una de tu agrado, para mayor seguridad.");
//                    mailManager.SendMailTextWithHTML(correo, "Bienvenido a Pali", nombre, password);
                    Mensaje.showAndWait(Alert.AlertType.INFORMATION, "Registro de Usuario", "El usuario ha sido creado de manera exitosa");
                    usuariosViewController.cargarDatos("");
                    closeStage();
                }else{
                    Mensaje.showAndWait(Alert.AlertType.ERROR, "Registro de Usuario", "Ha surgido un error al crear el usuario, intenta luego.");
                }
            }else{
                //Editar
            }
        }else{
            Mensaje.showAndWait(Alert.AlertType.WARNING, "Opps :(", "Hay campos vacios");
        }
    }

    @FXML
    private void actSelRol(ActionEvent event) {
        if(cbxRol.getValue() != null){
            if(cbxRol.getValue().equals("Administrador")){
                rol = "A";
            }else{
               rol = "C"; 
            }
        }
    }
    
}
