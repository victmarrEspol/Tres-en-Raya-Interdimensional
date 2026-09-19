package TDA;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

import Game.Table;


public class Tree<E> {

    private NodeTree<Table> root;

    public Tree(Table tablero){
        this.root = new NodeTree<>(tablero);
    }

    public boolean isEmpty(){
        return root == null;
    }

    public NodeTree<Table> getRoot(){
        return root;
    }

    public void setRoot(NodeTree<Table> root){
        this.root = root;
    }


    public Table mejorJugada(char simboloMaquina, char simboloOponente){

        Table estadoActual = root.getContent();

        //Validar si la tabla ya esta llena, si el ganador es x o circulo
        if(estadoActual.isFull() || estadoActual.isWinner('o') || estadoActual.isWinner('x')){
            return estadoActual; //retorna el mismo estado
        }

        //Generar primer nivel del arbol
        List<Table> jugadasMaquina = generarSiguientesEstados(estadoActual, simboloMaquina);

        for(Table estadoX : jugadasMaquina){
            //Si algunos de los estados es ganador, el algoritmo acaba para no dejar pasar la jugada ganadora
            if(estadoX.isWinner(simboloMaquina)) return estadoX;

            // Añadir al hijo de la raíz ese estado
            root.addChild(estadoX);
        }

        //Guardar referencias para despues
        Tree<Table> mejorSubArbol = null;
        int maxUtilidadMinima = Integer.MIN_VALUE;

        //Generar el segundo nivel del arbol
        //Iterar sobre la lista de subarboles que contienen cada estado del jugador x
        for(Tree<Table> arbolHijo : root.getChildren()){
            Table tableroX = arbolHijo.getRoot().getContent();

            List<Table> respuestasOponente = generarSiguientesEstados(tableroX, simboloOponente);
            int minUtilidadFamilia = Integer.MAX_VALUE;

            if(respuestasOponente.isEmpty()){
                //Se evalua el hijo directamente si ya no hay mas tableros( ya no hay mas nietos )
                minUtilidadFamilia = calcularUtilidad(tableroX, simboloMaquina, simboloOponente);
            } else {
                for(Table tableroO : respuestasOponente){
                    //Agregamos nieto al arbol hijo
                    arbolHijo.getRoot().addChild(tableroO);

                    int u = calcularUtilidad(tableroO, simboloMaquina, simboloOponente);

                    //Calcular la minima utilidad entre todos los nietos de ese arbol hijo
                    if (u < minUtilidadFamilia) {
                        minUtilidadFamilia = u;
                    }
                }
            }

            if (minUtilidadFamilia > maxUtilidadMinima) {
                maxUtilidadMinima = minUtilidadFamilia;
                mejorSubArbol = arbolHijo;
            }
        }


        // Retorna la tabla almacenada en el mejor subárbol seleccionado
        return (mejorSubArbol != null) ? mejorSubArbol.getRoot().getContent() : estadoActual;

    }

    //Metodo auxiliar que me permite a mi generar los siguientes estados segun el tablero actual
    private List<Table> generarSiguientesEstados(Table t, char jugador){
        List<Table> listaEstados = new ArrayList<>();
        char[][] cuadro = t.getElements();

        for(int i = 0; i<3; i++){
            for(int j = 0; j<3; j++){
                if(cuadro[i][j] == '\u0000'){
                    Table nuevoTablero = new Table(t);
                    nuevoTablero.insert(i, j, jugador);
                    listaEstados.add(nuevoTablero);
                }

            }
        }
        return listaEstados;
    }

    public int calcularUtilidad(Table t, char simboloMaquina, char simboloOponente){
        return contarLineasDisponibles(t, simboloMaquina) - contarLineasDisponibles(t, simboloOponente);
    }

    private int contarLineasDisponibles(Table t, char jugador){
        char oponente = (jugador == 'x') ? 'o' : 'x';
        char[][] cuadro = t.getElements();
        int lineas = 0;

        //Verificar las filas y columnas
        for(int i = 0; i<3; i++){
            if (cuadro[i][0] != oponente && cuadro[i][1] != oponente && cuadro[i][2] != oponente) lineas++;
            if (cuadro[0][i] != oponente && cuadro[1][i] != oponente && cuadro[2][i] != oponente) lineas++;
        }

        //Verificar las diagonales
        if (cuadro[0][0] != oponente && cuadro[1][1] != oponente && cuadro[2][2] != oponente) lineas++;
        if (cuadro[0][2] != oponente && cuadro[1][1] != oponente && cuadro[2][0] != oponente) lineas++;

        return lineas;


    }


}


