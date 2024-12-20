import java.awt.*;

/*
    Clase que genera un laberinto usado en el tablero de juego
 */
public class Laberinto {

    // ATRIBUTOS
    //==================================================================================================================
    private int anchoBloque = 35;
    private int altoBloque = 35;

    // ancho panel / ancho bloque y viceversa 700/x = ancho  700/y = alto
    private int numeroColumna = 20;
    private int numeroFila = 20;
    private int fila = 0;
    private int columna = 0;

    // CONSTRUCTORES
    //==================================================================================================================


    // METODOS
    //==================================================================================================================

    // Metodo que pinta el laberinto, usado primordialmente para observar donde se encuentra (no se pintara al final, sera una imagen)
//    public void pintar(Graphics g) {
//        Graphics2D g2d = (Graphics2D) g;
//
//        int[][] laberinto = crearLaberinto(); // obtenemos el laberinto y volcamos en variable
//
//        for (fila = 0; fila < numeroFila; fila++) {
//            for (columna = 0; columna < numeroColumna; columna++) {
//                if (laberinto[fila][columna] == 1) {
//
//                    // pintamos muro
////                    g2d.setColor(Color.GRAY);
////                    g2d.fillRect(columna * anchoBloque, fila * anchoBloque, anchoBloque, altoBloque);
//
////                    g2d.setColor(Color.BLACK);
////                    g2d.drawRect(columna * anchoBloque, fila * anchoBloque, anchoBloque, altoBloque);
////                    g2d.drawImage(brick, columna*40, fila*40, anchoBloque, altoBloque ,null);
//                } else {
////                    g2d.setColor(Color.WHITE);
////                    g2d.fillRect(columna * anchoBloque, fila * anchoBloque, anchoBloque, altoBloque);
////                    g2d.drawImage(grass, columna*40, fila*40, anchoBloque, altoBloque ,null);
//                }
//            }
//        }
//    }

    // Metodo que crea el laberinto
    public int[][] crearLaberinto() {
        int laberinto[][] = {
                {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
                {1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
                {1, 1, 0, 0, 0, 1, 0, 0, 1, 1, 1, 1, 0, 0, 1, 0, 0, 0, 1, 1},
                {1, 0, 0, 1, 0, 1, 0, 0, 1, 0, 0, 1, 0, 0, 1, 0, 1, 0, 0, 1},
                {1, 0, 1, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 1, 0, 1},
                {1, 0, 0, 0, 0, 1, 1, 1, 1, 0, 0, 1, 1, 1, 1, 0, 0, 0, 0, 1},
                {1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1},
                {1, 0, 0, 0, 0, 1, 1, 0, 0, 1, 1, 0, 0, 1, 1, 0, 0, 0, 0, 1},
                {1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1},
                {1, 0, 0, 1, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 1, 0, 0, 1},
                {1, 0, 0, 1, 1, 1, 1, 1, 0, 1, 1, 0, 1, 1, 1, 1, 1, 0, 0, 1},
                {1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1},
                {1, 1, 1, 0, 0, 1, 0, 0, 0, 0, 0, 0, 1, 0, 1, 0, 1, 1, 1, 1},
                {2, 0, 1, 0, 0, 1, 0, 1, 0, 1, 1, 0, 1, 0, 1, 0, 0, 0, 0, 1},
                {1, 0, 1, 0, 0, 1, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 1},
                {1, 0, 1, 1, 1, 1, 0, 1, 0, 1, 1, 0, 1, 0, 1, 1, 1, 0, 0, 1},
                {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 1},
                {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1}
        };
        return laberinto;
    }

    // METODOS GETTERS & SETTERS
    //==================================================================================================================

    public int getAnchoBloque() {
        return anchoBloque;
    }

    public void setAnchoBloque(int anchoBloque) {
        this.anchoBloque = anchoBloque;
    }

    public int getAltoBloque() {
        return altoBloque;
    }

    public void setAltoBloque(int altoBloque) {
        this.altoBloque = altoBloque;
    }

    public int getNumeroColumna() {
        return numeroColumna;
    }

    public void setNumeroColumna(int numeroColumna) {
        this.numeroColumna = numeroColumna;
    }

    public int getNumeroFila() {
        return numeroFila;
    }

    public void setNumeroFila(int numeroFila) {
        this.numeroFila = numeroFila;
    }

    public int getFila() {
        return fila;
    }

    public void setFila(int fila) {
        this.fila = fila;
    }

    public int getColumna() {
        return columna;
    }

    public void setColumna(int columna) {
        this.columna = columna;
    }
}
