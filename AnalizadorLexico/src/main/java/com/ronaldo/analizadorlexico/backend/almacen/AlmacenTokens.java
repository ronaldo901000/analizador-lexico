package com.ronaldo.analizadorlexico.backend.almacen;

import com.ronaldo.analizadorlexico.backend.token.Token;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author ronaldo
 */
public class AlmacenTokens {

       private List<Token> listaTokens;

       public AlmacenTokens() {
              listaTokens = new ArrayList<>();
       }

       public List<Token> getListaTokens() {
              return listaTokens;
       }

       public void setListaTokens(List<Token> listaTokens) {
              this.listaTokens = listaTokens;
       }


}
