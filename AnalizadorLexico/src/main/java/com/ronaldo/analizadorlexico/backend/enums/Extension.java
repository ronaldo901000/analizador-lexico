/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package com.ronaldo.analizadorlexico.backend.enums;

/**
 *
 * @author ronaldo
 */
public enum Extension {
       JSON("json"),
       TXT("txt"),
       CSV("csv");

       private String extension;

       Extension(String extension) {
              this.extension = extension;
       }
       
       public String getExtension(){
              return extension;
       }
}
