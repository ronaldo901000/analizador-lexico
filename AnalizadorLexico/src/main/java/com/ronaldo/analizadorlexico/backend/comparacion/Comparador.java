package com.ronaldo.analizadorlexico.backend.comparacion;

import com.ronaldo.analizadorlexico.backend.exception.DefinicionException;
import com.ronaldo.analizadorlexico.backend.almacen.AlmacenTokens;
import com.ronaldo.analizadorlexico.backend.almacenamiento.DefinicionToken;
import com.ronaldo.analizadorlexico.backend.enums.TipoToken;
import com.ronaldo.analizadorlexico.backend.lectura.PalabraSimple;
import com.ronaldo.analizadorlexico.backend.token.Token;
import java.awt.Color;
import java.util.List;
/**
 *
 * @author ronaldo
 */
public class Comparador {
       private AlmacenTokens almacen;
       private Sintaxis sintaxis;

       private List <DefinicionToken> listaDefinicionTokens;
       
       public Comparador(AlmacenTokens almacen){
              sintaxis= new Sintaxis();
              this.almacen=almacen;

       }
       

       
       /**
        * 
        * @param palabraSimple 
        */
       public void evaluarTipoToken(PalabraSimple palabraSimple) {
              try {
                     if (sintaxis.esComentarioLinea(palabraSimple.getCadena())) {
                            crearToken(palabraSimple, Color.GREEN, TipoToken.COMENTARIO_LINEA.getNombre());
                     } else if (sintaxis.esPalabraReservada(palabraSimple.getCadena())) {
                            crearToken(palabraSimple, Color.BLUE, TipoToken.PALABRA_RESERVADA.getNombre());
                     } else if (sintaxis.esCadena(palabraSimple.getCadena())) {
                            crearToken(palabraSimple, Color.ORANGE, TipoToken.CADENA.getNombre());
                     } else if (sintaxis.esDecimal(palabraSimple.getCadena())) {
                            crearToken(palabraSimple, Color.BLACK, TipoToken.NUMERO_DECIMAL.getNombre());
                     } else if (sintaxis.esNumero(palabraSimple.getCadena())) {
                            crearToken(palabraSimple, Color.GREEN, TipoToken.NUMERO_ENTERO.getNombre());
                     } else if (sintaxis.esIdentificador(palabraSimple.getCadena())) {
                            crearToken(palabraSimple, new Color(139, 69, 19), TipoToken.IDENTIFICADOR.getNombre());
                     } else if (sintaxis.esOperador(palabraSimple.getCadena())) {
                            crearToken(palabraSimple, Color.yellow, TipoToken.OPERADOR.getNombre());
                     } else if (sintaxis.esDeAgrupacion(palabraSimple.getCadena())) {
                            crearToken(palabraSimple, new Color(128, 0, 128), TipoToken.AGRUPACION.getNombre());
                     }else if(sintaxis.esDePuntuacion(palabraSimple.getCadena())){
                            crearToken(palabraSimple, Color.pink, TipoToken.PUNTUACION.getNombre());
                     } else {
                            
                            analizarError(palabraSimple);
                            /*
                            crearToken(palabraSimple, Color.RED, TipoToken.ERROR.getNombre());
                            almacen.agregarAlContador();

                            */
                            
                           
                            
                     }
              } catch (DefinicionException e) {
              }

       }
       
       private void analizarError(PalabraSimple palabra) {
              int inicio = 0;
              String cadena = palabra.getCadena();
              char c = cadena.charAt(inicio);

              //el primer caracter es digito
              if (esDigito(c)) {
                     boolean encontroNoDigito = false;
                     int posicionError = -1;

                     // Buscar el primer caracter que no sea dígito
                     for (int i = inicio + 1; i < cadena.length(); i++) {
                            char q = cadena.charAt(i);
                            if (!esDigito(q)) {
                                   encontroNoDigito = true;
                                   posicionError = i;
                                   break;
                            }
                     }

                     if (encontroNoDigito) {
                            // Extraer la parte con error, desde inicio hasta el caracter que no es digito
                            String parteConError = cadena.substring(inicio, posicionError + 1);
                            crearTokenError(palabra, parteConError);

                            // Verificar si el resto son solo digitos
                            String resto = cadena.substring(posicionError + 1);

                            if (!resto.isEmpty()) {
                                   if (sintaxis.esNumero(resto)) {
                                          Token token = new Token(TipoToken.NUMERO_ENTERO.getNombre(),
                                                  Color.GREEN, resto, palabra.getFila(),
                                                  palabra.getColumna() + posicionError + 1, palabra.isEsFinal());
                                          token.setPosicionCaracter(palabra.getPosicionCaracter() + posicionError + 1);
                                          almacen.getListaTokens().add(token);
                                   } else if (sintaxis.esDecimal(resto)) {
                                          Token token = new Token(TipoToken.NUMERO_DECIMAL.getNombre(),
                                                  Color.BLACK, resto, palabra.getFila(),
                                                  palabra.getColumna() + posicionError + 1, palabra.isEsFinal());
                                          token.setPosicionCaracter(palabra.getPosicionCaracter() + posicionError + 1);
                                          almacen.getListaTokens().add(token);
                                   } else {
                                          // Si el resto no es número válido, marcarlo como error también
                                          crearTokenError(palabra, resto);
                                   }
                            }
                     } else {
                            // Toda la cadena son dígitos - es número válido
                            Token token = new Token(TipoToken.NUMERO_ENTERO.getNombre(),
                                    Color.GREEN, cadena, palabra.getFila(),
                                    palabra.getColumna(), palabra.isEsFinal());
                            token.setPosicionCaracter(palabra.getPosicionCaracter());
                            almacen.getListaTokens().add(token);
                     }
              } else {
                     // Si no empieza con dígito, marca toda la palabra como error
                     crearTokenError(palabra, cadena);
              }

              almacen.agregarAlContador();
       }

       private void crearTokenError(PalabraSimple palabra, String texto) {
              Token token = new Token(TipoToken.ERROR.getNombre(),
                      Color.RED, texto, palabra.getFila(), palabra.getColumna(), palabra.isEsFinal());
              almacen.getListaTokens().add(token);

       }

       private boolean esDigito(char c) {
              return c >= '0' && c <= '9';
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

       public Sintaxis getSintaxis() {
              return sintaxis;
       }

       public void setSintaxis(Sintaxis sintaxis) {
              this.sintaxis = sintaxis;
       }


       public List<DefinicionToken> getListaDefinicionTokens() {
              return listaDefinicionTokens;
       }

       public void setListaDefinicionTokens(List<DefinicionToken> listaDefinicionTokens) {
              this.listaDefinicionTokens = listaDefinicionTokens;
       }
       
       
}
