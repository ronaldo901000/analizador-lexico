package com.ronaldo.analizadorlexico.backend.carga;

import com.ronaldo.analizadorlexico.backend.Motor;
import com.ronaldo.analizadorlexico.backend.almacenamiento.DefinicionToken;
import com.ronaldo.analizadorlexico.backend.enums.TipoToken;
import com.ronaldo.analizadorlexico.backend.lectura.PalabraSimple;
import com.ronaldo.analizadorlexico.backend.token.Token;
import java.awt.Color;
import javax.swing.JTextPane;

/**
 *
 * @author ronaldo
 */
public class ProcesadorPalabras {
       private static final char COMILLAS='"';
       private static final char ESPACIO=' ';
       private Motor motor;
       private AnalizadorBloqueComentario analizadorBloque;
       public ProcesadorPalabras(Motor motor) {
              this.motor = motor;
               analizadorBloque = new AnalizadorBloqueComentario(motor);
       }

       /**
        * metodo encargado de analizar el texto que esta en el textPane
        *
        * @param texto
        */
       public void analizarTexto(String texto, JTextPane textPane) {
              String[] lineas = texto.split("\n", -1);
              int posicionCaracterEnMovimiento = 0;

              // Elimina todos los tokens del almacen
              motor.getAlmacenTokens().getListaTokens().clear();

              for (int numeroLinea = 0; numeroLinea < lineas.length; numeroLinea++) {
                     String lineaActual = lineas[numeroLinea];
                     int columnaActual = 0;
                     int indiceCaracter = 0;

                     while (indiceCaracter < lineaActual.length()) {
                            char caracterActual = lineaActual.charAt(indiceCaracter);

                            if (analizadorBloque.hayCaracterInicioBloque(caracterActual, lineaActual, indiceCaracter)) {
                                   String[] lineasDesdeInicioBloque = analizadorBloque.obtenerLineas(lineas, indiceCaracter, numeroLinea);
                                   analizadorBloque.aplicarBloqueFin(lineasDesdeInicioBloque,
                                           posicionCaracterEnMovimiento + columnaActual,
                                           textPane);

                                   indiceCaracter = lineaActual.length();
                            }

                            if (elCaracterPerteneceComentarioDeLinea(caracterActual, lineaActual, indiceCaracter)) {
                                   int inicio = indiceCaracter;
                                   int fin = lineaActual.length();
                                   String restoDeLinea = lineaActual.substring(inicio, fin);
                                   Token token = new Token(TipoToken.COMENTARIO_LINEA.getNombre(), Color.GREEN, restoDeLinea, numeroLinea, columnaActual, true);
                                   token.setPosicionCaracter(posicionCaracterEnMovimiento + columnaActual);
                                   motor.getImpresor().colorearToken(textPane, token);
                                   break;
                            }
                            
                            if (caracterActual == COMILLAS) {
                                   int posicionFinCadena = encontrarFinCadena(lineaActual, indiceCaracter);
                                   String cadenaCompleta = lineaActual.substring(indiceCaracter, posicionFinCadena + 1);
                                   PalabraSimple palabra = new PalabraSimple(cadenaCompleta, numeroLinea, columnaActual);
                                   palabra.setPosicionCaracter(posicionCaracterEnMovimiento + columnaActual);
                                   palabra.setEsFinal(esFinalDeFila(lineaActual, posicionFinCadena+1));
                                   motor.getComparador().evaluarTipoToken(palabra);
                                   columnaActual += cadenaCompleta.length();
                                   indiceCaracter = posicionFinCadena + 1;

                            } else if (caracterActual == ESPACIO) {
                                   columnaActual++;
                                   indiceCaracter++;
                            } else {
                                   
                                   int posicionFinLexema = obtenerFinLexema(lineaActual, indiceCaracter);
                                   String lexemaActual = lineaActual.substring(indiceCaracter, posicionFinLexema);

                                   PalabraSimple palabra = new PalabraSimple(lexemaActual, numeroLinea, columnaActual);
                                   palabra.setPosicionCaracter(posicionCaracterEnMovimiento + columnaActual);
                                   palabra.setEsFinal(esFinalDeFila(lineaActual, posicionFinLexema));
                                   motor.getComparador().evaluarTipoToken(palabra);

                                   columnaActual += lexemaActual.length();
                                   indiceCaracter = posicionFinLexema;
                            }
                     }

                     posicionCaracterEnMovimiento += lineaActual.length() + 1;

              }
       }

       /**
        *
        * @param linea
        * @param posicionInicio
        * @return
        */
       private int encontrarFinCadena(String linea, int posicionInicio) {
              int posicionActual = posicionInicio + 1;

              while (posicionActual < linea.length()) {
                     if (linea.charAt(posicionActual) == COMILLAS) {
                            return posicionActual;
                     }
                     posicionActual++;
              }

              return linea.length() - 1;
       }

       /**
        *
        * @param linea
        * @param posicionInicio
        * @return
        */
       //
       private int obtenerFinLexema(String linea, int posicionInicio) {
              int posicionActual = posicionInicio;

              while (posicionActual < linea.length()) {
                     char caracterActual = linea.charAt(posicionActual);
                     if (caracterActual == ESPACIO || caracterActual == COMILLAS) {
                            return posicionActual;
                     }
                     posicionActual++;
              }

              return linea.length();
       }

       /**
        *
        * @param c
        * @param linea
        * @param posicionActual
        * @return
        */
       private boolean elCaracterPerteneceComentarioDeLinea(char c, String linea, int posicionActual) {
              for (int i = 0; i < motor.getVerificador().getArchivador().getListadoDefinicionTokens().size(); i++) {
                     DefinicionToken definicion = motor.getVerificador().getArchivador().getListadoDefinicionTokens().get(i);

                     if (definicion.getNombre().equals(TipoToken.COMENTARIO_LINEA.getNombre())) {
                            for (int j = 0; j < definicion.getElementos().size(); j++) {
                                   String simboloComentario = definicion.getElementos().get(j);

                                   // Verificar si el caracter actual coincide con el primer caracter del simbolo
                                   if (simboloComentario.charAt(0) == c) {
                                          if (siEsComentarioDeLinea(linea, posicionActual, simboloComentario)) {
                                                 return true;
                                          }
                                   }
                            }
                     }
              }
              return false;
       }

       /**
        *
        * @param linea
        * @param posicionInicial
        * @param simboloComentario
        * @return
        */
       private boolean siEsComentarioDeLinea(String linea, int posicionInicial, String simboloComentario) {
              if (posicionInicial + simboloComentario.length() > linea.length()) {
                     return false;
              }
              // Verificar caracter por caracter del comentario de comentario
              for (int i = 0; i < simboloComentario.length(); i++) {
                     char caracterSimbolo = simboloComentario.charAt(i);
                     char caracterLinea = linea.charAt(posicionInicial + i);

                     if (caracterSimbolo != caracterLinea) {
                            return false;
                     }
              }

              return true;
       }

       private boolean esFinalDeFila(String linea, int ultimoIndice) {
              if (ultimoIndice == linea.trim().length()) {
                     return true;
              } else {
                     return false;
              }
       }

}
