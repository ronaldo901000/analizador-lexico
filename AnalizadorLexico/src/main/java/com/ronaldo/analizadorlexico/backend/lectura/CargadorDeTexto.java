package com.ronaldo.analizadorlexico.backend.lectura;
import com.ronaldo.analizadorlexico.backend.Motor;
import com.ronaldo.analizadorlexico.backend.almacenamiento.AlmacenTokens;
import com.ronaldo.analizadorlexico.backend.comparacion.Comparador;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JTextPane;

/**
 *
 * @author ronaldo
 */
public class CargadorDeTexto {
       private List <PalabraSimple> palabras;
       private LectorDeTexto lector;
       private Motor motor;

       public CargadorDeTexto(Motor motor) {
              this.motor=motor;
              palabras= new ArrayList<>();
              lector= new LectorDeTexto(motor);
       }
       
       public void pedirLecturaArchivo(File file, JTextPane txtPane) {
              lector.leerTexto(file, palabras, txtPane);
              motor.usarComparador();
              for (int i = 0; i < motor.getAlmacen().getListaTokens().size(); i++) {
                     motor.getImpresor().imprimirEnPane(txtPane, motor.getAlmacen().getListaTokens().get(i));
              }
       }

       public List<PalabraSimple> getPalabras() {
              return palabras;
       }

       public LectorDeTexto getLector() {
              return lector;
       }
       
       
       
}
