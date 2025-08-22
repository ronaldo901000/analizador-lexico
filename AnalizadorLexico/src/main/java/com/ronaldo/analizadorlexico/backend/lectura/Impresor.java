package com.ronaldo.analizadorlexico.backend.lectura;

import com.ronaldo.analizadorlexico.backend.token.Token;
import java.awt.Color;
import java.util.List;
import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

/**
 *
 * @author ronaldo
 */
public class Impresor {

       /**
        * metodo que se encarga de imprimir texto en el jtextArea y resalta la palabra buscada
        *
        * @param palabras
        * @param txtArea
        */
       public void imprimirBusqueda(List<PalabraSimple> palabras, JTextPane txtPane, String palabraBuscada) {
              StyledDocument doc = txtPane.getStyledDocument();
              Style estiloNormal = doc.addStyle("normal", null);
              StyleConstants.setForeground(estiloNormal, Color.BLACK);

              Style estiloResaltado = doc.addStyle("resaltado", null);
              StyleConstants.setForeground(estiloResaltado, Color.RED);
              StyleConstants.setBold(estiloResaltado, true);
              StyleConstants.setBackground(estiloResaltado, Color.YELLOW);

              try {
                     doc.remove(0, doc.getLength());

                     for (int i = 0; i < palabras.size(); i++) {
                            PalabraSimple palabra = palabras.get(i);
                            String texto = palabra.getCadena();

                            if (texto.equals(palabraBuscada)) {
                                   doc.insertString(doc.getLength(), texto, estiloResaltado);
                            } else {
                                   doc.insertString(doc.getLength(), texto, estiloNormal);
                            }
                            if (palabra.isEsFinal()) {
                                   doc.insertString(doc.getLength(), "\n", estiloNormal);
                            } else {
                                   doc.insertString(doc.getLength(), " ", estiloNormal);
                            }
                     }

              } catch (BadLocationException ex) {
                     ex.printStackTrace();
              }
       }

       public void imprimirEnPane(JTextPane textPane, Token token) {
              StyledDocument doc = textPane.getStyledDocument();
              Color color = token.getColor();

              // Crear un estilo para el token y aplicar el color
              Style style = textPane.addStyle("TokenStyle", null);
              StyleConstants.setForeground(style, color);

              try {
                     // Insertar el lexema del token
                     doc.insertString(doc.getLength(), token.getLexema(), style);

                     // Si el token es final, agregar salto de línea
                     if (token.isEsFinal()) {
                            doc.insertString(doc.getLength(), "\n", style);
                     }

              } catch (BadLocationException e) {
                     e.printStackTrace();
              }
       }

}
