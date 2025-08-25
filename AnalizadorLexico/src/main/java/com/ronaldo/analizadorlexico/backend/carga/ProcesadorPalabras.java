package com.ronaldo.analizadorlexico.backend.carga;

import com.ronaldo.analizadorlexico.backend.Motor;
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

       private static final char COMILLAS = '"';
       private static final char ESPACIO = ' ';
       private Motor motor;
       private AnalizadorBloqueComentario analizadorBloque;
       private int numeroLinea;
       private int columnaEnLinea;

       public ProcesadorPalabras(Motor motor) {
              this.motor = motor;
              analizadorBloque = new AnalizadorBloqueComentario(motor,this);
       }

       /**
        * metodo encargado de analizar el texto que esta en el textPane
        *
        * @param texto
        */
       public void analizarTexto(String texto, JTextPane textPane) {
              //se reinician todos los componentes 
              motor.getAlmacenTokens().getListaTokens().clear();
              motor.getAlmacenTokens().setContadorErrores(0);
              int posicionActual = 0;
              numeroLinea = 0;
              columnaEnLinea = 0;

              while (posicionActual < texto.length()) {
                     char caracterActual = texto.charAt(posicionActual);

                     if (caracterActual == '\n') {
                            numeroLinea++;
                            columnaEnLinea = 0;
                            posicionActual++;
                            continue;
                     }

                     int nuevaPosicion = analizadorBloque.verificarSiHayBloqueComentario(
                             caracterActual, texto, posicionActual, textPane, numeroLinea, columnaEnLinea);

                     if (nuevaPosicion != -1) {
                            int caracteresAvanzados = nuevaPosicion - posicionActual;
                            columnaEnLinea += caracteresAvanzados;
                            posicionActual = nuevaPosicion;
                            continue;
                     }

                     if (elCaracterPerteneceComentarioDeLinea(caracterActual, texto, posicionActual)) {
                            int inicioComentario = posicionActual;
                            
                            while (posicionActual < texto.length() && texto.charAt(posicionActual) != '\n') {
                                   posicionActual++;
                            }

                            String comentario = texto.substring(inicioComentario, posicionActual);
                            Token token = new Token(
                                    TipoToken.COMENTARIO_LINEA.getNombre(),
                                    new Color(0, 95, 0),comentario,numeroLinea,columnaEnLinea,true);
                            token.setPosicionCaracter(inicioComentario);
                            motor.getImpresor().colorearToken(textPane, token);

                            columnaEnLinea += comentario.length();
                     }

                     if (caracterActual == COMILLAS) {
                            int inicioCadena = posicionActual;
                            posicionActual++; 
                            columnaEnLinea++;

                            
                            while (posicionActual < texto.length()) {
                                   if (texto.charAt(posicionActual) == COMILLAS) {
                                          
                                          if (posicionActual == 0 || texto.charAt(posicionActual - 1) != '\\') {
                                                 break;
                                          }
                                   }
                                   posicionActual++;
                            }

                            if (posicionActual < texto.length()) {
                                   posicionActual++; 
                                   String cadenaCompleta = texto.substring(inicioCadena, posicionActual);

                                   PalabraSimple palabra = new PalabraSimple(cadenaCompleta, numeroLinea, columnaEnLinea);
                                   palabra.setPosicionCaracter(inicioCadena);
                                   palabra.setEsFinal(posicionActual >= texto.length() || texto.charAt(posicionActual) == '\n');
                                   motor.getComparador().evaluarTipoToken(palabra);

                                   columnaEnLinea += cadenaCompleta.length();
                            }
                     }

                     if (caracterActual == ESPACIO) {
                            columnaEnLinea++;
                            posicionActual++;
                            continue;
                     }

                     int inicioLexema = posicionActual;
                     while (posicionActual < texto.length()) {
                            char c = texto.charAt(posicionActual);
                            if (c == ESPACIO || c == '\n' || c == COMILLAS) {
                                   break;
                            }
                            posicionActual++;
                     }

                     if (inicioLexema < posicionActual) {
                            String lexemaActual = texto.substring(inicioLexema, posicionActual);

                            PalabraSimple palabra = new PalabraSimple(lexemaActual, numeroLinea, columnaEnLinea);
                            palabra.setPosicionCaracter(inicioLexema);
                            palabra.setEsFinal(posicionActual >= texto.length() || texto.charAt(posicionActual) == '\n');
                            motor.getComparador().evaluarTipoToken(palabra);

                            columnaEnLinea += lexemaActual.length();
                     }
              }
       }

       private boolean elCaracterPerteneceComentarioDeLinea(char caracter, String texto, int posicion) {
              if (caracter == '/') {
                     // Verificar si el siguiente carácter es también '/'
                     if (posicion + 1 < texto.length() && texto.charAt(posicion + 1) == '/') {
                            return true;
                     }
              }
              return false;
       }

       public void agregarLinea(){
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
