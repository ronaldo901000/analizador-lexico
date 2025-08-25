package com.ronaldo.analizadorlexico.backend;

import com.ronaldo.analizadorlexico.backend.token.Token;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ronaldo
 */
public class ContadorLexemas {
       
       /**
        * 
        * @param tokens
        */
       public List<ResumenLexema> recontarLexemas(List<Token> tokens) {
              List<Token> copia = new ArrayList<>(tokens);
              List<ResumenLexema> resumenes = new ArrayList<>();
              for (int i = 0; i < copia.size(); i++) {
                     if (!copia.get(i).isYaSeReviso()) {
                            String primerLexema = copia.get(i).getLexema();
                            int contador = 1;
                            copia.get(i).setYaSeReviso(true);
                            for (int j = i + 1; j < copia.size(); j++) {
                                   if (copia.get(j).getLexema().equals(primerLexema)) {
                                          copia.get(j).setYaSeReviso(true);
                                          contador++;
                                   }
                            }
                            ResumenLexema resumen = new ResumenLexema(primerLexema, copia.get(i).getTipo(), contador);
                            resumenes.add(resumen);
                     }

              }
              return resumenes;
       }
}
