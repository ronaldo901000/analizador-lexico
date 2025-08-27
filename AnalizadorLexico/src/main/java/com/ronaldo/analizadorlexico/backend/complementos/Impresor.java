package com.ronaldo.analizadorlexico.backend.complementos;

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
        * 
        * @param textPane
        * @param token 
        */
       
       public void colorearTexto(List<Token> tokens, JTextPane textPane) {
              for (int i = 0; i < tokens.size(); i++) {
                     colorearToken(textPane, tokens.get(i));
              }
       }

       /**
        * metodo que se encarga de imprimir cada token con su color correspondiente
        * @param textPane
        * @param token 
        */
       public void colorearToken(JTextPane textPane, Token token) {
              SwingUtilities.invokeLater(() -> {
                     StyledDocument document = textPane.getStyledDocument();
                     int inicio = token.getPosicionCaracter(); 
                     int longitud = token.getLexema().length();

                     Style style = textPane.addStyle("TokenStyle", null);
                     StyleConstants.setForeground(style, token.getColor());

                     try {
                            document.setCharacterAttributes(inicio, longitud, style, false);
                     } catch (Exception ex) {
                            ex.printStackTrace();
                     }
              });
       }

       /**
        * 
        * @param txtPane
        * @param cadena
        * @param esPatron 
        */
       public void imprimirLaBusqueda(JTextPane txtPane, String cadena, boolean esPatron) {
              try {
                     StyledDocument document = txtPane.getStyledDocument();

                     Style estiloNormal = txtPane.addStyle("normal", null);
                     StyleConstants.setForeground(estiloNormal, Color.BLACK);

                     Style estiloResaltado = txtPane.addStyle("resaltado", null);
                     StyleConstants.setForeground(estiloResaltado, Color.RED);
                     StyleConstants.setBold(estiloResaltado, true);
                     StyleConstants.setBackground(estiloResaltado, Color.YELLOW);

                     if (esPatron) {
                            document.insertString(document.getLength(), cadena, estiloResaltado);
                     } else {
                            document.insertString(document.getLength(), cadena, estiloNormal);
                     }
              } catch (BadLocationException ex) {
                     ex.printStackTrace();
              }
       }

}
