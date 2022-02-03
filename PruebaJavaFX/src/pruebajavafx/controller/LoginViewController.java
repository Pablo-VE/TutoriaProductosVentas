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
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import pruebajavafx.dto.UsuariosDto;
import pruebajavafx.services.UsuariosService;
import pruebajavafx.utils.AppContext;
import pruebajavafx.utils.GeneratePDF;
import pruebajavafx.utils.Mensaje;
import pruebajavafx.utils.Respuesta;

/**
 * FXML Controller class
 *
 * @author Pablo-VE
 */
public class LoginViewController implements Initializable {

    @FXML
    private TextField txtUsuario;
    @FXML
    private PasswordField txtPassword;
    @FXML
    private Button btnIngresar;
    
    private UsuariosService usuariosService;    
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        usuariosService = new UsuariosService();
        txtUsuario.setText("pablovenegaselizondo@gmail.com");
        txtPassword.setText("1234");
    }    

    @FXML
    private void actIngresar(ActionEvent event) {
        String user = txtUsuario.getText();
        String password = txtPassword.getText();
        
        if(user.isEmpty()){
            Mensaje.showAndWait(Alert.AlertType.WARNING, "Campo vacío", "Por favor, digita el nombre de usuario");
        }else{
            if(password.isEmpty()){
                Mensaje.showAndWait(Alert.AlertType.WARNING, "Campo vacío", "Por favor, digita la contraseña");
            }else{
                Respuesta res = usuariosService.getUsuarioByNombreUsuario(user, password);
                Mensaje.showAndWait(Alert.AlertType.INFORMATION, res.getMensaje(), res.getMensajeInterno());
                if(res.getEstado()){
                    Ingresar((UsuariosDto) res.getResultado("Usuario"));
                }       
            }
        }
        
    }
    
    private void Ingresar(UsuariosDto usuario){
        
        
        
        AppContext.getInstance().set("usuarioLogeado", usuario);
        
        Stage stageActual =  (Stage) btnIngresar.getScene().getWindow();
        stageActual.close();
       
        try{
            Parent root = FXMLLoader.load(getClass().getResource("../views/HelloWorld.fxml"));
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setResizable(false);
            stage.setTitle("Supemercados Pali");
            stage.show();
        }catch(Exception e){
            System.out.println("Error: Controller - LoginViewController - Ingresar()");
        }
       
    }
    
}
