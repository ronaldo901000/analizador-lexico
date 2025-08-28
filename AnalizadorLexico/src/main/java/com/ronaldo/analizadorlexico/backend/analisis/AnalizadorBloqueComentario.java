package com.ronaldo.analizadorlexico.backend.analisis;

import com.ronaldo.analizadorlexico.backend.Motor;
import com.ronaldo.analizadorlexico.backend.almacenamiento.DefinicionToken;
import com.ronaldo.analizadorlexico.backend.enums.TipoToken;
import com.ronaldo.analizadorlexico.backend.token.Token;
import java.awt.Color;
import javax.swing.JTextPane;

/**
 *
 * @author ronaldo
 */
public class AnalizadorBloqueComentario {
       private final char SALTO_LINEA='\n';
       private Motor motor;
       private ProcesadorTexto procesador;

       public AnalizadorBloqueComentario(Motor motor, ProcesadorTexto procesador) {
              this.motor = motor;
              this.procesador = procesador;
       }

       /**
        *
        * @param c
        * @param texto
        * @param posicionActual
        * @return
        */
       public int verificarSiHayBloqueComentario(char c, String texto, int posicionActual, JTextPane textPane, int numeroLinea, int columna) {
              DefinicionToken definicion = motor.getVerificador().getArchivador()
                      .obtenerDefinicionEspecifica(TipoToken.BLOQUE_INICIO.getNombre());

              for (String simboloComentario : definicion.getElementos()) {
                     if (simboloComentario.charAt(0) == c) {
                            if (siEsSimboloBloqueInicio(texto, posicionActual, simboloComentario)) {
                                   String siguientePedazo = texto.substring(posicionActual, posicionActual + simboloComentario.length());

                                   if (simboloComentario.equals(siguientePedazo)) {
                                          int inicioBloque = posicionActual;
                                          int posicionCierre = buscarSimboloCierre(texto, inicioBloque + simboloComentario.length());
                                          if (posicionCierre == -1) {
                                                 return -1;
                                          }
                                          String lexema = texto.substring(inicioBloque, posicionCierre);
                                          Token token = new Token(TipoToken.BLOQUE_FIN.getNombre(),
                                                  new Color(0, 95, 0), lexema, 0, 0, true);
                                          token.setPosicionCaracter(inicioBloque);
                                          motor.getImpresor().colorearToken(textPane, token);
                                          return posicionCierre + 1;
                                   }
                            }
                     }
              }
              return -1;
       }

       /**
        * 
        * @param texto
        * @param posicionInicio
        * @return 
        */
       public int buscarSimboloCierre(String texto, int posicionInicio) {
              DefinicionToken definicion = motor.getVerificador().getArchivador()
                      .obtenerDefinicionEspecifica(TipoToken.BLOQUE_FIN.getNombre());
              for (String simboloCierre : definicion.getElementos()) {
                     int posicionBusqueda = posicionInicio;
                     int longitudSimbolo = simboloCierre.length();
                     int columnaLinea=posicionBusqueda;
                     while (posicionBusqueda <= texto.length() - longitudSimbolo) {
                            while (posicionBusqueda < texto.length() && texto.charAt(posicionBusqueda) == SALTO_LINEA) {
                                   posicionBusqueda++;
                                   procesador.agregarLinea();
                                   procesador.setColumnaEnLinea(0);
                                   columnaLinea=0;
                            }
                            if (posicionBusqueda > texto.length() - longitudSimbolo) {
                                   break;
                            }
                            boolean coincide = true;
                            for (int i = 0; i < longitudSimbolo; i++) {
                                   if (texto.charAt(posicionBusqueda + i) != simboloCierre.charAt(i)) {
                                          
                                          coincide = false;
                                          break;
                                   }
                            }
                            if (coincide) {
                                   procesador.setColumnaEnLinea(columnaLinea);
                                   return posicionBusqueda + longitudSimbolo;
                            }
                            posicionBusqueda++;
                            columnaLinea++;
                     }
              }
              return -1;
       }

       /**
        *
        * @param texto
        * @param posicionActual
        * @param simboloComentario
        * @return
        */
       private boolean siEsSimboloBloqueInicio(String texto, int posicionActual, String simboloComentario) {
              if (posicionActual + simboloComentario.length() > texto.length()) {
                     return false;
              }
              String pedazoTexto = texto.substring(posicionActual, posicionActual + simboloComentario.length());
              if (pedazoTexto.equals(simboloComentario)) {
                     return true;
              }

              return false;
       }

}
