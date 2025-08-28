package com.ronaldo.analizadorlexico.backend.comparacion;

import com.ronaldo.analizadorlexico.backend.exception.DefinicionException;
import com.ronaldo.analizadorlexico.backend.almacen.AlmacenTokens;
import com.ronaldo.analizadorlexico.backend.almacenamiento.DefinicionToken;
import com.ronaldo.analizadorlexico.backend.analisis.CreadorDeTokens;
import com.ronaldo.analizadorlexico.backend.analisis.RecuperadorDeErrores;
import com.ronaldo.analizadorlexico.backend.enums.TipoToken;
import com.ronaldo.analizadorlexico.backend.lectura.PalabraSimple;
import java.awt.Color;
import java.util.List;
import javax.swing.JOptionPane;
/**
 *
 * @author ronaldo
 */
public class Comparador {
       private Sintaxis sintaxis;
       private RecuperadorDeErrores recuperador;
       CreadorDeTokens creador;

       private List <DefinicionToken> listaDefinicionTokens;
       
       public Comparador(AlmacenTokens almacen){
              sintaxis= new Sintaxis();
              recuperador= new RecuperadorDeErrores(sintaxis, almacen);
              creador= new CreadorDeTokens(almacen);
       }
       

       
       /**
        * 
        * @param palabraSimple 
        */
       public void evaluarTipoToken(PalabraSimple palabraSimple) {
              try {
                     if (sintaxis.esComentarioLinea(palabraSimple.getCadena())) {
                            creador.crearToken(palabraSimple, Color.GREEN, TipoToken.COMENTARIO_LINEA.getNombre());
                     } else if (sintaxis.esPalabraReservada(palabraSimple.getCadena())) {
                            creador.crearToken(palabraSimple, Color.BLUE, TipoToken.PALABRA_RESERVADA.getNombre());
                     } else if (sintaxis.esCadena(palabraSimple.getCadena())) {
                            creador.crearToken(palabraSimple, Color.ORANGE, TipoToken.CADENA.getNombre());
                     } else if (sintaxis.esDecimal(palabraSimple.getCadena())) {
                            creador.crearToken(palabraSimple, Color.BLACK, TipoToken.NUMERO_DECIMAL.getNombre());
                     } else if (sintaxis.esNumero(palabraSimple.getCadena())) {
                            creador.crearToken(palabraSimple, Color.GREEN, TipoToken.NUMERO_ENTERO.getNombre());
                     } else if (sintaxis.esIdentificador(palabraSimple.getCadena())) {
                            creador.crearToken(palabraSimple, new Color(139, 69, 19), TipoToken.IDENTIFICADOR.getNombre());
                     } else if (sintaxis.esOperador(palabraSimple.getCadena())) {
                            creador.crearToken(palabraSimple, Color.yellow, TipoToken.OPERADOR.getNombre());
                     } else if (sintaxis.esDeAgrupacion(palabraSimple.getCadena())) {
                            creador.crearToken(palabraSimple, new Color(128, 0, 128), TipoToken.AGRUPACION.getNombre());
                     }else if(sintaxis.esDePuntuacion(palabraSimple.getCadena())){
                            creador.crearToken(palabraSimple, Color.pink, TipoToken.PUNTUACION.getNombre());
                     } else { 
                            recuperador.analizarError(palabraSimple);
                     }
              } catch (DefinicionException e) {
                     JOptionPane.showMessageDialog(null,e.getMessage());
              }

       }

       public Sintaxis getSintaxis() {
              return sintaxis;
       }

       public void setSintaxis(Sintaxis sintaxis) {
              this.sintaxis = sintaxis;
       }


       public List<DefinicionToken> getListaDefinicionTokens() {
              return listaDefinicionTokens;
       }

       public void setListaDefinicionTokens(List<DefinicionToken> listaDefinicionTokens) {
              this.listaDefinicionTokens = listaDefinicionTokens;
       }
       
       
}
