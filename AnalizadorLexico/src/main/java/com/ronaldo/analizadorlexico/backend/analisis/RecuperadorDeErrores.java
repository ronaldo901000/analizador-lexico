package com.ronaldo.analizadorlexico.backend.analisis;

import com.ronaldo.analizadorlexico.backend.almacen.AlmacenTokens;
import com.ronaldo.analizadorlexico.backend.comparacion.Sintaxis;
import com.ronaldo.analizadorlexico.backend.enums.TipoToken;
import com.ronaldo.analizadorlexico.backend.lectura.PalabraSimple;
import com.ronaldo.analizadorlexico.backend.token.Token;
import java.awt.Color;

/**
 *
 * @author ronaldo
 */
public class RecuperadorDeErrores {
       private Sintaxis sintaxis;
       private AlmacenTokens almacen;
       
       public RecuperadorDeErrores(Sintaxis sintaxis, AlmacenTokens almacen) {
              this.sintaxis = sintaxis;
              this.almacen = almacen;
       }

       /**
        * 
        * @param palabra 
        */
       public void analizarError(PalabraSimple palabra) {
              try {
                     int inicio = 0;
                     String cadena = palabra.getCadena();
                     char c = cadena.charAt(inicio);

                     //el primer caracter es digito
                     if (sintaxis.esDigito(c)) {
                            int posicionError = -1;

                            // Buscar el primer caracter que no sea digito
                            for (int i = inicio + 1; i < cadena.length(); i++) {
                                   char q = cadena.charAt(i);
                                   if (!sintaxis.esDigito(q)) {
                                          posicionError = i;
                                          break;
                                   }
                            }

                            // Extraer la parte con error, desde inicio hasta el caracter que no es digito
                            String parteConError = cadena.substring(inicio, posicionError + 1);
                            //crearTokenError(palabra, parteConError);

                            // Verificar si el resto son solo digitos
                            String resto = cadena.substring(posicionError + 1, cadena.length());

                            if (sintaxis.esNumero(resto)) {
                                   crearTokenError(palabra, parteConError);
                                   crearTokenValido(TipoToken.NUMERO_ENTERO.getNombre(), Color.GREEN, palabra, resto, posicionError);

                            } else if (sintaxis.esDecimal(resto)) {
                                   crearTokenError(palabra, parteConError);
                                   crearTokenValido(TipoToken.NUMERO_DECIMAL.getNombre(), Color.BLACK, palabra, resto, posicionError);

                            } 
                            else if(sintaxis.esPalabraReservada(resto)){
                                   crearTokenError(palabra, parteConError);
                                   crearTokenValido(TipoToken.PALABRA_RESERVADA.getNombre(), Color.BLUE, palabra, resto, posicionError);
                            }
                            else if(sintaxis.esCadena(resto)){
                                   crearTokenError(palabra, parteConError);
                                   crearTokenValido(TipoToken.CADENA.getNombre(), Color.ORANGE, palabra, resto, posicionError);
                            }
                            else {

                                   crearTokenError(palabra, cadena);
                            }

                     } else {

                            crearTokenError(palabra, cadena);
                     }

                     almacen.agregarAlContador();
              } catch (Exception e) {
              }

       }

       /**
        * 
        * @param palabra
        * @param texto 
        */
       private void crearTokenError(PalabraSimple palabra, String texto) {
              Token token = new Token(TipoToken.ERROR.getNombre(),
                      Color.RED, texto, palabra.getFila(), palabra.getColumna(), palabra.isEsFinal());
              token.setPosicionCaracter(palabra.getPosicionCaracter());
              almacen.getListaTokens().add(token);

       }

       /**
        * 
        * @param tipo
        * @param color
        * @param palabra
        * @param texto
        * @param posicionError 
        */
       public void crearTokenValido(String tipo, Color color, PalabraSimple palabra, String texto, int posicionError) {
              Token token = new Token(tipo, color, texto,
                      palabra.getFila(), palabra.getColumna() + posicionError + 1, palabra.isEsFinal());
              token.setPosicionCaracter(palabra.getPosicionCaracter() + posicionError + 1);
              almacen.getListaTokens().add(token);
       }

}
