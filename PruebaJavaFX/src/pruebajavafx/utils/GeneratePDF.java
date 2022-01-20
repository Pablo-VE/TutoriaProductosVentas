/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pruebajavafx.utils;

import com.itextpdf.text.Document;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import pruebajavafx.dto.DetallesDto;
import pruebajavafx.dto.VentasDto;

/**
 *
 * @author PabloVE
 */
public class GeneratePDF {
    public static void CreatePDFWithOnlyParagraph(String nombreArchivo, String texto){
        try{
            String fileName = "../PDFs/"+nombreArchivo+".pdf";
            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream(fileName));
            document.open();
            Paragraph paragraph = new Paragraph(texto);
            document.add(paragraph);
            document.close();
            
            File objetofile = new File ("../PDFs/"+nombreArchivo+".pdf");
            Desktop.getDesktop().open(objetofile);
        }catch(Exception ex){
            System.out.println("error");
            Logger.getLogger(GeneratePDF.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void CreatePDFVenta(VentasDto venta, ArrayList<DetallesDto> detalles){
        try{
            String fileName = "../PDFs/"+"venta_"+venta.getVenId()+"_"+venta.getVenFecha().getTime()+".pdf";
            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream(fileName));
            document.open();
            
            document.add(new Paragraph("Fecha: "+ Helpers.dateToString(venta.getVenFecha())));
            
            document.add(new Paragraph("Venta con codigo: "+venta.getVenId()));
            
            document.add(new Paragraph("Nombre del cliente: "+venta.getVenCliente()));
            document.add(new Paragraph("Precio total: ₡"+venta.getVenPrecioTotal()));
            
            document.add(new Paragraph(" "));
            document.add(new Paragraph(" "));
            document.add(new Paragraph("Detalle: "));
            document.add(new Paragraph(" "));
            document.add(new Paragraph(" "));
            
            PdfPTable table = new PdfPTable(4); //cantidad de columnas
            PdfPCell colunm = new PdfPCell(new Phrase("Nombre del producto"));
            table.addCell(colunm);
            colunm = new PdfPCell(new Phrase("Cantidad"));
            table.addCell(colunm);
            colunm = new PdfPCell(new Phrase("Precio unitario"));
            table.addCell(colunm);
            colunm = new PdfPCell(new Phrase("Subtotal"));
            table.addCell(colunm);
            table.setHeaderRows(1);
            
            for(int i=0; i<detalles.size(); i++){
                table.addCell(detalles.get(i).getDetProducto().getProNombre());
                table.addCell(String.valueOf(detalles.get(i).getDetCantidad()));
                table.addCell(String.valueOf(detalles.get(i).getDetPrecio()));
                table.addCell(String.valueOf(detalles.get(i).getDetPrecio()*detalles.get(i).getDetCantidad()));
            }
            
            document.add(table);
            
            document.add(new Paragraph(" "));
            document.add(new Paragraph(" "));
            document.add(new Paragraph(" "));
            
            document.add(Image.getInstance("../PDFs/gracias.png"));
            
            document.close();
            
            File objetofile = new File ("../PDFs/"+"venta_"+venta.getVenId()+"_"+venta.getVenFecha().getTime()+".pdf");
            Desktop.getDesktop().open(objetofile);
            
        }catch(Exception ex){
            System.out.println("error");
            Logger.getLogger(GeneratePDF.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
