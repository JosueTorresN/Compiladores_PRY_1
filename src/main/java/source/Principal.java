/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package source;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.logging.Level;
import java.util.logging.Logger;
import java_cup.Lexer;
import java_cup.runtime.Scanner;
import java_cup.runtime.Symbol;
import jflex.exceptions.SilentExit;

/**
 *
 * @author jtn19
 */
public class Principal {
    public static void main(String[] args) {
//        String ruta = System.getProperty("user.dir") + "/src/source/Lexer.flex";
        String rutaJFlex = "C:/Users/Usuario/Documents/NetBeansProjects/Compiladores_PRY_1/src/main/java/source/Lexer.flex";
        String rutaCup = "C:/Users/Usuario/Documents/NetBeansProjects/Compiladores_PRY_1/src/main/java/source/Sintax2.cup";
        //String rutaLexerCup = "C:/Users/Usuario/Documents/NetBeansProjects/Compiladores_PRY_1/src/main/java/source/LexerCup.flex";
        try{
//        String[] archivo = {ruta};
//        jflex.Main.generate(archivo);
        //generarLexer(rutaJFlex);
        generarLexer(rutaLexerCup);//activador//
        generateCup(rutaCup);//activador//
        //ejercicioParser1("C://Users//Usuario//Desktop//pru.txt");
        //ejercicioLexer1("C://Users//Usuario//Desktop//pru.txt");        
//generarLexer(rutaLexerCup);
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
//
    
public static void ejercicioLexer1(String rutaScanear) throws IOException{
        Reader reader = new BufferedReader(new FileReader (rutaScanear));
        reader.read();
        LexerCup lex = new LexerCup(reader);
        int i = 0;
        Symbol token;
        while(true)
        {
            token = lex.next_token();
            if(token.sym != 0){
                System.out.println("Token: "+token.sym+ ", Valor: "+(token.value==null?lex.yytext():token.value.toString()));
            }
            else{        
                System.out.println("Cantidad de lexemas encontrados: "+i);
                return;
            }
            i++;
        }
    }    
//
    public static void ejercicioParser1 (String rutaparsear) throws Exception{ 
            Reader inputLexer = new FileReader (rutaparsear);
            LexerCup myLexer = new LexerCup (inputLexer);
            Sintax myParser = new Sintax ( myLexer);
            myParser.parse();
        }
}
