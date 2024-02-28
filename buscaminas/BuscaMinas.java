package buscaminas;

import java.util.Random;

public class BuscaMinas {
    private int[][] tablero;
    private boolean[][] casillasDescubiertas;
    private int minas;

    /**
     * Constructor de la clase BuscaMinas que inicializa el tablero y coloca las minas aleatoriamente
     * @param filas
     * @param columnas
     * @param minas
     */
    public BuscaMinas(int filas, int columnas, int minas) {
        this.tablero = new int[filas][columnas];
        this.casillasDescubiertas = new boolean[filas][columnas];
        this.minas = minas;

        colocarMinasAleatorias();
    }

    /**
     * Método para colocar las minas aleatoriamente en el tablero
     */
    private void colocarMinasAleatorias() {
        Random random = new Random();
        int minasColocadas = 0;

        while (minasColocadas < minas) {
            int fila = random.nextInt(tablero.length);
            int columna = random.nextInt(tablero[0].length);

            if (tablero[fila][columna] != -1) {
                tablero[fila][columna] = -1;
                minasColocadas++;
            }
        }
    }

    /**
     * Método para descubrir una casilla y comprobar si hay mina
     * @param fila
     * @param columna
     * @return
     */
    public boolean descubrirCasilla(int fila, int columna) {
        if (casillasDescubiertas[fila][columna]) {
            return false;
        }
    
        casillasDescubiertas[fila][columna] = true;
    
        if (tablero[fila][columna] == -1) {
            return true;
        }
    
        int numMines = 0;
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (fila + i >= 0 && fila + i < tablero.length && columna + j >= 0 && columna + j < tablero[0].length) {
                    if (tablero[fila + i][columna + j] == -1) {
                        numMines++;
                    }
                }
            }
        }
    
        tablero[fila][columna] = numMines;
    
        if (numMines == 0) {
            for (int i = -1; i <= 1; i++) {
                for (int j = -1; j <= 1; j++) {
                    if (fila + i >= 0 && fila + i < tablero.length && columna + j >= 0 && columna + j < tablero[0].length) {
                        descubrirCasilla(fila + i, columna + j);
                    }
                }
            }
        }
    
        return false;
    }
    
    /**
     * Método para comprobar si el jugador ha ganado
     * @return
     */
    public boolean hasGanado() {
        for (int i = 0; i < tablero.length; i++) {
            for (int j = 0; j < tablero[0].length; j++) {
                if (tablero[i][j] != -1 && !casillasDescubiertas[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Método para obtener el estado del tablero y pintar los números con sus colores correspondientes
     * @return
     */
    public String obtenerEstadoTablero() {
        StringBuilder sb = new StringBuilder();
    
        for (int i = 0; i < tablero.length; i++) {
            for (int j = 0; j < tablero[0].length; j++) {
                if (casillasDescubiertas[i][j]) {
                    switch (tablero[i][j]) {
                        case 0:
                            sb.append(tablero[i][j] + " ");
                            break;
                        case 1:
                            sb.append("\033[0;34m" + tablero[i][j] + "\033[0m ");  // Azul
                            break;
                        case 2:
                            sb.append("\033[0;32m" + tablero[i][j] + "\033[0m ");  // Verde
                            break;
                        default:
                            sb.append("\033[0;31m" + tablero[i][j] + "\033[0m ");  // Rojo
                            break;
                    }
                } else {
                    sb.append("\033[0;33mX\033[0m ");  // Amarillo para X
                }
            }
            sb.append("\n");
        }
    
        return sb.toString();
    }

    /**
     * Método para comprobar si el juego ha terminado
     * @return
     */
    public boolean juegoTerminado() {
        for (int i = 0; i < tablero.length; i++) {
            for (int j = 0; j < tablero[0].length; j++) {
                if (tablero[i][j] == 'M' && casillasDescubiertas[i][j]) {
                    return true;
                }
                if (tablero[i][j] != 'M' && !casillasDescubiertas[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }
}
