/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pruebajavafx.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author PabloVE
 */
public class Helpers {
    
    
    public static String dateToString(Date fecha){
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss"); 
        String strDate = dateFormat.format(fecha);  
        return strDate;
    }
}
