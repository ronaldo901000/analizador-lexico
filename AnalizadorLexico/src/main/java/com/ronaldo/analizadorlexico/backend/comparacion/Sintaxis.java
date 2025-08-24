package com.ronaldo.analizadorlexico.backend.comparacion;

import com.ronaldo.analizadorlexico.backend.DefinicionException;
import java.util.List;

import com.ronaldo.analizadorlexico.backend.almacenamiento.DefinicionToken;
import com.ronaldo.analizadorlexico.backend.enums.TipoToken;
/**
 *
 * @author ronaldo
 */
public class Sintaxis {

       private List<DefinicionToken> listaDefinicionTokens;

       /**
        * 
        * @param elemento
        * @return 
        */
       public boolean esComentarioLinea(String elemento) throws DefinicionException{
              DefinicionToken comentario = obtenerDefincion(TipoToken.COMENTARIO_LINEA.getNombre());
              if(comentario==null){
                     throw new DefinicionException("no hay definicion de comentario");
              }
              for (int i = 0; i < comentario.getElementos().size(); i++) {
                     if (elemento.equals(comentario.getElementos().get(i))) {
                            return true;
                     }
              }
              
              return false;
       }
       /**
        * 
        * @param elemento
        * @return 
        */
       public boolean esCadena(String elemento){
              if(elemento.length()==0){
                     return false;
              }
              if(elemento.charAt(0)=='"' && elemento.charAt(elemento.length()-1)=='"'){
                     return true;            
              }
              return false;
       }

       /**
        * 
        * @param elemento
        * @return 
        */
       public boolean esPalabraReservada(String elemento) throws DefinicionException{
              DefinicionToken palabraReservada = obtenerDefincion(TipoToken.PALABRA_RESERVADA.getNombre());
              if(palabraReservada==null){
              
              }
              for (int i = 0; i < palabraReservada.getElementos().size(); i++) {
                     if (elemento.equals(palabraReservada.getElementos().get(i))) {
                            return true;
                     }
              }

              return false;
       }

       /**
        * 
        * @param elemento
        * @return 
        */
       public boolean esDecimal(String elemento) {
              if (elemento == null || elemento.isEmpty()) {
                     return false;
              }

              boolean tienePunto = false;
              int digitosAntesPunto = 0;
              int digitosDespuesPunto = 0;

              for (int i = 0; i < elemento.length(); i++) {
                     char c = elemento.charAt(i);
                     if (c == '.') {
                            if (tienePunto) {
                                   return false;
                            }
                            tienePunto = true;
                            continue;
                     }

                     if (!esDigito(c)) {
                            return false; 
                     }
                     if (tienePunto) {
                            digitosDespuesPunto++;
                     } else {
                            digitosAntesPunto++;
                     }
              }
              return tienePunto && digitosDespuesPunto >= 1;
       }

       private boolean esDigito(char c) {
              return c >= '0' && c <= '9';
       }

       /**
        * 
        * @param elemento
        * @return 
        */
       public boolean esNumero(String elemento) {
              if (elemento == null || elemento.isEmpty()) {
                     return false;
              }

              for (int i = 0; i < elemento.length(); i++) {
                     if (!esDigito(elemento.charAt(i))) {
                            return false;
                     }
              }

              return true;
       }

       /**
        *
        * @param elemento
        * @return 
        */
       public boolean esIdentificador(String elemento) {
              if (elemento.length() == 0) {
                     return false;
              }
              char c = elemento.charAt(0);
              return existeEnAlfabeto(c);
       }
       
       public boolean esOperador(String elemento){
              DefinicionToken operador = obtenerDefincion(TipoToken.OPERADOR.getNombre());
              for (int i = 0; i <operador.getElementos().size(); i++) {
                     if(elemento.equals(operador.getElementos().get(i))){
                            return true;
                     }
              }
              return false;
       }
       
       public boolean esDeAgrupacion(String elemento){
              DefinicionToken agrupacion =obtenerDefincion(TipoToken.AGRUPACION.getNombre());
              for (int i = 0; i < agrupacion.getElementos().size(); i++) {
                     if(elemento.equals(agrupacion.getElementos().get(i))){
                            return true;
                     }
              }
              return false;
       }

       /**
        * 
        * @param c
        * @return 
        */
       private boolean existeEnAlfabeto(char c) {
              for (char letra = 'a'; letra <= 'z'; letra++) {
                     if (c == letra) {
                            return true;
                     }
              }

              for (char letra = 'A'; letra <= 'Z'; letra++) {
                     if (c == letra) {
                            return true;
                     }
              }
              return false;
       }

       public DefinicionToken obtenerDefincion(String tipo) {
              for (int i = 0; i < listaDefinicionTokens.size(); i++) {
                     if (listaDefinicionTokens.get(i).getNombre().equals(tipo)) {
                            return listaDefinicionTokens.get(i);
                     }
              }
              return null;
       }

       public List<DefinicionToken> getListaDefinicionTokens() {
              return listaDefinicionTokens;
       }

       public void setListaDefinicionTokens(List<DefinicionToken> listaDefinicionTokens) {
              this.listaDefinicionTokens = listaDefinicionTokens;
       }
       
       
}
