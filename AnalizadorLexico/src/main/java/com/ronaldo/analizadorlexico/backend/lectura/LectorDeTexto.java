package com.ronaldo.analizadorlexico.backend.lectura;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.StyledDocument;


/**
 *
 * @author ronaldo
 */
public class LectorDeTexto {
       public LectorDeTexto(){

       }
       /**
        * metodo que se encarga de leer el texto que el usuario ingrese desde el txt
        * @param file
        * @param palabras 
        */
       public void leerTexto(File file, JTextPane txtPane) {
              try (BufferedReader buffer = new BufferedReader(new FileReader(file))) {
                     StyledDocument doc = txtPane.getStyledDocument();
                     doc.remove(0, doc.getLength()); 

                     String linea;
                     while ((linea = buffer.readLine()) != null) {
                            doc.insertString(doc.getLength(), linea + "\n", null);
                     }
              } catch (IOException | BadLocationException e) {
                     e.printStackTrace();
              }
       }

       public void leerConfiguracion(File file, JTextPane txtPane) {
              if (file == null) {
                     return;
              }
              String contenido = "";
              try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                     String linea;
                     while ((linea = reader.readLine()) != null) {
                           contenido+=linea+"\n"; 
                     }
                     txtPane.setText(contenido);
              } catch (IOException e) {
                     e.printStackTrace();
              }
       }

}
