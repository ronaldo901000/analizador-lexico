package com.ronaldo.analizadorlexico.backend;

import com.ronaldo.analizadorlexico.backend.almacen.AlmacenPalabrasSimples;
import com.ronaldo.analizadorlexico.backend.almacen.AlmacenTokens;
import com.ronaldo.analizadorlexico.backend.almacenamiento.Verificador;
import com.ronaldo.analizadorlexico.backend.carga.ProcesadorPalabras;
import com.ronaldo.analizadorlexico.backend.comparacion.Comparador;
import com.ronaldo.analizadorlexico.backend.lectura.CargadorDeTexto;
import com.ronaldo.analizadorlexico.backend.complementos.Impresor;
import com.ronaldo.analizadorlexico.backend.token.Token;
import com.ronaldo.analizadorlexico.frontend.FramePrincipal;
import java.io.File;
import java.util.List;
import javax.swing.JTextPane;

/**
 *
 * @author ronaldo
 */
public class Motor {

       private AlmacenTokens almacenTokens;
       private CargadorDeTexto cargador;
       private Impresor impresor;
       private Verificador verificador;
       private Comparador comparador;
       private AlmacenPalabrasSimples almacenSimples;
       private ProcesadorPalabras procesador;
       private boolean hayJson;

       public void iniciar() {
              procesador= new ProcesadorPalabras(this);
              almacenTokens = new AlmacenTokens();
              almacenSimples = new AlmacenPalabrasSimples();
              impresor = new Impresor();
              comparador = new Comparador(almacenTokens, almacenSimples.getPalabrasSimples());
              cargador = new CargadorDeTexto();
              verificador = new Verificador();
              FramePrincipal frame = new FramePrincipal(this);
              frame.setVisible(true);
       }

       public void buscarPalabra(JTextPane txtPane, String palabraBuscada) {
              impresor.imprimirBusqueda(almacenTokens.getListaTokens(), txtPane, palabraBuscada);
       }

       public void iniciarCreacionTokens() {
              comparador.setListaDefinicionTokens(verificador.getArchivador().getListadoDefinicionTokens());

              comparador.iniciarComparacion();
       }

       /**
        * metodo que se encarga de controlar la peticion del frame de analizar, generar tokens y pintar las palabras
        * @param archivoDeEntrada
        * @param txtPaneEntrada 
        */
       public void realizarAccionesAccionesTexto(File archivoDeEntrada, JTextPane txtPaneEntrada) {
              cargador.pedirLecturaArchivo(archivoDeEntrada, txtPaneEntrada);

              if (hayJson) {
                     procesador.analizarTexto(txtPaneEntrada.getText(),txtPaneEntrada);
                     impresor.colorearTexto(almacenTokens.getListaTokens(), txtPaneEntrada);
              }

       }
       

       public void realizarAccionesEdicionTexto(JTextPane txtPaneEntrada) {
              if (hayJson) {
                     procesador.analizarTexto(txtPaneEntrada.getText(), txtPaneEntrada);
                     impresor.colorearTexto(almacenTokens.getListaTokens(), txtPaneEntrada);
              }

       }

       public void colorear(JTextPane textPane) {
              List<Token> tokens = almacenTokens.getListaTokens();
              impresor.colorearToken(textPane, tokens.get(tokens.size() - 1));

       }

       public void leerYCargarJson(File archivo) {
              verificador.getArchivador().eliminarDefiniciones();
              verificador.leerConfiguracion(archivo);
              comparador.getSintaxis().setListaDefinicionTokens(verificador.getArchivador().getListadoDefinicionTokens());
              hayJson=true;
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

       public Comparador getComparador() {
              return comparador;
       }

       public void setComparador(Comparador comparador) {
              this.comparador = comparador;
       }

       public AlmacenTokens getAlmacenTokens() {
              return almacenTokens;
       }

       public void setAlmacenTokens(AlmacenTokens almacenTokens) {
              this.almacenTokens = almacenTokens;
       }

       public AlmacenPalabrasSimples getAlmacenSimples() {
              return almacenSimples;
       }

       public void setAlmacenSimples(AlmacenPalabrasSimples almacenSimples) {
              this.almacenSimples = almacenSimples;
       }

}
