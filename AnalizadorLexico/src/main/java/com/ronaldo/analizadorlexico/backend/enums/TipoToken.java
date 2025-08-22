package com.ronaldo.analizadorlexico.backend.enums;

/**
 *
 * @author ronaldo
 */
public enum TipoToken {
       
       PALABRA_RESERVADA("palabrasReservadas"),
       IDENTIFICADOR("identificador"),
       NUMERO_ENTERO("entero"),
       NUMERO_DECIMAL("decimal"),
       CADENA("cadena"),
       OPERADOR("operadores"),
       AGRUPACION("agrupacion"),
       PUNTUACION("puntuacion"),
       COMENTARIO("comentarios"),
       COMENTARIO_LINEA("linea"),
       BLOQUE_INICIO("bloqueInicio"),
       BLOQUE_FIN("bloqueFin"),
       ERROR("error");

       private String nombre;

       TipoToken(String nombre) {
              this.nombre = nombre;
       }

       public String getNombre() {
              return nombre;
       }

}
