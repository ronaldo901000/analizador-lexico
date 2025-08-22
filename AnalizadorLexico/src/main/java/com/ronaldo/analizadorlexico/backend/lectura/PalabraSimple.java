package com.ronaldo.analizadorlexico.backend.lectura;

/**
 *
 * @author ronaldo
 */
public class PalabraSimple {

       private String cadena;
       private int fila;
       private int columna;
       private boolean esFinal;
       
       public PalabraSimple(String cadena, int fila, int columna) {
              this.cadena = cadena;
              this.fila = fila;
              this.columna = columna;
              
       }

       public String getCadena() {
              return cadena;
       }

       public int getFila() {
              return fila;
       }

       public int getColumna() {
              return columna;
       }

       public boolean isEsFinal() {
              return esFinal;
       }

       public void setEsFinal(boolean esFinal) {
              this.esFinal = esFinal;
       }
       
       


}
