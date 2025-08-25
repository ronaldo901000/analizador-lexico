package com.ronaldo.analizadorlexico.backend.almacenamiento;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author ronaldo
 */
public class DefinicionToken {
       
       private String nombre;
       private List<String> elementos;

       public DefinicionToken(String nombre) {
              this.nombre = nombre;
              this.elementos = elementos;
              elementos= new ArrayList<>();
       }

       public void agregarElemento(String elementoNuevo){
              elementos.add(elementoNuevo);
       }
       public String getNombre() {
              return nombre;
       }


       public List<String> getElementos() {
              return elementos;
       }

       public void setElementos(List<String> elementos) {
              this.elementos = elementos;
       }
       
       

}
