/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package source;

/**
 *
 * @author Usuario
 */
public class TError {

    String lexema, tipo, descripcion;
    int line, columna;

    public TError(String le, int li, int co, String t, String de) {
        lexema = le;
        line = li;
        columna = co;
        tipo = t;
        descripcion = de;
    }
}
