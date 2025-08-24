/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ronaldo.analizadorlexico.backend.escritura;

import java.io.File;
import java.io.FileWriter;
import java.util.List;

/**
 *
 * @author ronaldo
 */
public class Escritor {
       
       public File reescribirJson(String texto, File file) {
              try (FileWriter writer = new FileWriter(file)) {
                     writer.write(texto);
              } catch (Exception e) {
                     e.printStackTrace();
              }
              return file;
       }
}
