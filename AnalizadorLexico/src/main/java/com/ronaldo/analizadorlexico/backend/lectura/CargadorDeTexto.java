package com.ronaldo.analizadorlexico.backend.lectura;
import java.io.File;

import javax.swing.JTextPane;

/**
 *
 * @author ronaldo
 */
public class CargadorDeTexto {

       private LectorDeTexto lector;


       public CargadorDeTexto() {
              lector= new LectorDeTexto();
       }
       
       public void pedirLecturaArchivo(File file, JTextPane txtPane) {
              lector.leerTexto(file, txtPane);
       }


       public LectorDeTexto getLector() {
              return lector;
       }
       
       
       
}
