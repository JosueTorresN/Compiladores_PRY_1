/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package source;

import java.io.File;
/**
 *
 * @author jtn19
 */
public class Principal {
    public static void main(String[] args) {
        String ruta = System.getProperty("user.dir") + "/src/source/Lexer.flex";
        generarLexer(ruta);
    }
    
    public static void generarLexer(String ruta) {
        File archivo = new File(ruta);
        jflex.Main.generate(archivo);
    }
}
