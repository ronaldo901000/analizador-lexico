package com.ronaldo.analizadorlexico.backend.almacen;

import com.ronaldo.analizadorlexico.backend.almacenamiento.DefinicionToken;
import com.ronaldo.analizadorlexico.backend.enums.TipoToken;
import com.ronaldo.analizadorlexico.backend.token.Token;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author ronaldo
 */
public class AlmacenTokens {

       private List<Token> listaTokens;
       private int contadorErrores;
       private static final int CIEN=100;
       
       
       public AlmacenTokens() {
              listaTokens = new ArrayList<>();
       }

       public void agregarAlContador(){
              contadorErrores++;
       }
       public List<Token> getListaTokens() {
              return listaTokens;
       }

       public void setListaTokens(List<Token> listaTokens) {
              this.listaTokens = listaTokens;
       }

       public boolean todosLosTokensSonCorrectos() {
              for (int i = 0; i < listaTokens.size(); i++) {
                     if (listaTokens.get(i).getTipo().equals(TipoToken.ERROR.getNombre())) {
                            return false;
                     }
              }
              return true;
       }

       public int getContadorErrores() {
              return contadorErrores;
       }

       public void setContadorErrores(int contadorErrores) {
              this.contadorErrores = contadorErrores;
       }

       public double obtenerPorcentajeTokensValidos() {
              try {
                     int total = listaTokens.size();
                     int validos = obtenerCantidadValidos();
                     System.out.println("total tokens: " + total);
                     System.out.println("validos: " + validos);

                     return ((double) validos / total) * CIEN;

              } catch (Exception e) {
                     return 0;
              }
       }


       private int obtenerCantidadValidos() {
              int contador=0;
              for (int i = 0; i < listaTokens.size(); i++) {
                     if (!listaTokens.get(i).getTipo().equals(TipoToken.ERROR.getNombre())) {
                            contador++;
                     }
              }
              return contador;
       }
}
