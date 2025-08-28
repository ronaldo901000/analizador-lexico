package com.ronaldo.analizadorlexico.backend.analisis;

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
public class ProcesadorTexto {

       private static final char COMILLAS = '"';
       private static final char ESPACIO = ' ';
       private static final char SALTO_LINEA='\n';
       private Motor motor;
       private AnalizadorBloqueComentario analizadorBloque;
       private int numeroLinea;
       private int columnaEnLinea;

       public ProcesadorTexto(Motor motor) {
              this.motor = motor;
              analizadorBloque = new AnalizadorBloqueComentario(motor,this);
       }

       /**
        * metodo encargado de analizar el texto que esta en el textPane
        * 
        * @param texto
        * @param textPane 
        */
       public void analizarTexto(String texto, JTextPane textPane) {
              // reiniciar listas y contadores
              motor.getAlmacenTokens().getListaTokens().clear();
              motor.getAlmacenTokens().setContadorErrores(0);
              int posicionActual = 0;
              numeroLinea = 0;
              columnaEnLinea = 0;

              while (posicionActual < texto.length()) {
                     char caracterActual = texto.charAt(posicionActual);
                     // salto de linea
                     if (caracterActual == SALTO_LINEA) {
                            numeroLinea++;
                            columnaEnLinea = 0;
                            posicionActual++;
                     } //espacio entre carateres
                     else if (caracterActual == ESPACIO) {
                            columnaEnLinea++;
                            posicionActual++;

                     } else {
                            //bloque de comentario
                            int nuevaPosicion = analizadorBloque.verificarSiHayBloqueComentario(
                                    caracterActual, texto, posicionActual, textPane, numeroLinea, columnaEnLinea);

                            if (nuevaPosicion != -1) {
                                   posicionActual = nuevaPosicion;

                            } // comentario de linea
                            else if (elCaracterPerteneceAComentarioDeLinea(texto, posicionActual)) {
                                   int inicioComentario = posicionActual;

                                   while (posicionActual < texto.length() && texto.charAt(posicionActual) != SALTO_LINEA) {
                                          posicionActual++;
                                   }

                                   String comentario = texto.substring(inicioComentario, posicionActual);
                                   Token token = new Token(TipoToken.COMENTARIO_LINEA.getNombre(),
                                           new Color(0, 95, 0), comentario, numeroLinea, columnaEnLinea, true);
                                   token.setPosicionCaracter(inicioComentario);
                                   motor.getImpresor().colorearToken(textPane, token);

                                   columnaEnLinea += comentario.length();

                                   //cadena entre comillas
                            } else if (caracterActual == COMILLAS) {
                                   int inicioCadena = posicionActual;
                                   posicionActual++;
                                   columnaEnLinea++;

                                   boolean comillaCierreEncontrada = false;

                                   while (posicionActual < texto.length()) {
                                          if (texto.charAt(posicionActual) == COMILLAS) {
                                                 comillaCierreEncontrada = true;
                                                 break;
                                          }
                                          posicionActual++;
                                   }
                                   //si encuentra la comilla de cierre crea el token comilla
                                   if (comillaCierreEncontrada) {
                                          String cadena = texto.substring(inicioCadena, posicionActual + 1);
                                          PalabraSimple palabra = new PalabraSimple(cadena, numeroLinea, columnaEnLinea);
                                          palabra.setPosicionCaracter(inicioCadena);
                                          motor.getComparador().evaluarTipoToken(palabra);
                                          posicionActual++;
                                          //crea un token de error
                                   } else {
                                          String cadenaIncompleta = texto.substring(inicioCadena, texto.length());
                                          Token errorToken = new Token(TipoToken.ERROR.getNombre(), Color.RED, cadenaIncompleta, numeroLinea, columnaEnLinea, true);
                                          errorToken.setPosicionCaracter(inicioCadena);
                                          motor.getAlmacenTokens().getListaTokens().add(errorToken);
                                          motor.getImpresor().colorearToken(textPane, errorToken);

                                          posicionActual = texto.length();
                                          columnaEnLinea += cadenaIncompleta.length();
                                   }
                                   //se analizan los demas lexemas
                            } else {
                                   int inicioLexema = posicionActual;
                                   while (posicionActual < texto.length()) {
                                          char c = texto.charAt(posicionActual);
                                          if (esSeparador(c)) {
                                                 break;
                                          }
                                          posicionActual++;
                                   }

                                   if (inicioLexema < posicionActual) {
                                          String lexemaActual = texto.substring(inicioLexema, posicionActual);

                                          PalabraSimple palabra = new PalabraSimple(lexemaActual, numeroLinea, columnaEnLinea);
                                          palabra.setPosicionCaracter(inicioLexema);
                                          palabra.setEsFinal(posicionActual >= texto.length() || texto.charAt(posicionActual) == SALTO_LINEA);
                                          motor.getComparador().evaluarTipoToken(palabra);

                                          columnaEnLinea += lexemaActual.length();
                                   }
                            }
                     }
              }
       }

       /**
        * 
        * @param texto
        * @param posicion
        * @return 
        */
       private boolean elCaracterPerteneceAComentarioDeLinea(String texto, int posicion) {
              DefinicionToken comentarioLinea = motor.getVerificador().getArchivador()
                      .obtenerDefinicionEspecifica(TipoToken.COMENTARIO_LINEA.getNombre());
              
              for (int i = 0; i < comentarioLinea.getElementos().size(); i++) {
                     String simbolo = comentarioLinea.getElementos().get(i);
                     if(posicion+simbolo.length()>texto.length()){
                            return false;
                     }
                     String posibleSimbolo = texto.substring(posicion, posicion + simbolo.length());
                     if (posibleSimbolo.equals(simbolo)) {
                            return true;
                     }

              }
              return false;
       }

       private boolean esSeparador(char c) {
              return c == ESPACIO || c == SALTO_LINEA;
       }

       public void agregarLinea() {
              numeroLinea++;
       }

       public int getNumeroLinea() {
              return numeroLinea;
       }

       public void setNumeroLinea(int numeroLinea) {
              this.numeroLinea = numeroLinea;
       }

       public int getColumnaEnLinea() {
              return columnaEnLinea;
       }

       public void setColumnaEnLinea(int columnaEnLinea) {
              this.columnaEnLinea = columnaEnLinea;
       }

       
}
