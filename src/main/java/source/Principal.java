/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package source;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import jflex.exceptions.SilentExit;
/**
 *
 * @author jtn19
 */
public class Principal {
    public static void main(String[] args) {
//        String ruta = System.getProperty("user.dir") + "/src/source/Lexer.flex";
        String rutaJFlex = "D:\\Universidad\\compiladores\\Compiladores_PRY_1\\src\\main\\java\\flex\\Lexer.jflex";
        String rutaCup = "D:\\Universidad\\compiladores\\Compiladores_PRY_1\\src\\main\\java\\flex\\Sintax.cup";
        try{
//        String[] archivo = {ruta};
//        jflex.Main.generate(archivo);
        generarLexer(rutaJFlex);
        generateCup(rutaCup);
        } catch (SilentExit e) {
            System.out.println(e.getMessage());
        } catch (Exception ex) {
            Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void generarLexer(String ruta) throws SilentExit {
        String[] archivo = {ruta};
        jflex.Main.generate(archivo);
    }
    
    public static void generateCup(String ruta) throws IOException, Exception {
        String[] archivo = {"-parser", "Sintax", ruta};
        java_cup.Main.main(archivo);
    }
}
