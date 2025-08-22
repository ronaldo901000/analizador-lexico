package com.ronaldo.analizadorlexico.backend.comparacion;

import com.ronaldo.analizadorlexico.backend.almacenamiento.AlmacenTokens;
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
       
       public Comparador(AlmacenTokens almacen){
              sintaxis= new Sintaxis();
              
              this.almacen=almacen;
       }
       
       public void iniciarComparacion(){
              sintaxis.setListaDefinicionTokens(listaDefinicionTokens);
              for (int i = 0; i < palabrasSimples.size(); i++) {
                     if(sintaxis.esComentarioLinea(palabrasSimples.get(i).getCadena())){
                            asignarToken(palabrasSimples.get(i), Color.GREEN,TipoToken.COMENTARIO_LINEA.getNombre());
                     }
                     else if(sintaxis.esCadena(palabrasSimples.get(i).getCadena())){
                            asignarToken(palabrasSimples.get(i), Color.ORANGE,TipoToken.CADENA.getNombre());
                     }
                     else if(sintaxis.esPalabraReservada(palabrasSimples.get(i).getCadena())){
                            System.out.println("si es reservada");
                            asignarToken(palabrasSimples.get(i), Color.BLUE, TipoToken.PALABRA_RESERVADA.getNombre());
                     }
                     else if(sintaxis.esDecimal(palabrasSimples.get(i).getCadena())){
                            asignarToken(palabrasSimples.get(i), Color.BLACK, TipoToken.NUMERO_DECIMAL.getNombre());
                     }
                     else if(sintaxis.esNumero(palabrasSimples.get(i).getCadena())){
                            asignarToken(palabrasSimples.get(i), Color.GREEN, TipoToken.NUMERO_ENTERO.getNombre());
                     }
                     else{
                            asignarToken(palabrasSimples.get(i), Color.RED, TipoToken.ERROR.getNombre());
                     }
              }
       }

       
        public void asignarToken(PalabraSimple palabra, Color color, String tipo) {  
              Token token = new Token(tipo, color, palabra.getCadena()+" ",palabra.getFila(),
                      palabra.getColumna(), palabra.isEsFinal());
              almacen.getListaTokens().add(token);

       }

       public Sintaxis getSintaxis() {
              return sintaxis;
       }

       public void setSintaxis(Sintaxis sintaxis) {
              this.sintaxis = sintaxis;
       }

       public List<PalabraSimple> getPalabrasSimples() {
              return palabrasSimples;
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
