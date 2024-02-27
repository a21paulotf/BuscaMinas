package buscaminas;

import java.util.Random;

public class BuscaMinas {
    private int[][] tablero;
    private boolean[][] casillasDescubiertas;
    private int minas;

    public BuscaMinas(int filas, int columnas, int minas) {
        this.tablero = new int[filas][columnas];
        this.casillasDescubiertas = new boolean[filas][columnas];
        this.minas = minas;

        colocarMinasAleatorias();
    }

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
