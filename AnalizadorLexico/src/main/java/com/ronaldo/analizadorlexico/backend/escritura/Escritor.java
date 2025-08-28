package com.ronaldo.analizadorlexico.backend.escritura;

import com.ronaldo.analizadorlexico.backend.ResumenLexema;
import com.ronaldo.analizadorlexico.backend.almacenamiento.DefinicionToken;
import com.ronaldo.analizadorlexico.backend.token.Token;
import java.io.File;
import java.io.FileWriter;
import java.util.List;

/**
 *
 * @author ronaldo
 */
public class Escritor {

       private static final String SALTO_LINEA = "\n";
       private static final char COMILLA = '"';
       private static final char DOS_PUNTOS = ':';
       private static final char CORCHETE_ABIERTO = '{';
       private static final char CORCHETE_CERRADO = '}';
       private static final char LLAVE_ABRIR = '[';
       private static final char LLAVE_CIERRE = ']';
       private static final char COMA = ',';
       private static final String TABULADO = "  ";

       public File reescribirArchivoEntrada(String texto, File file) {
              try (FileWriter writer = new FileWriter(file)) {
                     writer.write(texto);
              } catch (Exception e) {
                     e.printStackTrace();
              }
              return file;
       }

       /**
        * 
        * @param definiciones
        * @param file
        * @return 
        */
       public File reescribirArchivo(List<DefinicionToken> definiciones, File file) {
              try (FileWriter writer = new FileWriter(file)) {
                     writer.write(CORCHETE_ABIERTO);
                     writer.write(SALTO_LINEA);

                     for (int i = 0; i < 4; i++) {
                            writer.write(TABULADO + COMILLA + definiciones.get(i).getNombre() + COMILLA + DOS_PUNTOS + " " + LLAVE_ABRIR);
                            for (int j = 0; j < definiciones.get(i).getElementos().size(); j++) {
                                   writer.write(COMILLA + definiciones.get(i).getElementos().get(j) + COMILLA + COMA + " ");
                            }
                            writer.write(LLAVE_CIERRE + " " + COMA);
                            writer.write(SALTO_LINEA);
                     }
                     writer.write(TABULADO + COMILLA + "comentarios:" + COMILLA + DOS_PUNTOS + " " + CORCHETE_ABIERTO);
                     writer.write(SALTO_LINEA);
                     for (int i = 4; i < definiciones.size(); i++) {
                            writer.write(TABULADO + COMILLA + definiciones.get(i).getNombre() + COMILLA + DOS_PUNTOS + " ");
                            for (int j = 0; j < definiciones.get(i).getElementos().size(); j++) {
                                   writer.write(COMILLA + definiciones.get(i).getElementos().get(j) + COMILLA + COMA + " ");
                            }
                            writer.write(SALTO_LINEA);

                     }
                     writer.write(CORCHETE_CERRADO);
                     writer.write(SALTO_LINEA);
                     writer.write(CORCHETE_CERRADO);
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
                            writer.write(SALTO_LINEA);
                            escribirReporteTokens(writer, tokensValidos);
                            writer.write(SALTO_LINEA);
                            writer.write(SALTO_LINEA);
                            writer.write(SALTO_LINEA);
                            writer.write("Lexema, Tipo Token, Cantidad Repetido");
                            writer.write(SALTO_LINEA);
                            escribirResumenes(writer, resumenes);
                     }
                     if (tokensErroneos != null) {
                            writer.write("Tipo, Simbolo o Cadena, Fila, Columna");
                            writer.write(SALTO_LINEA);
                            escribirReporteTokens(writer, tokensErroneos);
                            writer.write(SALTO_LINEA);
                            writer.write(SALTO_LINEA);
                            writer.write(SALTO_LINEA);
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

       /**
        *
        * @param writer
        * @param resumenLexemas
        * @throws Exception
        */
       private void escribirResumenes(FileWriter writer, List<ResumenLexema> resumenLexemas) throws Exception {
              for (int i = 0; i < resumenLexemas.size(); i++) {
                     writer.write(resumenLexemas.get(i).getLexema() + ", " + resumenLexemas.get(i).getTipo() + ", "
                             + resumenLexemas.get(i).getCantidadRepetida());
                     writer.write("\n");
              }
       }

}
