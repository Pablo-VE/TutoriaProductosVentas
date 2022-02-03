/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pruebajavafx.controller;

import java.io.FileInputStream;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import pruebajavafx.dto.UsuariosDto;
import pruebajavafx.services.UsuariosService;
import pruebajavafx.utils.AppContext;
import pruebajavafx.utils.Mensaje;
import pruebajavafx.utils.Respuesta;

/**
 * FXML Controller class
 *
 * @author PabloVE
 */
public class PerfilViewController implements Initializable {

    @FXML
    private TextField txtCorreo;
    @FXML
    private ImageView imgRol;
    @FXML
    private TextField txtRol;
    
    private UsuariosDto usuario;
    @FXML
    private TextField txtNombre;
    @FXML
    private Button btnGuardar;
    @FXML
    private Button btnCambiarContrasena;
    @FXML
    private PasswordField txtContrasenaActual;
    @FXML
    private PasswordField txtNuevaContrasena;
    @FXML
    private PasswordField txtConfirmarContrasena;
    
    private String correoActual;
    
    private UsuariosService usuariosService;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cargarDatos();
        onChangeCorreo();
        usuariosService = new UsuariosService();
    }

    private void cargarDatos(){
        usuario = (UsuariosDto) AppContext.getInstance().get("usuarioLogeado");
        
        txtNombre.setText(usuario.getUsuNombre());
        txtCorreo.setText(usuario.getUsuUsuario());
        correoActual = usuario.getUsuUsuario();
        
        txtContrasenaActual.setText("");
        txtNuevaContrasena.setText("");
        txtConfirmarContrasena.setText("");
        
        setRol();
        setFieldsDisable();
    }
    
    private void setFieldsDisable(){
        txtNombre.setDisable(true);
        txtRol.setDisable(true);
        btnGuardar.setDisable(true);
    }
    
    private void setRol(){
        FileInputStream input;
        try{
            if(usuario.getUsuRol().equals("A")){
                input = new FileInputStream("./src/pruebajavafx/resources/admin.png");
                txtRol.setText("Administrador");
            }else{
                input = new FileInputStream("./src/pruebajavafx/resources/cajero.png");
                txtRol.setText("Cajero");
            }
            
            Image image = new Image(input);
            imgRol.setImage(image);
        }catch(Exception ex){
            
        }
    }

    private void onChangeCorreo(){
        txtCorreo.textProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue.equals(correoActual)){
                btnGuardar.setDisable(true);
            }else{
                if(newValue.isEmpty()){
                    btnGuardar.setDisable(true);
                }else{
                    btnGuardar.setDisable(false);
                }
                
            }
        });
    }

    @FXML
    private void actGuardar(ActionEvent event) {
        usuario.setUsuUsuario(txtCorreo.getText());
        guardarUsuario();
    }
    
    private void guardarUsuario(){
        Respuesta res = usuariosService.saveUsuario(usuario);
        if(res.getEstado()){
            Mensaje.showAndWait(Alert.AlertType.INFORMATION, "Usuario actualizado", "Tu usuario ha sido actualizado de manera existosa");
            AppContext.getInstance().set("usuarioLogeado", res.getResultado("Usuario"));
            cargarDatos();
        }
    }

    @FXML
    private void actCambiarContrasena(ActionEvent event) {
        String contrasenaActual = txtContrasenaActual.getText();
        String contrasenaNueva = txtNuevaContrasena.getText();
        String confirmarContrasena = txtConfirmarContrasena.getText();
        if(!contrasenaActual.isEmpty() && !contrasenaNueva.isEmpty() && !confirmarContrasena.isEmpty()){
            if(contrasenaActual.equals(contrasenaNueva)){
                Mensaje.showAndWait(Alert.AlertType.WARNING, "Cambio de contraseña", "Contraseña nueva no puede ser igual a contraseña actual");
            }else{
                if(contrasenaNueva.equals(confirmarContrasena)){
                    Respuesta res1 = usuariosService.getUsuarioByNombreUsuario(usuario.getUsuUsuario(), contrasenaActual);
                    if(res1.getEstado()){
                        usuario.setUsuContrasena(contrasenaNueva);
                        guardarUsuario();
                    }else{
                        Mensaje.showAndWait(Alert.AlertType.WARNING, "Cambio de contraseña", "Contraseña actual no coincide");
                    }
                }else{
                    Mensaje.showAndWait(Alert.AlertType.WARNING, "Cambio de contraseña", "Contraseña a confirmar no coincide con la contraseña nueva");
                }
            }
        }else{
            Mensaje.showAndWait(Alert.AlertType.WARNING, "Cambio de contraseña", "Campos vacios");
        }
    }
    
}
