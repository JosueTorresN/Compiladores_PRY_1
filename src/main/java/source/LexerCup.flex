package source;
import java_cup.runtime.Symbol;
import java.util.LinkedList;
import source.TError;
%%
 
//------->Lista que guarda los errores
%{

public static LinkedList<TError> TablaError = new LinkedList<TError>();
%}
%class LexerCup
%type java_cup.runtime.Symbol
%cup
%full
%line
%char
%function next_token
I = [:jletter:] [:jletterdigit:]*
D= 0 | [1-9][0-9]*
cadenas=[:jletterdigit:]*
caracter=[:jletterdigit:]
espacio=[ \t\r\n]+
%{
    private Symbol symbol(int type, Object value){
        return new Symbol(type, yyline, yycolumn, value);
    }
    
    private Symbol symbol(int type){
        return new Symbol(type, yyline, yycolumn);
    }
%}
%%

/* Tipo de dato Int */
( "int" ) {return new Symbol(sym.Entero,  yytext());}

/* Tipo de dato Int */
( "float" ) {return new Symbol(sym.Flotante,  yytext());}

/* Tipo de dato Boolean */
( "bool" ) {return new Symbol(sym.Booleano,  yytext());}

/* Tipo de dato Char */
( "char" ) {return new Symbol(sym.Char,  yytext());}

/* Espacios en blanco */
{espacio} {/*Ignore*/}

/* Comentarios */
( "//"(.)* ) {/*Ignore*/}

/* Tipo de dato String */
( "string" ) {return new Symbol(sym.Cadena,  yytext());}

/* Palabra reservada If */
( "if" ) {return new Symbol(sym.If,  yytext());}

/* Palabra reservada Else*/
( "else" ) {return new Symbol(sym.Else,  yytext());}

/* Palabra reservada Switch */
( "switch" ) { return new Symbol (sym.Switch, yytext());}

/* Palabra reservada Case */
( "case" ) { return new Symbol (sym.Case_, yytext());}

/* Palabra reservada While */
( "while" ) {return new Symbol(sym.While,  yytext());}

/* Palabra reservada For */
( "forRange" ) {return new Symbol(sym.For,  yytext());}

/* Operador Igual */
( "=" ) {return new Symbol(sym.Igual,  yytext());}

/* Operador Suma */
( "+" ) {return new Symbol(sym.Suma,  yytext());}

/* Operador Resta */
( "-" ) {return new Symbol(sym.Resta,  yytext());}

/* Operador Multiplicacion */
( "*" ) {return new Symbol(sym.Multiplicacion,  yytext());}

/* Operador Potencia */
( "^" ) {return new Symbol(sym.Potencia,  yytext());}

/* Operador Division */
( "/" ) {return new Symbol(sym.Division,  yytext());}

/* Operador Modulo */
( "%" ) {return new Symbol(sym.Modulo,  yytext());}

/* Operadores logicos */
( "&&" | "||" | "!" | "&" | "|") {return new Symbol(sym.Op_logico,  yytext());}

/*Operadores Relacionales */
( ">" | "<" | "==" | "!=" | ">=" | "<=" ) {return new Symbol(sym.Op_relacional,  yytext());}

/* Operadores Atribucion */
( "+=" | "-="  | "*=" | "/=" | "%=" ) {return new Symbol(sym.Op_atribucion,  yytext());}

/* Operadores Incremento y decremento */
( "++" | "--" ) {return new Symbol(sym.Op_incremento,  yytext());}

/*Operadores Booleanos*/
( "true" | "false" ) {return new Symbol(sym.Op_booleano,  yytext());}

/* Parentesis de apertura */
( "(" ) {return new Symbol(sym.Parentesis_a,  yytext());}

/* Parentesis de cierre */
( ")" ) {return new Symbol(sym.Parentesis_c,  yytext());}

/* Llave de apertura */
( "{" ) {return new Symbol(sym.Llave_a,  yytext());}

/* Llave de cierre */
( "}" ) {return new Symbol(sym.Llave_c,  yytext());}

/* Marcador de inicio de algoritmo */
( "main" ) {return new Symbol(sym.Main,  yytext());}

/* Punto y coma */
( ";" ) {return new Symbol(sym.P_coma,  yytext());}

/* Coma */
( "," ) {return new Symbol(sym.Coma,  yytext());}

/* Dospuntos */
( ":" ) {return new Symbol (sym.P_dospuntos, yytext());}

/* Palabra reservada Separador */
( "#" ) {return new Symbol(sym.Separador,  yytext());}

/* Palabra reservada Interrupcion */
( "break" ) {return new Symbol(sym.Break,  yytext());}

/* Palabra reservada Leer */
( "read" ) {return new Symbol(sym.Read,  yytext());}

/* Palabra reservada Escribir */
( "write" ) {return new Symbol(sym.Write,  yytext());}

/* Palabra reservada Return */
( "return" ) {return new Symbol(sym.Return,  yytext());}

/* Palabra reservada Global */
( "global" ) {return new Symbol(sym.Global,  yytext());}

/* Palabra reservada Por Defecto */
( "default" ) {return new Symbol(sym.Default,  yytext());}

/* Identificador */
{ I } {return new Symbol(sym.Identificador,  yytext());}

/* Cadena_String */
"\"" {cadenas} "\"" {return new Symbol(sym.Cadena_String,  yytext());}

/* Cadena_Char */
"\'" {caracter} "\'" {return new Symbol(sym.Cadena_Char,  yytext());}

/* Numero */
(-{D}|{D}) {return new Symbol(sym.Numero,  yytext());}

/* Float */
(-{D}|{D})"."{D} {return new Symbol(sym.Float,  yytext());}


/* Comentario_simple*/
"//" {cadenas} {return new Symbol(sym.Coment_Simp,  yytext());}

/* Comentario_completo */
"/*" {cadenas} "*/" {return new Symbol(sym.Coment_Comp,  yytext());}

/* Error de analisis */
. {System.out.println("Error Lexico"+yytext()+" Linea "+yyline+" Columna "+yycolumn);
TError datos = new TError(yytext(),yyline,yycolumn, "Error Lexico", "Simbolo no existe");
TablaError.add(datos);}




/* Error de analisis */
// . {System.out.println("Error Lexico"+yytext()+" Linea "+yyline+" Columna "+yycolumn);
//TError datos = new TError(yytext(),yyline,yycolumn, "Error Lexico", "Simbolo no existe");
//TablaError.add(datos);return new Symbol(sym.ERROR,  yytext());}

