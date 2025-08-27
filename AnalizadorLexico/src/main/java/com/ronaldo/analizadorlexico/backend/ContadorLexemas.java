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
              List<ResumenLexema> resumenes = new ArrayList<>();
              for (int i = 0; i < tokens.size(); i++) {
                     if (!tokens.get(i).isYaSeReviso()) {
                            String primerLexema = tokens.get(i).getLexema();
                            int contador = 1;
                            tokens.get(i).setYaSeReviso(true);
                            for (int j = i + 1; j < tokens.size(); j++) {
                                   if (tokens.get(j).getLexema().equals(primerLexema)) {
                                          tokens.get(j).setYaSeReviso(true);
                                          contador++;
                                   }
                            }
                            ResumenLexema resumen = new ResumenLexema(primerLexema, tokens.get(i).getTipo(), contador);
                            resumenes.add(resumen);
                     }
              }
              reiniciarEstadoToken(tokens);
              return resumenes;
       }
        public void reiniciarEstadoToken(List<Token> tokens){
               for (int i = 0; i <tokens.size(); i++) {
                      tokens.get(i).setYaSeReviso(false);
               }
        }
}
