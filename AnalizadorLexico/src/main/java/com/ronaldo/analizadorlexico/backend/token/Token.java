package com.ronaldo.analizadorlexico.backend.token;

import java.awt.Color;

public class Token {
       private String tipo;
       private Color color;
       private String lexema;
       private int fila;
       private int columna;
       private int posicionCaracter;
       private boolean esFinal;

       public Token(String tipo,Color color, String lexema, int fila, int columna, boolean esFinal) {
              this.tipo=tipo;
              this.color = color;
              this.lexema = lexema;
              this.fila = fila;
              this.columna = columna;
              this.esFinal=esFinal;
       }

       public Color getColor() {
              return color;
       }

       public String getLexema() {
              return lexema;
       }

       public int getFila() {
              return fila;
       }

       public int getColumna() {
              return columna;
       }

       public String getTipo() {
              return tipo;
       }

       public boolean isEsFinal() {
              return esFinal;
       }

       public void setEsFinal(boolean esFinal) {
              this.esFinal = esFinal;
       }

       public int getPosicionCaracter() {
              return posicionCaracter;
       }

       public void setPosicionCaracter(int posicionCaracter) {
              this.posicionCaracter = posicionCaracter;
       }

       
       

}
