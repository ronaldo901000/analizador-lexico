package com.ronaldo.analizadorlexico.backend.escritura;

import com.ronaldo.analizadorlexico.backend.ResumenLexema;
import com.ronaldo.analizadorlexico.backend.token.Token;
import java.io.File;
import java.io.FileWriter;
import java.util.List;

/**
 *
 * @author ronaldo
 */
public class Escritor {
       
       public File reescribirArchivo(String texto, File file) {
              try (FileWriter writer = new FileWriter(file)) {
                     writer.write(texto);
              } catch (Exception e) {
                     e.printStackTrace();
              }
              return file;
       }

       /**
        *
        * @param tokensValidos
        * @param file
        * @param tokensErroneos
        */
       public void escribirReporteExportacion(List<Token> tokensValidos, File file, List<Token> tokensErroneos, List<ResumenLexema> resumenes) {
              try (FileWriter writer = new FileWriter(file)) {
                     if (tokensValidos != null) {
                            writer.write("Nombre Token, Lexema, Fila, Columna");
                            writer.write("\n");
                            escribirReporteTokens(writer, tokensValidos);
                            writer.write("\n");
                            writer.write("\n");
                            writer.write("\n");
                            writer.write("Lexema, Tipo Token, Cantidad Repetido");
                            writer.write("\n");
                            escribirResumenes(writer, resumenes);
                     }
                     if (tokensErroneos != null) {
                            writer.write("Tipo, Simbolo o Cadena, Fila, Columna");
                            writer.write("\n");
                            escribirReporteTokens(writer, tokensErroneos);
                            writer.write("\n");
                            writer.write("\n");
                            writer.write("\n");
                     }

              } catch (Exception e) {
                     e.printStackTrace();
              }
       }

       /**
        *
        * @param writer
        * @param tokens
        * @throws Exception
        */
       private void escribirReporteTokens(FileWriter writer, List<Token> tokens) throws Exception {
              for (int i = 0; i < tokens.size(); i++) {
                     Token token = tokens.get(i);
                     writer.write(token.getTipo() + ", " + token.getLexema() + ", "
                             + (token.getFila() + 1) + ", " + (token.getColumna() + 1));
                     writer.write("\n");
              }
       }

       private void escribirResumenes(FileWriter writer, List<ResumenLexema> resumenLexemas) throws Exception {
              for (int i = 0; i < resumenLexemas.size(); i++) {
                     writer.write(resumenLexemas.get(i).getLexema()+ ", " + resumenLexemas.get(i).getTipo()+ ", "
                             + resumenLexemas.get(i).getCantidadRepetida());
                     writer.write("\n");
              }
       }
       
       

}
