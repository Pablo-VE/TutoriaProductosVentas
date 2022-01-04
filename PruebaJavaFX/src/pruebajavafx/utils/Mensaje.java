/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pruebajavafx.utils;

import java.util.Optional;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.image.ImageView;
import javafx.stage.Window;

/**
 *
 * @author ccarranza
 */
public class Mensaje {

    public static void show(AlertType tipo, String titulo, String mensaje) {

        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.getButtonTypes().set(0, new ButtonType("OK", ButtonBar.ButtonData.OK_DONE));
        alert.show();
    }
    
    public static void showAndWait(AlertType tipo, String titulo, String mensaje) {

        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.getButtonTypes().set(0, new ButtonType("OK", ButtonBar.ButtonData.OK_DONE));
        alert.showAndWait();
    }
}
