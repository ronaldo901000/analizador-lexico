package com.ronaldo.analizadorlexico.backend.comparacion;

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
       public boolean esComentarioLinea(String elemento) {
              DefinicionToken comentario = obtenerDefincion(TipoToken.COMENTARIO_LINEA.getNombre());
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
       public boolean esPalabraReservada(String elemento) {
              DefinicionToken palabraReservada = obtenerDefincion(TipoToken.PALABRA_RESERVADA.getNombre());
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
              if (!elemento.contains(".")) {
                     return false;
              }
              try {
                     double numero = Double.parseDouble(elemento);
                     return true;
              } catch (NumberFormatException e) {
                     return false;
              }

       }
       
       public boolean esNumero(String elemento) {
              try {
                     int numero = Integer.parseInt(elemento);
                     return true;
              } catch (NumberFormatException e) {
                     return false;
              }
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
