package com.ronaldo.analizadorlexico.backend.lectura;

import com.ronaldo.analizadorlexico.backend.Motor;
import com.ronaldo.analizadorlexico.backend.almacenamiento.AlmacenTokens;
import com.ronaldo.analizadorlexico.backend.comparacion.Comparador;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import javax.swing.JTextPane;


/**
 *
 * @author ronaldo
 */
public class LectorDeTexto {
       private Motor motor;
       
       public LectorDeTexto(Motor motor){
              this.motor =motor;
       }
       /**
        * metodo que se encarga de leer el texto que el usuario ingrese desde el txt
        * @param file
        * @param palabras 
        */
       public void leerTexto(File file, List<PalabraSimple> palabras, JTextPane txtPane) {
              try (BufferedReader buffer = new BufferedReader(new FileReader(file))) {
                     String linea;
                     int numeroLinea = 0;
                     txtPane.setText("");

                     while ((linea = buffer.readLine()) != null) {
                            String[] palabrasLinea = linea.split(" ");

                            for (int posicionPalabra = 0; posicionPalabra < palabrasLinea.length; posicionPalabra++) {
                                   String palabraTexto = palabrasLinea[posicionPalabra];

                                   PalabraSimple palabraSimple = new PalabraSimple(
                                           palabraTexto,
                                           numeroLinea,
                                           posicionPalabra
                                   );

                                   if (posicionPalabra == palabrasLinea.length - 1) {
                                          palabraSimple.setEsFinal(true);
                                   }

                                   palabras.add(palabraSimple);
                            }

                            numeroLinea++;
                     }


              } catch (IOException e) {
                     e.printStackTrace();
              }
       }



}
