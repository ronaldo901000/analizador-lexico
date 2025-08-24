package com.ronaldo.analizadorlexico.backend.complementos;

import com.ronaldo.analizadorlexico.backend.lectura.PalabraSimple;
import com.ronaldo.analizadorlexico.backend.token.Token;
import java.awt.Color;
import java.util.List;
import javax.swing.JTextPane;
import javax.swing.SwingUtilities;
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
        * @param tokens
        * @param txtArea
        */
       public void imprimirBusqueda(List<Token> tokens, JTextPane txtPane, String palabraBuscada) {
              StyledDocument doc = txtPane.getStyledDocument();
              Style estiloNormal = doc.addStyle("normal", null);
              StyleConstants.setForeground(estiloNormal, Color.BLACK);

              Style estiloResaltado = doc.addStyle("resaltado", null);
              StyleConstants.setForeground(estiloResaltado, Color.RED);
              StyleConstants.setBold(estiloResaltado, true);
              StyleConstants.setBackground(estiloResaltado, Color.YELLOW);

              try {
                     doc.remove(0, doc.getLength());

                     for (int i = 0; i < tokens.size(); i++) {
                            Token token = tokens.get(i);
                            String texto = token.getLexema();

                            if (texto.equals(palabraBuscada)) {
                                   doc.insertString(doc.getLength(), texto, estiloResaltado);
                            } else {
                                   doc.insertString(doc.getLength(), texto, estiloNormal);
                            }
                            if (token.isEsFinal()) {
                                   doc.insertString(doc.getLength(), "\n", estiloNormal);
                            } else {
                                   doc.insertString(doc.getLength(), " ", estiloNormal);
                            }
                     }

              } catch (BadLocationException ex) {
                     ex.printStackTrace();
              }
       }

       public void imprimirColoresTexto(JTextPane textPane, List<Token> tokens) {
              StyledDocument doc = textPane.getStyledDocument();

              try {
                     // Limpiar contenido antes de reescribir
                     doc.remove(0, doc.getLength());

                     for (Token token : tokens) {
                            Style style = textPane.addStyle("TokenStyle", null);
                            StyleConstants.setForeground(style, token.getColor());

                            doc.insertString(doc.getLength(), token.getLexema(), style);

                            if (token.isEsFinal()) {
                                   doc.insertString(doc.getLength(), "\n", style);
                            }
                     }
              } catch (BadLocationException e) {
                     e.printStackTrace();
              }
       }

       /**
        * 
        * @param textPane
        * @param token 
        */
       
       public void colorearTexto(List<Token> tokens, JTextPane textPane) {
              for (int i = 0; i < tokens.size(); i++) {
                     colorearToken(textPane, tokens.get(i));
              }
       }

       public void colorearToken(JTextPane textPane, Token token) {
              SwingUtilities.invokeLater(() -> {
                     StyledDocument doc = textPane.getStyledDocument();
                     int inicio = token.getPosicionCaracter(); 
                     int longitud = token.getLexema().length();

                     Style style = textPane.addStyle("TokenStyle", null);
                     StyleConstants.setForeground(style, token.getColor());

                     try {
                            doc.setCharacterAttributes(inicio, longitud, style, false);
                     } catch (Exception ex) {
                            ex.printStackTrace();
                     }
              });
       }
      

}
