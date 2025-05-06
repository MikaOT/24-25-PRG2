import java.util.Scanner;

class Klondike {

    private Baraja baraja;
    private Palo[] palos;
    private Descarte descarte;
    private Columna[] columnas;

    private final int NUM_PALOS = 4;
    private final int NUM_COLUMNAS = 7;

    public Klondike() {
        baraja = new Baraja();
        descarte = new Descarte();
        palos = new Palo[NUM_PALOS];
        for (int palo = 0; palo < NUM_PALOS; palo++) {
            palos[palo] = new Palo();
        }
        columnas = new Columna[NUM_COLUMNAS];
        for (int columna = 0; columna < NUM_COLUMNAS; columna++) {
            columnas[columna] = new Columna(baraja, columna + 1);
        }
    }

    public void jugar() {
        boolean estaJugando = true;
        do {
            Menu menu = new Menu();
            menu.mostrar();
            mostrarTapete();
            int opcion = menu.getOpcion();
            switch (opcion) {
                case 1:
                    baraja.moverA(descarte);
                    break;
                case 2:
                    descarte.moverA(this.escogerPalo("A"));
                    break;
                case 3:
                    descarte.moverA(this.escogerColumna("A"));
                    break;
                case 4:
                    this.escogerPalo("De").moverA(this.escogerColumna("A"));
                    break;
                case 5:
                    this.escogerColumna("De").moverA(this.escogerPalo("A"));
                    break;
                case 6:
                    this.escogerColumna("De").moverA(this.escogerColumna("A"));
                    break;
                case 7:
                    this.escogerColumna("De").voltear();
                    break;
                case 8:
                    descarte.voltear(baraja);
                    break;
                case 9:
                    estaJugando = !estaJugando;
                    break;
            }
        } while (estaJugando);
    }

    private Palo escogerPalo(String prefijo) {
        int indicePalo = escogerOpcion(prefijo, NUM_PALOS, "qué palo?");
        return palos[indicePalo - 1];
    }

    private Columna escogerColumna(String prefijo) {
        int indiceColumna = escogerOpcion(prefijo, NUM_COLUMNAS, "qué columna?");
        return columnas[indiceColumna - 1];
    }

    private static final Scanner scanner = new Scanner(System.in);

    private int escogerOpcion(String prefijo, int max, String mensaje) {
        Intervalo intervalo = new Intervalo(1, max);
        int opcion = -1;
        boolean error;
        do {
            try {
                System.out.println("¿" + prefijo + " " + mensaje + " [1-" + max + "]?");
                opcion = scanner.nextInt();
                error = !intervalo.incluye(opcion);
                if (error) {
                    System.out.println("Selección inválida. Por favor, elige un número entre 1 y " + max + ".");
                }
            } catch (Exception e) {
                System.out.println("Entrada inválida. Por favor, ingresa un número.");
                scanner.nextLine();
                error = true;
            }
        } while (error);
        return opcion;
    }

    private void mostrarTapete() {
        System.out.println("\n================= ESTADO DEL TABLERO =================");
        System.out.print("Baraja: ");
        baraja.mostrar();
        System.out.print("Descarte: ");
        descarte.mostrar();
        System.out.println("\nPILAS DE PALOS:");
        for (int palo = 0; palo < NUM_PALOS; palo++) {
            System.out.print((palo + 1) + "º ");
            palos[palo].mostrar();
        }
        System.out.println("\nCOLUMNAS:");
        for (int columna = 0; columna < NUM_COLUMNAS; columna++) {
            columnas[columna].mostrar();
        }
        System.out.println("=====================================================\n");
    }

    public static void main(String[] args) {
        new Klondike().jugar();
    }
}