package com.ronaldo.analizadorlexico.backend.almacen;

import com.ronaldo.analizadorlexico.backend.lectura.PalabraSimple;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ronaldo
 */
public class AlmacenPalabrasSimples  {

       private List<PalabraSimple> palabrasSimples;

       public AlmacenPalabrasSimples(){
              palabrasSimples= new ArrayList<>();
       }

       public List<PalabraSimple> getPalabrasSimples() {
              return palabrasSimples;
       }

       public void setPalabrasSimples(List<PalabraSimple> palabrasSimples) {
              this.palabrasSimples = palabrasSimples;
       }
       
       

}
