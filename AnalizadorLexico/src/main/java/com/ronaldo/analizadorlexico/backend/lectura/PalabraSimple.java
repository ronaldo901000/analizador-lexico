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
       private int posicionCaracter;
       
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

       public int getPosicionCaracter() {
              return posicionCaracter;
       }

       public void setPosicionCaracter(int posicionCaracter) {
              this.posicionCaracter = posicionCaracter;
       }
       
       
       


}
