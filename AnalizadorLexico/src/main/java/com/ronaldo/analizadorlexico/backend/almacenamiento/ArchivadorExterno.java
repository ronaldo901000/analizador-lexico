package com.ronaldo.analizadorlexico.backend.almacenamiento;

import com.ronaldo.analizadorlexico.backend.enums.TipoToken;
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

       /**
        * 
        * @param indice
        * @param elemento 
        */
       public void agregarElementoDefinicion(int indice, String elemento) {
              //si no encuentra el indice no hace nada
              if(listadoDefinicionTokens.get(indice)==null){
                     return;
              }
              // se agrega un nuevo elemento
              listadoDefinicionTokens.get(indice).agregarElemento(elemento);

       }

       /**
        * 
        * 
        * @param indice
        * @param indiceElemento 
        */
       public void eliminarElementoDefinicion(int indice, int indiceElemento) {
              if (listadoDefinicionTokens.get(indice) == null) {
                     return;
              }
              if(listadoDefinicionTokens.get(indice).getElementos().get(indiceElemento)==null){
                     return;
              }
              // se elimina el elemento en el indice seleccionado
              listadoDefinicionTokens.get(indice).getElementos().remove(indiceElemento);
       }
       
       public List<DefinicionToken> darDefinicionesJson(){
              List<DefinicionToken> definiciones = new ArrayList<>();
              for (int i = 0; i < listadoDefinicionTokens.size(); i++) {
                     if(!listadoDefinicionTokens.get(i).getNombre().equals(TipoToken.CADENA.getNombre()) &&
                             !listadoDefinicionTokens.get(i).getNombre().equals(TipoToken.ERROR.getNombre()) &&
                             !listadoDefinicionTokens.get(i).getNombre().equals(TipoToken.IDENTIFICADOR.getNombre())&&
                             !listadoDefinicionTokens.get(i).getNombre().equals(TipoToken.NUMERO_DECIMAL.getNombre()) &&
                             !listadoDefinicionTokens.get(i).getNombre().equals(TipoToken.NUMERO_ENTERO.getNombre())){
                            definiciones.add(listadoDefinicionTokens.get(i));
                     }
              }
              return definiciones;
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
