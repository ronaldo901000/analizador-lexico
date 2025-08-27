package com.ronaldo.analizadorlexico.backend.analisis;

import com.ronaldo.analizadorlexico.backend.almacen.AlmacenTokens;
import com.ronaldo.analizadorlexico.backend.lectura.PalabraSimple;
import com.ronaldo.analizadorlexico.backend.token.Token;
import java.awt.Color;

/**
 *
 * @author ronaldo
 */
public class CreadorDeTokens {

       private AlmacenTokens almacen;

       public CreadorDeTokens(AlmacenTokens almacen) {
              this.almacen = almacen;
       }

       /**
        * 
        * @param palabra
        * @param color
        * @param tipo 
        */
       public void crearToken(PalabraSimple palabra, Color color, String tipo) {
              // se ordenan los parametros
              String lexema = palabra.getCadena();
              int fila = palabra.getFila();
              int columna = palabra.getColumna();
              boolean esFinal = palabra.isEsFinal();
              int posicionCaracter = palabra.getPosicionCaracter();

              // se crea el token
              Token token = new Token(tipo, color, lexema, fila, columna, esFinal);
              token.setPosicionCaracter(posicionCaracter);

              //se guarda el token
              almacen.getListaTokens().add(token);

       }
}
