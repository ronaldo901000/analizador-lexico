package com.ronaldo.analizadorlexico.backend.almacenamiento;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ronaldo
 */
public class ArchivadorExterno {
       private List<DefinicionToken> listadoDefinicionTokens;
       
       public ArchivadorExterno(){
              listadoDefinicionTokens= new ArrayList<>();
       }

       public List<DefinicionToken> getListadoDefinicionTokens() {
              return listadoDefinicionTokens;
       }
       
       public void agregarDefinicion(DefinicionToken definicion){
              listadoDefinicionTokens.add(definicion);
       }
       
       public void eliminarDefiniciones(){
              listadoDefinicionTokens.clear();
       }
       
}
