 package source;
import java_cup.runtime.Symbol;
%%

%class LexerCup
%type java_cup.runtime.Symbol
%cup
%full
%line
%char
%function next_token
L=[a-zA-Z_]+
D=[0-9]+
A=[a-zA-Z0-9]+
espacio=[ ,\t,\r,\n]+
%{
    private Symbol symbol(int type, Object value){
        return new Symbol(type, yyline, yycolumn, value);
    }
    
    private Symbol symbol(int type){
        return new Symbol(type, yyline, yycolumn);
    }
%}
%%

/* Espacios en blanco */
{espacio} {/*Ignore*/}

/* Comentarios */
( "//"(.)* ) {/*Ignore*/}

/* Comillas */
( "\"" ) {return new Symbol(sym.Comillas,  yytext());}

/* Tipos de datos */
( "byte" | "int" | "char" | "long" | "float" | "double" ) {return new Symbol(sym.T_dato,  yytext());}

/* Tipo de dato String */
( "String" ) {return new Symbol(sym.Cadena,  yytext());}

/* Palabra reservada If */
( "if" ) {return new Symbol(sym.If,  yytext());}

/* Palabra reservada Else*/
( "else" ) {return new Symbol(sym.Else,  yytext());}

/* Palabra reservada Do */
( "do" ) {return new Symbol(sym.Do,  yytext());}

/* Palabra reservada Switch */
( "switch" ) { return new Symbol (sym.Switch, yytext());}

/* Palabra reservada Case */
( "case" ) { return new Symbol (sym.Case_, yytext());}

/* Palabra reservada While */
( "while" ) {return new Symbol(sym.While,  yytext());}

/* Palabra reservada For */
( "forRange " ) {return new Symbol(sym.For,  yytext());}

/* Operador Igual */
( "=" ) {return new Symbol(sym.Igual,  yytext());}

/* Operador Suma */
( "+" ) {return new Symbol(sym.Suma,  yytext());}

/* Operador Resta */
( "-" ) {return new Symbol(sym.Resta,  yytext());}

/* Operador Multiplicacion */
( "*" ) {return new Symbol(sym.Multiplicacion,  yytext());}

/* Operador Potencia */
( "**" ) {return new Symbol(sym.Potencia,  yytext());}

/* Operador Division */
( "/" ) {return new Symbol(sym.Division,  yytext());}

/* Operador Modulo */
( "%" ) {return new Symbol(sym.Modulo,  yytext());}

/* Operadores logicos */
( "&&" | "||" | "!" | "&" | "|" ) {return new Symbol(sym.Op_logico,  yytext());}

/*Operadores Relacionales */
( ">" | "<" | "==" | "!=" | ">=" | "<=" | "<<" | ">>" ) {return new Symbol(sym.Op_relacional,  yytext());}

/* Operadores Atribucion */
( "+=" | "-="  | "*=" | "/=" | "%=" | "=" ) {return new Symbol(sym.Op_atribucion,  yytext());}

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

/* Corchete de apertura */
( "[" ) {return new Symbol(sym.Corchete_a,  yytext());}

/* Corchete de cierre */
( "]" ) {return new Symbol(sym.Corchete_c,  yytext());}

/* Marcador de inicio de algoritmo */
( "main" ) {return new Symbol(sym.Main,  yytext());}

/* Punto y coma */
( ";" ) {return new Symbol(sym.P_coma,  yytext());}

/* Dospuntos */
( ":" ) {return new Symbol (sym.P_dospuntos, yytext());}

/* Palabra reservada Separador */
( "#" ) {return new Symbol(sym.Separador,  yytext());}

/* Palabra reservada Coma */
( "," ) {return new Symbol(sym.Coma,  yytext());}

/* Palabra reservada Interrupcion */
( "break" ) {return new Symbol(sym.Break,  yytext());}

/* Palabra reservada Return */
( "return" ) {return new Symbol(sym.Return,  yytext());}

/* Palabra reservada Funcion */
( "func" ) {return new Symbol(sym.Func,  yytext());}

/* Palabra reservada Global */
( "global" ) {return new Symbol(sym.Global,  yytext());}

/* Palabra reservada Por Defecto */
( "default" ) {return new Symbol(sym.Default,  yytext());}

/* Identificador */
{L}({L}|{D})* {return new Symbol(sym.Identificador,  yytext());}

/* Caracteres */
{A}+ {return new Symbol(sym.Caracteres,  yytext());}

/* Numero */
"-"{D}+|{D}+ {return new Symbol(sym.Numero,  yytext());}

/* Float */
"-"{D}+|{D}+"."{D}+ {return new Symbol(sym.Float,  yytext());}

/* Error de analisis */
 . {return new Symbol(sym.ERROR,  yytext());}
