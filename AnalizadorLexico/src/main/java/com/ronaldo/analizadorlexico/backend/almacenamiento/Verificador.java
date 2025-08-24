package com.ronaldo.analizadorlexico.backend.almacenamiento;

import com.ronaldo.analizadorlexico.backend.enums.TipoToken;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ronaldo
 */
public class Verificador {

       private ArchivadorExterno archivador;

       public Verificador() {
              archivador = new ArchivadorExterno();
       }

       public ArchivadorExterno getArchivador() {
              return archivador;
       }

       /**
        *
        * @param file
        */
       public void leerConfiguracion(File file) {
              try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                     String linea;

                     while ((linea = reader.readLine()) != null) {
                            linea = linea.trim();

                            if (!linea.isEmpty()) {
                                   obtenerTipo(linea);
                            }
                     }

              } catch (IOException e) {
                     e.printStackTrace();
              }
       }

       /**
        *
        * @param texto
        */
       private void obtenerTipo(String texto) {
              int inicio = texto.indexOf("\"");
              int fin = texto.indexOf("\"", inicio + 1);

              if (inicio != -1 && fin != -1) {
                     String token = texto.substring(inicio + 1, fin);
                     int llaveInicio = texto.indexOf("[") + 1;
                     int llavefin = texto.length() - 2;
                     if (llaveInicio == -1 || llavefin == -1) {
                            return;
                     }
                     if (!token.equals(TipoToken.COMENTARIO_LINEA.getNombre())
                             && !token.equals(TipoToken.BLOQUE_INICIO.getNombre()) && !token.endsWith(TipoToken.BLOQUE_FIN.getNombre())) {
                            String elementos = texto.substring(llaveInicio, llavefin);
                            organizarTipo(token, elementos);
                     } else {
                            organizarTipo(token, texto);
                     }

              } else {

              }
       }

       /**
        *
        * @param tipoToken
        * @param elementos
        */
       public void organizarTipo(String tipoToken, String elementos) {
              if (tipoToken.equals(TipoToken.PALABRA_RESERVADA.getNombre())) {
                     DefinicionToken definicionToken = new DefinicionToken(TipoToken.PALABRA_RESERVADA.getNombre());
                     procesarElementos(elementos, definicionToken);
                     archivador.agregarDefinicion(definicionToken);
              }
              if (tipoToken.equals(TipoToken.OPERADOR.getNombre())) {
                     DefinicionToken definicionToken = new DefinicionToken(TipoToken.OPERADOR.getNombre());
                     procesarElementos(elementos, definicionToken);
                     archivador.agregarDefinicion(definicionToken);
              }
              if (tipoToken.equals(TipoToken.PUNTUACION.getNombre())) {
                     DefinicionToken definicionToken = new DefinicionToken(TipoToken.PUNTUACION.getNombre());
                     List<String> elementosPuntuacion = extraerSignosDePuntuacionEntreComillas(elementos);
                     definicionToken.setElementos(elementosPuntuacion);

              }
              if (tipoToken.equals(TipoToken.AGRUPACION.getNombre())) {
                     DefinicionToken definicionToken = new DefinicionToken(TipoToken.AGRUPACION.getNombre());
                     procesarElementos(elementos, definicionToken);
                     archivador.agregarDefinicion(definicionToken);
              }
              if (tipoToken.equals(TipoToken.COMENTARIO_LINEA.getNombre())) {
                     DefinicionToken definicionToken = new DefinicionToken(TipoToken.COMENTARIO_LINEA.getNombre());
                     procesarComentarios(elementos, definicionToken);
              }
              if (tipoToken.equals(TipoToken.BLOQUE_INICIO.getNombre())) {
                     DefinicionToken definicionToken = new DefinicionToken(TipoToken.BLOQUE_INICIO.getNombre());
                     procesarComentarios(elementos, definicionToken);
              }
              if (tipoToken.equals(TipoToken.BLOQUE_FIN.getNombre())) {
                     DefinicionToken definicionToken = new DefinicionToken(TipoToken.BLOQUE_FIN.getNombre());
                     procesarComentarios(elementos, definicionToken);
              }

       }

       /**
        *
        * @param linea
        * @param definicion
        */
       private void procesarElementos(String linea, DefinicionToken definicion) {
              if (linea.contains("\"")) {
                     String[] elementos = linea.split(",");
                     for (int i = 0; i < elementos.length; i++) {
                            int inicio = elementos[i].indexOf("\"");

                            int fin = elementos[i].indexOf("\"", inicio + 1);
                            if (inicio == -1 || fin == -1) {
                                   break;
                            }
                            String elemento = elementos[i].substring(inicio + 1, fin);
                            definicion.agregarElemento(elemento);
                     }
              }

       }

       /**
        *
        * @param texto
        * @return
        */
       public static List<String> extraerSignosDePuntuacionEntreComillas(String texto) {
              List<String> elementos = new ArrayList<>();
              int inicio = 0;

              while (true) {
                     inicio = texto.indexOf('"', inicio);
                     if (inicio == -1) {
                            break;
                     }

                     int fin = texto.indexOf('"', inicio + 1);
                     if (fin == -1) {
                            break;
                     }

                     String elemento = texto.substring(inicio + 1, fin);
                     elementos.add(elemento);

                     inicio = fin + 1;
              }

              return elementos;
       }

       /**
        *
        * @param linea
        * @param definicion
        */
       private void procesarComentarios(String linea, DefinicionToken definicion) {
              if (linea.contains(":")) {
                     String[] partes = linea.split(":");
                     if (partes.length == 2) {
                            String valor = partes[1].trim();
                            int inicio = valor.indexOf('"');
                            int fin = valor.indexOf('"', inicio + 1);
                            if (inicio != -1 && fin != -1) {
                                   String elemento = valor.substring(inicio + 1, fin);
                                   definicion.agregarElemento(elemento);
                                   archivador.agregarDefinicion(definicion);
                                   System.out.println(elemento);
                            }

                     }
              }
       }

}
