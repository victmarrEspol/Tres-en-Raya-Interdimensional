package Game;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Table{

    private char[][] elements;
    private String tipoVictoria;
    private int cantidadFichasMatrix;
    public Table(){
        this.elements =  new char[3][3];
        this.cantidadFichasMatrix = 0;
    }

    public Table(Table otra){
        //Este es un constructor que introduje para que me cree de una vez la tabla y sirva como base para crear las tablas sucesivamente
        this.elements = new char[3][3];
        char[][] matrizOriginal = otra.getElements();

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                this.elements[i][j] = matrizOriginal[i][j];
            }
        }

        tipoVictoria = "";

    }

    public char[][] getElements(){
        return elements;
    }

    public String getTipoVictoria(){
        return tipoVictoria;
    }

    public boolean insert(int posX, int posY, char tipoJugador){

        // Si ya está ocupada
        if(elements[posX][posY] != '\u0000') return false;

        // Si no está ocupada
        elements[posX][posY] = tipoJugador;

        cantidadFichasMatrix++;

        return true;

    }


    public boolean isFull(){
        for(int i=0; i<3; i++){
            for(int j=0; j<3; j++){
                // Si un elemento está vacío, entonces no está lleno
                if(elements[i][j] == '\u0000') return false;
            }
        }
        return true;
    }

    public boolean isWinner(char symbol){

        // Verificación de filas
        for(int i=0; i<3; i++){
            if(elements[i][0]==symbol &&
                    elements[i][1]==symbol &&
                    elements[i][2]==symbol){
                if(i==0) tipoVictoria = "f0";
                if(i==1) tipoVictoria = "f1";
                if(i==2) tipoVictoria = "f2";
                return true;
            }
        }

        // Verificación de columnas
        for(int j=0; j<3; j++){
            if(elements[0][j]==symbol &&
                    elements[1][j]==symbol &&
                    elements[2][j]==symbol){
                if(j==0) tipoVictoria = "c0";
                if(j==1) tipoVictoria = "c1";
                if(j==2) tipoVictoria = "c2";
                return true;
            }
        }

        // Diagonales

        // Principal
        if(elements[0][0]==symbol &&
                elements[1][1]==symbol &&
                elements[2][2]==symbol){
            tipoVictoria = "d1";
            return true;
        }

        // Alterna
        if(elements[0][2]==symbol &&
                elements[1][1]==symbol &&
                elements[2][0]==symbol){
            tipoVictoria = "d0";
            return true;
        }

        // Caso contrario
        return false;

    }

    public static boolean isWinnerMatrix(char[][] matrix, char symbol){

        // Verificación de filas
        for(int i=0; i<3; i++){
            if(matrix[i][0]==symbol &&
                    matrix[i][1]==symbol &&
                    matrix[i][2]==symbol){
                return true;
            }
        }

        // Verificación de columnas
        for(int j=0; j<3; j++){
            if(matrix[0][j]==symbol &&
                    matrix[1][j]==symbol &&
                    matrix[2][j]==symbol){
                return true;
            }
        }

        // Diagonales

        // Principal
        if(matrix[0][0]==symbol &&
                matrix[1][1]==symbol &&
                matrix[2][2]==symbol){
            return true;
        }

        // Alterna
        if(matrix[0][2]==symbol &&
                matrix[1][1]==symbol &&
                matrix[2][0]==symbol){
            return true;
        }

        // Caso contrario
        return false;

    }



    // El empate se da cuando está lleno y ninguno gana, así que ese debe ser el retorno
    public boolean isDraw(){
        return isFull() && !isWinner('x') && !isWinner('o');
    }

    public static int[] obtenerPosicionJugada(Table anterior, Table nuevo, char simboloMaquina){

        char[][] original = anterior.getElements();
        char[][] resultado = nuevo.getElements();

        for(int i = 0; i < 3; i++){
            for(int j = 0; j < 3; j++){
                if(original[i][j] == '\u0000' && resultado[i][j] == simboloMaquina){
                    return new int[]{i, j};
                }
            }
        }

        return null;
    }


    public static int[] obtenerPrimeraPosicionVacia(Table table){

        char[][] matrixTable = table.getElements();

        for(int i = 0; i < 3; i++){
            for(int j = 0; j < 3; j++){
                if(matrixTable[i][j] == '\u0000'){
                    return new int[]{i, j};
                }
            }
        }
        // Jamás se va a dar porque siempre habrá al menos un espacio vacío
        return null;
    }

    public static int[] obtenerInterseccionColumnas(Table table, char simboloJugador){
        char[][] matrixTable = table.getElements();

        int[] salida = new int[]{0, 0};

        ArrayList<int[]> posicionesX = new ArrayList<>();

        for(int i = 0; i < 3; i++){
            for(int j = 0; j < 3; j++){
                if(matrixTable[i][j] == simboloJugador){
                    posicionesX.add(new int[]{i, j});
                }
            }
        }

        // Si están en columnas o filas "Paralelas", retorna la posición (0, 0)
        if (posicionesX.get(0)[0] == posicionesX.get(1)[0] || posicionesX.get(0)[1] == posicionesX.get(1)[1]) {
            return salida;
        }


        // Si no es así, calculamos intersección de columnas o filas que contienen al menos un símbolo del jugador
        if(posicionesX.get(0)[0] == 1 || posicionesX.get(1)[1] == 1){
            salida[0] = posicionesX.get(1)[0];
            salida[1] = posicionesX.get(0)[1];
        } else if (posicionesX.get(0)[1] == 1 || posicionesX.get(1)[0] == 1){
            salida[0] = posicionesX.get(0)[0];
            salida[1] = posicionesX.get(1)[1];
        }

        return salida;

    }

    public static int[] obtenerPosicionInvincible(Table table, char simboloMaquina, char simboloJugador){

        char[][] matrixTable = table.getElements();

        // Identificar si ya se puede ganar
        for(int i = 0; i < 3; i++){
            for(int j = 0; j < 3; j++){
                if(matrixTable[i][j] == '\u0000'){
                    matrixTable[i][j] = simboloMaquina;
                    if(isWinnerMatrix(matrixTable, simboloMaquina)){
                        matrixTable[i][j] = '\u0000';
                        return new int[]{i, j};
                    }
                    matrixTable[i][j] = '\u0000';
                }
            }
        }

        // Prevenir que gane el jugador
        for(int i = 0; i < 3; i++){
            for(int j = 0; j < 3; j++){
                if(matrixTable[i][j] == '\u0000'){
                    matrixTable[i][j] = simboloJugador;
                    if(isWinnerMatrix(matrixTable, simboloJugador)){
                        matrixTable[i][j] = '\u0000';
                        return new int[]{i, j};
                    }
                    matrixTable[i][j] = '\u0000';
                }
            }
        }

        int cantidadFichasMatrix = table.cantidadFichasMatrix;


        // Basado en una estrategia que yo usaba cuando era niño :)
        // Si la cantidad es par, entonces la máquina inició la partida

        if(cantidadFichasMatrix % 2 == 0){

            if(cantidadFichasMatrix == 0) return new int[]{0, 0};

            if(cantidadFichasMatrix == 2){

                if(matrixTable[1][1] != '\u0000'){
                    return new int[]{2, 2};
                }

                if(matrixTable[0][1] == '\u0000' && matrixTable[0][2] == '\u0000'){

                    return new int[]{0, 2};

                }

                return new int[]{2, 0};

            }

            if(cantidadFichasMatrix == 4 && matrixTable[1][1] == '\u0000'){

                if(matrixTable[0][2] == simboloMaquina && matrixTable[1][0] == '\u0000' && matrixTable[2][0] == '\u0000'){

                    return new int[]{2, 0};

                }

                return new int[]{2, 2};

            }

            return obtenerPrimeraPosicionVacia(table);

        }


        // Si la cantidad es impar, entonces el jugador inició la partida                     +++++++
        else {

            if (cantidadFichasMatrix == 1 && matrixTable[1][1] == '\u0000') {

                return new int[]{1, 1};

            }

            if (cantidadFichasMatrix == 3 && matrixTable[1][1] == simboloJugador && matrixTable[2][2] == simboloJugador) {

                return new int[]{2, 0};

            }

            if (cantidadFichasMatrix == 3 && matrixTable[1][1] == simboloMaquina){

                // Si las casillas en esquinas opuestas están llenas con los símbolos del jugador:

                if(matrixTable[0][0] == simboloJugador && matrixTable[2][2] == simboloJugador ||
                     matrixTable[0][2] == simboloJugador && matrixTable[2][0] == simboloJugador){
                    return new int[]{0, 1};
                }

                // Si no se da lo de arriba, calcular intersección de columnas de las fichas del jugador

                return obtenerInterseccionColumnas(table, simboloJugador);

            }

            return obtenerPrimeraPosicionVacia(table);

        }
    }


    public static int[] obtenerPosicionRandom(Table table, char simboloMaquina, char simboloJugador){

        char[][] matrixTable = table.getElements();

        // Identificar si ya se puede ganar
        for(int i = 0; i < 3; i++){
            for(int j = 0; j < 3; j++){
                if(matrixTable[i][j] == '\u0000'){
                    matrixTable[i][j] = simboloMaquina;
                    if(isWinnerMatrix(matrixTable, simboloMaquina)){
                        matrixTable[i][j] = '\u0000';
                        return new int[]{i, j};
                    }
                    matrixTable[i][j] = '\u0000';
                }
            }
        }

        // Prevenir que gane el jugador
        for(int i = 0; i < 3; i++){
            for(int j = 0; j < 3; j++){
                if(matrixTable[i][j] == '\u0000'){
                    matrixTable[i][j] = simboloJugador;

                    if(isWinnerMatrix(matrixTable, simboloJugador)){
                        matrixTable[i][j] = '\u0000';
                        return new int[]{i, j};
                    }
                    matrixTable[i][j] = '\u0000';
                }
            }
        }


        ArrayList<int[]> posicionesDisponibles = new ArrayList<>();

        for(int i = 0; i < 3; i++){
            for(int j = 0; j < 3; j++){
                if(matrixTable[i][j] == '\u0000'){
                    posicionesDisponibles.add(new int[]{i, j});
                }
            }
        }

        Random random = new Random();

        int posicionElegida = random.nextInt(posicionesDisponibles.size());

        return posicionesDisponibles.get(posicionElegida);

    }



}
