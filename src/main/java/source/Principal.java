/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package source;

import jflex.exceptions.SilentExit;
/**
 *
 * @author jtn19
 */
public class Principal {
    public static void main(String[] args) {
//        String ruta = System.getProperty("user.dir") + "/src/source/Lexer.flex";
        String ruta = "D:\\Universidad\\compiladores\\Compiladores_PRY_1\\src\\main\\java\\source\\Lexer.jflex";
        System.out.println(ruta);
        try{
        String[] archivo = {ruta};
        jflex.Main.generate(archivo);
//        generarLexer(ruta);
        } catch (SilentExit e) {
            System.out.println(e.getMessage());
        }
    }
    
    public static void generarLexer(String ruta) throws SilentExit {
        String[] archivo = {ruta};
        jflex.Main.generate(archivo);
    }
}
