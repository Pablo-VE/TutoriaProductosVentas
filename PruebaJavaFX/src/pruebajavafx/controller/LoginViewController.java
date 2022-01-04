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
import pruebajavafx.models.Usuario;
import pruebajavafx.utils.AppContext;
import pruebajavafx.utils.Mensaje;

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
    
    private Usuario usuario1;
    private Usuario usuario2;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        usuario1 = new Usuario("pablove", "Pablo Venegas", "1234", "admin");
        usuario2 = new Usuario("elizab", "Elizabeth Ortiz", "123", "particular");
        txtUsuario.setText("pablove");
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
                boolean loginU1 = usuario1.compareUser(user, password);
                if(loginU1){
                    Mensaje.showAndWait(Alert.AlertType.INFORMATION, "Inicio de sesión exitoso", "Bienvenid@ "+usuario1.getNombre());
                    Ingresar(usuario1);
                }else{
                   boolean loginU2 = usuario2.compareUser(user, password);
                   if(loginU2){
                       Mensaje.showAndWait(Alert.AlertType.INFORMATION, "Inicio de sesión exitoso", "Bienvenid@ "+usuario2.getNombre());
                       Ingresar(usuario2);
                   }else{
                       Mensaje.showAndWait(Alert.AlertType.ERROR, "Opps :(", "Usuario o contraseña incorrectos");
                   }
                }
            }
        }
        
    }
    
    private void Ingresar(Usuario usuario){
        
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
