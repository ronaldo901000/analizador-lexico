package com.ronaldo.analizadorlexico.backend.comparacion;

import com.ronaldo.analizadorlexico.backend.DefinicionException;
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
       private List <PalabraSimple> palabrasSimples;
       private List <DefinicionToken> listaDefinicionTokens;
       
       public Comparador(AlmacenTokens almacen, List <PalabraSimple> palabrasSimples){
              sintaxis= new Sintaxis();
              this.almacen=almacen;
              this.palabrasSimples=palabrasSimples;
       }
       
       public void iniciarComparacion(){
              sintaxis.setListaDefinicionTokens(listaDefinicionTokens);
              for (int i = 0; i < palabrasSimples.size(); i++) {
                     evaluarTipoToken(palabrasSimples.get(i));
              }
       }
       
       /**
        * 
        * @param palabraSimple 
        */
       public void evaluarTipoToken(PalabraSimple palabraSimple) {
              try {
                     if (sintaxis.esComentarioLinea(palabraSimple.getCadena())) {
                            crearToken(palabraSimple, Color.GREEN, TipoToken.COMENTARIO_LINEA.getNombre());
                     } else if (sintaxis.esCadena(palabraSimple.getCadena())) {
                            crearToken(palabraSimple, Color.ORANGE, TipoToken.CADENA.getNombre());
                     } else if (sintaxis.esPalabraReservada(palabraSimple.getCadena())) {
                            crearToken(palabraSimple, Color.BLUE, TipoToken.PALABRA_RESERVADA.getNombre());
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
                     } else {
                            crearToken(palabraSimple, Color.RED, TipoToken.ERROR.getNombre());

                     }
              } catch (DefinicionException e) {
              }

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

       public void setPalabrasSimples(List<PalabraSimple> palabrasSimples) {
              this.palabrasSimples = palabrasSimples;
       }

       public List<DefinicionToken> getListaDefinicionTokens() {
              return listaDefinicionTokens;
       }

       public void setListaDefinicionTokens(List<DefinicionToken> listaDefinicionTokens) {
              this.listaDefinicionTokens = listaDefinicionTokens;
       }
       
       
}
