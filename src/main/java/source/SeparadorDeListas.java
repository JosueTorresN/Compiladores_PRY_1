/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package source;

/**
 *
 * @author Usuario
 */
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SeparadorDeListas {
    private static boolean[] registrosT = new  boolean[10];
    private static List<String> generatedLines2 = new ArrayList<>();
    private static List<String> generatedLines = new ArrayList<>();
    private static String[] lista = {"variable_globalTs_", "variable_int_", "variable_forEach_main_","variable_main_","variable_nuevaFuncion_"};
    private static String reserOperaciones = "\\^%+\\-\\*&!<>;:,/";
    private static List<String> dataLista = new ArrayList<>();//Lista que contiene los elementos de data
    
    
    public static void main(String[] args) {
        setearRegistrosT();
        String filePath = "C:\\Users\\Usuario\\Documents\\NetBeansProjects\\Compiladores_PRY_1\\3D_Code.txt"; // Reemplaza con la ruta de tu archivo

        try {
            List<List<String>> listOfLists = readAndProcessFile(filePath);

            // Imprimir cada elemento de cada lista
            for (List<String> list : listOfLists) {
                convertToAssembler(list);//LLamada a la funcion que escribe el asm
                printList(list);
            }
            
        } catch (IOException e) {
            e.printStackTrace();
        }
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("output.asm"))) {
            //procesarCodigoMIPS(generatedLines);
            
            for (String asmLine : generatedLines) {//Se puede cambiar a generatedLines para revisar el recorrdo primario
                //System.out.println("Impresion de " +asmLine);
                writer.write(asmLine);
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    //función encargada de pasar el contenido a mips
    // Lee el archivo y procesa sus líneas en listas
    public static List<List<String>> readAndProcessFile(String filePath) throws IOException {
        List<List<String>> listOfLists = new ArrayList<>();
        List<String> currentList = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            boolean flag = false;
            while ((line = br.readLine()) != null) {

                if (line.contains(":")&& flag == false) {
                    if (!currentList.isEmpty()) {
                        listOfLists.add(new ArrayList<>(currentList));
                        currentList.clear();
                    }
                }
                if (line.startsWith("DATA:")){
                    flag = true;
                }
                currentList.add(line);
            }
            
        }

        // Añadir la última lista si no está vacía
        if (!currentList.isEmpty()) {
            listOfLists.add(new ArrayList<>(currentList));
        }

        return listOfLists;
    }

    // Imprime los elementos de una lista
    public static void printList(List<String> list) {
        for (String element : list) {
            //System.out.println(element);
        }
        //System.out.println("---- Fin de la lista ----");
    }
    
    
    //clase encargada de convertir en ensamblador
    private static int tempCounter = 0;

    public static void convertToAssembler(List<String> inputLines) {
        //generatedLines.clear();

        //generatedLines.add(inputLines.get(0));
        generatedLines.add(".data");
        for (int i = 1; i < inputLines.size(); i++) {
            String line = inputLines.get(i);
            
            //System.out.println("Respuesta de la comparacion "+line);
            if (line.contains("=") && !compararConLista(line)) {//Si tiene igual, no tiene diferente(!=) y si no es una variable declarada en el data entonces ingresa
                
                
                
                //String[] parts = line.split("=");
                String[] parts = definidorDeSplit(line);
                String leftHandSide = parts[0].trim();
                String rightHandSide = parts[1].trim();
                //System.out.println("Elemento izquierda "+leftHandSide+" Elemento derecha "+rightHandSide);
                //
                boolean assignedToTemp = leftHandSide.matches("t[0-9]+")||leftHandSide.matches("f[0-9]+");
                boolean assignedToF = leftHandSide.matches("f[0-9]+");
                //boolean assignedToTempRight = rightHandSide.matches("t[0-9]+");
                //System.out.println("Elemento de la derecha "+rightHandSide);
                //System.out.println("Eelementos data "+dataLista);
                if(dataLista.contains(rightHandSide)){
                    generatedLines.add("lw $" + leftHandSide + ", " + rightHandSide);
                }else if(!assignedToTemp){
                    generatedLines.add("sw $" + rightHandSide + ", " + leftHandSide);
                }
                
                //ojo esto era un if
                else if (rightHandSide.startsWith("-")) {
                    // Es un número negativo
                    String dest = leftHandSide;
                    String value = rightHandSide.substring(1).trim(); // Eliminar el signo negativo
                    generatedLines.add("li $" + dest + ", -" + value);
                } else if (rightHandSide.matches(".*["+reserOperaciones+"].*")) {
                    // Hay una operación aritmética
                    rightHandSide = rightHandSide.replace("||", "|");
                    rightHandSide = rightHandSide.replace("&&", "&");
                    String[] operationParts = rightHandSide.split("["+reserOperaciones+"]");
                    String dest = leftHandSide;
                    String src1 = operationParts[0].trim();
                    //System.out.println("lado izq "+leftHandSide+" src1 "+src1);
                    //System.out.println("vea => "+Arrays.toString(operationParts)+ "derecha carac "+rightHandSide);
                    String src2 = operationParts[1].trim();    
                    
                    String operator = rightHandSide.replaceAll("[^"+reserOperaciones+"]", "");
                    
                    // Reemplazar los símbolos
                    switch (operator) {
                        case "+":
                            operator = "add";
                            break;
                        case "-":
                            operator = "addi"; // Sumar con el valor negativo
                            src2 = "-" + src2;
                            break;
                        case "*":
                            operator = "mul";
                            break;
                        case "/":
                            operator = "div";
                            break;
                        case "%":
                            operator = "div";
                            break; 
                        case "^":
                            operator = "pot";
                            break;
                        case "&":
                            operator = "and";
                            break;
                        case "|":
                            operator = "or";
                            break; 
                        case "!":
                            operator = "bnq";
                            break;
                        case ";":
                            operator = "beq";
                            break;
                         case "<":
                            operator = "bgt";
                            break;
                         case ">":
                            operator = "blt";
                            break;
                         case ":":
                            operator = "bge";
                            break;  
                         case ",":
                            operator = "ble";
                            break; 
                        // Puedes agregar casos para otros operadores aquí
                    }
                    
                    if(operator.equals("bnq")||operator.equals("beq")||operator.equals("bgt")||operator.equals("blt")||operator.equals("bge")||operator.equals("ble")){
                        String nline = inputLines.get(i+1);
                        //System.out.println("Pasa por aqui "+nline);
                        if(nline.startsWith("jumif")){
                            i++;
                            String[] nParts = definidorDeSplit(nline);
                            //String nLeftHandSide = parts[0].trim();
                            String nRightHandSide = nParts[1].trim();
                            generatedLines.add(operator + " $" + src1 + ", $" + src2 + ", " + nRightHandSide );
                        }
                    }else if (!operator.equals("pot")){
                        generatedLines.add(operator + " $" + dest + ", $" + src1 + ", $" + src2);
                    }else{
                        creadorPotencias(src1,src2);
                       
                    }
                    //Agragar un if para guardar el cocioente si operador == div
                } else {
                    
                    // Asignación simple
                    String dest = leftHandSide;
                    String value = rightHandSide.trim();
                    //System.out.println("Caso especial"+"li $" + dest + ", " + value);
                    if(value.equals("true")){
                        value = "1";
                        generatedLines.add("li $" + dest + ", " + value);
                    }else if(value.equals("false")){
                        value = "0";
                        generatedLines.add("li $" + dest + ", " + value);
                    }else if(assignedToF){
                        
                        generatedLines.add("li.s $" + dest + ", " + value);
                    }else{
                        System.out.println("Respuesta de la comparacion "+line+" des "+dest);
                        generatedLines.add("li $" + dest + ", " + value);
                    }
                  
                }
            }else if(line.split(" ")[0].trim().equals("goto")){
                
                String[] nparts = line.split(" ");
                String nrightHandSide = nparts[1].trim();
                String nVar = nrightHandSide.replace("goto","j");
                generatedLines.add("j "+nVar);
            }else if(line.isEmpty()){
                System.out.println("fin del contenido");
//            }else if(line.startsWith("jumif")){
//                int largo = generatedLines.size()-1;
//                String modifElemento = generatedLines.get(largo);
                //Continuar aqui
            }else{
                if(line.startsWith("begin_Func_globalTs:")){
                    generatedLines.add(".text");
                }else if(line.contains(":")){
                    generatedLines.add(line);
                }else{
                    dataLista.add(splitter(line));
                    //System.out.println("La linea es "+splitter(line));
                    generatedLines.add("  "+splitter(line)+":"+" .word 0");
                }
            }
        }
    }
    //Funcion encargada de compara el valor proveniente del metodo convertToAssembler y 
    public static boolean compararConLista(String str) {
        // Lista de valores predefinidos
        //System.out.println("compara eso => "+str);
 
        // Iterar sobre la lista y comparar con el String dado
        for (String valor : lista) {
            //System.out.println("Valor que ingresa"+ str);
            //System.out.println("valor de esto => "+valor.startsWith(str));
            if (str.startsWith(valor)) {
                return true; // Coincidencia encontrada
            }
        }

        return false; // No se encontró coincidencia
    }
    
    //Funcion encargada de retornar el valor resultante de splitear un string que comparta palabras reservadas por el codigo 3d 
    public static String splitter(String str) {
        // Lista de valores predefinidos

        //System.out.println("soy un string "+str);
        // Iterar sobre la lista y comparar con el String dado
        for (int i = 0; i < lista.length; i++) {
            if (str.startsWith(lista[i])) {
                String nVar = str.replace(lista[i],"");
                ///Seccion donde se splitea
                String [] nVarSplited = nVar.split("=");
                nVar = nVarSplited[0];
                //System.out.println("Me splitee "+nVar);
                ///Seccion donde se splitea
                return nVar; // Coincidencia encontrada
            }
        }

         // No se encontró coincidencia
        return null;
    }
    //Funcion encargada de crear potencias 
    public static void creadorPotencias(String src1,String src2){
        System.out.println("Hay");
        generatedLines.add("move $t0, "+src1);
        generatedLines.add("bucle_potencia:");
        generatedLines.add("  beqz"+ "$"+src1+", fin_bucle");
        generatedLines.add("  mul "+ "$" + src1 +", $"+ src1 +", $t0" );
        generatedLines.add("  subi "+ "$" + src2 +", $"+ src2 +", 1");
        generatedLines.add("  j bucle_potencia");
        generatedLines.add("fin_bucle:");
        generatedLines.add("  jr ra");
    }
    //Funcion encargada de setear los registro t en falso al inicio del programa
    public static void setearRegistrosT(){
        for(int i=0; i!=10; i++){
            registrosT[i] = false;
        }
    }
    //Funcion encargada de regresar el split adecuado dependiendo de si es (<,>,=,!=)
    public static String[] definidorDeSplit(String line){
        
        if(line.contains("!=")){
            line = line.replace("!=", "!");
            return line.split("=");
            
        }else if(line.contains("jumif ")){
            //System.out.println("La linea es "+line.split("goto")[1]);
            return line.split("goto");
        }else if(line.contains("==")){
            line = line.replace("==", ";");
            return line.split("=");        
        }else if(line.contains("<=")){
            line = line.replace("<=", ":");
            return line.split("=");
        }else if(line.contains(">=")){
            line = line.replace(">=", ",");
            return line.split("=");
        }else if(line.contains("<")){
            //line = line.replace("<", "<");
            return line.split("=");
        }else if(line.contains(">")){
            //line = line.replace("<", "<");
            return line.split("=");
   
        }else{
            return line.split("=");
        }
        
    }
    
    
    
    
    
    
    
    
    
    
    
    
    //Funcion encargada del control de la reasignacion de registros 
    public static void procesarCodigoMIPS(List<String> codigoMIPS) {        
        for (String instruccion : codigoMIPS) {
            if (instruccion.startsWith("li")) {
                procesarInstruccionLI(instruccion);
            } else if (instruccion.startsWith("add") || instruccion.startsWith("div") || instruccion.startsWith("addi")) {
                procesarInstruccionAritmetica(instruccion);
            } else if (instruccion.startsWith("sw")) {
                procesarInstruccionSW(instruccion);
            }else{
                generatedLines2.add(instruccion);
                //System.out.println("instrucciones =>"+instruccion);
            }
        }
    }
    //Funcion encargada de procesar las etiquetas y reasignar registros
    private static void procesarInstruccionLI(String instruccion) {
        // Obtener el registro temporal
        String registro = instruccion.split(",")[0].split("\\s+")[1];
        System.out.println("registro "+registro);
        
        String temVarReg = registro.replace("$t", "");
        int tempVarInt = Integer.valueOf(temVarReg);
        //System.out.println("registro tem "+temVarReg);
        // Verificar si el registro está en uso
        //if (registrosT[obtenerIndiceRegistro(registro)]) {
        if (registrosT[obtenerIndiceRegistro(registro)]) {
            // Cambiar el registro a uno disponible
            String nuevoRegistro = obtenerRegistroDisponible();
            instruccion = instruccion.replace(registro, nuevoRegistro);
        }

        // Marcar el registro como en uso
        registrosT[obtenerIndiceRegistro(instruccion.split(",")[0].split("\\s+")[1])] = true;

        // Procesar la instrucción y almacenar el resultado
        generatedLines2.add(instruccion);
    }
    private static void procesarInstruccionAritmetica(String instruccion) {
        // Obtener los registros temporales
        String[] registros = instruccion.split(",");
        String registroDestino = registros[0].split("\\s+")[1];
        String registroFuente1 = registros[1].trim();
        String registroFuente2 = registros[2].trim();

        // Liberar el registro usado por la derecha
        registrosT[obtenerIndiceRegistro(registroFuente1)] = false;
        registrosT[obtenerIndiceRegistro(registroFuente2)] = false;

        // Marcar el registro destino como en uso
        registrosT[obtenerIndiceRegistro(registroDestino)] = true;

        // Procesar la instrucción y almacenar el resultado
        generatedLines2.add(instruccion);
    }

    private static void procesarInstruccionSW(String instruccion) {
        // Obtener los registros y el destino de sw
        String[] partes = instruccion.split(",");
        String registroFuente = partes[0].split("\\s+")[1].trim();
        String destino = partes[1].trim();

        // Liberar el registro usado por la derecha
        registrosT[obtenerIndiceRegistro(registroFuente)] = false;

        // Procesar la instrucción y almacenar el resultado
        generatedLines2.add(instruccion);
    }
    //Funcion encargada de obtener el indice
    private static int obtenerIndiceRegistro(String registro) {
        System.out.println("registro optenido "+registro.substring(1));
        String temVarReg = registro.replace("$t", "");
        int tempVarInt = Integer.valueOf(temVarReg);
        return tempVarInt;
    }
    //Funcion encargada de saber si un registro se encuentra en disposicion de uso 
    private static String obtenerRegistroDisponible() {
        for (int i = 0; i < registrosT.length; i++) {
            if (!registrosT[i]) {
                registrosT[i] = true;
                return "t" + i;
            }
        }
        // Aquí podrías manejar el caso en el que no hay registros disponibles
        throw new IllegalStateException("No hay registros temporales disponibles.");
    }

}
