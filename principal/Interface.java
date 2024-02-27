package principal;

import buscaminas.BuscaMinas;
import java.util.Scanner;

public class Interface {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int ancho;
        do {
            System.out.println("Introduce el ancho del tablero (debe ser mayor o igual a 3):");
            while (!scanner.hasNextInt()) {
                System.out.println("Por favor, introduce un número entero:");
                scanner.next();
            }
            ancho = scanner.nextInt();
        } while (ancho < 3);

        int alto;
        do {
            System.out.println("Introduce el alto del tablero (debe ser mayor o igual a 3):");
            while (!scanner.hasNextInt()) {
                System.out.println("Por favor, introduce un número entero:");
                scanner.next();
            }
            alto = scanner.nextInt();
        } while (alto < 3);

        int maxMinas = ancho * alto;
        int numMinas;
        do {
            System.out.println("Introduce el número de minas (debe ser un número positivo menor que " + maxMinas + "):");
            while (!scanner.hasNextInt()) {
                System.out.println("Por favor, introduce un número entero:");
                scanner.next();
            }
            numMinas = scanner.nextInt();
        } while (numMinas <= 0 || numMinas >= maxMinas);

        BuscaMinas juego = new BuscaMinas(ancho, alto, numMinas);
        scanner.nextLine();

        while (true) {
            System.out.println("Estado actual del tablero:");

            System.out.println(juego.obtenerEstadoTablero());

            int fila, columna;
            do {
                System.out.println("Introduce las coordenadas de la casilla (fila columna):");
                while (!scanner.hasNextInt()) {
                    System.out.println("Por favor, introduce un números enteros:");
                    scanner.next();
                }
                fila = scanner.nextInt();
                columna = scanner.nextInt();
            } while (fila < 0 || fila >= alto || columna < 0 || columna >= ancho);
            scanner.nextLine();
        
            boolean resultado = juego.descubrirCasilla(fila, columna);
            if (resultado) {
                System.out.println("¡Boom! Has encontrado una mina. Fin del juego.");
                break;
            }
        
            if (juego.hasGanado()) {
                System.out.println("¡Felicidades! Has desbloqueado todo el tablero. Fin del juego.");
                break;
            }
        }

        scanner.close();
    }
}