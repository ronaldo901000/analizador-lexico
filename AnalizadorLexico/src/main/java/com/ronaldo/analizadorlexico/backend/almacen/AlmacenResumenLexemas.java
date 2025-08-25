package com.ronaldo.analizadorlexico.backend.almacen;

import com.ronaldo.analizadorlexico.backend.ResumenLexema;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ronaldo
 */
public class AlmacenResumenLexemas  {
       private List<ResumenLexema> resumenes;
       
       public AlmacenResumenLexemas(){
              resumenes= new ArrayList<>();
       }

       public List<ResumenLexema> getResumenes() {
              return resumenes;
       }

       public void setResumenes(List<ResumenLexema> resumenes) {
              this.resumenes = resumenes;
       }

       
       
}
