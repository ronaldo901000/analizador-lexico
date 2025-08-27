package com.ronaldo.analizadorlexico.backend.busqueda;

import com.ronaldo.analizadorlexico.backend.complementos.Impresor;
import javax.swing.JTextPane;

/**
 *
 * @author ronaldo
 */
public class BuscadorDePatrones {
       
       private Impresor impresor;
       
       public BuscadorDePatrones(Impresor impresor){
              this.impresor=impresor;
       }

       /**
        * 
        * @param texto
        * @param patron
        * @param txtPane 
        */
       public void buscarPatrones(String texto, String patron, JTextPane txtPane) {
              int indice = 0;
              int longPatron = patron.length();
              txtPane.setText("");
              while (indice < texto.length()) {
                     boolean patronEncontrado = false;

                     if (indice + longPatron <= texto.length()) {
                            String segmento = texto.substring(indice, indice + longPatron);
                            if (segmento.equals(patron)) {
                                   impresor.imprimirLaBusqueda(txtPane, segmento, true);
                                   indice += longPatron;
                                   patronEncontrado = true;
                            }
                     }
                     if (!patronEncontrado) {
                            impresor.imprimirLaBusqueda(txtPane, texto.charAt(indice) + "", false);
                            indice++;
                     }
              }
       }

}
