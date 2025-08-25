package com.ronaldo.analizadorlexico.backend.almacenamiento;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ronaldo
 */
public class ArchivadorExterno {
       private List<DefinicionToken> listadoDefinicionTokens;
       
       
       public DefinicionToken obtenerDefinicionEspecifica(String tipo) {
              for (int i = 0; i < listadoDefinicionTokens.size(); i++) {
                     if (listadoDefinicionTokens.get(i).getNombre().equals(tipo)) {
                            return listadoDefinicionTokens.get(i);
                     }
              }
              return null;
       }
       
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
