package com.ronaldo.analizadorlexico.backend.escritura;

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
       public void escribirReporteExportacion(List<Token> tokensValidos, File file, List<Token> tokensErroneos) {
              try (FileWriter writer = new FileWriter(file)) {
                     if (tokensValidos != null) {
                            escribirReporteTokens(writer, tokensValidos);
                            writer.write("\n");
                            writer.write("\n");
                            writer.write("\n");
                     }
                     if (tokensErroneos != null) {
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
                     writer.write(tokens.get(i).getTipo() + ", " + tokens.get(i).getLexema() + ", "
                             + tokens.get(i).getFila() + ", " + tokens.get(i).getColumna());
                     writer.write("\n");
              }
       }

}
