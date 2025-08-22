package com.ronaldo.analizadorlexico.backend;

import com.ronaldo.analizadorlexico.backend.almacenamiento.AlmacenTokens;
import com.ronaldo.analizadorlexico.backend.almacenamiento.Verificador;
import com.ronaldo.analizadorlexico.backend.comparacion.Comparador;
import com.ronaldo.analizadorlexico.backend.lectura.CargadorDeTexto;
import com.ronaldo.analizadorlexico.backend.lectura.Impresor;
import com.ronaldo.analizadorlexico.frontend.FramePrincipal;
import javax.swing.JTextPane;


/**
 *
 * @author ronaldo
 */
public class Motor {
       private AlmacenTokens almacen;
       private CargadorDeTexto cargador;
       private Impresor impresor;
       private Verificador verificador;
       private Comparador comparador;

       public void iniciar() {
              almacen= new AlmacenTokens();
              impresor = new Impresor();
              comparador = new Comparador(almacen);
              cargador = new CargadorDeTexto(this);
              FramePrincipal frame = new FramePrincipal(this);
              
              verificador = new Verificador();
              
              frame.setVisible(true);
       }

       public void buscarPalabra(JTextPane txtPane, String palabraBuscada) {
              impresor.imprimirBusqueda(cargador.getPalabras(), txtPane, palabraBuscada);
       }
       
       public void usarComparador(){
              comparador.setPalabrasSimples(cargador.getPalabras());
              comparador.setListaDefinicionTokens(verificador.getArchivador().getListadoDefinicionTokens());
              comparador.iniciarComparacion();
       }

       public CargadorDeTexto getCargador() {
              return cargador;
       }

       public Impresor getImpresor() {
              return impresor;
       }

       public Verificador getVerificador() {
              return verificador;
       }

       public AlmacenTokens getAlmacen() {
              return almacen;
       }

       public void setAlmacen(AlmacenTokens almacen) {
              this.almacen = almacen;
       }

       public Comparador getComparador() {
              return comparador;
       }

       public void setComparador(Comparador comparador) {
              this.comparador = comparador;
       }

}
