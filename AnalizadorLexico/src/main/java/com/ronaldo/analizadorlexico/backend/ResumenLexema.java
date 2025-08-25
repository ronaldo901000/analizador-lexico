package com.ronaldo.analizadorlexico.backend;

/**
 *
 * @author ronaldo
 */
public class ResumenLexema {

       private String lexema;
       private String tipo;
       private int cantidadRepetida;

       public ResumenLexema(String lexema, String tipo, int cantidadRepetida) {
              this.lexema = lexema;
              this.tipo = tipo;
              this.cantidadRepetida = cantidadRepetida;
       }

       public String getLexema() {
              return lexema;
       }

       public String getTipo() {
              return tipo;
       }

       public int getCantidadRepetida() {
              return cantidadRepetida;
       }

}
