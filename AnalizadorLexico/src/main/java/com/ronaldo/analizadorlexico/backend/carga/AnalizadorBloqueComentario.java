package com.ronaldo.analizadorlexico.backend.carga;

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

       private Motor motor;

       public AnalizadorBloqueComentario(Motor motor) {
              this.motor = motor;
       }

       /**
        *
        * @param c
        * @param linea
        * @param posicionActual
        * @return
        */
       public boolean hayCaracterInicioBloque(char c, String linea, int posicionActual) {
              for (int i = 0; i < motor.getVerificador().getArchivador().getListadoDefinicionTokens().size(); i++) {
                     DefinicionToken definicion = motor.getVerificador().getArchivador().getListadoDefinicionTokens().get(i);

                     if (definicion.getNombre().equals(TipoToken.BLOQUE_INICIO.getNombre())) {
                            for (int j = 0; j < definicion.getElementos().size(); j++) {
                                   String simboloComentario = definicion.getElementos().get(j);
                                   if (simboloComentario.charAt(0) == c) {
                                          if (siEsSimboloBloqueInicio(linea, posicionActual, simboloComentario)) {
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
        * @param lineasDesdeInicioBloque
        * @param posicionGlobalCaracter
        * @param textPane 
        */
       public void aplicarBloqueFin(String[] lineasDesdeInicioBloque, int posicionGlobalCaracter, JTextPane textPane) {
              DefinicionToken definicion = obtenerDefinicionBloqueFin();
              if (definicion == null) {
                     return;
              }
              for (int i = 0; i < definicion.getElementos().size(); i++) {
                     String simboloFinBloque = definicion.getElementos().get(i);
                     String bloqueAcumulado = "";

                     for (int numeroLinea = 0; numeroLinea < lineasDesdeInicioBloque.length; numeroLinea++) {
                            String lineaActual = lineasDesdeInicioBloque[numeroLinea];
                            int indiceFin = lineaActual.indexOf(simboloFinBloque);
                            
                            if (indiceFin != -1) {
                                   bloqueAcumulado += lineaActual.substring(0, indiceFin + simboloFinBloque.length());
                                   Token token = new Token(TipoToken.BLOQUE_FIN.getNombre(),
                                           new Color(0, 95, 0), bloqueAcumulado, numeroLinea, indiceFin, false);
                                   token.setPosicionCaracter(posicionGlobalCaracter);
                                   motor.getImpresor().colorearToken(textPane, token);
                                   return;
                            }
                     }

              }
       }
       /**
        * 
        * @param linea
        * @param posicionInicial
        * @param simboloComentario
        * @return 
        */
       private boolean siEsSimboloBloqueInicio(String linea, int posicionInicial, String simboloComentario) {
              if (posicionInicial + simboloComentario.length() > linea.length()) {
                     return false;
              }
              for (int i = 0; i < simboloComentario.length(); i++) {
                     char caracterSimbolo = simboloComentario.charAt(i);
                     char caracterLinea = linea.charAt(posicionInicial + i);

                     if (caracterSimbolo != caracterLinea) {
                            return false;
                     }
              }

              return true;
       }
       
       /**
        * 
        * @param lineas
        * @param posicionActual
        * @param lineaActual
        * @return 
        */
       public String[] obtenerLineas(String[] lineas, int posicionActual, int lineaActual) {
              int resultado = lineas.length - lineaActual;
              String[] lineasNecesarias = new String[resultado];
              int contador = 0;

              for (int i = lineaActual; i < lineas.length; i++) {
                     if (i == lineaActual) {
                            String seccionLinea = lineas[i].substring(posicionActual);
                            lineasNecesarias[contador] = seccionLinea;
                     } else {
                            lineasNecesarias[contador] = lineas[i];
                     }
                     contador++;
              }

              return lineasNecesarias;
       }

       /**
        * 
        * @return 
        */
       public DefinicionToken obtenerDefinicionBloqueFin(){
              DefinicionToken definicionBloqueFin=null;
              for (int i = 0; i <motor.getVerificador().getArchivador().getListadoDefinicionTokens().size(); i++) {
                     if(motor.getVerificador().getArchivador().getListadoDefinicionTokens().get(i).getNombre().equals(TipoToken.BLOQUE_FIN.getNombre())){
                            definicionBloqueFin=motor.getVerificador().getArchivador().getListadoDefinicionTokens().get(i);
                     }
              }
              return definicionBloqueFin;
       }
}
